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

package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.store.basic.UserStore;
import java.util.List;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the login page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;
  private ConversationStore convoStore;
  private MessageStore messageStore;

  /**
   * Set up state for handling login-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
	super.init();
	setUserStore(UserStore.getInstance());
	setConversationStore(ConversationStore.getInstance());
	setMessageStore(MessageStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
	this.userStore = userStore;
  }

  void setConversationStore(ConversationStore convoStore) {
	this.convoStore = convoStore;
  }

  void setMessageStore(MessageStore messageStore) {
	this.messageStore = messageStore;
  }
  /**
   * This function fires when a user requests the /ladmin URL. It simply forwards the request to
   * login.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
	request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }

	public void addAdmin(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

			String username = request.getParameter("username");
			System.out.println(username);
			if (!username.matches("[\\w*\\s*]*")) {
	  			request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
	  			request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
	  			return;
	  		}

		    if (userStore.isUserRegistered(username)) {
		      	request.setAttribute("error", "That username is already taken.");
		      	request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
		      	return;
		    }

			userStore.addAdmin(username);
			request.setAttribute("success", "Admin successfully added");
		    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
	}

	public void refreshStats(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
			List<User> totalUsers = userStore.getUsers();
			List<Conversation> totalConvos = convoStore.getAllConversations();
			List<Message> totalMessages = messageStore.getAllMessages();

			int numTotalUsers = totalUsers.size();
			int numTotalConvos = totalConvos.size();
			int numTotalMessages = totalMessages.size();

			request.getSession().setAttribute("numUsers", numTotalUsers);
			request.getSession().setAttribute("numConvos", numTotalConvos);
			request.getSession().setAttribute("numMessages", numTotalMessages);

		}

  /**
   * This function fires when a user submits the refresh stats form (clicks the refresh stats button). It gets the totalUsers,
   TotalConvos, and totalMessages each time to provide a hot-reload of what is happening.
   */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		// 	A method allowing the admin to refresh stats on the fly!
			refreshStats(request, response);
			System.out.println(request.getParameter("username"));
			if(request.getParameter("username") != null){
				addAdmin(request, response);
			}
			else{
				response.sendRedirect("/admin");
			}

	}

}

