package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

	/** Store class that gives access to Conversations. */
	private ConversationStore conversationStore;

	/** Store class that gives access to Messages. */
	private MessageStore messageStore;

	/** Store class that gives access to Users. */
	private UserStore userStore;

	/* Stores class that gives access to Activities. */
	private ActivityStore activityStore;

	/*
	 * Sets the ConversationStore used by this servlet. This function provides a common setup method
	 * for use by the test framework or the servlet's init() function.
	 */
	void setConversationStore(ConversationStore conversationStore) {
		this.conversationStore = conversationStore;
	}

	/*
	 * Sets the MessageStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}

	/*
	 * Sets the UserStore used by this servlet. This function provides a common setup method for use
	 * by the test framework or the servlet's init() function.
	 */
	void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}
	/*
	 * Sets the ActivityStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	void setActivityStore(ActivityStore activityStore) {
		this.activityStore = activityStore;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		setConversationStore(ConversationStore.getInstance());
		setMessageStore(MessageStore.getInstance());
		setUserStore(UserStore.getInstance());
		setActivityStore(ActivityStore.getInstance());
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String searchingValue = ""; //remember to do toLowerCase here
    List<Activity> activities = activityStore.getAllActivities();
    List<Activity> searchResults = new ArrayList<Activity>();

    //adds the activities to searchResults here based on search
    for(Activity activity : activities) {
      if(activity.getType().equals(ActivityType.USER)) {
        String userName = userStore.getUser(activity.getActivityId()).getName();
        if(userName.toLowerCase().equals(searchingValue)) {
          searchResults.add(activity);
        }

      }
      else if(activity.getType().equals(ActivityType.CONVERSATION)) {
        //conversationStore.getConversationWithTitle() //no way to get with activity UUID
      }
      else if(activity.getType().equals(ActivityType.MESSAGE)) {
        //in progress
      }
      else {
        System.out.println("Invalid activity type...");
      }
    }

    request.setAttribute("activities", activities);
    request.setAttribute("searchResults", searchResults);
    request.getRequestDispatcher("/WEB-INF/view/search.jsp").forward(request, response);
	}



}
