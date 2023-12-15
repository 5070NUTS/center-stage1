package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class Prenut2 extends LinearOpMode{
    public void runOpMode() {
        Drivetrain hazel = new Drivetrain(this);
        Mechanisms mech = new Mechanisms(this);
        CenterstagePipeline pipeline = new CenterstagePipeline(this, hardwareMap);

        pipeline.initializeCamera();
        hazel.initDrivetrain(hardwareMap);
        mech.initMechanisms(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Region1 Brightness", pipeline.getRegion1Y());
            telemetry.addData("Region2 Brightness", pipeline.getRegion2Y());
            telemetry.addData("Position", pipeline.getPosition());
            telemetry.update();

            int position = pipeline.getPosition();



            if(position == 1){
                break;

            }
            else if(position == 2){
                //mech.closeClaws();
                hazel.drive(-1920,0.5);

                //hazel.wait(5000);
                mech.axle1.setTargetPosition(4300);
                mech.axle2.setTargetPosition(4300);
                mech.viperSlide.setTargetPosition(1050);

            }
            else if(position == 3){
                break;

            }


        }

    }
}

