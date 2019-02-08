/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ApproachLevelOne extends Command {
  public double error, kp, kd;
  public ApproachLevelOne() {
    requires(Robot.lift);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    

    error = 0 - Robot.lift.liftencoder.getPosition();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(error > .5 && Robot.lift.PIDSpeed(kp, kd, error) < 0){
      Robot.lift.lift.set(0);
    }
    else {
      Robot.lift.lift.set(Robot.lift.PIDSpeed(kp, kd, error));
    }
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
