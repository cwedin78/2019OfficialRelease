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
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public WPI_TalonSRX armMotor, intake;
  public Encoder armEncoder;

  public double value, last_error;


  public Arm() {
  armMotor = new WPI_TalonSRX(6);
  intake = new WPI_TalonSRX(7);
  armEncoder = new Encoder(0, 1, false);

 
  
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
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
