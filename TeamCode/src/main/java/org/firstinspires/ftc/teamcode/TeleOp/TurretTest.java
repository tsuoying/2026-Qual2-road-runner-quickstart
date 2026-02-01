package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.ProgrammingBoards.TurretV2;


@TeleOp
public class TurretTest extends LinearOpMode {
    private TurretV2 turret;
    @Override
    public void runOpMode() throws InterruptedException {
        turret = new TurretV2(hardwareMap);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            turret.update();  // Handles everything

            telemetry.addData("tx", turret.limelight.getLatestResult() != null ?
                    turret.limelight.getLatestResult().getTx() : "null");
            telemetry.addData("angle", turret.getCurrentAngle());
            telemetry.addData("vel", turret.turret.getVelocity());
            telemetry.addData("ticks", turret.getCurrentTicks());
            telemetry.update();
        }
    }
}
