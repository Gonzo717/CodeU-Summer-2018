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
// populated by the creator, then a

public class Group extends Conversation {
	private final ArrayList<User> users;

	public Group(UUID id, UUID owner, String title, Instant creation, ArrayList<User> users){
		super(id, owner, title, creation);
		this.users = users;
	}

	public void addUser(User user){
		//should I do by User or by username?
		users.add(user);
	}

	public void removeUser(User user){
		users.remove(user);
	}

	public boolean isGroup(){
		return true;
	}

	//returns true if the user is allowed to view this group message
	public boolean accessAllowed(User user){
		//should I do by User or by Username?
		for(User iterableUser : users){
			if(iterableUser.equals(user)){
				return true;
			}
		}
		return false;
	}

	public String getTitle(){
		return title;
	}

	public ArrayList getAllUsers(){
		// Im just going to return all Usernames
		return users;
	}
}
