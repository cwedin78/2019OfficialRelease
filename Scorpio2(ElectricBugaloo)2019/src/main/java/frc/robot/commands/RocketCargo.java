/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RocketCargo extends Command {
  public double setpoint, error, kp, kd;
  public RocketCargo() {
    
    requires(Robot.arm);
    requires(Robot.pdp);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
 
    kp = Robot.arm.anglep;
    kd = Robot.arm.angled;

    setpoint = 485;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    error = setpoint - Robot.arm.armEncoder.get();

    double armspeed = Robot.arm.PIDSpeed(kp, kd, error);

    if(Robot.pdp.board.getCurrent(8) > Robot.arm.stallvalue){
      Robot.arm.spiked.start();
    }
    else {
      Robot.arm.spiked.stop();
      Robot.arm.spiked.reset();
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
  

  if(Robot.m_oi.operator.getRawButton(3) && Robot.arm.spiked.get() < Robot.arm.stalltime){
      Robot.arm.intake.set(Robot.arm.suck);
    }
    else if (Robot.m_oi.operator.getRawButton(5)){
      Robot.arm.intake.set(Robot.arm.shoot);
    }
    else{
      Robot.arm.intake.set(0);
    }
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.m_oi.operator.getY()) > .3;
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
