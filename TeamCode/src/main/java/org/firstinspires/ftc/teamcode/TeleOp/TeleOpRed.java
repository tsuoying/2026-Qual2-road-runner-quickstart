package org.firstinspires.ftc.teamcode.TeleOp;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.DriveTrain;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Flywheel;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Intake;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Spindexer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Transfer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Turret;


@TeleOp
public class TeleOpRed extends LinearOpMode {
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
        flywheel.flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double speed = 0.8;

        //PIDFCoefficients pidf = new PIDFCoefficients(25,0.05,0,25);
        //flywheel.flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("current", spindexer.spindexer.getCurrentPosition());
            telemetry.addData("target", spindexer.spindexer.getTargetPosition());
            telemetry.addData("vel", flywheel.returnVel());


            telemetry.addData("balls", spindexer.ballCount);




            //DRIVETRAIN
            driveTrain.driveMotors(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);



            //FLYWHEEL
            if(gamepad2.squareWasPressed()){
                speed = speed - 0.025;
            } else if (gamepad2.circleWasPressed()) {
                speed = speed + 0.025;
            }

            if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_stick_x == 0){
                flywheel.flywheel.setVelocity(speed*1600);
                driveTrain.stopMotor();
            }else{
                flywheel.flywheel.setPower(0.8);
            }



            //INTAKE


            if(gamepad2.right_bumper){
                spindexer.spindexer.setPower(0.125);
                sleep(200);
                transfer.transferUp(1);
                intake.runIntake(1);
            }else if(gamepad2.left_bumper){
                intake.runIntake(-1);
                spindexer.spindexer.setPower(-0.2);
            }
            else{
                spindexer.spindexer.setPower(0.5);
                intake.runIntake(1);
                transfer.transferDown(1);
            }



            //TRANSFER


            //BALLCOUNT
            //spindexer.checkIfBall();

            //RESET BALL COUNT


            //TURRET AUTO ALIGN
            if(gamepad2.cross){turret.TurnToAT(24);}else{turret.TurnTo(0);}

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
