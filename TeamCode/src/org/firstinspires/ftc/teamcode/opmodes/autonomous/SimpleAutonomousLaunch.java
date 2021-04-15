package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Simple Autonomous Launch")
public class SimpleAutonomousLaunch extends LinearOpMode {
    private AutonomousMecanumHardwareInterface hardwareInterface;

    @Override
    public void runOpMode() {
        hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);
        hardwareInterface.shooterMotor.setPower(1.0f);
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        while (opModeIsActive()) {
            if (time.milliseconds() > 500) {
                hardwareInterface.shooterTrigServo.setPosition(1.0);
                time.reset();
            } else {
                hardwareInterface.shooterTrigServo.setPosition(0.0);
            }
        }
    }
}
