package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop", group="Teleop")
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

            // Important variables.
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            // Remove the negative from left stick y if you're gross and want up/down inverted.
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;

            // Less important variables.
            // These are more just to reduce redundancy.
            double cosVal = r * Math.cos(robotAngle);
            double sinVal = r * Math.sin(robotAngle);

            // Set wheel powers to what they need to be.
            hardwareInterface.driveFrontLeft.setPower(cosVal + rightX);
            hardwareInterface.driveFrontRight.setPower(sinVal - rightX);
            hardwareInterface.driveRearLeft.setPower(sinVal + rightX);
            hardwareInterface.driveRearRight.setPower(cosVal - rightX);

            // Telemetry stuff.
            telemetry.addData("Elapsed Time: ", runtime.seconds());
            telemetry.addData("\nLeft X: ", gamepad1.left_stick_x);
            telemetry.addData("Left Y: ", -gamepad1.left_stick_y);
            telemetry.addData("\nRight X: ", rightX);
            telemetry.update();
        }
    }
}
