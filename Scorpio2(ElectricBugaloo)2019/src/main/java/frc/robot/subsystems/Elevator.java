/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;


public class Elevator extends Subsystem {

public double value, last_error;

public CANSparkMax lift;


public CANEncoder liftencoder;

  public Elevator(){

    lift = new CANSparkMax(7, MotorType.kBrushless);
    //the gear ratio is 7:1
    //with the shaft average radius (it's a hex), it should be roughly .75 inches per rotation of the NEO
    liftencoder = new CANEncoder(lift);


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
