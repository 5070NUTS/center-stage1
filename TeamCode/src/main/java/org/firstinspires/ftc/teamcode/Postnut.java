package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class Postnut extends LinearOpMode {
    public void runOpMode(){
        Drivetrain drivetrain = new Drivetrain(this);
        Mechanisms mech = new Mechanisms(this);

        waitForStart();

        drivetrain.initDrivetrain(hardwareMap);
        mech.initMechanisms(hardwareMap);
        mech.initEncoders();

        double pivotPos = 0;

        double speedScale = 0.8;

        while (opModeIsActive()) {
            mech.rightHanging.setPosition(0.13);

            //driving motion
            double drive = (-1 * (gamepad1.left_stick_y));
            double strafe = (gamepad1.left_stick_x);
            double rotate = (gamepad1.right_stick_x);
            double FL = speedScale * (drive + strafe + rotate);
            double FR = speedScale * (drive - strafe - rotate);
            double BL = speedScale * (drive - strafe + rotate);
            double BR = speedScale * (drive + strafe - rotate);

            drivetrain.frontLeft.setPower(FL);
            drivetrain.frontRight.setPower(FR);
            drivetrain.backLeft.setPower(BL);
            drivetrain.backRight.setPower(BR);

            //axle motion

            if (gamepad2.dpad_left) {
                mech.rotateAxle("up");
            } else if (gamepad2.dpad_right) {
                mech.rotateAxle("down");
            }
            telemetry.addData("axle current", mech.axle1.getCurrentPosition());

            // slide motion
            telemetry.addData("viper current", mech.viperSlide.getCurrentPosition());
            if (gamepad2.dpad_up) {
                mech.extendSlide("up");
            } else if (gamepad2.dpad_down) {
                mech.extendSlide("down");
            }
            telemetry.update();

            // rotate pivot servo
            if (gamepad2.left_trigger > 0) {
                mech.setPivotScore();
                pivotPos = mech.pivotScore;
            }
            if (gamepad2.right_trigger > 0) {
                mech.setPivotGrab();
                pivotPos = mech.pivotGrab;
            }
            if (gamepad1.x) {
                pivotPos -= 0.05;
                mech.pivot.setPosition(pivotPos);
            }
            if (gamepad1.b) {
                pivotPos += 0.05;
                mech.pivot.setPosition(pivotPos);
            }

            //claw motion
            //open
            if (gamepad2.left_bumper) {
                mech.openClaws();
            }
            //close
            if (gamepad2.right_bumper) {
                mech.closeClaws();
            }


            //hanging motion
            if (gamepad2.right_stick_button) {
                mech.releaseHooks();
            }
        }
    }
}
