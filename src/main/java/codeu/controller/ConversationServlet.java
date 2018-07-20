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

import codeu.model.data.Conversation.Type;
import codeu.model.data.Conversation.Visibility;

import codeu.model.data.User;
import codeu.model.data.Group;
import codeu.model.store.basic.ConversationStore;
import codeu.model.data.Activity;
import codeu.model.store.basic.GroupConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.UUID;

// import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.ServingUrlOptions.Builder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the conversations page. */
public class ConversationServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Group Conversations. */
  private GroupConversationStore groupConversationStore;

  /** Store class that gives access to Activity */
  private ActivityStore activityStore;

	/** Blobs for the avatarImage!!! */
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private ImagesService imagesService = ImagesServiceFactory.getImagesService();

  /**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setGroupConversationStore(GroupConversationStore.getInstance());
    setActivityStore(ActivityStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  void setGroupConversationStore(GroupConversationStore groupConversationStore) {
		this.groupConversationStore = groupConversationStore;
  }

  void setActivityStore(ActivityStore activityStore) {
		this.activityStore = activityStore;
  }

  /**
   * This function fires when a user navigates to the conversations page. It gets all of the
   * conversations from the model and forwards to conversations.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Conversation> conversations = conversationStore.getAllConversations();
		List<Group> groups = groupConversationStore.getAllGroupConversations();
		/* for every conversation in conversationStore{
			blobstoreService.serve(blobKey, res); so this means I have to load each time?!
		*/
		// blobstoreService.serve(blobKey, res);
    request.setAttribute("conversations", conversations);
		request.setAttribute("groups", groups);
    request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the conversations page. It gets the
   * logged-in username from the session and the new conversation title from the submitted form
   * data. It uses this to create a new Conversation object that it adds to the model.
   */

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them create a conversation
      response.sendRedirect("/conversations");
      return;
    }

		if (username == null) {
      // user was not found, don't let them create a conversation
      System.out.println("User not found: " + username);
      response.sendRedirect("/conversations");
      return;
    }

    User user = userStore.getUser(username);
		/* This bit here now deals with the issue of creating a Conversation Title
		 *
		 * This'll be split 3 ways: 1. in a PUBLIC conversation, the convoTitle will be specified upon creation
		 * 2. in a GROUP Conversation, the convoTitle does not have to be specified, it will just take the form of
		 * the members in the chat (HOW?) which can then be changed after creation. 3. In a Direct message, the
		 * conversation title CANNOT be changed or set, as it will only display the "other" user as the Title.
		 */

		 //we will have parameters in the .jsp for:

		 //title
		 //type
		 //members
		 //visibility (check button, can only be one!)
		 //ValidTime: (check button (infinity for never deleted), can only be one!)
		 // --description-- set to null initially, not needed upon creation but can change later.
		 String conversationParameters = request.getParameter("conversationParameters");
		 String conversationTitle = request.getParameter("conversationTitle");
		 String visibility = (String) request.getParameter("conversationVisibility");
		 Visibility conversationVisibility = null;
		 if(visibility.equals("Public")){
			 conversationVisibility = Visibility.PUBLIC;
		 } else if(visibility.equals("Group")){
			 conversationVisibility = Visibility.GROUP;
		 } else if(visibility.equals("Direct")){
			 conversationVisibility = Visibility.DIRECT;
		 }

		 // String type = (String) request.getParameter("conversationType");
		 // Type conversationType = null;
		 // if(type.equals("Text")){
			//  conversationType = Type.TEXT;
		 // } else if(type.equals("Image")){
			//  conversationType = Type.IMG;
		 // } else if(type.equals("Hybrid")){
			//  conversationType = Type.HYBRID;
		 // TODO: Change this once I learn how to use BlobStore !!!!!!!}

		 //It'll be hard coded for now :(
		 Type conversationType = Type.TEXT;

		 String validTimeDigit = (String) request.getParameter("conversationValidTimeDigit");

		 String validTimeUnit = (String) request.getParameter("conversationValidTimeUnit");
		 //so can be FOREVER, 3 hour, 4 day, 23 sec, 4 min. So i'll have to parse it very specifically
		 String validTime = validTimeDigit + "/" + validTimeUnit;

		 ChronoUnit validTimeChronoUnit = null;
		 if(validTimeUnit != null){
			 validTimeChronoUnit = ChronoUnit.valueOf(validTimeUnit);
		 } else{
			 validTimeChronoUnit = ChronoUnit.DECADES;
		 }

		 HashSet<UUID> members = new HashSet<UUID>();
		 String conversationDescription = (String) request.getParameter("conversationDescription");

		 if((visibility.equals("Public"))){ // This denotes the visibility is Group or Direct
			 members.add(UUID.fromString("00000000-0000-0000-0000-000000000000")); //the "nil" UUID denotes a public one!
		 } else{
			 members.add(user.getId());
			 System.out.println("members are:");
			 System.out.println(members);
		 }

		 if(conversationVisibility == null || conversationType == null || validTime == null || conversationDescription == null){
			 request.setAttribute("error", "Please fill out all fields");
       request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
       return;
		 }


		if(conversationTitle == null){
			conversationTitle = username + "s " + conversationType.toString() + " Conversation";
			//i.e. Luis's Group Conversation
		}

    if (!conversationTitle.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
      return;
    }

    if (conversationStore.isTitleTaken(conversationTitle)) {
      // conversation title is already taken, just go into that conversation instead of creating a new Conversation
			// TODO: might have to rethink and come back to this because it might turn out to be an issue
      response.sendRedirect("/chat/" + conversationTitle);
      return;
    }

		if (groupConversationStore.isTitleTaken(conversationTitle)){
		  response.sendRedirect("/chat/" + conversationTitle);
		  return;
		}

		String avatarImageURL = null;

		if(request.getParameter("avatarImage/" + conversationTitle) != null){
			//now the big boy, handling the avatarImage :/ !!!!!!!!!!!!!!!!!!!!!!!!
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

			// Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
			Map<String, List<BlobKey>> allBlobs = blobstoreService.getUploads(request);
			List<BlobKey> blobKeys = allBlobs.get("avatarImage/" + conversationTitle);
			//by convention, the actual image should be stored in the last index of this blobKeys list.

			BlobKey avatarBlobKey = blobKeys.get((blobKeys.size()-1));
			ServingUrlOptions servingURLOptions = ServingUrlOptions.Builder.withBlobKey(avatarBlobKey);
			avatarImageURL = imagesService.getServingUrl(servingURLOptions);
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

	//now handle the requests for each of the cases:
	//Type = GROUP
	//Type = PUBLIC
	//Type = Direct

		// switch(conversationVisibility)
		// {
		// 	case GROUP:
		// 			//create a group with the members specified!
		// 			//This is also something I have to check within the chat servlet
		// 	case DIRECT:
		// 			//create a Direct Conversation with the other person
		// 			//this is tough because I dont know when in the useflow to add members
		// 			//find some way to denote max size as being = 2, no more.
		// 			//I guess I have to check this in the Chat Servlet, and structure it that way? hmmm.
		// 	case PUBLIC:
		// 			// the members change, among other things! :D
		// 			// just going to make a judgment call yolo, members = null, denoting no restrictions, everyone can see
		// 			members = null;
		// 	default:

		//Have to do this regardless of the type of conversation Conversation! :D

			Conversation conversation = new Conversation( UUID.randomUUID(), user.getId(), conversationTitle,
																										Instant.now(), members, conversationType,
																										conversationVisibility, avatarImageURL, validTime,
																										conversationDescription );
			conversationStore.addConversation(conversation);
			request.setAttribute("conversation", conversation);
			// response.sendRedirect("/chat/" + conversationTitle);

			Activity convoAct = new Activity("newConvo", UUID.randomUUID(), user.getId(), conversation.getCreationTime());
			activityStore.addActivity(convoAct);

			response.sendRedirect("/chat/" + conversationTitle);
	}
}
