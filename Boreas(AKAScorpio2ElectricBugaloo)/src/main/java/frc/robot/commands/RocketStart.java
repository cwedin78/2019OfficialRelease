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
/**
 * @author Nikolai (AdmiralTyhard)
 */

public class RocketStart extends Command {
  public Timer osctime;

  public double kp, kd, setpoint, error;
  public RocketStart() {
    requires(Robot.release);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setpoint = 135;

    kp = 0.055;
    kd = 0.025;
    osctime = new Timer();
    osctime.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    error = setpoint - Robot.release.roter.get();

    Robot.release.thrower.set(Robot.release.PIDSpeed(kp, kd, error));
  
    if(Math.abs(error) > 10){
      osctime.reset();
    }
    else{

    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
  return osctime.get() > 0.4;
    //  return Robot.release.roter.get() > 150;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.release.thrower.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.release.thrower.set(0);
  }
}
