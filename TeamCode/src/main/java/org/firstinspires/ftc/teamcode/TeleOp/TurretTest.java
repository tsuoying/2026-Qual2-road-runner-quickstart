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
    private Limelight3A limelight;
    @Override
    public void runOpMode() throws InterruptedException {
        turret = new TurretV2(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        // pipeline 0 is a placeholder, put apriltag pipeline when configured
        limelight.pipelineSwitch(0);

        limelight.start();


        waitForStart();
        turret.turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        while (!isStopRequested() && opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            if(result != null){
                if(result.isValid()) {
                    int currAngle = turret.turret.getCurrentPosition();
                    turret.TurnTo((int) (result.getTx()));
                    telemetry.addData("c+tx", currAngle+result.getTx());
                   // telemetry.addData("limelight", result.getTx());
                    //
//                    if (result.getTx() >20) {
//                        turret.turret.setPower(-0.7);
//                    }
//                    else if (result.getTx() >10) {
//                        turret.turret.setPower(-0.6);
//                    }
//                    else if (result.getTx() > 5) {
//                        turret.turret.setPower(-0.3);
//                    }
//                    else if (result.getTx() >2) {
//                        turret.turret.setPower(-0.1);
//                    }
//                    else if (result.getTx() < -20) {
//                        turret.turret.setPower(0.7);
//                    }
//                    else if (result.getTx() < -10) {
//                        turret.turret.setPower(0.6);
//                    }else if (result.getTx() < -5) {
//                        turret.turret.setPower(0.3);
//                    }
//                    else if (result.getTx() < -2) {
//                        turret.turret.setPower(0.1);
//                    }else {
//                        turret.turret.setPower(0);
//                    }
                }else{
                    turret.turret.setPower(0);
                }

            }


            telemetry.addData("curAng", turret.turret.getCurrentPosition());
            telemetry.update();

            turret.turret.setPower(-gamepad2.left_stick_x);
        }
    }
}
