package org.firstinspires.ftc.teamcode.TeleOp;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.DriveTrain;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Flywheel;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Intake;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Spindexer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Transfer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Turret;


@TeleOp
public class TeleOpTestV2 extends LinearOpMode {
    private DriveTrain driveTrain;
    private Spindexer spindexer;
    private Intake intake;
    private Transfer transfer;

    private Flywheel flywheel;

    private Turret turret;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = new DriveTrain(hardwareMap);
        spindexer = new Spindexer(hardwareMap);
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        flywheel = new Flywheel(hardwareMap);
        turret = new Turret(hardwareMap);


        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("current", spindexer.spindexer.getCurrentPosition());
            telemetry.addData("target", spindexer.spindexer.getTargetPosition());
            telemetry.addData("vel", flywheel.returnVel());
            telemetry.addData("red", spindexer.getRedResult());
            telemetry.addData("green", spindexer.getGreenResult());
            telemetry.addData("blue", spindexer.getBlueResult());
            telemetry.addData("touch", spindexer.touchSensor.isPressed());
            telemetry.addData("distance", spindexer.distanceSensor.getDistance(DistanceUnit.MM));
            telemetry.addData("Accepting Balls? : ", spindexer.returnShootingMode());
            telemetry.addData("balls", spindexer.ballCount);




            //DRIVETRAIN
            driveTrain.driveMotors(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);



            //FLYWHEEL
            if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_stick_x == 0){
                flywheel.runFlywheelVel(0.9);
            }else{
                flywheel.flywheel.setPower(0.8);
            }

            //SWITCHING MODES AND TURNING 120
            if (gamepad2.leftBumperWasPressed()){
                spindexer.switchModes();
            }
            if (gamepad2.rightBumperWasPressed()){
                spindexer.rotateThird();
            }


            //INTAKE
            intake.runIntake(1);


            //TRANSFER
            if(gamepad2.square){
                transfer.transferDown(1);
            }else{
                transfer.transferDown(-1);
            }

            //BALLCOUNT
            spindexer.checkIfBall();

            //RESET BALL COUNT
            if(gamepad2.circleWasPressed()){spindexer.resetBallCount();}


            //TURRET AUTO ALIGN
            if(gamepad2.cross){turret.TurnToAT();}else{turret.TurnTo(0);}


            telemetry.addData("angle", turret.getCurrAngle());
            telemetry.addData("tx", turret.getTx());
            Log.d("turret", ""+turret.getCurrAngle());
            Log.d("limelight", ""+turret.limelight.getLatestResult());
            LLResult result = turret.limelight.getLatestResult();

            //get limelight values:
            if (result != null) {
                if(result.isValid()) {
                    telemetry.addData("limelight angle", turret.limelight.getLatestResult().getTx());
                    if(!result.getFiducialResults().isEmpty()) {
                        telemetry.addData("tag id", result.getFiducialResults().get(0).getFiducialId());
                    }

                }
            }
            telemetry.update();

        }

    }
}
