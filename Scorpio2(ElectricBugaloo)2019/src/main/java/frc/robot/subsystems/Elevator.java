/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.OperatorLift;


public class Elevator extends Subsystem {

public DigitalInput lowerlimit, upperlimit;

public double value, last_error, controlDZ, bottom, top, Up, Ud, Dp, Dd;

public CANSparkMax lift;

public CANEncoder liftencoder;

  
  public Elevator(){

    lift = new CANSparkMax(8, MotorType.kBrushless);
    //the gear ratio is 7:1
    //with the shaft average radius (it's a hex), it should be roughly .75 inches per rotation of the NEO
    liftencoder = new CANEncoder(lift);

    lowerlimit = new DigitalInput(6);
    upperlimit = new DigitalInput(7);
    
    controlDZ = 0.3;
    bottom = -0.5;
    top = 88.5;

    Up = 0.06;
    Ud = 0.015;

    Dp = 0.01;
    Ud = 0.009;
  }


  /**
 * This is a manual pid loop where you can set the P and D values
 * @param uP (the coefficients for the proportional part of PID) (when error > 0)
 * @param uD (the coefficients for the derivative part of PID) (when error > 0)
 * @param uP (the coefficients for the proportional part of PID) (when error < 0)
 * @param uD (the coefficients for the derivative part of PID) (when error < 0)
 * @param error (the source of error for the PID loop)
 * 
 */
public double PIDSpeed(double uP, double uD, double dP, double dD, double error){

if (error > 0){
    value = (uP * error) + (uD * (error - last_error) / 0.05);

}
else{
  value = (dP * error) + (dD * (error - last_error) / 0.05);

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

//DZ

if (Math.abs(input)< deadzone){
  returnvalue = 0;
}
else{
  returnvalue = Math.signum(input) * ((Math.abs(input) - deadzone) *(1/1 - deadzone));
}
return returnvalue;
}

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
