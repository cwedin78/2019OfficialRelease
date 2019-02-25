/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Nikolai (AdmiralTyhard)
 */
public class HatchRelease extends Subsystem {

  public WPI_TalonSRX thrower;

  public Encoder roter;

  public double value, last_error;

  public HatchRelease(){

    thrower = new WPI_TalonSRX(9);
    thrower.setInverted(false);

    roter = new Encoder(2, 3, false);

  }
  /**
 * @param source What encoder are you using?
 * @param highSpeed The highest speed it will go
 * @param recSpeed Avrage speed it will go
 * @param resetSpeed The speed that slows it down
 * @param highTick This lowest the tick shoulc go
 * @param recTick The avrage tick in the middle
 * @param resetTick the highest the tick should be 
 *
 * 
 */

public double ejectspeed(Encoder source, double highSpeed, double recSpeed, double resetSpeed, double highTick, double recTick, double resetTick){

  if(source.get() <= highTick){
      return highSpeed;
  }
  if(source.get() > highTick && source.get() <= recTick){
      return recSpeed;
  }
  if(source.get() > recTick){
      return resetSpeed;
  }
    else{
      return 0;
    }
}

/**
 * This is a manual pid loop where you can set the P and D values
 * @param kP (the coefficients for the proportional part of PID)
 * @param kD (the coefficients for the derivative part of PID)
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



  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
