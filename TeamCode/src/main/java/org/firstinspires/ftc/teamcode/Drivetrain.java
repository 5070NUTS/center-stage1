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
    private DcMotor viperSlideLeft;
    private DcMotor viperSlideRight;
    private DcMotor intake;
    private Servo launcher;
    //private Servo left;
    private Servo middle;
    private Servo right;
    //private Servo door;

    IMU imu;
    YawPitchRollAngles ypr;
    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode, HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backRight = hardwareMap.get(DcMotor.class,"backRight");
        intake = hardwareMap.get(DcMotor.class,"intake");
        launcher = hardwareMap.get(Servo.class, "launcher");
        //left = hardwareMap.get(Servo.class, "left");
        middle = hardwareMap.get(Servo.class, "middle");
        right = hardwareMap.get(Servo.class, "right");
        //door = hardwareMap.get(Servo.class, "door");
        viperSlideLeft = hardwareMap.get(DcMotor.class,"viperSlideLeft");
        viperSlideRight = hardwareMap.get(DcMotor.class,"viperSlideRight");



        this.opMode = opMode;

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        viperSlideLeft.setDirection(DcMotor.Direction.REVERSE);
        viperSlideRight.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.FORWARD);

        encoders();
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        viperSlideLeft.setPower(0);
        viperSlideRight.setPower(0);
        intake.setPower(0);


    }

    public void encoders(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viperSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void teleop(Gamepad gamepad1, Gamepad gamepad2) {
        launcher.setPosition(1);

        //gamepad 1
        //drivings
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



        //viper slides
        slideVipers(gamepad2.left_stick_y);

        if(viperSlideLeft.getCurrentPosition() == 400){
            right.setPosition(0.5);
            wait(90);
            right.setPosition(0.45);
        }

        //intake

        double pull = (gamepad2.left_trigger);
        double eject = (-gamepad2.right_trigger);
        intake.setPower(pull + eject);

        while(gamepad2.right_trigger > 0.8){
            right.setPosition(0.45);
        }


       // if (gamepad2.y) {

        //launcher
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
    public void testTele(Gamepad gamepad1, Gamepad gamepad2){

        opMode.telemetry.addData("door current", middle.getPosition());

        opMode.telemetry.update();
        if(gamepad2.y){
            middle.setPosition(0.5);
        }


    }
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
        viperSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        viperSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlideLeft.setTargetPosition(position);
        viperSlideRight.setTargetPosition(position);

        viperSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viperSlideLeft.setPower(power);
        viperSlideRight.setPower(power);

        while (viperSlideLeft.isBusy()) {
            opMode.telemetry.addData("viperSlideLeft current", viperSlideLeft.getCurrentPosition());
            opMode.telemetry.addData("viperSlideLeft target", viperSlideLeft.getTargetPosition());

            opMode.telemetry.addData("viperSlideRight current", viperSlideRight.getCurrentPosition());
            opMode.telemetry.addData("viperSlideRight target", viperSlideRight.getTargetPosition());
            opMode.telemetry.update();
        }

        viperSlideLeft.setPower(0);
        viperSlideRight.setPower(0);

    }

    public void gaySlides(int targetPositions, double power) {
        viperSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        viperSlideLeft.setTargetPosition(targetPositions);
        viperSlideRight.setTargetPosition(targetPositions);

        viperSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        viperSlideLeft.setPower(power);
        viperSlideRight.setPower(power);

        while (viperSlideLeft.isBusy()) {
            opMode.telemetry.addData("viperSlideLeft current", viperSlideLeft.getCurrentPosition());
            opMode.telemetry.addData("viperSlideLeft target", viperSlideLeft.getTargetPosition());

            opMode.telemetry.addData("viperSlideRight current", viperSlideRight.getCurrentPosition());
            opMode.telemetry.addData("viperSlideRight target", viperSlideRight.getTargetPosition());
            opMode.telemetry.update();
        }

        viperSlideLeft.setPower(0);
        viperSlideRight.setPower(0);
    }

    public void slideVipers(double power) {
        viperSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        viperSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        viperSlideLeft.setPower(power);
        viperSlideRight.setPower(power);
    }

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

    public void autoSet(double pos){
        right.setPosition(pos);

    }
    public void wait(int ms) {
        ElapsedTime timer = new ElapsedTime();
        timer.startTime();
        while (timer.milliseconds() < ms) {
            opMode.telemetry.update();
        }
    }


}
