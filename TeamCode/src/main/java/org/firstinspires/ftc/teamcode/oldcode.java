//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
///**
// * Created by Michael Vierra, FTC 8461 on 9/13/2517.
// * Edited by andrewzheng on 10/28/18
// */
//
//@Autonomous(name="TileRunnerCraterAuto")
//
////@Disabled
//public class TileRunnerCraterAuto extends LinearOpMode {
//
//    //HARDWARE DECLARATION
//    //I2cDeviceSynch pixyCam;
//    DcMotor flDrive;
//    DcMotor frDrive;
//    DcMotor rlDrive;
//    DcMotor rrDrive;
//    DcMotor intakeMotor;
////    CRServo intakeServo;
//
//    double x, y, width, height, numObjects;
//
//    byte[] pixyData;
//
//    int sequence = 0;
//    int count = 0;
//    String mineralPos = "";
//
//
//    @Override
//
//    public void runOpMode() throws InterruptedException {
//
//        //INIT HARDWARE
//
////        pixyCam = hardwareMap.i2cDeviceSynch.get("pixy");
//
//        flDrive = hardwareMap.get(DcMotor.class, "fl_drive");
//        frDrive = hardwareMap.get(DcMotor.class, "fr_drive");
//        rlDrive = hardwareMap.get(DcMotor.class, "rl_drive");
//        rrDrive = hardwareMap.get(DcMotor.class, "rr_drive");
//        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
////        intakeServo = hardwareMap.get(CRServo.class, "intake_servo");
//
//        flDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        flDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        flDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        flDrive.setDirection(DcMotor.Direction.FORWARD);
//        frDrive.setDirection(DcMotor.Direction.REVERSE);
//        rlDrive.setDirection(DcMotor.Direction.FORWARD);
//        rrDrive.setDirection(DcMotor.Direction.REVERSE);
//        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
//
//        waitForStart();
//
//        while (opModeIsActive()){
//            //move forward
//            encoderDrive(1, 10, 10, 10, 10);
//            sleep(20);
//
////            pixyData = pixyCam.read(0x51, 5);
////            numObjects = pixyData[0];
////            x = pixyData[1];
////            y = pixyData[2];
////            width = pixyData[3];
////            height = pixyData[4];
////            //diagonal = Math.sqrt((width*width)+(height*height));
////            sleep(20);
////
////            while(numObjects <= 0 && count <= 10){
////                pixyCam.engage();
////                pixyData = pixyCam.read(0x51, 5);
////                numObjects = pixyData[0];
////                x = pixyData[1];
////                y = pixyData[2];
////                width = pixyData[3];
////                height = pixyData[4];
////                telemetry.addData("checking", 0);
////                telemetry.update();
////                sleep(20);
////                count ++;
////            }
////
////            if(x < 0 && numObjects > 0){
////                mineralPos = "right";
////                telemetry.addData("right", 1);
////                //telemetry.addData("Right", 0);
////                telemetry.update();
////            }else if(x > 0 && numObjects > 0){
////                mineralPos = "center";
////                telemetry.addData("center", 1);
////                //telemetry.addData("Left", 0);
////                telemetry.update();
////
////            }
////            else if(numObjects == 0){
////                mineralPos = "left";
////                telemetry.addData("left", 1);
////                sequence += 1;telemetry.update();
////            }
//
//            sequence += 1;telemetry.update();
//            mineralPos = "left";
//
//            telemetry.addData("Number of objects", numObjects);
//            telemetry.addData("X Value", x);
//            telemetry.addData("Y Value", y);
//            telemetry.addData("Width", width);
//            telemetry.addData("Height", height);
//            telemetry.addData("Length", pixyData.length);
//            telemetry.addData("Sequence", sequence);
//            telemetry.update();
//            sleep(20);
//
//            if (mineralPos == "left" && sequence == 1) {
//                //turn left
//                encoderDrive(.2,-20,20,-20,20);
//                sleep(20);
//                //move forward
//                encoderDrive(.7, 45, 45, 45, 45);
//                sleep(20);
//                //move backward
//                encoderDrive(.7, -45, -45, -45, -45);
//                sleep(20);
//                //turn right
//                encoderDrive(.2,20,-20,20,-20);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//            else if (mineralPos == "center" && sequence == 1) {
//                //move forward
//                encoderDrive(.7, 45, 45, 45, 45);
//                sleep(20);
//                //move backward
//                encoderDrive(.7, -45, -45, -45, -45);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//            else if (mineralPos == "right" && sequence == 1) {
//                //turn right
//                encoderDrive(.2,20,-20,20,-20);
//                sleep(20);
//                //move forward
//                encoderDrive(.7, 45, 45, 45, 45);
//                sleep(20);
//                //move backward
//                encoderDrive(.7, -45, -45, -45, -45);
//                sleep(20);
//                //turn left
//                encoderDrive(.2,-20,20,-20,20);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//
//            if (sequence == 2) {
//                //turn left
//                encoderDrive(.2, -30, 30, -30, 30);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//
//            if (sequence == 3){
//                //move forward
//                encoderDrive(.7, 80, 80, 80, 80);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//
//            if (sequence == 4){
//                //turn left
//                encoderDrive(.2, -36, 36, -36, 36);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//
//            if (sequence == 5){
//                //move forward
//                encoderDrive(.7, 100, 100, 100, 100);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//
//            //eject marker
//            if (sequence == 6) {
//                intakeMotor.setPower(1);
//                sleep(50);
//                intakeMotor.setPower(0);
//                encoderDrive(.7, 75, 75, 75, 75);
//                sleep(20);
//                sequence += 1;telemetry.update();
//                //turn left
//                encoderDrive(.2, -100, 100, -100, 100);
//
//                sequence += 1;telemetry.update();
//            }
//
//            if (sequence == 7){
//                //move forward
//                encoderDrive(.7, 75, 75, 75, 75);
//                sleep(20);
//                sequence += 1;telemetry.update();
//            }
//
//            sleep(100000); //just to wait for timeout
//        }
//    }
//
//    public void encoderDrive(double speed, int flDrivePos, int frDrivePos, int rlDrivePos, int rrDrivePos) {
//        flDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        flDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        flDrivePos = flDrivePos * (1125/((45/35)*(32))) + flDrive.getCurrentPosition();
//        frDrivePos = frDrivePos * (1125/((45/35)*(32))) + frDrive.getCurrentPosition();
//        rlDrivePos = rlDrivePos * (1125/((45/35)*(32))) + rlDrive.getCurrentPosition();
//        rrDrivePos = rrDrivePos * (1125/((45/35)*(32))) + rrDrive.getCurrentPosition();
//
//        flDrive.setTargetPosition(flDrivePos);
//        frDrive.setTargetPosition(frDrivePos);
//        rlDrive.setTargetPosition(rlDrivePos);
//        rrDrive.setTargetPosition(rrDrivePos);
//
//        flDrive.setPower(speed);
//        frDrive.setPower(speed);
//        rlDrive.setPower(speed);
//        rrDrive.setPower(speed);
//
//        while (flDrive.isBusy() || frDrive.isBusy() || rlDrive.isBusy() || rrDrive.isBusy()){
//
//        }
//
//        flDrive.setPower(0);
//        frDrive.setPower(0);
//        rlDrive.setPower(0);
//        rrDrive.setPower(0);
//
//        flDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//    }
//}