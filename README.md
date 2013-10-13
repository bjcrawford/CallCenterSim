CallCenterSim
=============

## Description

The Call Center Simulation project is a Java or C++ program that will simulate the operations of a
customer service call center. The call center consists of a number of incoming phone lines that
customers may use to call for assistance with company product. The center is staffed by a number of
customer service representatives that will answer customer calls and handle their questions or
complaints.

### The goal the simulation is to:

1. Limit the number of times a customer hangs up before the call is answered to increase customer satisfaction.
2. Limit the number amount of time that customer service representatives are idle to reduce cost.

## Customer Requirements

### General Simulation

- The system shall begin execution of the simulation immediately on activation of the simulation program.
- The system shall simulate closing of the call center after a configurable amount of simulation time has passed.
-- After the call center’s simulated close, the system shall stop generating incoming call traffic.
- The system shall continue to process calls received prior to the closing of the call center.
- The system shall terminate when all calls have been processed.

### Incoming Phone Line Requirements

5. The system shall simulate incoming phone lines used by customers to request assistance.
6. The system shall allow the number of incoming phone lines to be set at the beginning of the simulation.
6.1. Once established, the number of lines shall remain constant for the duration of the simulation.
7. The system shall allow at most one simulated phone call to be active on a simulated line at any given time. A line may be inactive when there are no calls available on the line at that time.

### Call Processing Requirements

8. The system shall simulate customer service representatives handling incoming calls from the simulated phone lines.
9. The system shall allow the number of customer service representatives to be set at the beginning of the simulation.
9.1. Once established. The number of customer service representatives shall remain constant for the duration of the simulation.
10. The customer service representatives shall take the next call from any line on which there is a waiting call.
11. The system shall allow the customer service representative to handle at most one phone call at any given time. A customer service representative may be idle when there are no calls available on any line.
12. The system shall implement a “fairness” algorithm for choosing calls from the available lines such that no line is “starved” for attention.

### Call Duration Requirements

13. The system shall randomly determine an amount of time it would take an average customer service representative to handle the call.
13.1. The time shall be specified in whole minutes.
13.2. The expected duration of a call shall be no less than two (2) minutes.
13.3. The expected duration of a call shall be no greater than thirty (30) minutes.
14. The system shall automatically cancel any call left waiting for service more than (10) minutes. This is to simulate a customer hang-up.

### Customer Service Representative Performance Requirements

15. The system shall simulate customer service representatives with differing skill levels.
15.1. Each customer service representative may be average, better than average, or worse  than average by different amounts.
15.1.1. An average customer service representative will process a call in exactly the expected duration. That is, the customer service representative has a performance factor of 1.
15.1.2. A better than average customer service representative will process a call in less than the expected duration. That is, the customer service representative has a performance factor of less than 1.
15.1.3. A worse than average customer service representative will process a call in more than the expected duration. That is, the customer service representative has a performance factor of greater than 1.
15.1.4. The range of customer service representative performance shall be 0.5 (best) to 1.5 (worst).
15.1.5. Customer service representative performance may be determined at random, or by

### Clock Requirement

16. The system shall implement a simulation clock separate from the system’s real time clock.
16.1. The clock shall run at an accelerated rate. This is so that, for example, and eight (8) hour test is completed in minutes, rather than hours.

### Simulation Statistic Requirements

17. The system shall report all of its collected statistics at the completion of the simulation.
18. The system shall collect the following statistics during execution of the simulation:
18.1. Minimum amount of time a call remained on hold
18.2. Average amount of time a call remained on hold
18.3. Maximum amount of time a call remained on hold
18.4. Maximum number of calls on hold at one time
18.5. Number of dropped calls (hang-ups)
18.6. Amount of time that each phone line was idle
18.7. Amount of time that each customer service representative was idle
