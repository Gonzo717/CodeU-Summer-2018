package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import java.util.HashSet;


/** Class representing a user's profile. */
public class Profile {
  private final UUID id;
  private final Instant creation;
  private UUID profilePicId;
  private HashSet<User> followers;
  private HashSet<User> following;
  private String college;
  private int brownie_points;
  private HashSet<Conversation> pinnedConvos;
  private String aboutMe;

/**
* Constructs a new profile
*
* @param id              the id of this profile Store
* @param profilePicId    the id of this users profilePicStore
* @param followers       the list of Users that follow this user
* @param following       the list of Users that this user is following
* @param college         the college this user attends
* @param brownie_points  the number of brownie_points this user has
* @param pinnedConvos    the conversations this user has pinned
* @param aboutMe         the "about me" of this profile
* @param creation        the creation time of this profile
*/

  public Profile(UUID id, Instant creation){
        this.id = id;
        this.creation = creation;
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
    public UUID getPicId(){
      return profilePicId;
    }

    /**
    * Returns the HashSet of followers of this profile
    */
    public HashSet getFollowers(){
      return followers;
    }

    /**
     * Returns the HashSet of users this profile follows
    */
    public HashSet getFollowing(){
      return following;
    }
    /**
     * Returns the college of this profile.
     */
    public String getCollege(){
      return college;
    }

    /**
     * Returns the number of brownie points in this profile.
     */
    public int getPoints(){
      return brownie_points;
    }

    /**
     * Returns the HashSet of pinned conversations in this profile.
     */
     public HashSet getPinnedConvos(){
       return pinnedConvos;
     }

     /**
      * Returns the aboutMe of this profile.
      */
    public String getAboutMe(){
       return aboutMe;
     }

     /**
      * Returns the creation time of this Profile.
      */
     public Instant getCreationTime() {
       return creation;
     }

     /** adds aboutMe data to Profile object. */
     public void setAboutMe(String aboutMe){
       this.aboutMe = aboutMe;
     }

}
