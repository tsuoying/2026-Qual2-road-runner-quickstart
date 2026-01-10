package org.firstinspires.ftc.teamcode.Autons;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.DriveTrain;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Flywheel;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Intake;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Spindexer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Transfer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Turret;

@Autonomous
public class AutoBlueTopV1 extends LinearOpMode {
    private DriveTrain driveTrain;
    private Spindexer spindexer;
    private Intake intake;
    private Transfer transfer;
    private Flywheel flywheel;
    private Turret turret;

    @Override
    public void runOpMode() throws InterruptedException {
        turret = new Turret (hardwareMap);
        flywheel = new Flywheel (hardwareMap);
        intake = new Intake (hardwareMap);
        spindexer = new Spindexer (hardwareMap);
        transfer = new Transfer (hardwareMap);

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-48,-48, Math.toRadians(225)));

        Action shotOne = drive.actionBuilder(new Pose2d(-48,-48,Math.toRadians(225)))
                .strafeToLinearHeading(new Vector2d(-10, -12), Math.toRadians(225))
                .build();
        Action takeBallsOneP1 = drive.actionBuilder(new Pose2d(-10, -12, Math.toRadians(225)))
                .splineToLinearHeading(new Pose2d(-10,-28,Math.toRadians(270)), Math.toRadians(90))
                .build();
        Action takeBallsOneP2 = drive.actionBuilder(new Pose2d(-10, -28, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(-10,-54,Math.toRadians(270)), Math.toRadians(90))
                .build();
        Action shotTwo = drive.actionBuilder(new Pose2d(-10, -54, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(-10,-12,Math.toRadians(225)), Math.toRadians(0))
                .build();
        Action takeBallsTwoP1 = drive.actionBuilder(new Pose2d(-10, -12, Math.toRadians(225)))
                .splineToLinearHeading(new Pose2d(12,-25,Math.toRadians(270)), Math.toRadians(0))
                .build();
        Action takeBallsTwoP2 = drive.actionBuilder(new Pose2d(8, -25, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(12,-57),Math.toRadians(270))
                .build();
        Action shot3 = drive.actionBuilder(new Pose2d(14, -57, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(-10,-12),Math.toRadians(225))
                .build();
        Action takeBallsThreeP1 = drive.actionBuilder(new Pose2d(-10, -12, Math.toRadians(225)))
                .splineToLinearHeading(new Pose2d(38,-25,Math.toRadians(270)), Math.toRadians(0))
                .build();
        Action takeBallsThreeP2 = drive.actionBuilder(new Pose2d(38, -25, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(38, -57), Math.toRadians(270))
                .build();
        Action shot4 = drive.actionBuilder(new Pose2d(36, -57, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(21,-12), Math.toRadians(180))
                .build();



        waitForStart();
        if (isStopRequested()) return;

        while(!isStopRequested() && opModeIsActive()) {
            flywheel.runFlywheelVel(0.9);
            turret.TurnTo(45);
            intake.runIntake(1);
            sleep(600);
            //spindexer
            Actions.runBlocking(shotOne);
            //spindexer
            transfer.transferUp(1);
            sleep(3100);
            transfer.transferDown(1);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    takeBallsOneP1,
                                    takeBallsOneP2,
                                    shotTwo
                            )
                    )
            );
            transfer.transferUp(1);
            sleep(950);
            sleep(1700);
            transfer.transferDown(1);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    takeBallsTwoP1,
                                    takeBallsTwoP2,
                                    shot3
                            )
                    )
            );
            transfer.transferUp(1);
            sleep(950);
            sleep(1700);
            transfer.transferDown(1);
            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction(
                                    takeBallsThreeP1,
                                    takeBallsThreeP2,
                                    shot4
                            )
                    )
            );
            sleep(30000);


        }
    }
}
