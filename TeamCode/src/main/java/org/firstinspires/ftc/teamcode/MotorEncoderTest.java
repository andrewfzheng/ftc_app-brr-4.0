package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by johnduval on 11/21/17.
 * Edited by andrewzheng on 10/17/18.
 */

@Autonomous(name="MotorEncoderTest")

public class MotorEncoderTest extends LinearOpMode {
    DcMotor flDrive;
    DcMotor frDrive;
    DcMotor rlDrive;
    DcMotor rrDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        flDrive = hardwareMap.dcMotor.get("fl_drive");
        frDrive = hardwareMap.dcMotor.get("fr_drive");
        rlDrive = hardwareMap.dcMotor.get("rl_drive");
        rrDrive = hardwareMap.dcMotor.get("rr_drive");

        flDrive.setDirection(DcMotor.Direction.REVERSE);
        rlDrive.setDirection(DcMotor.Direction.REVERSE);

        flDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        flDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            flDrive.setPower(-.5);
            frDrive.setPower(.5);
            rlDrive.setPower(.5);
            rrDrive.setPower(-.5);
            sleep(2000);
            flDrive.setPower(0);
            frDrive.setPower(0);
            rlDrive.setPower(0);
            rrDrive.setPower(0);
            telemetry.addData("front left: ", flDrive.getCurrentPosition());
            telemetry.addData("front right: ", frDrive.getCurrentPosition());
            telemetry.addData("rear left: ", rlDrive.getCurrentPosition());
            telemetry.addData("rear right: ", rrDrive.getCurrentPosition());
            telemetry.update();
            sleep(5000);
            flDrive.setPower(.5);
            frDrive.setPower(-.5);
            rlDrive.setPower(-.5);
            rrDrive.setPower(.5);
            sleep(2000);
            flDrive.setPower(0);
            frDrive.setPower(0);
            rlDrive.setPower(0);
            rrDrive.setPower(0);
            telemetry.addData("front left: ", flDrive.getCurrentPosition());
            telemetry.addData("front right: ", frDrive.getCurrentPosition());
            telemetry.addData("rear left: ", rlDrive.getCurrentPosition());
            telemetry.addData("rear right: ", rrDrive.getCurrentPosition());
            telemetry.update();
            sleep(10000);
            requestOpModeStop();
        }
    }
}