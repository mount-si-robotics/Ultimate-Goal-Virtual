package org.firstinspires.ftc.teamcode.opmodes.testing.motor_error;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.opmodes.teleop.TeleOpMecanumHardwareInterface;

@TeleOp(name="Launcher Error Testing", group="Teleop")
public class LauncherErrorTests extends LinearOpMode {
    // ------------------------------ //
    // Instance variables.

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    // Controller inputs.
    private double leftStickX;
    private double leftStickY;
    private double rightStickX;

    // Hardware interface.
    TeleOpMecanumHardwareInterface hardwareInterface = null;


    // ------------------------------ //
    // Main method.

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        this.hardwareInterface = new TeleOpMecanumHardwareInterface(hardwareMap);

        waitForStart();

        this.hardwareInterface.intakeMotor.setPower(1.0);
        this.hardwareInterface.shooterMotor.setPower(0.0);

        double shooterMotorPower = 0.0;

        while (opModeIsActive())
        {

            // First get and assign game-pad values.

            // Assign values.
            this.initStickVal();
            // Floor controller input values if they are insignificant.
            this.limitStickVal(.25);


            // Now assign proper powers to motors.

            // Important variables.
            double r = Math.hypot(leftStickX, leftStickY);
            // (Remove the negative from left stick y if you're gross and want up/down inverted)
            double hypotenuse = Math.atan2(-leftStickY, leftStickX) - Math.PI / 4;
            double xDisplacement = r * Math.cos(hypotenuse);
            double yDisplacement = r * Math.sin(hypotenuse);
            // Use said important variables above to assign proper powers to motors.
            this.assignMotorPowers(
                    xDisplacement,
                    yDisplacement,
                    rightStickX
            );

            if (gamepad1.a) {
                this.hardwareInterface.shooterTrigServo.setPosition(1.0);
            } else {
                this.hardwareInterface.shooterTrigServo.setPosition(0.0);
            }

            if (gamepad1.dpad_up && shooterMotorPower < 1.0) {
                shooterMotorPower += 0.001;
            } else if (gamepad1.dpad_down && shooterMotorPower > -1.0) {
                shooterMotorPower -= 0.001;
            }

            if (this.hardwareInterface.shooterMotor.getPower() != shooterMotorPower) {
                this.hardwareInterface.shooterMotor.setPower(shooterMotorPower);
            }

            // Finally just update telemetry.
            telemetry.addData("Shooter Motor Power: ", shooterMotorPower);

            this.updateTelemetry();


        }
    }


    // ------------------------------ //
    // Game-pad value helper methods.

    // Assigns values from the game-pad to the stick values.
    private void initStickVal()
    {

        leftStickX = gamepad1.left_stick_x;
        leftStickY = gamepad1.left_stick_y;
        rightStickX = gamepad1.right_stick_x;

    }

    // Floors all stick values beneath the provided minVal.
    private void limitStickVal(double minVal)
    {

        if(Math.abs(leftStickX) <= minVal)
        {
            leftStickX = 0;
        }
        if(Math.abs(leftStickY) <= minVal)
        {
            leftStickY = 0;
        }
        if(Math.abs(rightStickX) <= minVal)
        {
            rightStickX = 0;
        }

    }


    // ------------------------------ //
    // Telemetry helper methods.

    /*
    Displays the following values within the telemetry window:
        - Elapsed time
        - Game-pad LeftX
        - Game-pad LeftY
        - Game-pad RightX
     */
    private void updateTelemetry()
    {

        telemetry.addData("Elapsed Time: ", runtime.seconds());
        // Display controller inputs so things aren't all that black box-ey.
        telemetry.addData("\nLeft X: ", gamepad1.left_stick_x);
        telemetry.addData("Left Y: ", -gamepad1.left_stick_y);
        telemetry.addData("\nRight X: ", rightStickX);
        telemetry.update();

    }


    // ------------------------------ //
    // Motor power helper methods.

    // Set wheel powers to what they need to be based on given data.
    private void assignMotorPowers(double xDisplacement, double yDisplacement, double rightStickX)
    {

        // Front wheels will use xDisplacement, while rear wheels will use yDisplacement.
        // Angle will be added to left wheels, while for right wheels it will be subtracted.
        this.hardwareInterface.driveFrontRight.setPower(yDisplacement - rightStickX);
        this.hardwareInterface.driveRearRight.setPower (xDisplacement - rightStickX);
        this.hardwareInterface.driveFrontLeft.setPower (xDisplacement + rightStickX);
        this.hardwareInterface.driveRearLeft.setPower  (yDisplacement + rightStickX);

    }
}

