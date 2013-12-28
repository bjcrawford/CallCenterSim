

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class represents the log to be used for recording all statistical 
 * and event related information concerning the call center simulation. 
 * The required parameters for creating a Log object include: an integer, 
 * the length of the shift to be recorded; an integer, the number of phone 
 * lines to be used in the simulation; and an integer, the number of 
 * employees to be used in the simulation. Upon completion of the 
 * simulation, the printStatistics method will return a formatted string 
 * containing statistics and information concerning hold times, customer 
 * calls, and employee/phone line idle times. The printEventLog method 
 * will return a formatted string containing each event in the simulation 
 * and its time of occurrences (careful, this string will likely be very 
 * long). 
 * 
 * @author Brett Crawford
 */
public class Log {

	private static DecimalFormat fmt2 = new DecimalFormat("00");
	private static DecimalFormat fmt3 = new DecimalFormat("000");

	private int totalCustomers;
	private int callsOnHoldMax;
	private int hangUps;
	private int busySignals;
	private int successfulCalls;
	private int shiftLength;
	private String eventLog;
	private Clock clock;
	private ArrayList<Integer> holdTimes;
	private ArrayList<Integer> phoneLineActiveTimes;
	private ArrayList<Integer> employeeActiveTimes;
	
	/**
	 * Creates a Log object with the specified parameters.
	 * 
	 * @param theClock
	 *            the clock used to manage time 
	 * @param theShiftLength
	 *            the length of the shift in hours used in the simulation  
	 * @param theNumberOfEmployees
	 *            the number of employees used in the simulation   
	 * @param theNumberOfPhoneLines
	 *            the number of phonelines used in the simulation  
	 */
	public Log(Clock theClock, int theShiftLength, int numberOfPhoneLines, int numberOfEmployees) {
		totalCustomers = 0;
		callsOnHoldMax = 0;
		hangUps = 0;
		busySignals = 0;
		successfulCalls = 0;
		shiftLength = theShiftLength * 60;
		eventLog = "";
		clock = theClock;
		holdTimes = new ArrayList<Integer>();
		phoneLineActiveTimes = new ArrayList<Integer>(numberOfPhoneLines);
		employeeActiveTimes = new ArrayList<Integer>(numberOfEmployees);
		
		for(int i = 0; i < numberOfPhoneLines; i++) {
			phoneLineActiveTimes.add(0);
		}

		for(int i = 0; i < numberOfEmployees; i++) {
			employeeActiveTimes.add(0);
		}
	}
	
	/**
	 * Records the addition of a single customer to the simulation's statistics.
	 */
	public void recordCustomer() {
		totalCustomers++;
	}
	
	/**
	 * Records the time and the received event string.
	 * 
	 * @param event
	 *           a string containing a description of the event
	 */
	public void recordEvent(String event) {
		eventLog = eventLog + fmt2.format(clock.getTime().get(Calendar.HOUR)) + ":" + 
	               fmt2.format(clock.getTime().get(Calendar.MINUTE)) + " - " + event;
	}
	
	/**
	 * Records the hold time of a single call.
	 * 
	 * @param holdTime
	 *           the hold time of the call
	 */
	public void recordHoldTime(int holdTime) {
		holdTimes.add(holdTime);
	}
	
	/**
	 * Records the number of calls on hold. Values less than the current
	 * maximum calls on hold value are not saved.
	 * 
	 * @param holdTime
	 *           the hold time of the call
	 */
	public void recordCallsOnHoldMax(int callsOnHold) {
		if(callsOnHold > callsOnHoldMax)
			callsOnHoldMax = callsOnHold;
	}
	
	/**
	 * Records a single hang up.
	 */
	public void recordHangUp() {
		hangUps++;
	}
	
	/**
	 * Records a single busy signal.
	 */
	public void recordBusySignal() {
		busySignals++;
	}
	
	/**
	 * Records a single successful call.
	 */
	public void recordSuccessfulCall() {
		successfulCalls++;
	}
	
	/**
	 * Records the active time of a phoneline.
	 * 
	 * @param id
	 *           the id of the phoneline recording active time
	 * @param activeTime
	 *           the amount of active time to record
	 */
	public void recordPhoneLineActiveTime(int id, int activeTime) {
		int newTime = phoneLineActiveTimes.get(id - 1) + activeTime;
		phoneLineActiveTimes.set(id - 1, newTime);
	}
	
