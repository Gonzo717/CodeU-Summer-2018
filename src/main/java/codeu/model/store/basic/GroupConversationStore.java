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

package codeu.model.store.basic;

import codeu.model.data.Group;
import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class GroupConversationStore {

  /** Singleton instance of ConversationStore. */
  private static GroupConversationStore instance;

  /**
   * Returns the singleton instance of ConversationStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static GroupConversationStore getInstance() {
    if (instance == null) {
      instance = new GroupConversationStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static GroupConversationStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new GroupConversationStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading GroupConversations from and saving GroupConversations
   * to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Conversations. */
  private List<Group> groupConversations;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private GroupConversationStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    groupConversations = new ArrayList<>();
  }

/** Access the current set of conversations known to the application. */
  public List<Group> getAllGroupConversations() {
    return groupConversations;
  }

  /** Add a new conversation to the current set of conversations known to the application. */
  public void addGroup(Group groupConvo) {
	groupConversations.add(groupConvo);
	persistentStorageAgent.writeThrough(groupConvo);
  }

  /** Check whether a Conversation title is already known to the application. */
  public boolean isTitleTaken(String title) {
    // This approach will be pretty slow if we have many Conversations.
    for (Group group : groupConversations) {
      if (group.getTitle().equals(title)) {
        return true;
      }
    }
    return false;
  }

  /** Find and return the Group Conversation with the given title. */
  public Group getGroupConversationWithTitle(String title) {
    for (Group groupConversation : groupConversations) {
      if (groupConversation.getTitle().equals(title)) {
        return groupConversation;
      }
    }
    return null;
  }

  /** Sets the List of Conversations stored by this ConversationStore. */
  public void setGroupConversations(List<Group> groupConversations) {
    this.groupConversations = groupConversations;
  }
}
