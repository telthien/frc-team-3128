package frc3128;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Watchdog;
import frc3128.Connection.Connection;
import frc3128.Controller.XControl;
import frc3128.DriveTank.DriveTank;
import frc3128.EventManager.Event;
import frc3128.EventManager.EventManager;
import frc3128.ListenerManager.ListenerManager;
import frc3128.PneumaticsManager.PistonID;
import frc3128.PneumaticsManager.PneumaticsManager;
import frc3128.Targeting.TiltLock;

class DebugOutputs extends Event {
    public void execute() {
        DebugLog.log(4, referenceName, "X1:" + Global.xControl1.x1 + ", Y1:" + Global.xControl1.y1);
        DebugLog.log(4, referenceName, "Y2: " + Global.xControl1.y2);
        DebugLog.log(3, referenceName, "gTilt:" + Global.gTilt.getAngle() + ", gTurn:" + Global.gTurn.getAngle());
        DebugLog.log(3, referenceName, "mSpeed: " + Global.mShoot.get() + " mSpeed2: " + Global.mShoot2.get());
    }
}

public class Global {
    public final static String referenceName = "Global";
    public final static EventManager eventManager = new EventManager();
    public static XControl xControl1;
    
    public final static PneumaticsManager pnManager = new PneumaticsManager(1, 1, 1, 2);
    public final static PistonID pstFire = PneumaticsManager.addPiston(1, 1, 1, 2, true, false);
    public final static PistonID pst2 = PneumaticsManager.addPiston(1, 3, 1, 4, true, false);
    
    public static DriveTank driveTank;
    public final static Jaguar mLB = new Jaguar(1, 3);
    public final static Jaguar mRB = new Jaguar(1, 1);
    public final static Jaguar mLF = new Jaguar(1, 2);
    public final static Jaguar mRF = new Jaguar(1, 4);
    public final static Jaguar mTilt = new Jaguar(1, 6);
    public final static Jaguar mShoot = new Jaguar(1, 7);
    public final static Jaguar mShoot2 = new Jaguar(1, 8);
    
    public final static Gyro gTilt = new Gyro(1, 2);
    public final static Gyro gTurn = new Gyro(1, 1);
    public final static Relay camLight = new Relay(1, 1, Relay.Direction.kForward);

    public static void initializeRobot() {
        Global.gTilt.reset();
        Global.gTurn.reset();
        DebugLog.setLogLevel(5);
        PneumaticsManager.setCompressorStateOff();
        DebugLog.log(3, referenceName, "ROBOT INITIALIZATION COMPLETE");
    }

    public static void initializeDisabled() {
        PneumaticsManager.setCompressorStateOn();
    }

    public static void initializeAuto() {
        EventManager.dropEvents(); ListenerManager.dropListeners();
        Global.gTilt.reset(); Global.gTurn.reset();
        Global.camLight.set(Relay.Value.kOn);
        
        (new Connection()).registerIterableEvent();
    }

    public static void initializeTeleop() {
        EventManager.dropEvents(); ListenerManager.dropListeners();
        Global.gTilt.reset(); DebugLog.log(2, referenceName, "GTilt reset starting manual! **Remove for autonomous**");
        Global.camLight.set(Relay.Value.kOn);

        Global.xControl1 = new XControl(1);
        driveTank = new DriveTank();
        (new Connection()).registerIterableEvent();
    }

    public static void robotKill() {
        EventManager.dropEvents();
        ListenerManager.dropListeners();
        Watchdog.getInstance().kill();
    }
}