package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
public class SimpleTeleOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private double calculateWheelCoefficient(double course, double wheelAngle) {
        return (Math.cos(course)-Math.sin(course)/Math.tan(wheelAngle))*Math.signum(wheelAngle);
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        TeleOpMecanumHardwareInterface hardwareInterface = new TeleOpMecanumHardwareInterface(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            /*
                Forward = 0,
                Backward = 180,
                Left = 90,
                Right = -90
             */

            double course = -Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI/2;
            double velocity = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double rotation = -gamepad1.right_stick_x;

            hardwareInterface.driveFrontLeft.setPower(calculateWheelCoefficient(course, -3*Math.PI/4) * velocity + rotation);
            hardwareInterface.driveFrontRight.setPower(calculateWheelCoefficient(course, 3*Math.PI/4) * velocity + rotation);
            hardwareInterface.driveRearLeft.setPower(calculateWheelCoefficient(course, Math.PI/4) * velocity + rotation);
            hardwareInterface.driveRearRight.setPower(calculateWheelCoefficient(course, -Math.PI/4) * velocity + rotation);

            telemetry.addData("Elapsed Time", runtime.seconds());
            telemetry.update();
        }
    }
}
