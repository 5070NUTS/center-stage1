package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Mechanisms {
    public DcMotor viperSlide;
    public DcMotor axle1;
    public DcMotor axle2;


    public Servo leftHanging;
    public Servo rightHanging;
    public Servo pivot;
    public double pivotGrab = 0.37;
    public double pivotScore = 0.2;

    public Servo rightClaw;
    public double rightClose = 0.188;
    public double rightOpen = 0.25;

    public Servo leftClaw;
    public double leftClose = 0.63;
    public double leftOpen = 0.2;

    double slideSpeed = 0.6;
    int maxPosition = 4200;
    LinearOpMode opMode;

    public Mechanisms(LinearOpMode op){
        opMode = op;
    }

    public void openClaws() {
        leftClaw.setPosition(leftOpen);
        rightClaw.setPosition(rightOpen);
    }

    public void closeClaws() {
        leftClaw.setPosition(leftClose);
        rightClaw.setPosition(rightClose);
    }

    public void setPivotGrab() {
        pivot.setPosition(pivotGrab);
    }

    public void setPivotScore() {
        pivot.setPosition(pivotScore);
    }

    public void releaseHooks() {
        leftHanging.setPosition(0.5);
        rightHanging.setPosition(0.5);
    }

    //    public void teleop(Gamepad gamepad1, Gamepad gamepad2) {
//
//        rightHanging.setPosition(0.13);
//
//        //driving motion
//        double drive = (-1*(gamepad1.left_stick_y));
//        double strafe = (gamepad1.left_stick_x);
//        double rotate = (gamepad1.right_stick_x);
//        double FL = drive + strafe + rotate;
//        double FR = drive - strafe - rotate;
//        double BL = drive - strafe + rotate;
//        double BR = drive + strafe - rotate;
//
//        //axle motion
//        double moveAxles = gamepad2.right_stick_y;
//        axle2.setPower(moveAxles/2);
//        axle1.setPower(moveAxles/2);
//
//
//
//        //viper slides
//       slideVipers(gamepad2.left_stick_y);
//
//       //claw motion
//        //open
//        if(gamepad2.left_bumper){
//            leftClaw.setPosition(0.2);
//            rightClaw.setPosition(0.25);
//        }
//        //close
//        if(gamepad2.right_bumper){
//            leftClaw.setPosition(0.63);
//            rightClaw.setPosition(0.188);
//        }
//
//        //pivot
//        if(gamepad2.x){
//            pivot.setPosition(0);
//        }
//        if(gamepad2.y){
//            pivot.setPosition(0.3);
//        }
//
//        //hanging motion
//       if(gamepad2.dpad_up){
//           leftHanging.setPosition(0.5);
//           rightHanging.setPosition(0.5);
//           wait(1500);
//       }
//
//        //axle to positions
//
//
//
//
//
//
//
//
//
//
//       // if (gamepad2.y) {
//
//        //launcher
//        /*
//        if(gamepad2.y){
//            launcher.setPosition(0.6);
//            wait(200);
//            launcher.setPosition(1);
//        }
//        //door
//        if(gamepad2.right_bumper){
//            middle.setPosition(0.45);
//        }
//        if(gamepad2.left_bumper){
//            middle.setPosition(0.1);
//        }
//
//        if(gamepad2.x){
//           //left.setPosition((0.5 - 0.4)/5);
//            right.setPosition(0.9);
//        }
//
//         */
//        /*
////lifting state
//        if(gamepad2.a){
//            //left.setPosition(0.3);
//            right.setPosition(0.15);
//            wait(375);
//            middle.setPosition(0.35);
//            wait(275);
//            right.setPosition(0.5);
//        }
//
//        if(gamepad2.b){
//            //left.setPosition((0.5 - 0.3)/5);
//            right.setPosition(0.5);
//
//        }
//
//        if(gamepad2.right_stick_button){
//            //left.setPosition((0.5+0.3)/5);
//            right.setPosition(1);
//        }
//
//         */
////        if(gamepad2.dpad_up){
////            slideVipers(-2400,0.5);
////            wait(800);
////            right.setPosition(0.10);
////            wait(375);
////            middle.setPosition(0.35);
////            wait(375);
////            right.setPosition(0.9);
////
////        }
////        if(gamepad2.dpad_right){
////
////            slideVipers(-3700,0.2);
////            wait(100);
////            right.setPosition(0.15);
////            wait(375);
////            middle.setPosition(0.35);
////
////        }
////        int targetPosition = 0;
////
////        while (Math.abs(gamepad1.left_stick_y) > 0) {
////            targetPosition += gamepad1.left_stick_y * 20;
////            wait(50);
////        }
////        gaySlides(targetPosition, 0.2);
//
//    }
    public void slideVipers(int position, double power, double runtimeMilli) {
        ElapsedTime runtimeTimer = new ElapsedTime();
        runtimeTimer.startTime();

        viperSlide.setTargetPosition(position);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(power);

        while (viperSlide.isBusy() && runtimeTimer.milliseconds() < runtimeMilli) {
            opMode.telemetry.addData("viperSlideRight current", viperSlide.getCurrentPosition());
            opMode.telemetry.addData("viperSlideRight target", viperSlide.getTargetPosition());
            opMode.telemetry.update();
        }

        viperSlide.setPower(0);

    }
    public void rotateAxle(String direction) {
        int pos1 = axle1.getCurrentPosition();
        int pos2 = axle2.getCurrentPosition();

        if(direction.equals("up") && pos1 <= maxPosition && pos2 <= maxPosition) {
            pos1 += 150;
            pos2 += 150;
        }
        else if(direction.equals("down") && pos1 <= maxPosition && pos2 <= maxPosition) {
            pos1 -= 150;
            pos2 -= 150;
        }

        axle1.setTargetPosition(pos1);
        axle1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        axle1.setPower(slideSpeed);

        axle2.setTargetPosition(pos2);
        axle2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        axle2.setPower(slideSpeed);
    }

    public void extendSlide(String direction) {
        int pos1 = viperSlide.getCurrentPosition();

        if(direction.equals("up") && pos1 <= maxPosition) {
            pos1 += 100;
        }
        else if(direction.equals("down") && pos1 <= maxPosition) {
            pos1 -= 100;

        }

        viperSlide.setTargetPosition(pos1);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(slideSpeed);
    }

    /*
    public void autoScore(int position, double power, double rightPos, double midPos) {
        viperSlideLeft.setTargetPosition(position);
        viperSlideRight.setTargetPosition(position);

        viperSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viperSlideLeft.setPower(power);
        viperSlideRight.setPower(power);

        wait(2000);
        right.setPosition(rightPos);
        wait(950);
        middle.setPosition(midPos);
        wait(800);
        right.setPosition(0.5);


        while (viperSlideLeft.isBusy()) {
            opMode.telemetry.addData("viperSlideLeft current", viperSlideLeft.getCurrentPosition());
            opMode.telemetry.addData("viperSlideLeft target", viperSlideLeft.getTargetPosition());

            opMode.telemetry.addData("viperSlideRight current", viperSlideRight.getCurrentPosition());
            opMode.telemetry.addData("viperSlideRight target", viperSlideRight.getTargetPosition());
            opMode.telemetry.update();
            break;
        }

    }
*/

    public void initMechanisms(HardwareMap hwMap){
        axle1 = hwMap.get(DcMotor.class,"axle1");
        axle2 = hwMap.get(DcMotor.class,"axle2");
        viperSlide = hwMap.get(DcMotor.class,"viperSlide");

        leftHanging = hwMap.get(Servo.class, "leftHanging");
        rightHanging = hwMap.get(Servo.class, "rightHanging");
        pivot = hwMap.get(Servo.class, "pivot");
        rightClaw = hwMap.get(Servo.class, "rightClaw");
        leftClaw = hwMap.get(Servo.class, "leftClaw");

        viperSlide.setDirection(DcMotor.Direction.FORWARD);
        axle1.setDirection(DcMotor.Direction.REVERSE);
        axle2.setDirection(DcMotor.Direction.REVERSE);

        axle1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        axle2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        axle1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        axle2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        axle1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        axle2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        viperSlide.setPower(0);
        axle1.setPower(0);
        axle2.setPower(0);
    }

    public void initEncoders(){
        axle1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        axle2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        axle1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        axle2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void wait(int ms) {
        ElapsedTime timer = new ElapsedTime();
        timer.startTime();
        while (timer.milliseconds() < ms) {
            opMode.telemetry.update();
        }
    }
}
