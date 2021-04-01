package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Simple Autonomous Launch")
public class SimpleAutonomousLaunch extends LinearOpMode {
    private AutonomousMecanumHardwareInterface hardwareInterface;

    @Override
    public void runOpMode() {
        hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);
        waitForStart();
        hardwareInterface.launch(0.8f);
        while (opModeIsActive()) {

        }
    }
}
