/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
/**
 * @author Nikolai (AdmiralTyhard)
 */
public class StopLift extends Command {
  public double setpoint, error, speed, up, ud, dp, dd;

  public Timer stabled;
  public StopLift() {
    requires(Robot.lift);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    stabled = new Timer();
    up = Robot.lift.Up;
    ud = Robot.lift.Ud;
    dp = Robot.lift.Dp;
    dd = Robot.lift.Dd;
    stabled.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(stabled.get() < 0.33){
      speed = 0;
      setpoint = Robot.lift.liftencoder.getPosition();
    }
    else {
      error = setpoint - Robot.lift.liftencoder.getPosition();
      speed = Robot.lift.PIDSpeed(up, ud, dp, dd, error);
    }
    Robot.lift.lift.set(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
