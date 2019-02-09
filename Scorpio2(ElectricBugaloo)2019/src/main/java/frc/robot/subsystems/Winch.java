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
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;


/**
 * Add your docs here.
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
  winchEncoder = new Encoder(4, 5, false);

  highlimit = 91200;
  lowlimit = -5;

  try {
    navx = new AHRS(I2C.Port.kMXP);
  } catch (RuntimeException ex) {
    DriverStation.reportError("Error instantating navX-MXP: " + ex.getMessage(), true);
  }
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
