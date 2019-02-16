/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LevelClimb extends Command {
  public double kP, kD;
  public LevelClimb() {

    requires(Robot.winch);
    requires(Robot.arm);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    kP = 0.1;
    kD = 0.02;
 
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = Robot.winch.PIDSpeed(kP, kD, Robot.winch.navx.getRoll());

    Robot.arm.armMotor.set(Robot.arm.CalculateControllerValue(0.3, Robot.m_oi.operator, true, "Y"));

    Robot.winch.winchMotor.set(speed);
    }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.winch.winchEncoder.get() <= Robot.winch.lowlimit && Robot.winch.PIDSpeed(kP, kD, Robot.winch.navx.getRoll()) < 0 || Robot.winch.winchEncoder.get() >= Robot.winch.highlimit && Robot.winch.PIDSpeed(kP, kD, Robot.winch.navx.getRoll()) > 0;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.winch.winchMotor.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.winch.winchMotor.set(0);

  }
}
