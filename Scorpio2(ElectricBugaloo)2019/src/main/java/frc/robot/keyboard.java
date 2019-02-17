/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Add your docs here.
 */

public class keyboard {
  public static boolean zero;

    public boolean getKeypress(){
      
        KeyListener key = new KeyListener(){
        
            @Override
            public void keyTyped(KeyEvent VK_0) {
                
            }
        
            @Override
            public void keyReleased(KeyEvent VK_0) {
                zero = false;
            }
        
            @Override
            public void keyPressed(KeyEvent VK_0) {
                zero = true;
            }
        };
        return zero;
    }
}
