

import java.util.*;

/**
 * This class represents an accelerated clock which will manage the passage 
 * of time within objects implementing from the ClockManaged interface.  
 * ClockManaged objects are contained within the class by using the 
 * addManagedItem method and are updated with the current time on each 
 * call to the countTick method. 
 * 
 * @author Brett Crawford
 */
public class Clock {
	
	private final int SECS_PER_TICK = 10;
	private Calendar currentTime;
	private ArrayList<ClockManaged> managedClocks;
	
	/**
	 * Creates a Clock object. The time is set to
	 * 8:00am.
	 */
	public Clock() {
		currentTime = Calendar.getInstance();
		currentTime.set(Calendar.HOUR_OF_DAY, 8);
		currentTime.set(Calendar.MINUTE, 0);
		currentTime.set(Calendar.SECOND, 0);
		currentTime.set(Calendar.MILLISECOND, 0);
		
		managedClocks = new ArrayList<ClockManaged>();
	}
	
	/**
	 * Adds a ClockManaged item to the list of items to be clock managed
	 * 
	 * @param newManagedItem
	 *            the ClockManaged item to be added
	 */
	public void addManagedItem(ClockManaged newManagedItem) {
		managedClocks.add(newManagedItem);
	}
	
	/**
	 * Simulates the passage of time by the the amount specified by
	 * SECS_PER_TICK. 
	 */
	public void countTick() {
		currentTime.add(Calendar.SECOND, SECS_PER_TICK);
		
		for(int i = 0; i < managedClocks.size(); i++) {
			managedClocks.get(i).clockTicked(currentTime);
		}
	}
	
	/**
	 * Creates a BinaryTreeNode object with the specified key and
	 * data. 
	 * 
	 * @return Calendar
	 * 			the clock objects current time
	 */
	public Calendar getTime() {
		return currentTime;
	}
	
	
	

}
