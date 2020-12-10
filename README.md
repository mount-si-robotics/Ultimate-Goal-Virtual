# Ultimate Goal (2020-2021) competition virtual season code

## Welcome!
This GitHub repository contains the source code for Mount Si Robotics club's Ultimate Goal *FIRST* Tech Challenge competition virtual robot. We will be using this repo for writing and testing code prior to physical robot testing.

## Getting Started

Usage instructions (from original repo):

  1. Install IntelliJ IDEA.
  2. <code>git clone https://github.com/mount-si-robotics/Ultimate-Goal-Virtual.git</code>.
  3. Write your OpModes in the org.firstinspires.ftc.teamcode package; make sure to include a @TeleOp or @Autonomous annotation.
     These must extend the OpMode class (may either extend OpMode OR LinearOpMode). OpMode must provide init() and loop() methods;
     LinearOpMode must provide runOpMode() method.
  4. If using gamepads: Make sure at least one gamepad is plugged in to the computer (or use virtual gamepad).
  5. Run the application (by clicking the green arrowhead at the toolbar).
  6. Press start-A or start-B on gamepad(s) to select which is gamepad1 vs. gamepad2.
  7. Use Configuration dropdown box to select a robot configuration. The configuration will be displayed.
  8. Use the Op Mode drop down box to select the desired OpMode.
  9. Prior to initialization, position the robot on the field by left-mouse-clicking the field (for robot position),
     and right-mouse-clicking (for robot orientation). This must be done with 3D camera in overhead view (center button).
  10. Use the INIT/START/STOP button as you would on the FTC Driver Station.
  11. Use the Camera buttons on the left to change the position of the 3D camera. Also once you've selected any
      camera position other than the overhead view, you can use mouse-drag to reposition camera: Left-drag to
      move camera around field, Alt-Left-drag to zoom in/out, and Right-drag to pan side to side or up-down.
  12. If desired use the sliders to introduce random and systematic motor error, and inertia.

## Info

A 3D simulator to help beginning Java programmers learn to program for FTC Robotics.

This uses the [FTC VR physics simulator from Beta8397](https://github.com/Beta8397/vr_physics).

UltimateBot (for the Ultimate Goal game)
Four mecanum wheels. Distance between the centers of front and back wheels is 14 inches, and
distance between the centers of left and right wheels is 16 inches. Each has a downward-facing color sensor at the
center of the robot, distance sensors on all four sides, and a BNO055 IMU. Wheel diameters for both robots are
4 inches. Each robot is 18 inches wide.

UltimateBot has a wheeled intake and a ring-shooting mechanism in front. The intake is powered by a DC motor. The
shooter is powered by a DC motor, with triggering by a servo. The vertical shooting angle is controlled by a servo
as well.

The field can be thought of as 12 feet wide. The field graphic is obtained from a bitmap (.bmp) image. The color sensor detects the field color beneath the center of the robot.