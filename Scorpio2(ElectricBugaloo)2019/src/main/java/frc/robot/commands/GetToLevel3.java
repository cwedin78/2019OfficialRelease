/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class GetToLevel3 extends Command {

  public double error, kp, kd, setpoint;

  public GetToLevel3() {
    requires(Robot.lift);

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    kp = .05;
    kd = .03;

    setpoint = 92.84; //closest estimate as of now

    error = setpoint - Robot.lift.liftencoder.getPosition();


  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    if (Robot.lift.liftencoder.getPosition() <= setpoint + 1){
      Robot.lift.lift.set(Robot.lift.PIDSpeed(kp, kd, error));
      }
  
      else {
        Robot.lift.lift.set(0);
      }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.m_oi.operator.getThrottle()) > Robot.lift.controlDZ;
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
