/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  public WPI_TalonSRX left0, left1, left2, right0, right1, right2;
  public SpeedControllerGroup left, right;
  DifferentialDrive drive;
  public double value, last_error;

  public DriveTrain(){
    left0 = new WPI_TalonSRX(0);
    left1 = new WPI_TalonSRX(1);
    left2 = new WPI_TalonSRX(2); 
    right0 = new WPI_TalonSRX(3);
    right1 = new WPI_TalonSRX(4);
    right2 = new WPI_TalonSRX(5);

    left1.setInverted(true);
    right1.setInverted(true);
    
    left = new SpeedControllerGroup(left0, left1, left2);
    right = new SpeedControllerGroup(right0, right1, right2);

    drive = new DifferentialDrive(left, right);

  }


  // Put methods for controlling this subsystem
  // here. Call these from Commands.

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


  
public void inputdrive(double forward, double twist){
  drive.tankDrive(forward, twist);
}



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


  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}