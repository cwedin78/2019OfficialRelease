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

public class OperatorLift extends Command {
  public double speed, setpoint, error, up, ud, dp, dd;

  public Timer stabled;
  public OperatorLift() {

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
    
    if(Math.abs(Robot.m_oi.operator.getThrottle()) < 0.3){
      if(stabled.get() < 0.33){
        speed = 0;
        setpoint = Robot.lift.liftencoder.getPosition();
      }
      else {
        error = setpoint - Robot.lift.liftencoder.getPosition();
        speed = Robot.lift.PIDSpeed(up, ud, dp, dd, error);
      }
    }
    else {
      speed = Robot.lift.GiveThrottle(0.3, Robot.m_oi.operator, false);
      stabled.reset();
      setpoint = Robot.lift.liftencoder.getPosition();
    }
  //  Robot.lift.lift.set(speed);
    
    if(speed > 0){
      if (Robot.lift.liftencoder.getPosition() > Robot.lift.top){ //|| Robot.lift.upperlimit.get()){
      Robot.lift.lift.set(0);
      }
      else {
        Robot.lift.lift.set(speed);
      }
    }
    else if (speed < 0) {
      if(Robot.lift.liftencoder.getPosition() < Robot.lift.bottom){//{ || Robot.lift.lowerlimit.get()){
        Robot.lift.lift.set(0);
      }
      else {
        Robot.lift.lift.set(speed);
      }
    }
    else {}
    
    }


  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lift.lift.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.lift.lift.set(0);
  }
}
