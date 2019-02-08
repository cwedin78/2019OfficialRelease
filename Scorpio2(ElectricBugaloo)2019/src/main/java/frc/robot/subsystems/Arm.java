/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ManualArm;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Timer spiked;
  public WPI_TalonSRX armMotor, intake;
  public Encoder armEncoder;

  public double value, last_error, stallvalue, stalltime;


  public Arm() {

  armMotor = new WPI_TalonSRX(6);
  intake = new WPI_TalonSRX(7);
  armEncoder = new Encoder(0, 1, false);

  stallvalue = 12;
  stalltime = .5;

  spiked = new Timer();
  }
/**
 * configures a controller input throught the X,Y, or Z axes, and combines them with the throttle.
 * This creates a precise control with the use of deadzones and a precision scale.
 * @param deadzone (make it so a simple touch doesn't do anything)
 * @param minimumscale (the slowest you want things to go)
 * @param maximumscale (the fastest)
 * @param controllertype (which controller are you using)
 * @param controllerinput (It's either "X", "Y", or "Z" for the three axes on a controller. Use caps, and put quotes) If X, Y, or Z are not chosen, it is defaulted to Z
 * @param inverted (whether or not you need to flip the controller input)
 */


public double CalculateControllerValue(double deadzone, double minimumscale, double maximumscale, Joystick controllertype, boolean inverted, String controllerinput ){
  double input;
  double returnvalue;

  if (controllerinput == "X") {
    input = controllertype.getX();
  }
  else if (controllerinput == "Y"){
    input = controllertype.getY();
  }
  else{
    input = controllertype.getZ();
  }
if(inverted){
  input = input * -1;
}
//this is drive code
boolean pTrig = controllertype.getTrigger();
double pMag = (controllertype.getThrottle() +1) /2;
double pScale;

//DZ
if (pTrig){
  pScale = 1;
}
else{
  pScale = (pMag + (maximumscale - minimumscale) + minimumscale);
}
if (Math.abs(input)< deadzone){
  returnvalue = 0;
}
else{
  returnvalue = Math.signum(input) * pScale * ((Math.abs(input) - deadzone) *(1/1 - deadzone));
}
return returnvalue;
}

  /**
 * This will be a much easier way to call PID loops using the drive
 * This method will likely be found on other subsystems soon enough
 * @param kP (the constant for the proportional part of PID)
 * @param kD (the constant for the derivative part of PID)
 * @param error (the source of error for the PID loop)
 * 
 */
public double PIDSpeed(double kP, double kD, double error){


  value = (kP * error) + (kD * (error - last_error) / 0.05);

  last_error = error;

  if (value > 1){
    return 1;
  }
  else if (value < -1){
    return -1;
  }
  else {
 return value;
  }
}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualArm());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
