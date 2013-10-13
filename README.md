CallCenterSim
=============

### Background

This was an individual final project assigned in a data structures and algorithms course I took in 
the fall of 2012. I implemented the project using Java.

### Description

The Call Center Simulation project is a Java or C++ program that will simulate the operations of a
customer service call center. The call center consists of a number of incoming phone lines that
customers may use to call for assistance with company product. The center is staffed by a number of
customer service representatives that will answer customer calls and handle their questions or
complaints.

##### The goal the simulation is to:

1. Limit the number of times a customer hangs up before the call is answered to increase customer satisfaction.
2. Limit the number amount of time that customer service representatives are idle to reduce cost.

### Customer Requirements

##### General Simulation

- The system shall begin execution of the simulation immediately on activation of the simulation program.
- The system shall simulate closing of the call center after a configurable amount of simulation time has passed.
  - After the call center’s simulated close, the system shall stop generating incoming call traffic.
- The system shall continue to process calls received prior to the closing of the call center.
- The system shall terminate when all calls have been processed.

##### Incoming Phone Line Requirements

- The system shall simulate incoming phone lines used by customers to request assistance.
- The system shall allow the number of incoming phone lines to be set at the beginning of the simulation.
  - Once established, the number of lines shall remain constant for the duration of the simulation.
- The system shall allow at most one simulated phone call to be active on a simulated line at any given time. A line may be inactive when there are no calls available on the line at that time.

##### Call Processing Requirements

- The system shall simulate customer service representatives handling incoming calls from the simulated phone lines.
- The system shall allow the number of customer service representatives to be set at the beginning of the simulation.
  - Once established. The number of customer service representatives shall remain constant for the duration of the simulation.
- The customer service representatives shall take the next call from any line on which there is a waiting call.
- The system shall allow the customer service representative to handle at most one phone call at any given time. A customer service representative may be idle when there are no calls available on any line.
- The system shall implement a “fairness” algorithm for choosing calls from the available lines such that no line is “starved” for attention.

##### Call Duration Requirements

- The system shall randomly determine an amount of time it would take an average customer service representative to handle the call.
  - The time shall be specified in whole minutes.
  - The expected duration of a call shall be no less than two (2) minutes.
  - The expected duration of a call shall be no greater than thirty (30) minutes.
- The system shall automatically cancel any call left waiting for service more than (10) minutes. This is to simulate a customer hang-up.

##### Customer Service Representative Performance Requirements

- The system shall simulate customer service representatives with differing skill levels.
  - Each customer service representative may be average, better than average, or worse  than average by different amounts.
    - An average customer service representative will process a call in exactly the expected duration. That is, the customer service representative has a performance factor of 1.
    - A better than average customer service representative will process a call in less than the expected duration. That is, the customer service representative has a performance factor of less than 1.
    - A worse than average customer service representative will process a call in more than the expected duration. That is, the customer service representative has a performance factor of greater than 1.
    - The range of customer service representative performance shall be 0.5 (best) to 1.5 (worst).
    - Customer service representative performance may be determined at random, or by

##### Clock Requirement

- The system shall implement a simulation clock separate from the system’s real time clock.
  - The clock shall run at an accelerated rate. This is so that, for example, and eight (8) hour test is completed in minutes, rather than hours.

##### Simulation Statistic Requirements

- The system shall report all of its collected statistics at the completion of the simulation.
- The system shall collect the following statistics during execution of the simulation:
  - Minimum amount of time a call remained on hold
  - Average amount of time a call remained on hold
  - Maximum amount of time a call remained on hold
  - Maximum number of calls on hold at one time
  - Number of dropped calls (hang-ups)
  - Amount of time that each phone line was idle
  - Amount of time that each customer service representative was idle

### Class Descriptions

- [CLASSDESC.md](http://github.com/bjcrawford/CallCenterSim/blob/master/CLASSDESC.md)
