package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;

import com.google.appengine.api.datastore.PrePut;
import com.google.appengine.api.datastore.PostPut;
import com.google.appengine.api.datastore.PutContext;
import com.google.appengine.api.datastore.Entity;

public class RegisterServlet extends HttpServlet {

	/** Store class that gives access to Users. */
	private UserStore userStore;

	/* Store class that gives acces Activities */
	private ActivityStore activityStore;

	/**
	 * Set up state for handling registration-related requests. This method is only called when
	 * running in a server, not when running in a test.
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
		setActivityStore(ActivityStore.getInstance());

	}

	/**
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String username = request.getParameter("username");

		// username at least 5 chars and cannot contain spaces
		if (!username.matches("^(?=\\S+$).{5,}$")) {
			request.setAttribute("error", "Invalid username");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}

		if (userStore.isUserRegistered(username)) {
			request.setAttribute("error", "That username is already taken.");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}

		String password = request.getParameter("password");
		// password must be at least 8 characters long, contain at least one digit, at least one lower case letter,
		// at least one upper case letter, and at least one punctuation character
		if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct})(?=\\S+$).{8,}$")) {
			request.setAttribute("error", "Invalid password");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		Boolean isAdmin = false;

		if (userStore.isUserAdmin(username)){
			isAdmin = true;
		}

		User user = new User(UUID.randomUUID(), UUID.randomUUID(), username, hashedPassword, isAdmin, Instant.now());
		userStore.addUser(user);

		// old way to add user activity to ActivityStore, keeping for ref
		Activity userAct = new Activity(ActivityType.USER, UUID.randomUUID(), user.getId(), user.getId(), user.getCreationTime());
		activityStore.addActivity(userAct);

		response.sendRedirect("/login");
	}

	@PrePut
	void testingPrePutCallback(PutContext context) {
		System.out.println("PRE PUT WAS CALLED WHOO");
	}

	//PostPut runs when the user datastore has a user put into it
	@PostPut(kinds = {"chat-users"}) // Only applies to chat-users query
	void addActivity(PutContext context) {
		//adds activity into activityStore
		// System.out.println("PostPut running for user register");
		//
		// Entity user = context.getCurrentElement();
		// Activity newActivity = new Activity(ActivityType.USER, UUID.randomUUID(), UUID.fromString((String) user.getProperty("uuid")), UUID.fromString((String) user.getProperty("uuid")), Instant.parse((String) user.getProperty("creation_time")));
		// activityStore.addActivity(newActivity);
	}
}
