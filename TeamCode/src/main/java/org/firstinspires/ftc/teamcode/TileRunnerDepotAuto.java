package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

/**
 * Created by Michael Vierra, FTC 8461 on 9/13/2017.
 * Edited by andrewzheng, 10/17/18
 */

/*
Bytes    16-bit word    Description
        ----------------------------------------------------------------
        0, 1     y              sync: 0xaa55=normal object, 0xaa56=color code object
        2, 3     y              checksum (sum of all 16-bit words 2-6, that is, bytes 4-13)
        4, 5     y              signature number
        6, 7     y              x center of object
        8, 9     y              y center of object
        10, 11   y              width of object
        12, 13   y              height of object
        */

public class TileRunnerDepotAuto extends LinearOpMode {
    I2cDeviceSynch pixyCam;

    double x, y, width, height, numObjects;

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
            width = pixyData[3];
            height = pixyData[4];
            numObjects = pixyData[0];

            telemetry.addData("0", 0xff&pixyData[0]);
            telemetry.addData("1", 0xff&pixyData[1]);
            telemetry.addData("2", 0xff&pixyData[2]);
            telemetry.addData("3", 0xff&pixyData[3]);
            telemetry.addData("4", 0xff&pixyData[4]);
            telemetry.addData("Length", pixyData.length);
            telemetry.update();
            sleep (500);
        }
    }
}