// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final UUID profileId;
  private final Boolean admin;
  private final String name;
  private final String passwordHash;
  private final Instant creation;

  /**
   * Constructs a new User.
   *
   * @param id           the ID of this User
   * @param name         the username of this User
   * @param passwordHash the password hash of this User
   * @param creation     the creation time of this User
   * @param admin        the type of user (admin or not)
   * @param profileId    the ID of this User's profileStore
   */

   public User(UUID id, UUID profileId, String name, String passwordHash, Boolean admin, Instant creation) {
    this.id = id;
    this.profileId = profileId;
    this.name = name;
    this.admin = admin;
    this.passwordHash = passwordHash;
    this.creation = creation;
  }

  /**
   * Returns the ID of this User.
   */
  public UUID getId() {
    return id;
  }

  /**
   * Returns the type (admin or not) of this User.
   */
  public String getType() {
    return admin;
  }

  /**
   * Returns the profile UUID of this user.
   */
  public UUID getProfileID() {
    return profileId;
  }

  /**
   * Returns the password hash of this User.
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Returns the creation time of this User.
   */
  public Instant getCreationTime() {
    return creation;
  }
}

  // /** adds aboutMe data to User object. */
  // public void setAboutMe(String aboutMe){
  //   this.aboutMe = aboutMe;
  // }

//   /** Returns aboutMe data of this User. */
//   public String getAboutMe(){
//     return aboutMe;
//   }
// }
