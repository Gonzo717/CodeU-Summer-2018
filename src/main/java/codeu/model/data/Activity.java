/* Used Conversation.java a reference */

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Activity {
	/*
	 * ActivityType helps differentiate what type of activity it is based on the following:
	 * UserAct - user has joined
	 * ConversationAct - conversation has been made
	 * MessageAct - message has been sent
	*/

	public enum ActivityType {
		USER("USER"),
		CONVERSATION("CONVERSATION"),
		MESSAGE("MESSAGE");

		private final String typeDescription;
		private ActivityType(String value) {
			this.typeDescription = value;
		}

		public String toString() {
			return typeDescription;
		}
	};

	public final ActivityType type;
	public final UUID id; //id of this activity object
	public final UUID ownerId; //user that "owns" this activity
	public final UUID activityId; //id of the new user/convo/msg
	public final Instant creation;

	/*
	* Constructs a new activity
	*
	* @param type is the type of activity made
	* @param id is the ID of who ever made the activity
	* @param creation is the creation time of this activity
	*/
	public Activity(ActivityType type, UUID id, UUID ownerId, UUID activityId, Instant creation) {
		this.type = type;
		this.id = id;
		this.ownerId = ownerId;
		this.activityId = activityId;
		this.creation = creation;

	}

	/* Returns the activity type */
	public ActivityType getType() {
		return type;
	}

	/* Returns the ID of the activity */
	public UUID getId() {
		return id;
	}

	/* Returns the ID of the user who has initiated tha activity */
	public UUID getOwnerId() {
		return ownerId;
	}

	public UUID getActivityId() {
		return activityId;
	}

	/* Returns the creation time of this activity */
	public Instant getCreationTime() {
		return creation;
	}

	public String getCreationTimeFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("MM/dd/yyyy h:m a zzz ")
				.withZone(ZoneId.systemDefault());

		return formatter.format(creation);
	}
}
