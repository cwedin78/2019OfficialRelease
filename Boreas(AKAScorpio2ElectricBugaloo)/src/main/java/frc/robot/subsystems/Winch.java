/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.sql.DriverAction;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.OperatorLift;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;


/**
 * @author Nikolai (AdmiralTryhard)
 */
public class Winch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

public WPI_TalonSRX winchMotor;
public Encoder winchEncoder;
public AHRS navx;
public double value, last_error;
public static double kP, kD, error;

public double highlimit, lowlimit;

public Winch() {


  winchMotor = new WPI_TalonSRX(10);
  winchMotor.setInverted(true);
  winchEncoder = new Encoder(4, 5, false);

  highlimit = 11612; 
  lowlimit = -5;

  try {
    navx = new AHRS(I2C.Port.kMXP);
  } catch (RuntimeException ex) {
    DriverStation.reportError("Error instantating navX-MXP: " + ex.getMessage(), true);
  }
}


  /**
 * This is a manual pid loop where you can set the P and D values
 * @param kP (the coefficients for the proportional part of PID)
 * @param kD (the coefficients for the derivative part of PID)
 * @param error (the source of error for the PID loop)
 * 
 */
public double PIDSpeed(double kP, double kD, double error, boolean Isinverted){

  value = (kP * error) + (kD * (error - last_error) / 0.05);
  if(!Isinverted){
    value = value;
  }
  else {
    value = value * -1;
  }

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

/**
 * Modified from calculatecontrollervalue, this method simply has deadzones for your throttle for more precise control
 * @param deadzone (make it so a simple touch doesn't do anything)
 * @param controllertype (which controller are you using)
 * @param inverted (whether or not you need to flip the controller input)
 */


public double GiveThrottle(double deadzone, Joystick controllertype, boolean inverted){
  double input;
  double returnvalue;

input = controllertype.getThrottle();
  
if(inverted){
  input = input * -1;
}
else{}

//DZ

if (Math.abs(input)< deadzone){
  returnvalue = 0;
}
else{
  returnvalue = Math.signum(input) * ((Math.abs(input) - deadzone) *(1/1 - deadzone));
}
return returnvalue;
}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
