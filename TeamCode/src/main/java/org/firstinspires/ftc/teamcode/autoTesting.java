package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class autoTesting extends LinearOpMode {
    public void runOpMode() {



        waitForStart();
        Drivetrain drivetrain = new Drivetrain(this);
        Mechanisms mechanisms = new Mechanisms(this);
        drivetrain.initDrivetrain(hardwareMap);
        mechanisms.initMechanisms(hardwareMap);
        while (opModeIsActive()) {


            telemetry.addData("frontLeft current", drivetrain.frontLeft.getCurrentPosition());
            //telemetry.addData("frontLeft target", frontLeft.getTargetPosition());

            telemetry.addData("frontRight current", drivetrain.frontRight.getCurrentPosition());
            //telemetry.addData("frontRight target", frontRight.getTargetPosition());

            telemetry.addData("backLeft current", drivetrain.backLeft.getCurrentPosition());
            //telemetry.addData("backLeft target", backLeft.getTargetPosition());

            telemetry.addData("backRight current", drivetrain.backRight.getCurrentPosition());
            //telemetry.addData("backRight target", backRight.getTargetPosition());

            telemetry.addData("viperslide current", mechanisms.viperSlide.getCurrentPosition());
            telemetry.update();




            //hazel.drive(1170,0.5);
            //break;

        }
    }
}
