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

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        TeleOpMecanumHardwareInterface hardwareInterface = new TeleOpMecanumHardwareInterface(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {

            double leftStickX = gamepad1.left_stick_x;
            double leftStickY = gamepad1.left_stick_y;
            double rightStickX = gamepad1.right_stick_x;

            // Floor controller input values if they are insignificant.
            double minValue = .25;
            if(Math.abs(leftStickX) <= minValue){
                leftStickX = 0;
            }
            if(Math.abs(leftStickY) <= minValue){
                leftStickY = 0;
            }
            if(Math.abs(rightStickX) <= minValue){
                rightStickX = 0;
            }

            // Important variables.
            double r = Math.hypot(leftStickX, leftStickY);
            // Remove the negative from left stick y if you're gross and want up/down inverted.
            double robotAngle = Math.atan2(-leftStickY, leftStickX) - Math.PI / 4;

            // Less important variables.
            // These are more just to reduce redundancy.
            double xDisplacement = r * Math.cos(robotAngle);
            double yDisplacement = r * Math.sin(robotAngle);

            // Set wheel powers to what they need to be.
            // Front wheels will use xDisplacement, while rear wheels will use yDisplacement.
            // Angle will be added to left wheels, while for right wheels it will be subtracted.
            hardwareInterface.driveFrontLeft.setPower(xDisplacement + rightStickX);
            hardwareInterface.driveRearLeft.setPower(yDisplacement + rightStickX);
            hardwareInterface.driveFrontRight.setPower(yDisplacement - rightStickX);
            hardwareInterface.driveRearRight.setPower(xDisplacement - rightStickX);

            // Telemetry stuff.
            telemetry.addData("Elapsed Time: ", runtime.seconds());
            // Display controller inputs so things aren't all that black box-ey.
            telemetry.addData("\nLeft X: ", gamepad1.left_stick_x);
            telemetry.addData("Left Y: ", -gamepad1.left_stick_y);
            telemetry.addData("\nRight X: ", rightStickX);
            telemetry.update();

        }
    }
}
