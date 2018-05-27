/* Used Conversation.java a reference */

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Activity {

	/* String type
 	 * newUser - user has joined
 	 * newConvo - conversation has been made
 	 * newMessage - message has been sent
 	*/
	public final String type;
	public final UUID id;
	public final Instant creation;
	
	/*
	*	Constructs a new activity
	*	
	* @param type is the type of activity made
	*	@param id is the ID of who ever made the activity
	*	@param creation is the creation time of this activity
	*/
	public Activity(String type, UUID id, Instant creation) {
		this.type = type;
		this.id = id;
		this.creation = creation;
	
	}
	
	/* Returns the activity type */
	public String getType() {
		return type;
	}
	
	/* Returns the ID of the user who has initiated the activity */
	public UUID getId() {
		return id;
	}
	
	/* Returns the creation time of this activity */
	public Instant getCreationTime() {
		return creation;
	}

}