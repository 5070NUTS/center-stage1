package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class Prenut2 extends LinearOpMode{
    public void runOpMode() {
        Drivetrain hazel = new Drivetrain(this, hardwareMap);


        waitForStart();
        while (opModeIsActive()) {
            hazel.strafe(950,0.2, "left");
            hazel.drive(-1175,0.2);
            hazel.wait(1000);
            hazel.autoScore(-2500,0.5,0.08,0.35);
            hazel.wait(650);
            hazel.drive(50,0.2);
            hazel.autoSet(0.9);
            hazel.strafe(800,0.2, "right");
            hazel.drive(-250,0.2);

            break;




            //hazel.drive(1170,0.5);
            //break;

        }
    }
}
