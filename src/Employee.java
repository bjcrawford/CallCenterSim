

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * This class represents an employee within the call center simulation. Similar 
 * to the Customer class, every employee is assigned an identification number 
 * based on the order in which it is created. The required parameters for 
 * creating an Employee object include: a reference to the Log, to keep track 
 * of customer statistics, and a float, to assign a performance factor to the 
 * employee. The performance factor determines the efficiency of the employee 
 * at taking customer service calls. A performance factor of 1 represents an 
 * average efficiency, finishing a customer’s call in the same amount of time 
 * as the customer’s expected call duration. A lower performance factor will 
 * result in an employee finishing customers’ calls at a faster than normal 
 * rate (i.e., a performance factor of 0.5 will result in an employee finishing 
 * the call in half the time). Conversely, a higher performance factor will 
 * result in an employee finishing customers’ call at a slower than normal 
 * rate. When a call is taken by an employee, the call length is generated 
 * using the performance factor and the employee will remain on the line 
 * until the call completion time has occurred. The call will then be completed 
 * and the employee will be returned to available. The employee records 
 * individual call lengths with the Log object in addition to the event 
 * occurrences, call initiated and call completed.
 * 
 * @author Brett Crawford
 */
public class Employee implements ClockManaged, CallParticipant{
	
	private static int lastID = 0;
	private static DecimalFormat fmt2 = new DecimalFormat("00");
	private static DecimalFormat fmt3 = new DecimalFormat("000");
	
	private int id;
	private boolean available;
	private float performanceFactor;
	private int callLength;
	private Log log;
	private Calendar callCompletionTime;
	private PhoneLine phoneLine;
	
	/**
	 * Creates an Employee object with the specified parameters.
	 * 
	 * @param theLog
	 *            the log object for recording events/statistics
	 * @param thePerformanceFactor
	 *            a float representing the effectiveness of an employee     
	 */
	public Employee(Log theLog, float thePerformanceFactor) {
		id = ++lastID;
		available = true;
		performanceFactor = thePerformanceFactor;
		callLength = 0;
		log = theLog;
		callCompletionTime = null;
		phoneLine = null;
	}
	
	/**
	 * Returns true if the employee is available to take a call, 
	 * otherwise returns false.
	 * 
	 * @return boolean
	 *            true if available, otherwise false         
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * Simulates the passage of time using the time parameter received
	 * 
	 * @param currentTime
	 *            the current time within the simulation         
	 */
	public void clockTicked(Calendar currentTime) { // If the employee has taken a call, but not yet set the completion time
		
		if(available == false && callCompletionTime != null) {
			if(currentTime.after(callCompletionTime)) {
				phoneLine.endCall(callLength);
			}
		}
		
		if(available == false && callCompletionTime == null) {
			callCompletionTime = (Calendar) currentTime.clone(); 
			callLength = (int) (phoneLine.getExpectedCallLength() * performanceFactor);
			log.recordEvent("  Employee ID: " + fmt3.format(id) + " - " + fmt2.format(callLength) + 
					   " minute service call initiated with Customer ID: " + fmt3.format(phoneLine.getCustomerID()) + ".\n");
			callCompletionTime.add(Calendar.MINUTE, callLength); // Set the completion time
		}
	}
	
	/**
	 * Assigns a phoneline to the employee.
	 * 
	 * @param thePhoneLine
	 *            the phoneline to be assigned        
	 */
	public void takeCall(PhoneLine thePhoneLine){
		available = false;
		phoneLine = thePhoneLine;
		phoneLine.connectEmployee(this);
	}
	
	/**
	 * Completes the employee's current call. The phoneline is released
	 * and the employee is returned to an available state.
	 * 
	 * @param thePhoneLine
	 *            the phoneline to be assigned        
	 */
	public void callCompleted() {
		log.recordEvent("    Employee ID: " + fmt3.format(id) + " - " + fmt2.format(callLength) + 
				   " minute service call completed with Customer ID: " + fmt3.format(phoneLine.getCustomerID()) + ".\n");
		log.recordSuccessfulCall();
		log.recordEmployeeActiveTime(id, callLength);
		available = true;
		callLength = 0;
		callCompletionTime = null;
		phoneLine = null;
	}
}
