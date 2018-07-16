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
    String conversationTitle = requestUrl.substring("/chat/".length());
    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    Group group = groupConversationStore.getGroupConversationWithTitle(conversationTitle);

    if(conversation != null){
      UUID conversationId = conversation.getId();
      List<Message> messages = messageStore.getMessagesInConversation(conversationId);
      request.setAttribute("messages", messages);
      request.setAttribute("conversation", conversation);
    } else if(group != null){
      UUID groupId = group.getId();
      List<Message> messages = messageStore.getMessagesInConversation(groupId);
      request.setAttribute("messages", messages);
      request.setAttribute("group", group);
    }

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
    String conversationTitle = requestUrl.substring("/chat/".length());
    Group group = groupConversationStore.getGroupConversationWithTitle(conversationTitle);
    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);

    if (conversation == null && group == null) {
      // couldn't find conversation or group, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

    String messageContent = request.getParameter("message");
    //This block signals that the user wants to manipulate the members in the Group (add/remove)

    if(messageContent == null && conversation == null){
      // because the user didn't type a message, he wants to change the members in the Group
      boolean addUsers = false;
      boolean removeUsers = false;
      int checkedUsers = 0;
      if(request.getParameter("addUsers") != null){
        addUsers = true;
        checkedUsers = (int) request.getSession().getAttribute("addUserCounter");//the number of checked users
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
            group.addUser(allowedUser);
          }
          else if(removeUsers){
            if (!(group.getOwnerId() == allowedUser.getId())){
              group.removeUser(allowedUser);
            } else{
              System.out.println("can't remove the owner bruv!");
            }
          }
        }
      }

      response.sendRedirect("/chat/" + conversationTitle);

    } else if(messageContent != null){
      //then parse the message and do all that jazz
      // this removes any HTML from the message content
      Message message = null;
      String cleanedMessageContent = Jsoup.clean(messageContent, Whitelist.none());
      if(group != null){
        message =
        new Message(
        UUID.randomUUID(),
        group.getId(),
        user.getId(),
        cleanedMessageContent,
        Instant.now());
      } else if (conversation != null){
        message =
        new Message(
        UUID.randomUUID(),
        conversation.getId(),
        user.getId(),
        cleanedMessageContent,
        Instant.now());
      }
      messageStore.addMessage(message);

      // old way to add msg to activitystore
      // Activity msgAct = new Activity("newMessage", UUID.randomUUID(), user.getId(), message.getCreationTime());
      // activityStore.addActivity(msgAct);

      //redirect is turned off bc of postput freezing the website
      //response.sendRedirect("/chat/" + conversationTitle);
    }
    // redirect to a GET request
  }

  //PostPut runs when the messages datastore has a message put into it
  @PostPut(kinds = {"chat-messages"}) // Only applies to chat-messages query
  void addActivity(PutContext context) {
    //adds activity into activityStore
    System.out.println("PostPut running for new message");
    Entity user = context.getCurrentElement();
    Activity newAct = new Activity(ActivityType.MESSAGE, UUID.randomUUID(), UUID.fromString((String) user.getProperty("uuid")), Instant.parse((String) user.getProperty("creation_time")));
    activityStore.addActivity(newAct);
    //Want to move redirect here because or else, website freezes
    //response.sendRedirect("/chat/" + conversationTitle);
  }
}
