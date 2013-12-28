

import java.util.*;

/**
 * This class represents the phone line/employee management system within the 
 * call center simulation. The required parameters for creating a PhoneManager 
 * object include: a reference to the Log, to keep track of call center 
 * statistics; a reference to the Clock, to allow time management of the 
 * phone lines and employees; and an integer, to represent the length call 
 * center shift. Once added using the addEmployee and addPhoneLine methods, 
 * the PhoneManager keeps references to all employees and phone lines used 
 * in the simulation. On each call to the interfaced clockTicked method, the 
 * PhoneManager determines if employees are available to take calls and if 
 * incoming calls are on hold. If so, employees will be paired with incoming 
 * calls until no more employees are available to take calls at that moment. 
 * Calls are taken on a first-come, first-served basis. When the allotted 
 * shift length has passed, the incoming lines are shut down, but calls that 
 * are currently in progress and calls that are waiting on hold are continued 
 * to be processed. When all calls have finished, the PhoneManager will shut 
 * down. The PhoneManager records opening and closing events of the call 
 * center and the overtime from the closing process using the Log object.
 * 
 * @author Brett Crawford
 */
public class PhoneManager implements ClockManaged {
	
	private boolean callCenterOpen;
	private boolean shiftComplete;
	private int shiftLength;
	private int overtime;
	private Clock clock;
	private Calendar closingTime;
	private Log log;
	private ArrayList<Employee> managedEmployees;
	private ArrayList<PhoneLine> managedPhoneLines;
	
	/**
	 * Creates a PhoneManager object with the specified parameters.
	 * 
	 * @param theLog
	 *            the log object for recording events/statistics
	 * @param theClock
	 *            the clock used to manage time
	 * @param theShiftLength
	 *            the length of the shift     
	 */
	public PhoneManager(Log theLog, Clock theClock, int theShiftLength) {
		callCenterOpen = true;
		shiftComplete = false;
		shiftLength = theShiftLength;
		overtime = 0;
		clock = theClock;
		closingTime = (Calendar) clock.getTime().clone();
		closingTime.add(Calendar.HOUR, shiftLength);
		log = theLog;
		log.recordEvent("Call center opened.\n");
		managedEmployees = new ArrayList<Employee>();
		managedPhoneLines = new ArrayList<PhoneLine>();
	}
	
	/**
	 * Adds an employee to the phone manager system
	 * 
	 * @param employee
	 *            the employee to be added to the phone manager    
	 */
	public void addEmployee(Employee employee) {
		clock.addManagedItem(employee);
		managedEmployees.add(employee);
	}
	
	/**
	 * Adds a phoneline to the phone manager system
	 * 
	 * @param phoneLine
	 *            the phoneline to be added to the phone manager    
	 */
	public void addPhoneLine(PhoneLine phoneLine) {
		managedPhoneLines.add(phoneLine);
	}
	
	/**
	 * Directs an incoming call into the phone manger system. Returns 
	 * an available phoneline from the system. If no phoneline is 
	 * available, a null PhoneLine is returned.
	 * 
	 * @param theCustomer
	 *            the customer calling the center
	 * @param theCustomerID
	 *            the customer's id
	 * @param theExpectedCallLength
	 *            the exppected length of the service call
	 * @return PhoneLine
	 *            an open phone is a line is available, otherwise null
	 */
	public PhoneLine directCall(Customer theCustomer, int theCustomerID, int theExpectedCallLength) {
		PhoneLine openPhoneLine = null;
		if(!areLinesFull() && callCenterOpen) {
			openPhoneLine = getNextAvailablePhoneLine();
			openPhoneLine.connectCustomer(theCustomer, theCustomerID, theExpectedCallLength);
		}
		return openPhoneLine;
	}
	
	/**
	 * Simulates the passage of time using the time parameter received
	 * 
	 * @param currentTime
	 *            the current time within the simulation         
	 */
	public void clockTicked(Calendar currentTime) {
		log.recordCallsOnHoldMax(getNumberOfLinesOnHold());
		
		while(isEmployeeAvailable() && areLinesOnHold()) {
				PhoneLine lineOnHold = getNextOnHoldPhoneLine();
				Employee availableEmployee = getNextAvailableEmployee();
				availableEmployee.takeCall(lineOnHold);
		}
		
		if(currentTime.after(closingTime)) {
			// Shut down incoming calls
			if(callCenterOpen) {
				callCenterOpen = false;
				log.recordEvent("Incoming phone lines shut down.\n");
			}
			if(areLinesClear()) {
				// Shut down phone manager
				shiftComplete = true;
				overtime = currentTime.get(Calendar.MINUTE);
				log.recordOvertime(overtime);
				log.recordEvent("Call center closed.\n");
			}
		}
	}
	
