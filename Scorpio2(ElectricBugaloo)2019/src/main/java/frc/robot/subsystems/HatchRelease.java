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
 * Add your docs here.
 */
public class HatchRelease extends Subsystem {

  public WPI_TalonSRX thrower;

  public Encoder roter;

  public HatchRelease(){

    thrower = new WPI_TalonSRX(9);

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

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