	/**
	 * Records the active time of an employee.
	 * 
	 * @param id
	 *           the id of the employee recording active time
	 * @param activeTime
	 *           the amount of active time to record
	 */
	public void recordEmployeeActiveTime(int id, int activeTime) {
		int newTime = employeeActiveTimes.get(id - 1) + activeTime;
		employeeActiveTimes.set(id - 1, newTime);
	}
	
	/**
	 * Records the overtime in minutes of the call center
	 * 
	 * @param overtime
	 * 			the overtime in minutes
	 */
	public void recordOvertime(int overtime) {
		shiftLength += overtime;
	}
	
	/**
	 * Returns a string containing the statistical information of the simulation.
	 * 
	 * @return String
	 *             a string containing the statistics of the simulation
	 */
	public String printStatistics() {
		
		int minHold = -1;
		int avHoldTotal = 0;
		int maxHold = -1;
		for(Integer hold : holdTimes) {
			if(minHold == -1) 
				minHold = hold;
			if(maxHold == -1)
				maxHold = hold;
			avHoldTotal = avHoldTotal + hold;
			if(hold < minHold)
				minHold = hold;
			if(hold > maxHold)
				maxHold = hold;
		}
		int avHold = avHoldTotal / holdTimes.size(); 
		
		String stat = "|==========================================================|\n" +
				      "|                    Simulation Results                    |\n" +
				      "|==========================================================|\n\n";

		stat +=       "   Hold Time Stats\n";
		stat +=       "     Minimum amount of time a call remained on hold: " + fmt2.format(minHold) + (minHold == 1 ? " minute" : " minutes" ) + "\n";
		stat +=       "     Average amount of time a call remained on hold: " + fmt2.format(avHold) + (avHold == 1 ? " minute" : " minutes" ) + "\n";
		stat +=       "     Maximum amount of time a call remained on hold: " + fmt2.format(maxHold) + (maxHold == 1 ? " minute" : " minutes" ) + "\n";
		stat +=       "     Maximum number of calls on hold at one time: " + callsOnHoldMax + "\n";
		stat +=       "   \n";
		stat +=       "   Total customer calls: " + totalCustomers + "\n";
		stat +=       "     Number of dropped calls (hang-ups): " + hangUps + "\n";
		stat +=       "     Number of lost calls (busy signals): " + busySignals + "\n";
		stat +=       "     Number of successfully completed calls: " + successfulCalls + "\n";
		stat +=       "   \n";
		stat +=       "   Total shift length (including overtime): " + (shiftLength / 60) + ((shiftLength / 60) == 1 ? " hour " : " hours " ) +
				       (shiftLength % 60) + ((shiftLength % 60) == 1 ? " minute" : " minutes" ) + "\n";
		stat +=       "   \n";
		stat +=       "   Phoneline Idle Times (including overtime)\n";
		for(int i = 0; i < phoneLineActiveTimes.size(); i++) {
			stat +=       "     Phoneline " + fmt2.format(i + 1) + ": " + fmt3.format(shiftLength - phoneLineActiveTimes.get(i)) + 
					       ((shiftLength - phoneLineActiveTimes.get(i)) == 1 ? " minute" : " minutes" ) + "\n";
		}
		stat +=       "   \n";
		stat +=       "   Employee Idle Times (including overtime)\n";
		for(int i = 0; i < employeeActiveTimes.size(); i++) {
			stat +=       "     Employee " + fmt2.format(i + 1) + ": " + fmt3.format(shiftLength - employeeActiveTimes.get(i)) + 
				           ((shiftLength - employeeActiveTimes.get(i)) == 1 ? " minute" : " minutes" ) + "\n";
		}
		
		stat += "\n|==========================================================|\n" +
			      "|==========================================================|\n";
		
		return stat;
	}
	
	/**
	 * Returns a string containing the event log from the simulation.
	 * 
	 * @return String
	 *             a string containing the event log of the simulation
	 */
	public String printEventLog() {
		return eventLog;
	}
}
