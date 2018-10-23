package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

/**
 * Created by Michael Vierra, FTC 8461 on 9/13/2017.
 * Edited by andrewzheng on 10/20/18
 */

@Autonomous(name="TileRunnerDepotAuto")

//@Disabled
public class TileRunnerDepotAuto extends LinearOpMode {

    //HARDWARE DECLARATION

    I2cDeviceSynch pixyCam;
    DcMotor flDrive;
    DcMotor frDrive;
    DcMotor rlDrive;
    DcMotor rrDrive;
    CRServo inServo;

    double x, y, width, height, numObjects;

    byte[] pixyData;

    int sequence = 0;
    String mineralPos = "";


    @Override

    public void runOpMode() throws InterruptedException {

        //INIT HARDWARE

        pixyCam = hardwareMap.i2cDeviceSynch.get("pixy");

        flDrive = hardwareMap.get(DcMotor.class, "fl_drive");
        frDrive = hardwareMap.get(DcMotor.class, "fr_drive");
        rlDrive = hardwareMap.get(DcMotor.class, "rl_drive");
        rrDrive = hardwareMap.get(DcMotor.class, "rr_drive");

        flDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        flDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flDrive.setDirection(DcMotor.Direction.REVERSE);
        frDrive.setDirection(DcMotor.Direction.FORWARD);
        rlDrive.setDirection(DcMotor.Direction.REVERSE);
        rrDrive.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()){

            pixyCam.engage();
            pixyData = pixyCam.read(0x51, 5);

            x = pixyData[1];
            y = pixyData[2];
            width = pixyData[3];
            height = pixyData[4];
            numObjects = pixyData[0];

            if (x < 80 && sequence == 0){
                mineralPos = "left";
                sequence += 1;
            }
            else if (80 < x && x < 160 && sequence == 0) {
                mineralPos = "center";
                sequence += 1;
            }
            else if (80 < x && x < 160 && sequence == 0) {
                mineralPos = "right";
                sequence += 1;
            }

            telemetry.addData("mineralPos", mineralPos);
            telemetry.addData("0", numObjects);
            telemetry.addData("1", x);
            telemetry.addData("2", y);
            telemetry.addData("3", width);
            telemetry.addData("4", height);
            telemetry.addData("Length", pixyData.length);
            telemetry.update();
            sleep(400);

            if (mineralPos == "left" && sequence == 1) {
                //turn left
                encoderDrive(.2,-10,10,-10,10);
                sleep(400);
                //move forward
                encoderDrive(1, 30, 30, 30, 30);
                sleep(400);
                //move backward
                encoderDrive(1, -30, -30, -30, -30);
                sleep(400);
                //turn right
                encoderDrive(.2,10,-10,10,-10);
                sleep(400);
                sequence += 1;
            }
            else if (mineralPos == "center" && sequence == 1) {
                //move forward
                encoderDrive(1, 30, 30, 30, 30);
                sleep(400);
                //move backward
                encoderDrive(1, -30, -30, -30, -30);
                sleep(400);
                sequence += 1;
            }
            else if (mineralPos == "right" && sequence == 1) {
                //turn right
                encoderDrive(.2,10,-10,10,-10);
                sleep(400);
                //move forward
                encoderDrive(1, 30, 30, 30, 30);
                sleep(400);
                //move backward
                encoderDrive(1, -30, -30, -30, -30);
                sleep(400);
                //turn left
                encoderDrive(.2,-10,10,-10,10);
                sleep(400);
                sequence += 1;
            }
            else {
                sequence += 1;
            }

            if (sequence == 2) {
                //turn left
                encoderDrive(.2, -20, 20, -20, 20);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 3){
                //move forward
                encoderDrive(1, 30, 30, 30, 30);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 4){
                //turn right
                encoderDrive(.2, 20, -20, 20, -20);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 5){
                //move forward
                encoderDrive(1, 40, 40, 40, 40);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 6) {
                inServo.setPower(-1);
            }

            if (sequence == 7){
                //move backward
                encoderDrive(1, -40, -40, -40, -40);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 8) {
                //turn left
                encoderDrive(.2, -20, 20, -20, 20);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 9){
                //move backward
                encoderDrive(1, -80, -80, -80, -80);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 10){
                //turn right
                encoderDrive(.2, 20, -20, 20, -20);
                sleep(400);
                sequence += 1;
            }

            if (sequence == 11){
                //move backward
                encoderDrive(1, -50, -50, -50, -50);
                sleep(400);
                sequence += 1;
            }

            sleep(100000); //just to wait for timeout
        }
    }

    public void encoderDrive(double speed, int flDrivePos, int frDrivePos, int rlDrivePos, int rrDrivePos) {
        flDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        flDrivePos = flDrivePos * (1120/((45/35)*(32))) + flDrive.getCurrentPosition();
        frDrivePos = frDrivePos * (1120/((45/35)*(32))) + frDrive.getCurrentPosition();
        rlDrivePos = rlDrivePos * (1120/((45/35)*(32))) + rlDrive.getCurrentPosition();
        rrDrivePos = rrDrivePos * (1120/((45/35)*(32))) + rrDrive.getCurrentPosition();

        flDrive.setTargetPosition(flDrivePos);
        frDrive.setTargetPosition(frDrivePos);
        rlDrive.setTargetPosition(rlDrivePos);
        rrDrive.setTargetPosition(rrDrivePos);

        flDrive.setPower(speed);
        frDrive.setPower(speed);
        rlDrive.setPower(speed);
        rrDrive.setPower(speed);

        while (flDrive.isBusy() || frDrive.isBusy() || rlDrive.isBusy() || rrDrive.isBusy()){

        }

        flDrive.setPower(0);
        frDrive.setPower(0);
        rlDrive.setPower(0);
        rrDrive.setPower(0);

        flDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}