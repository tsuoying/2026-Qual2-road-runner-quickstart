package org.firstinspires.ftc.teamcode.ProgrammingBoards;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Spindexer2 {
    public CRServo spindexer;

    Boolean acceptingBalls = true;


    public Spindexer2(HardwareMap hardwareMap){
        spindexer = hardwareMap.get(CRServo.class, "axon");
    }

    public void RunSpindexer(double pwr){
        spindexer.setPower(pwr);
    }



    //1425 ticks per full rotation
    //3.9583333333 ticks per degree
}