	/**
	 * Returns true if the call center is open, otherwise returns false.
	 * 
	 * @return boolean
	 *            true if open, otherwise false         
	 */
	public boolean isCallCenterOpen() {
		return callCenterOpen;
	}
	
	/**
	 * Returns true if the call center shift is complete, otherwise 
	 * returns false.
	 * 
	 * @return boolean
	 *            true if shift is complete, otherwise false         
	 */
	public boolean isShiftComplete() {
		return shiftComplete;
	}
	
	/**
	 * Returns true if an employee in the pool of employees is available
	 * to take a call, otherwise returns false.
	 * 
	 * @return boolean
	 *            true if an employee is available, otherwise false         
	 */
	private boolean isEmployeeAvailable() {
		boolean flag = false;
		for(Employee csr : managedEmployees) {
			if(csr.isAvailable())
				flag = true;
		}
		return flag;
	}
	
	/**
	 * Returns true if the pool of phonelines contains a line on hold, 
	 * otherwise returns false.
	 * 
	 * @return boolean
	 *            true if a line is waiting on hold, otherwise false         
	 */
	private boolean areLinesOnHold() {
		boolean flag = false;
		for(PhoneLine line : managedPhoneLines) {
			if(line.isOnHold())
				flag = true;
		}
		return flag;
	}
	
	/**
	 * Returns true if the pool of phonelines is full, otherwise returns 
	 * false.
	 * 
	 * @return boolean
	 *            true if phonelines are full, otherwise false         
	 */
	private boolean areLinesFull() {
		boolean flag = true;
		for(PhoneLine line : managedPhoneLines) {
			if(line.isAvailable())
				flag = false;
		}
		return flag;
	}
	
	/**
	 * Returns true if the pool of phonelines has no phoneline on hold, 
	 * otherwise returns false.
	 * 
	 * @return boolean
	 *            true if line are clear, otherwise false         
	 */
	private boolean areLinesClear() {
		boolean flag = true;
		for(PhoneLine line : managedPhoneLines) {
			if(!line.isAvailable())
				flag = false;
		}
		return flag;
	}
	
	/**
	 * Returns the first available employee based on their position in the
	 * arraylist and their availability. Returns a null employee if no
	 * employee is found.
	 * 
	 * @return Employee
	 *            the next available employee        
	 */
	private Employee getNextAvailableEmployee() {
		Employee availableEmployee = null;
		for(Employee csr : managedEmployees) {
			if(csr.isAvailable()) {
				availableEmployee = csr;
				break;
			}
		}
		return availableEmployee;
	}
	
	/**
	 * Returns the first available phoneline based on its position in the
	 * arraylist and their availability. Returns a null phoneline if no
	 * phoneline is found.
	 * 
	 * @return PhoneLine
	 *            the next available phoneline        
	 */
	private PhoneLine getNextAvailablePhoneLine() {
		PhoneLine availablePhoneLine = null;
		for(PhoneLine line : managedPhoneLines) {
			if(line.isAvailable()) {
				availablePhoneLine = line;
				break;
			}
		}
		return availablePhoneLine;
	}
	
	/**
	 * Returns the next phoneline with a call on hold based on the order 
	 * in which the calls were received. Returns a null phoneline is no
	 * phoneline is found.
	 * 
	 * @return PhoneLine
	 *            the next on hold phoneline        
	 */
	private PhoneLine getNextOnHoldPhoneLine() {
		PhoneLine nextOnHoldPhoneLine = null;
		int lowestInQueue = -1;
		for(PhoneLine line : managedPhoneLines) {
			if(lowestInQueue == -1 && line.isOnHold()) {
				lowestInQueue = line.getCustomerID();
				nextOnHoldPhoneLine = line;
				
			}
			else if(line.getCustomerID() != 0 && line.getCustomerID() < lowestInQueue && line.isOnHold()) {
				lowestInQueue = line.getCustomerID();
				nextOnHoldPhoneLine = line;
			}
		}
		return nextOnHoldPhoneLine;
	}
	
	/**
	 * Returns the number of phonelines with customers currently waiting 
	 * on hold.
	 * 
	 * @return int
	 *            the number of phoneslines with customers on hold        
	 */
	private int getNumberOfLinesOnHold() {
		int linesOnHold = 0;
		for(PhoneLine line : managedPhoneLines) 
			if(line.isOnHold())
				linesOnHold++;
		return linesOnHold;
	}
}
