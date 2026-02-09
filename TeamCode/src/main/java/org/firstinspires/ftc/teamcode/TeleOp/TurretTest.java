package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ProgrammingBoards.Intake;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Spindexer2;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.Transfer;
import org.firstinspires.ftc.teamcode.ProgrammingBoards.TurretV2;


@TeleOp
public class TurretTest extends LinearOpMode {

    private TurretV2 turretV2;


    @Override
    public void runOpMode() throws InterruptedException {
        turretV2 = new TurretV2(hardwareMap);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            if(gamepad1.cross){
                turretV2.turnTo(90);
            }else if(gamepad1.circle){
                turretV2.turnTo(-90);
            }else{
                turretV2.turnTo(0);
            }





            telemetry.update();

        }

    }
}
