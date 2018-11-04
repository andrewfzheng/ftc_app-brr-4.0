package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

@Autonomous(name="PixyTest")

public class PixyTest extends LinearOpMode {
        I2cDeviceSynch pixyCam;

        double x, y, width, height, numObjs, diagonal;

        byte[] pixyData;



        @Override
        public void runOpMode() throws InterruptedException {

            pixyCam = hardwareMap.i2cDeviceSynch.get("pixy");

            waitForStart();



            while(opModeIsActive()){
                pixyCam.engage();
                pixyData = pixyCam.read(0x51, 5);

                x = pixyData[1];
                y = pixyData[2];
                numObjs = pixyData[0];
                width = pixyData[3];
                height = pixyData[4];
                //diagonal = Math.sqrt((width*width)+(height*height));

                if(x < 0 && numObjs > 0 && width == 0){
                    telemetry.addData("Center", 1);
                }else if(x > 0 && numObjs > 0 && width == 0){
                    telemetry.addData("Right", 1);
                } else if(numObjs==0 && x==0 && x == 0){
                    telemetry.addData("Left", 1);
                }

                telemetry.addData("Number of objects", pixyData[0]);
                telemetry.addData("X Value", pixyData[1]);
                telemetry.addData("Y Value", pixyData[2]);
                telemetry.addData("Width", pixyData[3]);
                telemetry.addData("Height", pixyData[4]);
                telemetry.addData("Length", pixyData.length);
                telemetry.update();
                sleep(200);
            }
        }
    }
