

import java.text.DecimalFormat;
import java.util.*;

/**
 * This class represents a customer within the call center simulation. Each 
 * customer is assigned an identification number based on the order in which 
 * it is created. The required parameters for creating a Customer object 
 * include: a reference to the Log, to keep track of customer statistics; 
 * a reference to the PhoneManager, to assign the customer a PhoneManager 
 * to call; and an integer, to represent the length of the customer’s 
 * specific call. When the Customer is created, it will attempt to call 
 * the PhoneManager on the first call to the interfaced clockTicked method. 
 * The call to the PhoneManager will result in either a busy signal or a 
 * successful connection to a PhoneLine. If a busy signal is received, 
 * the customer will no longer attempt to connect to the PhoneManager. If 
 * the connection is successful, the customer will remain on the line for 
 * up to 10 minutes on hold before hanging up. After hanging up, the 
 * customer will no longer attempt to call the service center. Each 
 * customer records, with the Log object, their creation and any 
 * successfully placed call events, a busy signal received events, or 
 * hang up events.
 * 
 * @author Brett Crawford
 */
public class Customer implements ClockManaged, CallParticipant{
	
	private static int lastID = 0;
	private static DecimalFormat fmt2 = new DecimalFormat("00");
	private static DecimalFormat fmt3 = new DecimalFormat("000");
	
	private int id;
	private boolean attemptingCall;
	private int expectedCallLength;
	private Calendar hangUpTime;
	private Log log;
	private PhoneManager phoneManager;
	private PhoneLine phoneLine;
	
	/**
	 * Creates a Customer object with the specified parameters.
	 * 
	 * @param theLog
	 *            the log object for recording events/statistics
	 * @param thePhoneManager
	 *            the PhoneManager to be used to make calls
	 * @param theExpectedCallLength
	 *            The expected length of the service call to be made by the customer          
	 */
	public Customer(Log theLog, PhoneManager thePhoneManager, int theExpectedCallLength) {
		id = ++lastID;
		attemptingCall = true;
		expectedCallLength = theExpectedCallLength;
		hangUpTime = null;
		log = theLog;
		phoneManager = thePhoneManager;
		phoneLine = null;
		
		log.recordCustomer();
	}
	
	/**
	 * Simulates the passage of time using the time parameter received
	 * 
	 * @param currentTime
	 *            the current time within the simulation         
	 */
	public void clockTicked(Calendar currentTime) {
		
		if(attemptingCall) {
			if(phoneLine == null) { // Get phone line on first clock tick
				phoneLine = phoneManager.directCall(this, id, expectedCallLength);
				if(phoneLine != null) {
					log.recordEvent("Customer ID: " + fmt3.format(id) + " - " + fmt2.format(expectedCallLength) + " minute service call placed.\n");
				}
				else {
					attemptingCall = false;
					// Call not taken, busy signal
					log.recordBusySignal();
					log.recordEvent("Customer ID: " + fmt3.format(id) + " - Busy signal when attempting to call. Hung up.\n");
				}
			}
			else {
				if(hangUpTime == null) { // Determine hang up time once phoneLine has been established
					hangUpTime = (Calendar) currentTime.clone();
					hangUpTime.add(Calendar.MINUTE, 10); // Customer hangs up 10 minutes from time of call placed
				}
				
				if(phoneLine.isOnHold() && (currentTime.equals(hangUpTime) || currentTime.after(hangUpTime))) {
					phoneLine.hangUp();
					attemptingCall = false;
					log.recordEvent("Customer ID: " + fmt3.format(id) + " - On hold for more than 10 minutes. Hung up.\n");
				}
			}
		}
	}
	
	/**
	 * Called when the service call has completed      
	 */
	public void callCompleted() {
		attemptingCall = false;
		hangUpTime = null;
		phoneManager = null;
		phoneLine = null;
	}
}
