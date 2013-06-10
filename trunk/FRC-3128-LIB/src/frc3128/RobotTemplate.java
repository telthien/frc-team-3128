package frc3128;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Watchdog;
import frc3128.EventManager.EventManager;
import frc3128.EventManager.ListenerManager;

/* 
 * THIS FILE SHOULD NOT BE MODIFIED
 * --------------------------------
 * It serves as a link to the Global class
 * Events triggered here will be forwarded to the Global class
 * This will also invoke the EventManager's sequencing. Very critical.
 * 
 * AUTOGENERATED. DO NOT EDIT UNDER PENALTY OF 42.
 */
public class RobotTemplate extends IterativeRobot {
    public void robotInit() {
		DebugLog.setLogLevel(3);
        Global.initializeRobot();
    }

    public void disabledInit() {
        Global.initializeDisabled();
    }

    boolean autonomousHasBeenInit = false;
    public void autonomousInit() {
        if(!autonomousHasBeenInit) {
			EventManager.dropAllEvents(); ListenerManager.dropAllListeners();
            Global.initializeAuto(); 
            autonomousHasBeenInit = true; 
            teleopHasBeenInit = false;
        }
    }
    
    boolean teleopHasBeenInit = false;
    public void teleopInit() {
        if(!teleopHasBeenInit) {
            Global.initializeTeleop(); 
            teleopHasBeenInit = true; 
            autonomousHasBeenInit = false;
        }
    }
    
    public void disabledPeriodic() {        
        Watchdog.getInstance().feed();
        EventManager.processEvents();
    }

    public void autonomousPeriodic() {
        Watchdog.getInstance().feed();
        EventManager.processEvents();
    }

    public void teleopPeriodic() {
        Watchdog.getInstance().feed();
        EventManager.processEvents();
    }
}