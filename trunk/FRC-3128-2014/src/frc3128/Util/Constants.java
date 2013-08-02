package frc3128.Util;

/**
 *
 * @author Noah Sutton-Smolin
 */
public class Constants {
	public static final int DEFAULT_LOGLEVEL = 3; //Controls the standard logging level for the DebugLog
	public static final boolean EVENT_DUPLICATE_CHECKS = true; //Whether or not the EventManager runs duplicate checks
	public static final boolean START_CONTROL_ON_TARGETSET = true; //Whether a MotorLink should start its controller if the controller's value was set
	
	private Constants() {}
}