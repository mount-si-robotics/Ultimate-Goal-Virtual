package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Simple Autonomous Park")
public class SimpleAutonomousPark extends LinearOpMode {

    // Values to hold on to.
    private final DcMotorSimple.Direction REVERSE = DcMotorSimple.Direction.REVERSE;
    private final DcMotorSimple.Direction FORWARD = DcMotorSimple.Direction.FORWARD;

    // ----------------------------------------------------------------------------------------- //
    // Main methods.

    @Override
    public void runOpMode() throws InterruptedException {

        MecanumHardwareInterface hardwareInterface = new MecanumHardwareInterface(hardwareMap);

        this.deliverTargetC(1);

        hardwareInterface.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    // ----------------------------------------------------------------------------------------- //
    // Methods.

    // Methods for moving forwards and backwards.
    private void moveVertical(double inches, double speed, boolean goingForward){

        // If inches are negative for some reason, then go the other direction.
        if(inches < 0){

            goingForward = !goingForward;
            inches *= -1;

        }

        // Initialize interface object and target position.
        MecanumHardwareInterface hardwareInterface = new MecanumHardwareInterface(hardwareMap);

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
        MecanumHardwareInterface hardwareInterface = new MecanumHardwareInterface(hardwareMap);

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
        MecanumHardwareInterface hardwareInterface = new MecanumHardwareInterface(hardwareMap);

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
    private void moveMaster(MecanumHardwareInterface hardwareInterface, double inches, double speed){

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


    // ----------------------------------------------------------------------------------------- //
    // Helper methods.

    // Limits a given input between two ranges.
    private double limitToRange(double input, double min, double max){

        if(input > max){

            return max;

        } else if(input < min){

            return min;

        }
        return input;

    }

    // True means forward, false means backwards.
    // Takes in a hardware interface object as well as four booleans representing each motor.
    private void setMotorDirections(MecanumHardwareInterface hardwareInterface, boolean frontRight, boolean frontLeft, boolean rearRight, boolean rearLeft){

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


    // ----------------------------------------------------------------------------------------- //
    // Super high level methods.

    // Red alliance.
    public void deliverTargetC(double speed){

        // Move the left wobble goal up 10 feet.
        this.moveLeft(20, speed);
        this.moveForwards(115, speed);

        // Back out a little and get to a position to push the goal into the corner
        this.moveBackwards(10, speed);
        this.moveLeft(20, speed);
        this.moveForwards(20, speed);

        // Push the goal into the corner.
        this.moveRight(45, speed);

        // Move back to push the other goal.
        this.moveLeft(40, speed);
        this.moveBackwards(110, speed);

        // Push the other goal further to the wall, and reposition to get under it.
        this.moveRight(37, speed);
        this.moveLeft(10, speed);
        this.moveBackwards(15, speed);
        this.moveRight(25, speed);

        // Push the final goal to the top, then go down to park.
        this.moveForwards(115, speed);
        this.moveBackwards(40, speed);

    }

}
