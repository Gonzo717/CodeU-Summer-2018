package codeu.model.data;

import java.time.Instant;
import java.util.ArrayList;
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

	private final ArrayList<User> users;
	private final UUID id;
	private final UUID owner;
	private final String title;
	private final Instant creation;
	// private final Conversation conversation;

	public Group(UUID id, UUID owner, String title, Instant creation, ArrayList<User> users){
		// this.conversation = new Conversation(id, owner, title, creation);
		this.id = id;
		this.owner = owner;
		this.title = title;
		this.creation = creation;
		this.users = users;
	}

	public void addUser(User newUser){
		int size = users.size();
		for(int i = 0; i < size; i++){
			if(!(users.get(i).getId() == newUser.getId())){
				users.add(newUser);
			}
		}
	}

	public void removeUser(User user){
		users.remove(user);
	}

	//returns true if the user is allowed to view this group message
	public boolean isAccessAllowed(UUID id){
		//should I do by User or by Username?
		for(User iterableUser : users){
			if(iterableUser.getId() ==  id) {
				System.out.println("true");
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

	public ArrayList getAllUsers(){
		return users;
	}
}