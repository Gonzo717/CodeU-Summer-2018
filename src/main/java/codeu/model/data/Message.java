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
// package org.javatuples;

import java.time.Instant;
import java.util.UUID;
import org.javatuples.Pair;
import java.util.HashSet;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/** Class representing a message. Messages are sent by a User in a Conversation. */
public class Message {

  private final UUID id;
  private final UUID conversation;
  private final UUID author;
	private String messageType;
  private final Pair content;
  private final Instant creationTime;
	private HashSet<UUID> haveVoted;
	private int totalPoints;

  /**
   * Constructs a new Message.
   *
   * @param id the ID of this Message
   * @param conversation the ID of the Conversation this Message belongs to
   * @param author the ID of the User who sent this Message
	 * @param messageType a String either "text" "hybrid", or "media" denoting what to look for.
   * @param content a 2-tuple containing either <null, media>; <text, null>; or <text,media>; which denotes a message content.
   * @param creationTime the creation time of this Message
	 * @param totalPoints the net points for a given message
	 * @param haveVoted a HashSet containing the UUID objects of the Users that have voted
   */
  public Message(UUID id, UUID conversation, UUID author, Pair content, Instant creationTime) {
    this.id = id;
    this.conversation = conversation;
    this.author = author;
    this.creationTime = creationTime;
		this.content = content;
		this.haveVoted = new HashSet<UUID>();
		this.totalPoints = 0;

		String text = (String) content.getValue0();
		BlobKey media = (BlobKey) content.getValue1();

		if(text == null && media != null){
			this.messageType = "media";
		}

		else if(text != null && media == null){
			this.messageType = "text";
		}

		else if(text != null && media != null){
			this.messageType = "hybrid";
		}
  }

  /** Returns the ID of this Message. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the Conversation this Message belongs to. */
  public UUID getConversationId() {
    return conversation;
  }

  /** Returns the ID of the User who sent this Message. */
  public UUID getAuthorId() {
    return author;
  }

	public String getText() {
		return (String) content.getValue0();
	}

	public BlobKey getMedia() {
		//I still dunno how to get a blob? como?
		return (BlobKey) content.getValue1();
	}

  public Pair getContent() {
		// content is a tuple!
    return content;
  }

  /** Returns the creation time of this Message. */
  public Instant getCreationTime() {
    return creationTime;
  }

	public HashSet getVoters(){
		return haveVoted;
	}

	public void upVote(UUID id){
		// How to make it so that every user has only one point?
		// make a HashSet called haveVoted, update that with the id's of people that have have voted
		if(!haveVoted.contains(id)){
			totalPoints++;
			haveVoted.add(id);
		}
	}

	public void downVote(UUID id){
		if(haveVoted.contains(id)){
			totalPoints--;
			haveVoted.remove(id);
		}
	}

	public int getTotalPoints(){
		return totalPoints;
	}

/** Returns the formatted time of this message in the form of dd/MM/yyy h:m (time zone). */
  public String getFormattedTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:m a z").withZone(ZoneId.systemDefault());
    return formatter.format(creationTime);

  }
}
