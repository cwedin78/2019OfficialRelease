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
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    kp = .1;
    kd = 0.04;

    setpoint = 323;
    error = setpoint - Robot.arm.armEncoder.get();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.arm.armMotor.getBusVoltage() > Robot.arm.stallvalue){
      Robot.arm.spiked.start();
    }
    else {
      Robot.arm.spiked.stop();
      Robot.arm.spiked.reset();
    }

    Robot.arm.armMotor.set(Robot.arm.PIDSpeed(kp, kd, error));

  if(Robot.m_oi.operator.getRawButton(3) && Robot.arm.spiked.get() > Robot.arm.stalltime){
      Robot.arm.intake.set(-1);
    }
    else if (Robot.m_oi.operator.getRawButton(4)){
      Robot.arm.intake.set(1);
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