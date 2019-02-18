/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SpeedShot extends Command {
  public Timer osctime;

  public double kp, kd, setpoint, error;
  public SpeedShot() {
    requires(Robot.release);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setpoint = 152;

    kp = 0.05;
    kd = 0.02;
    osctime = new Timer();
    osctime.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    error = setpoint - Robot.release.roter.get();

    Robot.release.thrower.set(Robot.release.PIDSpeed(kp, kd, error));
  
   
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
  return Robot.release.roter.get() > 85;
    //  return Robot.release.roter.get() > 150;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
