<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Group" %>
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
Group group = (Group) request.getAttribute("group");
//This is the user's ID
UUID id = (UUID) request.getSession().getAttribute("id");
HashSet<User> allowedUsers = null; //have to instantiate the set of allowed users globally

String title = "placeholderTitle";
if (group != null){
	//Just doing some housekeeping with the rest of the code
	title = group.getTitle();
	allowedUsers = (HashSet<User>) group.getAllUsers();
}
if(conversation != null){
	title = conversation.getTitle();
}
//need to distinguish between conversation or group
String name = (String) request.getSession().getAttribute("user");
// User user = (User) request.getSession().getAttribute("User");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= title %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <link rel="shortcut icon" href="/images/JavaChipsLogo.png" />

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll;
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<%-- onload within the actual conversations created --%>
<body onload="scrollChat()">
  <nav>
    <a id="navTitle" href="/">Trill</a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
  	<!-- Add login checking for activity feed here -->
    <% if(request.getSession().getAttribute("admin") != null){ %>
      <a href="/admin">Admin</a>
    <% } %>
    <a href="/activityfeed">Activity Feed</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href ="/user/<%=request.getSession().getAttribute("user")%>">Your Profile Page</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/logout">Logout</a>
    <% } %>
  </nav>

  <div id="container">
  <% if(group != null){ // This IS ONLY FOR GROUP MESSAGES (PRIVATE)%>
	<% if(group.isAccessAllowed(id) && name != null){ //only if allowed and signed in, then display chat
		%>
		<h1> <%= group.getTitle() %>
		</h1>

		<script>
		function displayAllowedUsers() {
			var view = document.getElementById("display-allowed-users");
			if (view.style.display === "block") {
				view.style.display = "none";
			} else {
				view.style.display = "block";
			}
		}
		</script>

		<button onclick=displayAllowedUsers()>Show Members</button>
		<a href="" style="float: right">&#8635;</a></h1>
		<style>
		#display-allowed-users{
			font-size: 8;
			font-weight: lighter;
			width: 100%;
			text-decoration: none;
			display: none;
			list-style-type: none;
		}
		#display-allowed-users a{
			text-decoration: none;
			list-style-type: none;
		}
		#addMembers{
			font-size: 8;
			font-weight: lighter;
			text-decoration: none;
			width: auto;
			list-style-type: none;
			/* display: block; */
		}
		#addMembers a{
			text-decoration: none;
			list-style-type: none;
		}

		</style>
		<div id="display-allowed-users">
			<h4>Current Members</h4>
		<form action="/chat/<%= group.getTitle() %>" method="POST">
			<%
			// code for removing users
			int removeUserCounter = 0;
				for(User user: allowedUsers){
					String removeUsername = user.getName();
					if(group.isAccessAllowed(user.getId())){
						%>
						<input type="checkbox" name="<%=removeUserCounter%>" value="<%=removeUsername%>" id="removeMembers" style="font-size:12;"><a href="/user/<%=removeUsername%>"><label for="addMembers" style="font-size: 12;"><%= removeUsername %></label></a>
						<% //TODO: make this more efficient for pete's sake :(
						System.out.println("printing out the RemoveUserCounter:");
						System.out.println(removeUserCounter);
						request.getSession().setAttribute("removeUserCounter", removeUserCounter);
						removeUserCounter++;%>
						<br>
					<% }
				} %>
		<hr/>
			<h4>Add more members</h4>
			<div id="addMembers">
				<%  List<User> users = UserStore.getInstance().getUsers();
					int addUserCounter = 0; // already initialized
					for(User registeredUser: users){
						if(registeredUser.getId() != id && !group.isAccessAllowed(registeredUser.getId())){
							String addUsername = registeredUser.getName();
							 %>
							<input type="checkbox" name="<%=addUserCounter%>" value="<%=addUsername%>" id="addMembers" style="font-size:16;"><a href="/user/<%=addUsername%>"><label for="addMembers" style="font-size: 12;"><%= addUsername %></label></a>
							<%
							//TODO: make this more efficient for pete's sake :(
							request.getSession().setAttribute("addUserCounter", addUserCounter);
							System.out.println("printing out the AddUserCounter:");
							System.out.println(addUserCounter);
							addUserCounter++;%>
							<br>
							<%-- <li><a href="/user/<%=user.getName()%>"><%= user.getName() %></a> --%>
						<% }
					}
					if(addUserCounter == 0){ %>
						<h3 style="color:green;">All users are currently in this group!</h3>
					<% }%>
			<hr/>
				<input type="submit" name="addUsers" value="Add Checked Members">
				<input type="submit" name="removeUsers" value="Remove Checked Members">
		</form>
			</div>
		</div>
		<hr/>

		<div id="chat">
		  <ul>
		<%
		  for (Message message : messages) {
			String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
		%>
		  <li><strong><a href="/user/<%=author%>"><%= author %></a>:</strong> <%= message.getContent() %></li>
		<%
		  	}
		%>
		  </ul>
		</div>

		<hr/>

		<% if (request.getSession().getAttribute("user") != null) { %>
		<form action="/chat/<%= group.getTitle() %>" method="POST">
			<input type="text" name="message">
			<br/>
			<button type="submit">Send</button>
		</form>
		<% } else { %>
		  <p><a href="/login">Login</a> to send a message.</p>
		<% } %>

	<% } else{ %>
		<script> //access denied, being redirected
		window.alert("You are not part of this Group");
		window.location.replace("/conversations");
		</script>
  <% } %>

<% } %>
	<%-- Now display the conversation  --%>
	<%if(conversation != null && group == null){ %>
	    <h1><%= conversation.getTitle() %>
		<a href="" style="float: right">&#8635;</a></h1>
	    <hr/>

	    <div id="chat">
	      <ul>
	    <%
	      for (Message message : messages) {
	        String author = UserStore.getInstance()
	          .getUser(message.getAuthorId()).getName();
	    %>
	      <li><strong><a href="/user/<%=author%>"><%= author %></a>:</strong> <%= message.getContent() %></li>
	    <%
	      }
	    %>
	      </ul>
	    </div>
	    <hr/>

	    <% if (request.getSession().getAttribute("user") != null) { %>
	    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
	        <input type="text" name="message">
	        <br/>
	        <button type="submit">Send</button>
	    </form>
	    <% } else { %>
	      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>
<% } %>
    <hr/>

  </div>

</body>
</html>
