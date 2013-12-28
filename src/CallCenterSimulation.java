

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the main logic loop for the Call Center Simulation. It contains the 
 * simulation specific variables which can be modified to evaluate the performance of 
 * the simulation. This class creates the Clock, PhoneManager, Employee, PhoneLine, 
 * Customer, and Log objects used by the simulation and utilizes the Random class to 
 * produce values for the chance of customer creation, the customer call lengths and 
 * the employee performance factors. Customers have a chance to be created on each 
 * loop through the main logic based upon a given probability factor. The Customer 
 * objects are stored within this class. When the simulation is finished the 
 * statistical results are output to the console.
 * 
 * @author Brett Crawford
 */
public class CallCenterSimulation {
	
	/** The length (in hours) of the shift **/
	static final int shiftLength = 8;
	
	/** The number of phoneline the call center should use **/
	static final int numberOfPhoneLines = 12;
	
	/** The number of employees the call center should use **/
	static final int numberOfEmployees = 10;
	
	/** The minimum length (in minutes) of a call from a customer **/
	static final int expectedDurationMin = 2;
	
	/** The maximum length (in minutes) of a call from a customer **/
	static final int expectedDurationMax = 30;
	
	/** The probabilty (per tick) that a customer will be created and call **/
	static final float callProbability = 0.12F;
	
	/** The clock to be used in the simulation **/
	static final Clock clock = new Clock();
	
	/** The log to be used in the simulation **/
	static final Log log = new Log(clock, shiftLength, numberOfPhoneLines, numberOfEmployees);
	
	/** The RNG to be used for call probabilities and expected call duration **/
	static final Random rand = new Random();
	
	/** The phone manager to be used in the simulation **/
	static final PhoneManager phoneManager = new PhoneManager(log, clock, shiftLength);
	
	/** The arraylist to hold the customers in the simulation **/
	static final ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public static void main(String args[]) {
		
		initializeSim();

		run();
		
		// System.out.println(log.printEventLog());
		
		System.out.println(log.printStatistics());
	}
	
	/**
	 * Adds the phonemanager to the clock's managed item list. Creates and adds
	 * the phonelines and employees to the phonemanager. 
	 */
	public static void initializeSim() {
		clock.addManagedItem(phoneManager);
		
		for(int i = 0; i < numberOfPhoneLines; i++)
			phoneManager.addPhoneLine(new PhoneLine(log, clock));
		
		for(int i = 0; i < numberOfEmployees; i++)
			phoneManager.addEmployee(new Employee(log, rand.nextFloat() + 0.5F));
	}
	
	/**
	 * The main logic loop of the simulation. The loop continues while the 
	 * phonemanager has not yet completed the shift. On each loop, a random
	 * float is generated. If the random float falls within the call probability 
	 * range, a customer is created using another random integer to represent 
	 * the expected duration if the call using the given min and max. On each loop,
	 * the clock accelerates by the given tick value.
	 */
	public static void run() {
		
		while(!phoneManager.isShiftComplete()) {
			if(rand.nextFloat() < callProbability && phoneManager.isCallCenterOpen()) {
				int expectedDuration = rand.nextInt(expectedDurationMax - expectedDurationMin + 1) + expectedDurationMin;
				Customer newCustomer = new Customer(log, phoneManager, expectedDuration);
				customers.add(newCustomer);
				clock.addManagedItem(newCustomer);
			}
			clock.countTick();
		}
	}
	
	
}
