
import java.util.*;

/**
 * This interface allows an object to be used in conjunction with the Clock 
 * class to manage the passage of time. Any object implementing the 
 * ClockManaged interface must implement the clockTicked method which 
 * should contain any code that needs to be run on each tick of the clock.
 * 
 * @author Brett Crawford
 */
public interface ClockManaged {
	
	public void clockTicked(Calendar currentTime);
}
