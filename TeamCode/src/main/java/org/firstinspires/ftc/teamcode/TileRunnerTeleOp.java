package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
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

    Servo intakeServo;
    Servo collectorServo;
    DcMotor upMotor;
    DcMotor downMotor;
    DcMotor inMotor;
    Servo dispServo;
    DigitalChannel horizontalLimit;
    DigitalChannel verticalLimit;

    @Override public void runOpMode(){

        //INIT HARDWARE

        flDrive = hardwareMap.get(DcMotor.class, "fl_drive");
        frDrive = hardwareMap.get(DcMotor.class, "fr_drive");
        rlDrive = hardwareMap.get(DcMotor.class, "rl_drive");
        rrDrive = hardwareMap.get(DcMotor.class, "rr_drive");
        intakeServo = hardwareMap.get(Servo.class, "intake_servo");
        collectorServo = hardwareMap.get(Servo.class, "collector_servo");
        upMotor = hardwareMap.get(DcMotor.class, "up_motor");
        downMotor = hardwareMap.get(DcMotor.class, "down_motor");
        inMotor = hardwareMap.get(DcMotor.class, "in_motor");
        dispServo = hardwareMap.get(Servo.class, "disp_servo");
        horizontalLimit = hardwareMap.get(DigitalChannel.class, "horizontal_limit");
        verticalLimit = hardwareMap.get(DigitalChannel.class, "vertical_limit");


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

        upMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        downMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        inMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        downMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        inMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        upMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        downMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        inMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        upMotor.setDirection(DcMotor.Direction.FORWARD);
        downMotor.setDirection(DcMotor.Direction.REVERSE);
        inMotor.setDirection(DcMotor.Direction.FORWARD);

        //VARIABLES FOR HARDWARE
        double flDrivePower;
        double frDrivePower;
        double rlDrivePower;
        double rrDrivePower;
        double maxDrivePower;

        double forward; //positive is forward
        double rotate; //positive is clockwise

        double liftPower = 1;
        int currentHPos = 0;
        int currentVUPPos = upMotor.getCurrentPosition();
        int currentVDOWNPos = downMotor.getCurrentPosition();
        boolean isVPositionHolding;
        boolean isHPositionHolding;

        boolean isAccelReleased = false;
        boolean isAccelOn = true;

        boolean isReverseReleased = false;
        boolean isReverseOn = true;

        //boolean isCollectorExtended = false;
        
        waitForStart();

        while (opModeIsActive()){

            //ITERATIVE CODE
            forward = gamepad1.left_stick_y;
            //strafe = gamepad1.right_stick_x;
            rotate = gamepad1.left_stick_x;

            frDrivePower = forward + rotate;
            flDrivePower = forward - rotate;
            rrDrivePower = forward + rotate;
            rlDrivePower = forward - rotate;

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

            else {
                isReverseReleased = false;
            }

            // vertical lift
            if (gamepad2.dpad_down == true && verticalLimit.getState()) {
                upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                upMotor.setPower(-liftPower); //negative value to move up
                downMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                downMotor.setPower(-liftPower); //negative value to move up
                isVPositionHolding = false;
                currentVUPPos = upMotor.getCurrentPosition();
            }
            else if (gamepad2.dpad_up == true) {
                upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                upMotor.setPower(liftPower); //positive value to move down
                downMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                downMotor.setPower(liftPower); //positive value to move down
                isVPositionHolding = false;
                currentVDOWNPos = upMotor.getCurrentPosition();
            }
            else {
                upMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                upMotor.setTargetPosition(currentVUPPos);
                downMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                downMotor.setTargetPosition(currentVDOWNPos);
                isVPositionHolding = true;
            }

            //vertical dispenser
            if (gamepad2.x) {
                dispServo.setPosition(0.18);
            }
            else if (gamepad2.y) {
                dispServo.setPosition(0.9);
            }

            //horizontal intake flip
            if (gamepad2.a){
                //CALIBRATE intakeServo.setPosition(1);
            }
            else if (gamepad2.b) {
                //CALIBRATE intakeServo.setPosition(0);
            }

            //horizontal intake
            if (gamepad2.right_stick_y > 0 && horizontalLimit.getState()) {
                inMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                inMotor.setPower(gamepad2.right_stick_y); //no need to readjust up or down power because using ENCODERS
                isHPositionHolding = false;
                currentHPos = inMotor.getCurrentPosition();
            }
            else if (gamepad2.right_stick_y < 0) {
                inMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                inMotor.setPower(gamepad2.right_stick_y); //no need to readjust up or down power because using ENCODERS
                isHPositionHolding = false;
                currentHPos = inMotor.getCurrentPosition();
            }
            else {
                inMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                inMotor.setTargetPosition(currentHPos);
                isHPositionHolding = true;
            }

            //activate collector only if bumpers are pressed
            if (gamepad2.left_bumper || gamepad2.right_bumper) {
                while (gamepad2.left_bumper) {
                    collectorServo.setPosition(1);
                }
            }
            //stop intake
            else {
                collectorServo.setPosition(0.5);
            }
            //activate collector only if bumpers are pressed
            if (gamepad2.left_bumper || gamepad2.right_bumper) {
                while (gamepad2.right_bumper) {
                    collectorServo.setPosition(0);
                }
            }
            //stop intake
            else {
                collectorServo.setPosition(0.5);
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
            telemetry.addData("Vertical Lift position holding? ", isVPositionHolding);
            telemetry.addData("Horizontal Lift position holding? ", isHPositionHolding);
            telemetry.update();
        }
    }
}



