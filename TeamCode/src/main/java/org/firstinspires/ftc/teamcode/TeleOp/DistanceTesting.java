package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Flywheel;

@TeleOp
public class DistanceTesting extends LinearOpMode {
    private Limelight3A limelight;
    private Flywheel flywheel;
    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        flywheel = new Flywheel(hardwareMap);

        double speed = 0.85;

        PIDFCoefficients pidf = new PIDFCoefficients(150,0,0,11.7025);
        flywheel.flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        limelight.pipelineSwitch(0);
        limelight.start();


        waitForStart();

        while (opModeIsActive()&& !isStopRequested()) {

            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {

                // --- Read Limelight values ---
                double ty = result.getTy(); // vertical angle in degrees
                double ta = result.getTa(); // target area in percent (small number, usually 0-5 for far targets)

                // --- Constants ---
                double cameraHeight = 17.0;       // floor to camera (inches)
                double targetHeight = 29.5;       // floor to Apriltag (inches)
                double heightDifference = targetHeight - cameraHeight; // 12.5 inches
                double cameraAngle = 0.0;         // mounting angle in degrees

                // --- Distance from ty ---
                double distanceTy = -1;
                if (ty != 0) {
                    distanceTy = heightDifference / Math.tan(Math.toRadians(cameraAngle + ty));
                }

                // --- Distance from ta ---
                double distanceTa = -1;
                double taCalibrationConstant = 70.7; // recalibrated for taMeasured at 100 in
                if (ta > 0) {
                    distanceTa = taCalibrationConstant / Math.sqrt(ta);
                }

                // --- Blending thresholds ---
                double blendStart = 45.0; // inches
                double blendEnd   = 65.0; // inches

                // --- Determine final distance ---
                double distanceInches;

                if (distanceTy > 0 && distanceTy < blendStart) {
                    // Close range: use ty only
                    distanceInches = distanceTy;
                } else if (distanceTa > 0 && distanceTy > blendEnd) {
                    // Far range: use ta only
                    distanceInches = distanceTa;
                } else if (distanceTy > 0 && distanceTa > 0) {
                    // Blend region: linear interpolation
                    double t = (distanceTy - blendStart) / (blendEnd - blendStart);
                    t = Math.max(0, Math.min(1, t)); // clamp 0–1
                    distanceInches = distanceTy * (1 - t) + distanceTa * t;
                } else {
                    distanceInches = -1; // no valid measurement
                }

                // --- Telemetry ---
                if (distanceInches > 0) {
                    telemetry.addData("Distance (in)", distanceInches);
                    telemetry.addData("Ty (deg)", ty);
                    telemetry.addData("Ta (%)", ta);
                    telemetry.addData("DistanceTy", distanceTy);
                    telemetry.addData("DistanceTa", distanceTa);
                } else {
                    telemetry.addLine("No valid target detected");
                }

            } else {
                telemetry.addLine("No valid Limelight result");
            }








            telemetry.update();
        }
    }
}
