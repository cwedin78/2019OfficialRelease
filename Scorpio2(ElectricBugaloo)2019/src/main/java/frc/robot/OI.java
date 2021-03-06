/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.CargoCargo;
import frc.robot.commands.ManualArm;
import frc.robot.commands.RocketCargo;
import frc.robot.commands.SpeedShot;
import frc.robot.commands.StopLift;
import frc.robot.commands.StraightArm;
import frc.robot.commands.leaveStation;
import frc.robot.commands.stopHatch;
import frc.robot.commands.ApproachLevelOne;
import frc.robot.commands.ApproachLevelTwo;
import frc.robot.commands.ArmZero;
import frc.robot.commands.CenterTarget;
import frc.robot.commands.DriveRelease;
import frc.robot.commands.EjectHatch;
import frc.robot.commands.FullRelease;
import frc.robot.commands.GetToLevel3;
import frc.robot.commands.GroundCargo;
import frc.robot.commands.OperatorLift;
import frc.robot.commands.ResetArmEncoder;
import frc.robot.commands.ResetCam;
import frc.robot.commands.LevelClimb;
import frc.robot.commands.ManualWinch;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

public Joystick driver, operator;

public JoystickButton d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12, du, dr, dl, dd, ou, ol, or, od;

public static keyboard board;

public OI(){

  driver = new Joystick(0);
  operator = new Joystick(1);

  board = new keyboard();

    d1 = new JoystickButton(driver, 1);
    d2 = new JoystickButton(driver, 2);
    d3 = new JoystickButton(driver, 3);
    d4 = new JoystickButton(driver, 4);
    d5 = new JoystickButton(driver, 5);
    d6 = new JoystickButton(driver, 6);
    d7 = new JoystickButton(driver, 7);
    d8 = new JoystickButton(driver, 8);
    d9 = new JoystickButton(driver, 9);
    d10 = new JoystickButton(driver, 10);
    d11 = new JoystickButton(driver, 11);
    d12 = new JoystickButton(driver, 12);
  

    o1 = new JoystickButton(operator, 1);
    o2 = new JoystickButton(operator, 2);
    o3 = new JoystickButton(operator, 3);
    o4 = new JoystickButton(operator, 4);
    o5 = new JoystickButton(operator, 5);
    o6 = new JoystickButton(operator, 6);
    o7 = new JoystickButton(operator, 7);
    o8 = new JoystickButton(operator, 8);
    o9 = new JoystickButton(operator, 9);
    o10 = new JoystickButton(operator, 10);
    o11 = new JoystickButton(operator, 11);
    o12 = new JoystickButton(operator, 12);
    


    o1.whenPressed(new leaveStation());
    o2.whenPressed(new ApproachLevelOne());
    o4.whileHeld(new CargoCargo());
    o4.whenReleased(new ManualArm());
    o6.whileHeld(new RocketCargo());
    o6.whenReleased(new ManualArm());
    o7.whileHeld(new OperatorLift());
    o7.whenReleased(new StopLift());
    o9.whileHeld(new StraightArm());
    o9.whenReleased(new ManualArm());
    o10.whileHeld(new GroundCargo());
    o10.whenReleased(new ManualArm());
    o11.whenPressed(new ApproachLevelTwo());
    o12.whenPressed(new GetToLevel3());    

    d2.whileHeld(new CenterTarget());
    d3.whileHeld(new ManualWinch());
   // d4.whileHeld(new LevelClimb());
    d5.whenPressed(new FullRelease());
    d6.whenPressed(new DriveRelease());
    d7.whileHeld(new ResetCam());
    d8.whileHeld(new ResetArmEncoder()); 
    
    d11.whenPressed(new ArmZero());

  }

     

 
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
