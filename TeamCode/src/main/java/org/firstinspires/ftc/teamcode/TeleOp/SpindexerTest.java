package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.ProgrammingBoards.DriveTrain;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Flywheel;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Intake;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Spindexer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Spindexer2;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Transfer;


@TeleOp
public class SpindexerTest extends LinearOpMode {

    private Spindexer2 spindexer;
    private Transfer transfer;
    private Intake intake;



    @Override
    public void runOpMode() throws InterruptedException {
        spindexer = new Spindexer2(hardwareMap);
        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            spindexer.RunSpindexer(1);
            if(gamepad2.right_bumper){
                transfer.transferUp(1);
            }else{
                transfer.transferUp(-0.5);
            }
            intake.runIntake(1);




            telemetry.update();

        }

    }
}
