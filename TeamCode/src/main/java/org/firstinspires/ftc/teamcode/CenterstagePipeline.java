package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class CenterstagePipeline {

    LinearOpMode opMode;
    CenterstageDetection cameraPipeline;
    OpenCvInternalCamera phoneCam;

    HardwareMap hardwareMap;

    public CenterstagePipeline(LinearOpMode opMode, HardwareMap hwMap) {
        this.opMode = opMode;
        hardwareMap = hwMap;
    }
    public void initializeCamera() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        cameraPipeline = new CenterstageDetection();
        phoneCam.setPipeline(cameraPipeline);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(960,720, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {}
        });

    }

    public int getRegion1Y() {return cameraPipeline.getRegion1Y();}
    public int getRegion2Y() {return cameraPipeline.getRegion2Y();}
    public int getPosition() {return cameraPipeline.getPosition();}
}
