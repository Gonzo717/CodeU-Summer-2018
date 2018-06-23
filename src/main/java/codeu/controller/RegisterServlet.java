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
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;

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

		// username at least 5 chars and must contain at least one word character(a-z,A-Z,0-9),
		// optional punctuation chars, and no spaces.
		if (!username.matches("^(?=.*\\w)(?=\\S+$).{5,}$")) {
			request.setAttribute("error", "Make sure your username is at least 5 characters long and contains no spaces as well as
				at least one word character (a-z, A-Z, 0-9)");
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
		if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct})(?=\S+$).{8,}$")) {
			request.setAttribute("error", "Make sure your password is at least 8 characters and contains at least one of each:
			uppercase letter, lowercase letter, a digit, and punctuation characters");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		User user = new User(UUID.randomUUID(), username, hashedPassword, Instant.now());
		userStore.addUser(user);

		// adds user activity to ActivityStore
		Activity userAct = new Activity("newUser", UUID.randomUUID(), user.getId(), user.getCreationTime());
		activityStore.addActivity(userAct);

		response.sendRedirect("/login");
	}
}
