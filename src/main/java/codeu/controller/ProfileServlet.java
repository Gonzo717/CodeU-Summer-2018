package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends HttpServlet {
	//implement doGet so it knows where to go
	@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
					String requestUrl = request.getRequestURI();
					String currentProfile = requestUrl.substring("/user/".length());
					String username = (String) request.getSession().getAttribute("user");

					if (currentProfile.equals(username)){
						System.out.println(currentProfile);
						System.out.println(username);
						request.getRequestDispatcher("/WEB-INF/view/userprofile.jsp").forward(request, response);
					}
					else{
						request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
					}
				}
  }
