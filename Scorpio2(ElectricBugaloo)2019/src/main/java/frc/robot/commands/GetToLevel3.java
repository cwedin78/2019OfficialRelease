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
    kp = .01;
    kd = .003;

    setpoint = 92.84; //closest estimate as of now



  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    error = setpoint - Robot.lift.liftencoder.getPosition();

  double speed = Robot.lift.PIDSpeed(kp, kd, error);

  if(speed > 0){
    if (Robot.lift.liftencoder.getPosition() > Robot.lift.top || Robot.lift.upperlimit.get()){
    Robot.lift.lift.set(0);
    }
    else {
      Robot.lift.lift.set(speed);
    }
  }
  else {
    if(Robot.lift.liftencoder.getPosition() < Robot.lift.bottom || Robot.lift.lowerlimit.get()){
      Robot.lift.lift.set(0);
    }
    else {
      Robot.lift.lift.set(speed);
    }
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
