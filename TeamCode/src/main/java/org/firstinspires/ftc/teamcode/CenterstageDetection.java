package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CenterstageDetection extends OpenCvPipeline {
    final Scalar RED = new Scalar(255, 0, 0);
    final Scalar BLACK = new Scalar(0, 0, 0);
    final Scalar WHITE = new Scalar(255, 255, 255);

    int position;

    final Point region1_topLeft = new Point(225,90);
    final Point region2_topLeft = new Point(280,520);

    Point region1_pointA = new Point(region1_topLeft.x, region1_topLeft.y);
    Point region1_pointB = new Point(
            region1_topLeft.x + 80, // adding region width
            region1_topLeft.y + 80); // adding region height

    Point region2_pointA = new Point(region2_topLeft.x, region2_topLeft.y);
    Point region2_pointB = new Point(
            region2_topLeft.x + 40, // adding region width
            region2_topLeft.y + 115); // adding region height

    // Create points for left and right pole alignment autos to create submats for detection

    Mat region1;
    Mat YCrCb1 = new Mat();
    Mat Y1 = new Mat();
    int region1Y;

    Mat region2;
    Mat YCrCb2 = new Mat();
    Mat Y2 = new Mat();
    int region2Y;

    void extractChannels(Mat input) {
        Imgproc.cvtColor(input, YCrCb1, Imgproc.COLOR_RGB2YCrCb);
        Imgproc.cvtColor(input, YCrCb2, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb1, Y1, 0);
        Core.extractChannel(YCrCb2, Y2, 0);
    }

    @Override
    public void init(Mat firstFrame) {
        extractChannels(firstFrame);
        region1 = Y1.submat(new Rect(region1_pointA, region1_pointB));
        region2 = Y2.submat(new Rect(region2_pointA, region2_pointB));
        // create submats for a pole alignment on both the right and left

        /*
            Properly implement YCrCb for a more accurate alignment
            channel 0 => Y
            channel 1 => Cr
            channel 2 => Cb

            Telemetry all 3 channels and find out where yellow can be tracked the best and maybe even fix our regular autonomous to be more accurate.

            processFrame for all these submats and create Core.mean int values for YCrCb for each input
            use ImgProcs to highlight the pole yellow if detected and green if not based on YCrCb
         */
    }

    @Override
    public Mat processFrame(Mat input) {
        extractChannels(input);
        region1Y = (int) Core.mean(region1).val[0];
        region2Y = (int) Core.mean(region2).val[0];

        if (Math.abs(region1Y - region2Y) <= 10) {
            Imgproc.rectangle(input, region1_pointA, region1_pointB, RED, -1);
            Imgproc.rectangle(input, region2_pointA, region2_pointB, RED, -1);
            position = 3;
        } else {
            if (region1Y > region2Y) {
                Imgproc.rectangle(input, region1_pointA, region1_pointB, WHITE, -1);
                Imgproc.rectangle(input, region2_pointA, region2_pointB, RED, -1);
                position = 1;
            } else {
                Imgproc.rectangle(input, region1_pointA, region1_pointB, RED, -1);
                Imgproc.rectangle(input, region2_pointA, region2_pointB, WHITE, -1);
                position = 2;
            }
        }
        return input;
    }
    public int getRegion1Y() {
        return region1Y;
    }
    public int getRegion2Y() {
        return region2Y;
    }
    public int getPosition() {
        return position;
    }
}