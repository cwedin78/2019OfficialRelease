/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CenterTarget extends Command {
public double kP, kD, forward, correction; 
public CenterTarget() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    requires(Robot.limelight);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    kP = 0.02;
    kD = 0.02;
    Robot.limelight.SetVisionProcessingMode(0, 0);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    forward = Robot.drivetrain.CalculateControllerValue(.3, .25, .75, Robot.m_oi.driver, true, "Y");

    correction = Robot.drivetrain.PIDSpeed(kP, kD, Robot.limelight.tx.getDouble(0.0));

    Robot.drivetrain.inputdrive(forward, correction);

    
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
