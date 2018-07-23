package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.data.Profile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ProfileStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class ProfileServlet extends HttpServlet {
	/** Store class that gives access to Messages. */
	private MessageStore messageStore;
	/** Store class that gives access to Users. */
	private UserStore userStore;
	/** Store class that gives access to Profiles. */
	private ProfileStore profileStore;
	/**
	 * Set up state for handling login-related requests. This method is only called when running in a
	 * server, not when running in a test.
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
		setMessageStore(MessageStore.getInstance());
		setProfileStore(ProfileStore.getInstance());
	}

	/**
	 * Sets the UserStore used by this servlet. This function provides a common setup method for use
	 * by the test framework or the servlet's init() function.
	 */
	void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}

	/**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

	/**
   * Sets the ProfileStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
	void setProfileStore(ProfileStore profileStore) {
		this.profileStore = profileStore;
	}

	@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String currentProfile = requestUrl.substring("/user/".length());
		User user = userStore.getUser(currentProfile);
		String about = "";
		Profile profile = profileStore.getProfile(user.getProfileID());

		if (user == null){
			request.setAttribute("error", "THAT USER DOESN'T EXIST! ENTER A VALID USERNAME");
			request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
			return;
		}else {
			if (profile == null){
				about = "this user hasn't made a profile yet";
			}else{
				about = profile.getAboutMe();
			}
		}

		//creates arrayList of all messages sent by user whose profile is being displayed
		List<Message> messages = messageStore.getMessagesByUser(user.getId());

		request.setAttribute("about", about);
		request.setAttribute("currentProfile", currentProfile);
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  }

	//doPost is called when a user submits an aboutMe form on the profile page
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
				String username = (String) request.getSession().getAttribute("user");
				if (username == null) {
				 	//if user is not logged in, don't let them submit an about me form
				 	response.sendRedirect("/login");
				 	return;
				 }

				User user = userStore.getUser(username);
				Profile profile = profileStore.getProfile(user.getProfileID());
				if (profile == null){
					profile = new Profile(user.getProfileID(), Instant.now());
					profileStore.addProfile(profile);
				}
				if (user == null){
				 	//user is not logged in, redirect to login page
				 	response.sendRedirect("/login");
				 	return;
				 }
				//get aboutMe content submitted through the form
				String aboutMeContent = request.getParameter("about me");
				//removes any HTML from aboutMe content
				String cleanedAboutMeContent = Jsoup.clean(aboutMeContent, Whitelist.none());
				//update profile to include aboutMe data
				profile.setAboutMe(cleanedAboutMeContent);
			  profileStore.updateProfile(profile);
				response.sendRedirect("/user/" + username);
			}
}
