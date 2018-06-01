/* Used Conversation.java a reference */

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Activity {

	/* String type - type helps differentiate what type of activity it is based on the following:
 	 * newUser - user has joined
 	 * newConvo - conversation has been made
 	 * newMessage - message has been sent
 	*/
	public final String type;
	public final UUID id; //id of activity
	public final UUID owner; //user that "owns" this activity
	public final Instant creation;
	
	/*
	*	Constructs a new activity
	*	
	* @param type is the type of activity made
	*	@param id is the ID of who ever made the activity
	*	@param creation is the creation time of this activity
	*/
	public Activity(String type, UUID id, UUID owner, Instant creation) {
		this.type = type;
		this.id = id;
		this.owner = owner;
		this.creation = creation;
	
	}
	
	/* Returns the activity type */
	public String getType() {
		return type;
	}
	
	/* Returns the ID of the activity */
	public UUID getId() {
		return id;
	}
	
	/* Returns the ID of the user who has initiated tha activity */
	public UUID getOwner() {
		return owner;
	}
	
	/* Returns the creation time of this activity */
	public Instant getCreationTime() {
		return creation;
	}

}
