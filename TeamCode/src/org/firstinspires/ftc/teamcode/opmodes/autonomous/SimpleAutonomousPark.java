package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Simple Autonomous Park")
public class SimpleAutonomousPark extends LinearOpMode {

    // ------------------------------ //
    // Instance variables.

    private final DcMotorSimple.Direction REVERSE = DcMotorSimple.Direction.REVERSE;
    private final DcMotorSimple.Direction FORWARD = DcMotorSimple.Direction.FORWARD;

    // ------------------------------ //
    // Main methods.

    @Override
    public void runOpMode() throws InterruptedException {

        AutonomousMecanumHardwareInterface hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);

        this.deliverTargetC(false);

        this.moveBackwards(55, 1);
        this.moveLeft(25, 1);

        this.launchRings(250, 0.73);

        ElapsedTime elapsedTime = new ElapsedTime();
        while (opModeIsActive()) {
            if (elapsedTime.milliseconds() > 2000) break;
        }

        hardwareInterface.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    private void launchRings(double interval, double power) {
        AutonomousMecanumHardwareInterface hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);
        hardwareInterface.shooterMotor.setPower(power);

        for (int i = 0; i < 3; i++) {
            hardwareInterface.shooterTrigServo.setPosition(0.0);
            ElapsedTime elapsedTime = new ElapsedTime();
            while (opModeIsActive()) {
                if (elapsedTime.milliseconds() > interval) break;
            }
            hardwareInterface.shooterTrigServo.setPosition(1.0);
            elapsedTime.reset();
            while (opModeIsActive()) {
                if (elapsedTime.milliseconds() > interval) break;
            }
        }
    }

    // ------------------------------ //
    // Methods.

    // Methods for moving forwards and backwards.
    private void moveVertical(double inches, double speed, boolean goingForward){

        // If inches are negative for some reason, then go the other direction.
        if(inches < 0){

            goingForward = !goingForward;
            inches *= -1;

        }

        // Initialize interface object and target position.
        AutonomousMecanumHardwareInterface hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);

        // Move.
        this.setMotorDirections(
                hardwareInterface,
                goingForward,
                goingForward,
                goingForward,
                goingForward
        );
        this.moveMaster(
                hardwareInterface,
                inches,
                speed
        );

    }
    public void moveForwards(double inches, double speed){

        this.moveVertical(inches, speed, true);

    }
    public void moveForwards(double inches){

        this.moveForwards(inches, .2);

    }
    public void moveBackwards(double inches, double speed){

        this.moveVertical(inches, speed, false);

    }
    public void moveBackwards(double inches){

        this.moveBackwards(inches, .2);

    }

    // Methods for moving left and right.
    private void moveHorizontal(double inches, double speed, boolean goingRight){

        // If inches are negative for some reason, then go the other direction.
        if(inches < 0){

            goingRight = !goingRight;
            inches *= -1;

        }

        // Initialize interface object and target position.
        AutonomousMecanumHardwareInterface hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);

        // Move.
        this.setMotorDirections(
                hardwareInterface,
                goingRight,
                !goingRight,
                !goingRight,
                goingRight
        );
        this.moveMaster(
                hardwareInterface,
                inches,
                speed
        );

    }
    public void moveLeft(double inches, double speed){

        this.moveHorizontal(inches, speed, true);

    }
    public void moveLeft(double inches){

        this.moveLeft(inches, .2);

    }
    public void moveRight(double inches, double speed){

        this.moveHorizontal(inches, speed, false);

    }
    public void moveRight(double inches){

        this.moveRight(inches, .2);

    }

    // Methods for turning clockwise and counterclockwise.
    private void turn(double inches, double speed, boolean clockwise){

        // If inches are negative for some reason, then go the other direction.
        if(inches < 0){

            clockwise = !clockwise;
            inches *= -1;

        }

        // Initialize interface object and target position.
        AutonomousMecanumHardwareInterface hardwareInterface = new AutonomousMecanumHardwareInterface(hardwareMap);

        // Move.
        this.setMotorDirections(
                hardwareInterface,
                clockwise,
                !clockwise,
                clockwise,
                !clockwise
        );
        this.moveMaster(
                hardwareInterface,
                inches,
                speed
        );

    }
    public void turnClockwise(double inches, double speed){

        this.turn(inches, speed, true);

    }
    public void turnClockwise(double inches){

        this.turnClockwise(inches, .2);

    }
    public void turnCounterClockwise(double inches, double speed){

        this.turn(inches, speed, false);

    }
    public void turnCounterClockwise(double inches){

        this.turnCounterClockwise(inches, .2);

    }

    // Master movement methods.
    /** DO NOT USE THIS. This is just a backend function made to minimize redundancy. **/
    private void moveMaster(AutonomousMecanumHardwareInterface hardwareInterface, double inches, double speed){

        // Return early if speed or inches are 0.
        if(inches == 0 || speed == 0){

            return;

        }

        // Correct variables if they're out of range.
        inches = this.limitToRange(inches, 0, Integer.MAX_VALUE);
        speed = this.limitToRange(speed, 0, 1);

        // Initialize interface object and target position.
        double motorTicksPerInch = (2 * Math.PI * 2) / hardwareInterface.getTicksPerRevolution();
        int targetPosition = (int) (inches / motorTicksPerInch);

        // Set target position using variables above.
        hardwareInterface.setDriveTargetPosition(targetPosition);
        hardwareInterface.setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        // Move.
        hardwareInterface.setDriveMotorPower(speed);

        while (opModeIsActive() && hardwareInterface.isDriveMotorBusy()) {
            idle();
        }

    }


    // ------------------------------ //
    // Helper methods.

    // Limits a given input between two ranges.
    private double limitToRange(double input, double min, double max){

        if(input > max)
        {

            return max;

        }
        else if(input < min){

            return min;

        }
        return input;

    }

    // True means forward, false means backwards.
    // Takes in a hardware interface object as well as four booleans representing each motor.
    private void setMotorDirections(AutonomousMecanumHardwareInterface hardwareInterface, boolean frontRight, boolean frontLeft, boolean rearRight, boolean rearLeft){

        // Set direction for the frontRight motor.
        if(!frontRight) {
            hardwareInterface.driveFrontRight.setDirection(FORWARD);
        }else{
            hardwareInterface.driveFrontRight.setDirection(REVERSE);
        }

        // Set direction for the frontLeft motor.
        if(frontLeft) {
            hardwareInterface.driveFrontLeft.setDirection(FORWARD);
        }else{
            hardwareInterface.driveFrontLeft.setDirection(REVERSE);
        }

        // Set direction for the rearRight motor.
        if(!rearRight) {
            hardwareInterface.driveRearRight.setDirection(FORWARD);
        }else{
            hardwareInterface.driveRearRight.setDirection(REVERSE);
        }

        // Set direction for the rearLeft motor.
        if(rearLeft) {
            hardwareInterface.driveRearLeft.setDirection(FORWARD);
        }else{
            hardwareInterface.driveRearLeft.setDirection(REVERSE);
        }

    }


    // ------------------------------ //
    // Super mega ultra superior high level methods.

    // These methods will deliver both wobble goals to their respective target zones.
    public void deliverTargetC(boolean blueAlliance){

        double speed = 1;

        // Move the left wobble goal up 10 feet.
        this.moveHorizontal(20, speed, !blueAlliance);
        this.moveForwards(115, speed);

        // Back out a little and get to a position to push the goal into the corner
        this.moveBackwards(10, speed);
        this.moveHorizontal(20, speed, !blueAlliance);
        this.moveForwards(20, speed);

        // Push the goal into the corner.
        this.moveHorizontal(45, speed, blueAlliance);


        // Move back to push the other goal.
        this.moveHorizontal(40, speed, !blueAlliance);

        this.moveBackwards(110, speed);

        // Push the other goal further to the wall, and reposition to get under it.
        this.moveHorizontal(40, speed, blueAlliance);
        this.moveHorizontal(10, speed, !blueAlliance);

        this.moveBackwards(15, speed);
        this.moveHorizontal(25, speed, blueAlliance);


        // Push the final goal to the top, then go down to park.
        this.moveForwards(115, speed);
        this.moveBackwards(40, speed);

    }
    public void deliverTargetB(boolean blueAlliance){

        double speed = 1;

        // Move the left wobble goal up to the level of zone B.
        this.moveHorizontal(20, speed, !blueAlliance);
        this.moveForwards(95, speed);

        // Back out a little and get to a position to push the goal into the zone.
        this.moveBackwards(10, speed);
        this.moveHorizontal(17, speed, !blueAlliance);
        this.moveForwards(20, speed);

        // Push the goal into the zone.
        this.moveHorizontal(13, speed, blueAlliance);

        // Move back to push the other goal.
        this.moveHorizontal(7, speed, !blueAlliance);
        this.moveBackwards(100, speed);
        this.moveHorizontal(40, speed, blueAlliance);

        // Push the remaining wobble goal to up to the same level as target B.
        this.moveForwards(90, speed);

        // Get in position to push the wobble goal into the zone.
        this.moveBackwards(10, speed);
        this.moveHorizontal(19, speed, blueAlliance);
        this.moveForwards(15, speed);

        // Move the goal and park over the line.
        this.moveHorizontal(15, speed, !blueAlliance);
        this.moveBackwards(20, speed);

    }
    public void deliverTargetA(boolean blueAlliance){

        double speed = 1;

        // Move the left wobble goal up 10 feet.
        this.moveHorizontal(20, speed, !blueAlliance);
        this.moveForwards(75, speed);

        // Back out a little and get to a position to push the goal into the corner
        this.moveBackwards(10, speed);
        this.moveHorizontal(20, speed, !blueAlliance);
        this.moveForwards(20, speed);

        // Push the goal into the corner.
        this.moveHorizontal(45, speed, blueAlliance);


        // Move back to push the other goal.
        this.moveHorizontal(30, speed, !blueAlliance);

        this.moveBackwards(70, speed);

        // Push the other goal further to the wall, and reposition to get under it.
        this.moveHorizontal(30, speed, blueAlliance);
        this.moveHorizontal(10, speed, !blueAlliance);

        this.moveBackwards(15, speed);
        this.moveHorizontal(25, speed, blueAlliance);


        // Push the final goal to the top, then go down to park.
        this.moveForwards(70, speed);
        this.moveBackwards(10, speed);

        // Park over the line.
        this.moveHorizontal(30, speed, !blueAlliance);
        this.moveForwards(20, speed);

    }


}
