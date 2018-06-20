package codeu.model.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

   /**
    * Constructs a new group. A subclass of a Conversation, allowing for exclusive group Messages
	* of 2+ people.
    *
    * @param id (SUPER) the ID of this Conversation
    * @param owner (SUPER) the ID of the User who created this Conversation
    * @param title (SUPER) the title of this Conversation
    * @param creation (SUPER) the creation time of this Conversation
	* @param users the list of usernames of users that are allowed to access and participate in this group.
    */


//what makes up a group?
// We are going to save a list of allowed users. Initially the list will only be
// populated by the creator, then the creator (later designated admins, etc) can
// add more users of their choosing.

public class Group {

	//using HashSet instead of ArrayList for ease of use and for strictly unique elements
	private final HashSet<User> users;
	private final UUID id;
	private final UUID owner;
	private final String title;
	private final Instant creation;
	// private final Conversation conversation;

	public Group(UUID id, UUID owner, String title, Instant creation, HashSet<User> users){
		// this.conversation = new Conversation(id, owner, title, creation);
		this.id = id;
		this.owner = owner;
		this.title = title;
		this.creation = creation;
		this.users = users;
	}

	public void addUser(User newUser){
		//because it is a set, I can just add it without checking if present
		users.add(newUser);
	}

	public void removeUser(User user){
		boolean removed = users.remove(user);
		System.out.println(removed);
		System.out.println("user was removed");
	}

	//returns true if the user is allowed to view this group message
	public boolean isAccessAllowed(UUID id){
		//should I do by User or by Username?
		for(User iterableUser : users){
			if(iterableUser.getId() ==  id) {
				return true;
			}
		}
		return false;
	}

	public String getTitle(){
		// return conversation.getTitle();
		return title;
	}

	public UUID getId(){
		// return conversation.getId();
		return id;
	}

	public UUID getOwnerId(){
		// return conversation.getOwnerId();
		return owner;
	}

	public Instant getCreationTime(){
		// return conversation.getCreationTime();
		return creation;
	}

	// public Conversation getConversation(){
	// 	return conversation;
	// }

	public HashSet getAllUsers(){
		return users;
	}
}
