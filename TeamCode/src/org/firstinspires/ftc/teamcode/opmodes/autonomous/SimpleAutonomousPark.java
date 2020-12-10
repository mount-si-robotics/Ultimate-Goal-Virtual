package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Simple Autonomous Park")
public class SimpleAutonomousPark extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumHardwareInterface hardwareInterface = new MecanumHardwareInterface(hardwareMap);

        double motorTicksPerInch = (2 * Math.PI * 2) / hardwareInterface.getTicksPerRevolution();
        int targetPosition = (int) (10 / motorTicksPerInch);

        hardwareInterface.setDriveTargetPosition(targetPosition);
        hardwareInterface.setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        hardwareInterface.setDriveMotorPower(0.2);

        while (opModeIsActive() && hardwareInterface.isDriveMotorBusy()) {
            idle();
        }

        hardwareInterface.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
