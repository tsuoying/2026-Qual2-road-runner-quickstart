package org.firstinspires.ftc.teamcode.ProgrammingBoards;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flywheel {
    public DcMotorEx flywheel;
    public Flywheel(HardwareMap hardwareMap){
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void runFlywheelVel(double speed){
        flywheel.setVelocity(speed * 1760);
    }

    public double returnVel(){
        return flywheel.getVelocity();
    }

}
