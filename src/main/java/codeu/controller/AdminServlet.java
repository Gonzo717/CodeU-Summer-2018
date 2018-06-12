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

import codeu.model.data.Tictactoe;
import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.store.basic.UserStore;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import java.time.Instant;

import java.time.temporal.ChronoUnit;

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
	// Setting the game

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
			List<String> administrators = userStore.getAdmins();

			int numTotalUsers = totalUsers.size();
			int numTotalConvos = totalConvos.size();
			int numTotalMessages = totalMessages.size();

			//Getting most active user
			// List<Message> activeMessages = new ArrayList();
			Map<String, Integer> msgFrequency = new HashMap<String, Integer>();
			//Determining the bounds for what "recent" means
			//In this case "recent" means at most 24 hours ago.
			Instant lowerBound = Instant.now().minus(24, ChronoUnit.HOURS);
			for(Message message: totalMessages){
				if(message.getCreationTime().isAfter(lowerBound)){
					//found an active message, now get the user
			        String author = userStore.getUser(message.getAuthorId()).getName();
			        if (msgFrequency.get(author) == null){
			        	msgFrequency.put(author, 1);
			        } else {
			        	msgFrequency.put(author, msgFrequency.get(author) + 1);
			        }
				}
			}
			// now we have to get the user that corresponds with largest number
			String mostActiveUser = null;
			int mostMessages = 0; //Setting the minimum
			for(Map.Entry<String, Integer> entry: msgFrequency.entrySet()) {
				if(entry.getValue() > mostMessages){
					mostActiveUser = entry.getKey();
					mostMessages = entry.getValue();
				}
			}
			if(mostActiveUser == null){
				mostActiveUser = "There hasn't been an active user in the past 24 hours!";
			}
			request.getSession().setAttribute("numUsers", numTotalUsers);
			request.getSession().setAttribute("numConvos", numTotalConvos);
			request.getSession().setAttribute("numMessages", numTotalMessages);
			request.getSession().setAttribute("mostActiveUser", mostActiveUser);
			request.getSession().setAttribute("numAdministrators", administrators.size());
			request.getSession().setAttribute("getAllAdmin", administrators);
		}

	public void startGame(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
			request.getSession().setAttribute("TicTacToe", "playing");
			Tictactoe theGame = new Tictactoe();
			theGame.Game();
			request.getSession().setAttribute("player", 1);
			request.getSession().setAttribute("theGame", theGame);
			//We dont want two grids showing up.
			request.getSession().setAttribute("TicTacToe", null);

			request.getSession().setAttribute("boardFull", null);
			request.getSession().setAttribute("hasWon", null);
			// response.sendRedirect("/admin");
			request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
		}

	public void updateGame(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
			String move = (String) request.getParameter("board");
			Tictactoe theGame = (Tictactoe) request.getSession().getAttribute("theGame");
			int player = (int) request.getSession().getAttribute("player");
			int row = Integer.parseInt(move.substring(0,1));
			int col = Integer.parseInt(move.substring(1,2));

			theGame.set(row, col, player);
			// Check for winner/boardFull
			int winner = theGame.checkWin();
			boolean boardFull = false;
			if(winner != 0){ // checkWin() returns 0 if there is no winner yet
				if(winner == 1){
					// winner is X
					String playerWinner = "X";
					request.getSession().setAttribute("hasWon", playerWinner);
				} else if(winner == -1){
					//winner is O
					String playerWinner = "O";
					request.getSession().setAttribute("hasWon", playerWinner);
				}
			} else if(theGame.isFull()){
				// the game board is completely full
				boardFull = true;
				request.getSession().setAttribute("boardFull", boardFull);
			} else{
				// continue playing
				if(player == 1){ //1 for X, -1 for O
					request.getSession().setAttribute("player", -1);
				} else if(player == -1){
					request.getSession().setAttribute("player", 1);
				}
			}
			//update the game
			request.getSession().setAttribute("theGame", theGame);
			//forward the request!
			request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
		}

	public void clearConversations(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
			// convoStore.clearConversations();
			request.setAttribute("success", "All conversations successfully deleted.");
	}


  /**
   * This function fires when a user submits the refresh stats form (clicks the refresh stats button). It gets the totalUsers,
   TotalConvos, and totalMessages each time to provide a hot-reload of what is happening.
   */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		// 	A method allowing the admin to refresh stats on the fly!
			if(request.getParameter("username") != null){
				addAdmin(request, response);
			}
			else if(request.getParameter("updateGame") != null){
				updateGame(request, response);
			}
			else if (request.getParameter("playGame") != null){
				startGame(request, response);
				// response.sendRedirect("/admin");
			}
			else if(request.getParameter("clearConversations") != null){
				clearConversations(request, response);
			}
			else{
				refreshStats(request, response);
				response.sendRedirect("/admin");
			}

	}

}
