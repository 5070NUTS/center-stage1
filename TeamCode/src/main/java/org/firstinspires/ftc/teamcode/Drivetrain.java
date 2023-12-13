package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
public class Drivetrain {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor viperSlide;
    private DcMotor axle1;
    private DcMotor axle2;
    private Servo leftHanging;
    private Servo rightHanging;
    private Servo pivot;
    private Servo rightClaw;
    private Servo leftClaw;


    IMU imu;
    YawPitchRollAngles ypr;
    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode, HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backRight = hardwareMap.get(DcMotor.class,"backRight");
        axle1 = hardwareMap.get(DcMotor.class,"axle1");
        axle2 = hardwareMap.get(DcMotor.class,"axle2");
        leftHanging = hardwareMap.get(Servo.class, "leftHanging");
        rightHanging = hardwareMap.get(Servo.class, "rightHanging");
        pivot = hardwareMap.get(Servo.class, "pivot");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        viperSlide = hardwareMap.get(DcMotor.class,"viperSlide");



        this.opMode = opMode;

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        viperSlide.setDirection(DcMotor.Direction.REVERSE);
        axle1.setDirection(DcMotor.Direction.REVERSE);
        axle2.setDirection(DcMotor.Direction.REVERSE);

        encoders();
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        axle1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        axle2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        viperSlide.setPower(0);
        axle1.setPower(0);
        axle2.setPower(0);


    }

    public void encoders(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        axle1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        axle2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        axle1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        axle2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void teleop(Gamepad gamepad1, Gamepad gamepad2) {

        rightHanging.setPosition(0.13);

        //driving motion
        double drive = (-1*(gamepad1.left_stick_y));
        double strafe = (gamepad1.left_stick_x);
        double rotate = (gamepad1.right_stick_x);
        double FL = drive + strafe + rotate;
        double FR = drive - strafe - rotate;
        double BL = drive - strafe + rotate;
        double BR = drive + strafe - rotate;
        frontLeft.setPower(FL);
        frontRight.setPower(FR);
        backLeft.setPower(BL);
        backRight.setPower(BR);

        //axle motion
        double moveAxles = gamepad2.right_stick_y;
        axle2.setPower(moveAxles/2);
        axle1.setPower(moveAxles/2);



        //viper slides
       slideVipers(gamepad2.left_stick_y);

       //claw motion
        //open
        if(gamepad2.left_bumper){
            leftClaw.setPosition(0.2);
            rightClaw.setPosition(0.25);
        }
        //close
        if(gamepad2.right_bumper){
            leftClaw.setPosition(0.63);
            rightClaw.setPosition(0.188);
        }

        //pivot
        if(gamepad2.x){
            pivot.setPosition(0);
        }
        if(gamepad2.y){
            pivot.setPosition(0.3);
        }

        //hanging motion
       if(gamepad2.dpad_up){
           leftHanging.setPosition(0.5);
           rightHanging.setPosition(0.5);
           wait(1500);
       }











       // if (gamepad2.y) {

        //launcher
        /*
        if(gamepad2.y){
            launcher.setPosition(0.6);
            wait(200);
            launcher.setPosition(1);
        }
        //door
        if(gamepad2.right_bumper){
            middle.setPosition(0.45);
        }
        if(gamepad2.left_bumper){
            middle.setPosition(0.1);
        }

        if(gamepad2.x){
           //left.setPosition((0.5 - 0.4)/5);
            right.setPosition(0.9);
        }

         */
        /*
//lifting state
        if(gamepad2.a){
            //left.setPosition(0.3);
            right.setPosition(0.15);
            wait(375);
            middle.setPosition(0.35);
            wait(275);
            right.setPosition(0.5);
        }

        if(gamepad2.b){
            //left.setPosition((0.5 - 0.3)/5);
            right.setPosition(0.5);

        }

        if(gamepad2.right_stick_button){
            //left.setPosition((0.5+0.3)/5);
            right.setPosition(1);
        }

         */
//        if(gamepad2.dpad_up){
//            slideVipers(-2400,0.5);
//            wait(800);
//            right.setPosition(0.10);
//            wait(375);
//            middle.setPosition(0.35);
//            wait(375);
//            right.setPosition(0.9);
//
//        }
//        if(gamepad2.dpad_right){
//
//            slideVipers(-3700,0.2);
//            wait(100);
//            right.setPosition(0.15);
//            wait(375);
//            middle.setPosition(0.35);
//
//        }
//        int targetPosition = 0;
//
//        while (Math.abs(gamepad1.left_stick_y) > 0) {
//            targetPosition += gamepad1.left_stick_y * 20;
//            wait(50);
//        }
//        gaySlides(targetPosition, 0.2);

    }
    /*
    public void testTele(Gamepad gamepad1, Gamepad gamepad2){

        opMode.telemetry.addData("door current", middle.getPosition());

        opMode.telemetry.update();
        if(gamepad2.y){
            middle.setPosition(0.5);
        }


    }

     */
    public void drive(int forward, double power){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontLeft.setTargetPosition(forward);
        frontRight.setTargetPosition(forward);
        backLeft.setTargetPosition(forward);
        backRight.setTargetPosition(forward);


        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);


        while(frontLeft.isBusy()){
            opMode.telemetry.addData("frontLeft current", frontLeft.getCurrentPosition());
            opMode.telemetry.addData("frontLeft target", frontLeft.getTargetPosition());

            opMode.telemetry.addData("frontRight current", frontRight.getCurrentPosition());
            opMode.telemetry.addData("frontRight target", frontRight.getTargetPosition());

            opMode.telemetry.addData("backLeft current", backLeft.getCurrentPosition());
            opMode.telemetry.addData("backLeft target", backLeft.getTargetPosition());

            opMode.telemetry.addData("backRight current", backRight.getCurrentPosition());
            opMode.telemetry.addData("backRight target", backRight.getTargetPosition());
            opMode.telemetry.update();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }

    public void strafe(int slide, double power, String direction){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if(direction.equals("left")){
            frontLeft.setTargetPosition(-slide);
            frontRight.setTargetPosition(slide);
            backLeft.setTargetPosition(slide);
            backRight.setTargetPosition(-slide);

        } else if(direction.equals("right")){
            frontLeft.setTargetPosition(slide);
            frontRight.setTargetPosition(-slide);
            backLeft.setTargetPosition(-slide);
            backRight.setTargetPosition(slide);
        }


        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        while(frontLeft.isBusy()){
            opMode.telemetry.addData("frontLeft current", frontLeft.getCurrentPosition());
            opMode.telemetry.addData("frontLeft target", frontLeft.getTargetPosition());

            opMode.telemetry.addData("frontRight current", frontRight.getCurrentPosition());
            opMode.telemetry.addData("frontRight target", frontRight.getTargetPosition());

            opMode.telemetry.addData("backLeft current", backLeft.getCurrentPosition());
            opMode.telemetry.addData("backLeft target", backLeft.getTargetPosition());

            opMode.telemetry.addData("backRight current", backRight.getCurrentPosition());
            opMode.telemetry.addData("backRight target", backRight.getTargetPosition());
            opMode.telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }

    public void turn(double angle, double power, String direction){
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        imu.resetYaw();
        ypr = imu.getRobotYawPitchRollAngles();

        if (direction.equals("left")) { power *= -1; }

        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);

        while (opMode.opModeIsActive() && Math.abs(ypr.getYaw(AngleUnit.DEGREES)) < angle) {
            opMode.telemetry.addData("Current Angle", ypr.getYaw(AngleUnit.DEGREES));
            opMode.telemetry.addData("Target Angle", angle );
            opMode.telemetry.addData("Direction", direction);
            opMode.telemetry.update();

            ypr = imu.getRobotYawPitchRollAngles(); // Keep updating YPR until we reach ideal angle
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        encoders();

    }

   public void slideVipers(int position, double power) {
        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlide.setTargetPosition(position);

        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viperSlide.setPower(power);

        while (viperSlide.isBusy()) {


            opMode.telemetry.addData("viperSlideRight current", viperSlide.getCurrentPosition());
            opMode.telemetry.addData("viperSlideRight target", viperSlide.getTargetPosition());
            opMode.telemetry.update();
        }


        viperSlide.setPower(0);

    }


    public void slideVipers(double power) {
        viperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        viperSlide.setPower(power);
    }

    public void axleMove(double power) {
        viperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        viperSlide.setPower(power);
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


    public void wait(int ms) {
        ElapsedTime timer = new ElapsedTime();
        timer.startTime();
        while (timer.milliseconds() < ms) {
            opMode.telemetry.update();
        }
    }




}
