

import java.util.Calendar;

/**
 * This class represents a phone line with the call center simulation. 
 * Each phone line is assigned an identification number based on the 
 * order in which it is created. The required parameters for creating 
 * a PhoneLine object include: a reference to the Log, to keep track 
 * of customer statistics, and a clock, used for calculating and 
 * recording hold times and active times. The PhoneLine object is 
 * used by the PhoneManager to connect customers to employees. A 
 * customer is placed on the phone line to await connection to an 
 * employee designated by the phone management system. The PhoneLine 
 * records general hold times and phone line specific active times 
 * with the Log object.
 * 
 * @author Brett Crawford
 */
public class PhoneLine {
	
	private static int lastID = 0;
	
	private int id;
	private int customerID;
	private int expectedCallLength;
	private Calendar holdTimeStart;
	private CallParticipant customer;
	private CallParticipant employee;
	private Clock clock;
	private Log log;
	
	/**
	 * Creates a PhoneLine object with the specified parameters.
	 * 
	 * @param theLog
	 *            the log object for recording events/statistics
	 * @param theClock
	 *            the clock used to manage time 
	 */
	public PhoneLine(Log theLog, Clock theClock) {
		id = ++lastID;
		customerID = 0;
		customer = null;
		expectedCallLength = 0;
		holdTimeStart = null;
		employee = null;
		clock = theClock;
		log = theLog;
	}
	
	/**
	 * Returns true if the phoneline is available, otherwise returns false.
	 * 
	 * @return boolean
	 *            true if available, otherwise false         
	 */
	public boolean isAvailable() {
		boolean flag = true;
		if(customer != null)
			flag = false;
		return flag;
	}
	
	/**
	 * Returns true if the phoneline is on hold, otherwise returns false.
	 * 
	 * @return boolean
	 *            true if on hold, otherwise false         
	 */
	public boolean isOnHold() {
		boolean flag = false;
		if(customer != null && employee == null)
			flag = true;
		return flag;
	}
	
	/**
	 * Returns the ID of the customer current on the phoneline
	 * 
	 * @return int
	 *            the ID of the customer on the phoneline      
	 */
	public int getCustomerID() {
		return customerID;
	}
	
	/**
	 * Returns the expected length of the call on the phoneline
	 * 
	 * @return int
	 *            the expected length of the call on the phoneline        
	 */
	public int getExpectedCallLength() {
		return expectedCallLength;
	}
	
	/**
	 * Connects the customer and their information to the phoneline. Begins 
	 * recording the total time on hold.
	 * 
	 * @param theCustomer
	 *            the customer to be connected
	 * @param theCustomerID
	 *            the ID of the customer
	 * @param theExpectedCallLength
	 *            the expected length of the call        
	 */
	public void connectCustomer(CallParticipant theCustomer, int theCustomerID, int theExpectedCallLength) {
		customerID = theCustomerID;
		customer = theCustomer;
		expectedCallLength = theExpectedCallLength;
		holdTimeStart = (Calendar) clock.getTime().clone();
	}
	
	/**
	 * Allows the customer to hang up the phoneline while waiting on hold. 
	 * Total hold time is recorded and logged.
	 */
	public void hangUp() {
		customerID = 0;
		customer = null;
		expectedCallLength = 0;
		log.recordHangUp();
		int holdTime = clock.getTime().get(Calendar.MINUTE) - holdTimeStart.get(Calendar.MINUTE);
		if(holdTime < 0)
			holdTime = holdTime + 60;
		log.recordHoldTime(holdTime);
		log.recordPhoneLineActiveTime(id, holdTime);
		holdTimeStart = null;
	}
	
	/**
	 * Connects the employee to the phoneline. Records and logs the 
	 * customers hold time on the line.
	 * 
	 * @param theEmployee
	 *            the employee to be connected       
	 */
	public void connectEmployee(CallParticipant theEmployee) {
		employee = theEmployee;
		int holdTime = clock.getTime().get(Calendar.MINUTE) - holdTimeStart.get(Calendar.MINUTE);
		if(holdTime < 0)
			holdTime = holdTime + 60;
		log.recordHoldTime(holdTime);
		log.recordPhoneLineActiveTime(id, holdTime);
		holdTimeStart = null;
	}
	
	/**
	 * Allows the employee to end a call. The phoneline is returned to an available state.
	 * 
	 * @param theActualCallLength
	 *            the actual length of the call       
	 */
	public void endCall(int theActualCallLength) {
		log.recordPhoneLineActiveTime(id, theActualCallLength);
		employee.callCompleted();
		customer.callCompleted();
		customerID = 0;
		customer = null;
		expectedCallLength = 0;
		employee = null;
	}

}
