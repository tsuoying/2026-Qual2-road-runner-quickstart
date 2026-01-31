package org.firstinspires.ftc.teamcode.ProgrammingBoards;

import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class TurretV2
{
    public Limelight3A limelight;

    public DcMotorEx turret;

    double kP = 0.03, kI=0.00025;
    double integral = 0;
    double prevTargetAngle = 0;
    public TurretV2(HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A .class, "Limelight");

        // pipeline 0 is a placeholder, put apriltag pipeline when configured
        limelight.pipelineSwitch(0);
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        limelight.start();
    }


    private double toTicks(double angle)
    {
        return angle * 29.7770061728;
    }
    private double toDegrees(double ticks)
    {
        return ticks/29.7770061728;
    }



}
