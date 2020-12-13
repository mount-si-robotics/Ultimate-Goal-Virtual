package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumHardwareInterface {
    private HardwareMap internalHardwareMap;

    public DcMotor driveFrontLeft;
    public DcMotor driveFrontRight;
    public DcMotor driveRearRight;
    public DcMotor driveRearLeft;

    public MecanumHardwareInterface(HardwareMap hwMap) {
        this.internalHardwareMap = hwMap;

        driveFrontLeft = hwMap.dcMotor.get("front_left_motor");
        driveFrontRight = hwMap.dcMotor.get("front_right_motor");
        driveRearLeft = hwMap.dcMotor.get("back_left_motor");
        driveRearRight = hwMap.dcMotor.get("back_right_motor");

        driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRearLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRearRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setDriveMode(DcMotor.RunMode runMode) {
        driveFrontLeft.setMode(runMode);
        driveFrontRight.setMode(runMode);
        driveRearLeft.setMode(runMode);
        driveRearRight.setMode(runMode);
    }

    public void setDriveMotorPower(double power) {
        driveFrontLeft.setPower(power);
        driveFrontRight.setPower(power);
        driveRearLeft.setPower(power);
        driveRearRight.setPower(power);
    }

    public void setDriveTargetPosition(int targetPosition) {
        driveFrontLeft.setTargetPosition(targetPosition);
        driveFrontRight.setTargetPosition(targetPosition);
        driveRearLeft.setTargetPosition(targetPosition);
        driveRearRight.setTargetPosition(targetPosition);
    }

    public boolean isDriveMotorBusy() {
        return driveFrontLeft.isBusy() || driveFrontRight.isBusy() || driveRearLeft.isBusy() || driveRearRight.isBusy();
    }

    public double getTicksPerRevolution() {
        return driveFrontLeft.getMotorType().getTicksPerRev();
    }
}
