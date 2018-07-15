package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a user's profile. */
public class Profile {
  private final UUID id;
  private final UUID profilePicId;
  private final HashSet<User> followers;
  private final HashSet<User> following;
  private final String college;
  private final HashSet<String> classes;
  private final Int brownie_points;
  private final HashSet<Conversation> pinnedConvos;
  private final String aboutMe;

/**
* Constructs a new profile
*
* @param id              the id of this user
* @param profilePicId      the id of this users profilePiStore
* @param followers       the list of Users that follow this user
* @param following       the list of Users that this user is following
* @param college         the college this user attends
* @param classes         the classes this user has isTitleTaken
* @param brownie_points  the number of brownie_points this user has
* @param pinnedConvos    the conversations this user has pinned
* @param aboutMe         the "about me" of this user
*/

  public Profile(UUID id, UUID profilePicId, HashSet<User> followers, HashSet<User> following,String college,
      HashSet<String> classes, Int brownie_points, HashSet<Conversation> pinnedConvos, String aboutMe){
        this.id = id;
        this.profilePicId = profilePicId;
        this.followers = followers;
        this.following = following;
        this.college = college;
        this.classes = classes;
        this.brownie_points = brownie_points;
        this.pinnedConvos = pinnedConvos;
        this.aboutMe = aboutMe;
  }

  /**
   * Returns the ID of this profile.
   */
   public UUID getId(){
     return id;
   }

   /**
    * Returns the profilePicId of this profile.
    */
    public UUID getProfilePicID(){
      return profilePicID;
    }

    /**
    * Returns the HashSet of followers of this profile
    */
    public HashSet getFollowers(){
      return followers;
    }

    /**
     * Returns the college of this profile.
     */
    public String getCollege(){
      return college;
    }

    /**
     * Returns the HashSet of classes in this profile.
     */
    public HashSet getClasses() {
      return classes;
    }

    /**
     * Returns the number of brownie points in this profile.
     */
    public Int getPoints(){
      return brownie_points;
    }

    /**
     * Returns the HashSet of pinned conversations in this profile.
     */
     public HashSet getPinnedConvos(){
       return pinnedConvos;
     }

     public String getAboutMe(){
       return aboutMe;
     }
}
