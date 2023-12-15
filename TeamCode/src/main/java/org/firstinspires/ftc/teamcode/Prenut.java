package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class Prenut extends LinearOpMode{
    public void runOpMode() {
        Drivetrain hazel = new Drivetrain(this);
        Mechanisms mech = new Mechanisms(this);

        hazel.initDrivetrain(hardwareMap);
        mech.initMechanisms(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            hazel.drive(-1920,0.8);
        }




        }


    }

