package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by andrewzheng on 9/22/18.
 */

@TeleOp(name="TileRunnerTeleOp")
//@Disabled

public class TileRunnerTeleOp extends LinearOpMode {

    //HARDWARE DECLARATION

    DcMotor flDrive;
    DcMotor frDrive;
    DcMotor rlDrive;
    DcMotor rrDrive;

    DcMotor intakeMotor;
    Servo inServo;
    DcMotor upMotor;
    DcMotor downMotor;
    Servo dispServo;

    @Override public void runOpMode(){

        //INIT HARDWARE

        flDrive = hardwareMap.get(DcMotor.class, "fl_drive");
        frDrive = hardwareMap.get(DcMotor.class, "fr_drive");
        rlDrive = hardwareMap.get(DcMotor.class, "rl_drive");
        rrDrive = hardwareMap.get(DcMotor.class, "rr_drive");

        intakeMotor = hardwareMap.get(DcMotor.class, "in_motor");
        inServo = hardwareMap.get(Servo.class, "in_servo");
        upMotor = hardwareMap.get(DcMotor.class, "up_motor");
        downMotor = hardwareMap.get(DcMotor.class, "down_motor");
        dispServo = hardwareMap.get(Servo.class, "disp_servo");


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

        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        upMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        downMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        downMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        downMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        upMotor.setDirection(DcMotor.Direction.FORWARD);
        downMotor.setDirection(DcMotor.Direction.FORWARD);

        //VARIABLES FOR HARDWARE
        double flDrivePower;
        double frDrivePower;
        double rlDrivePower;
        double rrDrivePower;
        double maxDrivePower;

        double forward; //positive is forward
        double strafe; //positive is right
        double rotate; //positive is clockwise

        double intakeeMotorPower = 0.5;
        double liftPower = 0.5;
        int currentPos = 0;
        boolean isPositionHolding;

        boolean isAccelReleased = false;
        boolean isAccelOn = true;


        
        waitForStart();

        while (opModeIsActive()){
            //ITERATIVE CODE
            forward = gamepad1.left_stick_y;
            strafe = gamepad1.right_stick_x;
            rotate = gamepad1.left_stick_x;

            frDrivePower = forward - strafe	+ rotate;
            flDrivePower = forward + strafe	- rotate;
            rrDrivePower = forward + strafe	+ rotate;
            rlDrivePower = forward - strafe	- rotate;

            //normalize mecanum drive
            maxDrivePower = Math.abs(flDrivePower);
            if (Math.abs(frDrivePower) > maxDrivePower){
                maxDrivePower = Math.abs(frDrivePower);
            }
            if (Math.abs(rlDrivePower) > maxDrivePower){
                maxDrivePower = Math.abs(rlDrivePower);
            }
            if (Math.abs(rrDrivePower) > maxDrivePower){
                maxDrivePower = Math.abs(rrDrivePower);
            }
            if (maxDrivePower > 1) {
                flDrivePower /= maxDrivePower;
                frDrivePower /= maxDrivePower;
                rlDrivePower /= maxDrivePower;
                rrDrivePower /= maxDrivePower;
            }

            //turbo mode
            if (gamepad1.a) {
                if (isAccelReleased) {
                    isAccelReleased = false;
                    if (isAccelOn){
                        isAccelOn = false;
                    }
                    else{
                        isAccelOn = true;
                    }

                }
            }

            else {
                isAccelReleased = true;
            }

            if (isAccelOn){
                flDrivePower *= 0.6;
                frDrivePower *= 0.6;
                rlDrivePower *= 0.6;
                rrDrivePower *= 0.6;
            }

            if (gamepad2.right_stick_y > 0) {
                upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                upMotor.setPower(liftPower); //no need to readjust up or down power because using ENCODERS
                downMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                downMotor.setPower(liftPower);
                isPositionHolding = false;
                currentPos = upMotor.getCurrentPosition();
            }

            else if (gamepad2.right_stick_y < 0) {
                upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                upMotor.setPower(-liftPower); //negative value to move down
                downMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                downMotor.setPower(-liftPower); //negative value to move down
                isPositionHolding = false;
                currentPos = upMotor.getCurrentPosition();
            }

            else {
                upMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                upMotor.setTargetPosition(currentPos);
                upMotor.setPower(liftPower);
                isPositionHolding = true;
            }

            //set drive power
            flDrive.setPower(flDrivePower);
            frDrive.setPower(frDrivePower);
            rlDrive.setPower(rlDrivePower);
            rrDrive.setPower(rrDrivePower);

            //update telemetry
            telemetry.addData("flDrivePower: ", flDrivePower);
            telemetry.addData("frDrivePower: ", frDrivePower);
            telemetry.addData("rlDrivePower: ", rlDrivePower);
            telemetry.addData("rrDrivePower: ", rrDrivePower);
            telemetry.addData("Lift position holding? ", isPositionHolding);
            telemetry.update();

        }
    }
}



