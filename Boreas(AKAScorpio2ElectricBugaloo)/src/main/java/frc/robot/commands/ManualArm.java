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

public class ManualArm extends Command {
  public double setpoint, error, kp, kd, armspeed;

  public Timer spikedtime, stable;
public ManualArm() {
  requires(Robot.arm);
  requires(Robot.pdp);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    spikedtime = new Timer();
    stable = new Timer();
    kp = Robot.arm.anglep;
    kd = Robot.arm.angled;
    stable.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   
   if(Math.abs(Robot.m_oi.operator.getY()) < 0.3){
    if(stable.get() < 0.33){
      armspeed = 0;
      setpoint = Robot.arm.armEncoder.get();
    }
    else {
      error = setpoint - Robot.arm.armEncoder.get();
    armspeed = Robot.arm.PIDSpeed(kp, kd, error);
    }
    
   }
   else{
     armspeed = Robot.arm.CalculateControllerValue(0.3, Robot.m_oi.operator, true, "Y");
     setpoint = Robot.arm.armEncoder.get();
     stable.reset();
   }


    if(Robot.pdp.board.getCurrent(8) > Robot.arm.stallvalue){
      Robot.arm.spiked.start();
    }
    else {
      Robot.arm.spiked.reset();
    }
 
  if(Robot.m_oi.operator.getRawButton(3) && Robot.arm.spiked.get() < Robot.arm.stalltime){
      Robot.arm.intake.set(Robot.arm.suck);
    }
    else if (Robot.m_oi.operator.getRawButton(5)){
      Robot.arm.intake.set(Robot.arm.shoot);
    }
    else{
      Robot.arm.intake.set(0);
    } 
    if(Robot.m_oi.operator.getRawButton(8)){
      Robot.arm.intake.set(1);
    }
    else {
      Robot.arm.intake.set(0);
    }

    if(armspeed < 0 && Robot.arm.armEncoder.get() <= Robot.arm.botlimit){
      Robot.arm.armMotor.set(0);
    }
    else if (armspeed > 0 && Robot.arm.armEncoder.get() >= Robot.arm.groundlimit){
      Robot.arm.armMotor.set(0);
    }
    else{
    Robot.arm.armMotor.set(armspeed);
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
