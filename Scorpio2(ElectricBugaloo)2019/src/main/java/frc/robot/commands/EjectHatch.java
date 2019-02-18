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

public class EjectHatch extends Command {

  public double highspeed, recoveryspeed, resetspeed, endhigh, endrecovery, stopticks, kp, kd, error;

  public Timer backed;
  
  public EjectHatch() {
    requires(Robot.release);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    backed = new Timer();
    backed.start();

    highspeed = 1;
    recoveryspeed = .2;
    resetspeed = .05;
    endhigh = 70;
    endrecovery = 140;
    stopticks = 216;

    kp =  0.02;
    kd = 0.003;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    error = stopticks - Robot.release.roter.get();


    Robot.release.thrower.set(Robot.release.PIDSpeed(kp, kd, error));

    if(Math.abs(error) > 2){
      backed.reset();
    }
    else{
      
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    return backed.get() > 0.4;
    }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.release.thrower.set(0);
   // Robot.release.roter.reset();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
   Robot.release.thrower.set(0); 
  }
}
