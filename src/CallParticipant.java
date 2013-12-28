

/**
 * This interface allows an object to be used in conjunction with the 
 * PhoneLine class to manage connection of participants. Any object 
 * implementing the CallParticipant interface must implement the 
 * callCompleted method which should contain any code that needs 
 * to be run on completion of the call.
 * 
 * @author Brett Crawford
 */
public interface CallParticipant {
	
	public void callCompleted();
}
