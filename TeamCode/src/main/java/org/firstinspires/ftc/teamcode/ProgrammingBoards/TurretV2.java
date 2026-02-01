package org.firstinspires.ftc.teamcode.ProgrammingBoards;

import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TurretV2 {
    public Limelight3A limelight;
    public DcMotorEx turret;

    // PID
    public double kP = 0.01, kI = 0.00025, kD = 0.00015;
    private double integral = 0;
    private double prevError = 0;

    // Encoder & limits
    private OverflowEncoder turretEncoder;
    private static final double TICKS_PER_DEG = 29.7770061728;
    private static final double MAX_ANGLE = 70.0;
    private static final double MIN_ANGLE = -70.0;
    private static final double INTEGRAL_LIMIT = 50.0;

    public TurretV2(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        turretEncoder = new OverflowEncoder(new RawEncoder(turret));

        limelight.start();
    }

    private double toTicks(double angle) { return angle * TICKS_PER_DEG; }
    private double toDegrees(double ticks) { return ticks / TICKS_PER_DEG; }

    public double getCurrentAngle() {
        return toDegrees(turretEncoder.getPositionAndVelocity().position);
    }

    public double getCurrentTicks() {
        return turretEncoder.getPositionAndVelocity().position;
    }

    public void update() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) {
            turret.setPower(0);
            integral = 0;
            return;
        }

        double targetAngle = result.getTx();
        double currAngle = getCurrentAngle();

        // Clamp to limits
        targetAngle = Math.max(MIN_ANGLE, Math.min(MAX_ANGLE, targetAngle));

        double error = targetAngle - currAngle;
        // Normalize to shortest path
        while (error > 180) error -= 360;
        while (error < -180) error += 360;

        if (Math.abs(error) < 1.0) {
            turret.setPower(0);
            integral = 0;
            return;
        }

        integral += error;
        integral = Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integral));

        double derivative = error - prevError;
        double power = kP * error + kI * integral + kD * derivative;

        double velocity = turret.getVelocity();
        double scale = Math.min(1.0, Math.abs(error) / 10.0);
        if (Math.abs(velocity) > 1200) scale *= 0.5;
        power *= scale;

        power = Math.max(-0.4, Math.min(0.4, power));
        turret.setPower(-power);

        prevError = error;
    }
}
