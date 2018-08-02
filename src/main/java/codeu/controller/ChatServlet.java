// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//		http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Group;
import org.javatuples.Pair;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.GroupConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.google.appengine.api.datastore.PostPut;
import com.google.appengine.api.datastore.PutContext;
import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.ServingUrlOptions.Builder;

/** Servlet class responsible for the chat page. */
public class ChatServlet extends HttpServlet {

  /** Store class that gives access to Group Conversations. */
  private GroupConversationStore groupConversationStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Activities. */
  private ActivityStore activityStore;

	private ImagesService imagesService = ImagesServiceFactory.getImagesService();


  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
		setGroupConversationStore(GroupConversationStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
		setActivityStore(ActivityStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
  * Sets the ConversationStore used by this servlet. This function provides a common setup method
  * for use by the test framework or the servlet's init() function.
  */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
  * Sets the groupConversationStore used by this servlet. This function provides a common setup method
  * for use by the test framework or the servlet's init() function.
  */
  void setGroupConversationStore(GroupConversationStore groupConversationStore) {
		this.groupConversationStore = groupConversationStore;
  }

  /**
  * Sets the ActivityStore used by this servlet. This function provides a common setup method
  * for use by the test framework or the servlet's init() function.
  */
  void setActivityStore(ActivityStore activityStore) {
    this.activityStore = activityStore;
  }

  /**
  * Sets the MessageStore used by this servlet. This function provides a common setup method for
  * use by the test framework or the servlet's init() function.
  */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
  * Sets the UserStore used by this servlet. This function provides a common setup method for use
  * by the test framework or the servlet's init() function.
  */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
  * This function fires when a user navigates to the chat page. It gets the conversation title from
  * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
  * It then forwards to chat.jsp for rendering.
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    UUID conversationId = UUID.fromString(requestUrl.substring("/chat/".length()));
		System.out.println(conversationId);
    Conversation conversation = conversationStore.getConversationWithId(conversationId);
		List<Message> messages = messageStore.getMessagesInConversation(conversationId);
		request.setAttribute("conversation", conversation);
		request.setAttribute("messages", messages);


    request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
  }

  /**
  * This function fires when a user submits the form on the chat page. It gets the logged-in
  * username from the session, the conversation title from the URL, and the chat message from the
  * submitted form data. It creates a new Message from that data, adds it to the model, and then
  * redirects back to the chat page.
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    String requestUrl = request.getRequestURI();

		// define some things

    UUID conversationTitle = UUID.fromString(requestUrl.substring("/chat/".length()));
    Conversation conversation = conversationStore.getConversationWithId(conversationTitle);

    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

		//option for uploading conversation avatarImage
		System.out.println(request.getParameter("avatarImage/" + conversationTitle));
		if(request.getParameter("avatarImage/" + conversationTitle) != null){
			//now the big boy, handling the avatarImage :/ !!!!!!!!!!!!!!!!!!!!!!!!
			System.out.println("SOMETHING HAPPENED");
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

			// Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
			Map<String, List<BlobKey>> allBlobs = blobstoreService.getUploads(request);
			List<BlobKey> blobKeys = allBlobs.get("avatarImage/" + conversationTitle);
			//by convention, the actual image should be stored in the last index of this blobKeys list.
			System.out.println(blobKeys);
			BlobKey avatarBlobKey = blobKeys.get((blobKeys.size()-1));
			ServingUrlOptions servingURLOptions = ServingUrlOptions.Builder.withBlobKey(avatarBlobKey);
			String avatarImageURL = imagesService.getServingUrl(servingURLOptions);
			System.out.println("avatarImageURL");
			System.out.println(avatarImageURL);

			// response.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
			// String avatarImageURL = blobstoreService.createUploadUrl("/upload");
			// log.info("blobkeys size:"+blobKeys.size());

					// BlobKeyCache bc = BlobKeyCache.getBlobKeyCache();

					// if (blobKeys == null){
					// 	log.info("blobkey is null");
					// 	System.out.println("blobkey is null");
					// }
					// else {
					// 	// for(BlobKey blobkey:blobKeys){
					// 	// 	bc.add(blobkey);
					// 	// }
					// 		// response.sendRedirect("/serve.jsp?blob-key=" + blobKeys.get(0).getKeyString() + );
					// }
		}



		//check if there was any media uploaded
		BlobKey msgMedia = null;
		if(request.getParameter("messageMedia")!= null){
			String messageMediaString = request.getParameter("messageMedia"); //Have to get blobkey as a string, then convert it back to blobkey smh
			msgMedia = new BlobKey(messageMediaString);
		}

		String msgText = (String) request.getParameter("messageText");
		String cleanedMessageText = null;
		if(msgText != null){
			cleanedMessageText = Jsoup.clean(msgText, Whitelist.none());
		}

		Pair messageContent = new Pair<>(cleanedMessageText, msgMedia);
		//So this might also be tough, how to format it? Because it isn't just text anymore so
		if (conversation.getConversationVisibility().equals("GROUP") || conversation.getConversationVisibility().equals("DIRECT")){
			//this loop signifies the ability to add more members to the convo
			//This block signals that the user wants to manipulate the members in the Group (add/remove)
			if(messageContent.getValue0() == null && messageContent.getValue1() == null){
				// because the user didn't type a message, he wants to change the members in the Group
				boolean addUsers = false;
				boolean removeUsers = false;
				int checkedUsers = 0;
				if(request.getParameter("addUsers") != null){
					addUsers = true;
					checkedUsers = (int) request.getSession().getAttribute("addUserCounter");//the number of checked users

					if(conversation.getConversationVisibility().equals("DIRECT")){
						request.getSession().setAttribute("addedDirectMessageRecipient", "true");
					}

				}
				else if(request.getParameter("removeUsers") != null){
					removeUsers = true;
					checkedUsers = (int) request.getSession().getAttribute("removeUserCounter");//the number of checked users
				}
				// getting the actual Users from number of ints checked in the chat.jsp
				HashSet<String> mutableUsers = new HashSet<String>();
				for(int i = 0; i <= checkedUsers; i++){
					String counter = Integer.toString(i);
					mutableUsers.add(request.getParameter(counter));
				}
				// Now do something with it!
				for(String userName: mutableUsers){
					if(userName != null){
						// checks if the user is already allowed, do nothing; if not then add permission
						User allowedUser = userStore.getUser(userName);
						if(addUsers){
							conversation.addMember(allowedUser);
						}
						else if(removeUsers){
							if (!(conversation.getOwnerId() == allowedUser.getId())){
								conversation.removeMember(allowedUser);
							} else{
								System.out.println("can't remove the owner bruv!");
							}
						}
					}
				}
		}

	    response.sendRedirect("/chat/" + conversationTitle);

		}

		if(messageContent.getValue0() != null){
			//then parse the message and do all that jazz
			// this removes any HTML from the message content
			// Message message = null;
			// BlobKey messageMedia = message.getMedia();
			// String placeholder = (String) messageContent.getValue0();
			//
		  // String cleanedMessageText = Jsoup.clean(placeholder, Whitelist.none());
			// //Somehow use the BlobKey to actually retrieve the image.
			// messageContent.removeFrom0();
			// messageContent.setAt0(cleanedMessageText);
			// messageContent.setAt1(image); // Obviously have to change this...

		Message	message =
		        new Message(
		            UUID.randomUUID(),
		            conversation.getId(),
		            user.getId(),
		            messageContent,
		            Instant.now());
		  messageStore.addMessage(message);

      // old way to add msg to activitystore, keeping for ref
      Activity msgActivity = new Activity(ActivityType.MESSAGE, UUID.randomUUID(), message.getAuthorId(), message.getId(), message.getCreationTime());
      activityStore.addActivity(msgActivity);

      response.sendRedirect("/chat/" + conversationTitle);
    }
    // redirect to a GET request
  }

  //PostPut runs when the messages datastore has a message put into it
  @PostPut(kinds = {"chat-messages"}) // Only applies to chat-messages query
  void addActivity(PutContext context) {
    //adds activity into activityStore
    // System.out.println("PostPut running for new message");
    // Entity message = context.getCurrentElement();
    // System.out.println(message.getProperty("content"));
    // Activity newActivity = new Activity(ActivityType.MESSAGE, UUID.randomUUID(), UUID.fromString((String) message.getProperty("author_uuid")), UUID.fromString((String) message.getProperty("uuid")), Instant.parse((String) message.getProperty("creation_time")));

    //This line not getting called
    //activityStore.addActivity(newActivity);
  }
}
