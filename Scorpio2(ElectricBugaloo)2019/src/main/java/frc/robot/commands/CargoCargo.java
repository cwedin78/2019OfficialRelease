/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CargoCargo extends Command {
    
public double kP, kD;
public int error;
public CargoCargo() {
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    kP = 0.1;
    kD = 0.04;
    error = 126 - Robot.arm.armEncoder.get();
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

  if(Robot.m_oi.operator.getRawButton(3) && Robot.arm.spiked.get() > Robot.arm.stalltime){
    Robot.arm.intake.set(-1);
  }
  else if (Robot.m_oi.operator.getRawButton(4)){
    Robot.arm.intake.set(1);
  }
  else{
    Robot.arm.intake.set(0);
  }  
  Robot.arm.armMotor.set(Robot.arm.PIDSpeed(kP, kD, error));

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
