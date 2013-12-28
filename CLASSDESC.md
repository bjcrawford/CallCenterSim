Class Descriptions
==================

##### [Class CallCenterSimulation](http://github.com/bjcrawford/CallCenterSim/blob/master/src/CallCenterSimulation.java)

This class is the main logic loop for the Call Center Simulation. It contains the simulation specific variables which can be modified to evaluate the performance of the simulation. This class creates the Clock, PhoneManager, Employee, PhoneLine, Customer, and Log objects used by the simulation and utilizes the Random class to produce values for the chance of customer creation, the customer call lengths and the employee performance factors. Customers have a chance to be created on each loop through the main logic based upon a given probability factor. The Customer objects are stored within this class. When the simulation is finished the statistical results are output to the console.

##### [Class Clock](http://github.com/bjcrawford/CallCenterSim/blob/master/src/Clock.java)

This class represents an accelerated clock which will manage the passage of time within objects implementing from the ClockManaged interface.  ClockManaged objects are contained within the class by using the addManagedItem method and are updated with the current time on each call to the countTick method. 

##### [Interface ClockManaged](http://github.com/bjcrawford/CallCenterSim/blob/master/src/ClockManaged.java)

This interface allows an object to be used in conjunction with the Clock class to manage the passage of time. Any object implementing the ClockManaged interface must implement the clockTicked method which should contain any code that needs to be run on each tick of the clock.

##### [Class Customer](http://github.com/bjcrawford/CallCenterSim/blob/master/src/Customer.java)

This class represents a customer within the call center simulation. Each customer is assigned an identification number based on the order in which it is created. The required parameters for creating a Customer object include: a reference to the Log, to keep track of customer statistics; a reference to the PhoneManager, to assign the customer a PhoneManager to call; and an integer, to represent the length of the customer’s specific call. When the Customer is created, it will attempt to call the PhoneManager on the first call to the interfaced clockTicked method. The call to the PhoneManager will result in either a busy signal or a successful connection to a PhoneLine. If a busy signal is received, the customer will no longer attempt to connect to the PhoneManager. If the connection is successful, the customer will remain on the line for up to 10 minutes on hold before hanging up. After hanging up, the customer will no longer attempt to call the service center. Each customer records, with the Log object, their creation and any successfully placed call events, a busy signal received events, or hang up events.

##### [Class PhoneManager](http://github.com/bjcrawford/CallCenterSim/blob/master/src/PhoneManager.java)

This class represents the phone line/employee management system within the call center simulation. The required parameters for creating a PhoneManager object include: a reference to the Log, to keep track of call center statistics; a reference to the Clock, to allow time management of the phone lines and employees; and an integer, to represent the length call center shift. Once added using the addEmployee and addPhoneLine methods, the PhoneManager keeps references to all employees and phone lines used in the simulation. On each call to the interfaced clockTicked method, the PhoneManager determines if employees are available to take calls and if incoming calls are on hold. If so, employees will be paired with incoming calls until no more employees are available to take calls at that moment. Calls are taken on a first-come, first-served basis. When the allotted shift length has passed, the incoming lines are shut down, but calls that are currently in progress and calls that are waiting on hold are continued to be processed. When all calls have finished, the PhoneManager will shut down. The PhoneManager records opening and closing events of the call center and the overtime from the closing process using the Log object.

##### [Class Employee](http://github.com/bjcrawford/CallCenterSim/blob/master/src/Employee.java)

This class represents an employee within the call center simulation. Similar to the Customer class, every employee is assigned an identification number based on the order in which it is created. The required parameters for creating an Employee object include: a reference to the Log, to keep track of customer statistics, and a float, to assign a performance factor to the employee. The performance factor determines the efficiency of the employee at taking customer service calls. A performance factor of 1 represents an average efficiency, finishing a customer’s call in the same amount of time as the customer’s expected call duration. A lower performance factor will result in an employee finishing customers’ calls at a faster than normal rate (i.e., a performance factor of 0.5 will result in an employee finishing the call in half the time). Conversely, a higher performance factor will result in an employee finishing customers’ call at a slower than normal rate. When a call is taken by an employee, the call length is generated using the performance factor and the employee will remain on the line until the call completion time has occurred. The call will then be completed and the employee will be returned to available. The employee records individual call lengths with the Log object in addition to the event occurrences, call initiated and call completed.

##### [Class PhoneLine](http://github.com/bjcrawford/CallCenterSim/blob/master/src/PhoneLine.java)

This class represents a phone line with the call center simulation. Each phone line is assigned an identification number based on the order in which it is created. The required parameters for creating a PhoneLine object include: a reference to the Log, to keep track of customer statistics, and a clock, used for calculating and recording hold times and active times. The PhoneLine object is used by the PhoneManager to connect customers to employees. A customer is placed on the phone line to await connection to an employee designated by the phone management system. The PhoneLine records general hold times and phone line specific active times with the Log object.

##### [Interface CallParticipant](http://github.com/bjcrawford/CallCenterSim/blob/master/src/CallParticipant.java)

This interface allows an object to be used in conjunction with the PhoneLine class to manage connection of participants. Any object implementing the CallParticipant interface must implement the callCompleted method which should contain any code that needs to be run on completion of the call.

##### [Class Log](http://github.com/bjcrawford/CallCenterSim/blob/master/src/Log.java)

This class represents the log to be used for recording all statistical and event related information concerning the call center simulation. The required parameters for creating a Log object include: an integer, the length of the shift to be recorded; an integer, the number of phone lines to be used in the simulation; and an integer, the number of employees to be used in the simulation. Upon completion of the simulation, the printStatistics method will return a formatted string containing statistics and information concerning hold times, customer calls, and employee/phone line idle times. The printEventLog method will return a formatted string containing each event in the simulation and its time of occurrences (careful, this string will likely be very long). 

