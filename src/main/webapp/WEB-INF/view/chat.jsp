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
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Group" %>
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
Group group = (Group) request.getAttribute("group");
UUID id = (UUID) request.getSession().getAttribute("id");
System.out.println("The id that chat.jsp has:");
System.out.println(id);
String title = "placeholder";
if (group == null){
	//Just doing some housekeeping with the rest of the code
	title = conversation.getTitle();
	System.out.println("Group is null");
	System.out.println(conversation);
}
if(conversation == null){
	title = group.getTitle();
	System.out.println("conversation is null");
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
		<h1><%= group.getTitle() %>
		<a href="" style="float:right">+</a>
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
		<form action="/chat/<%= group.getTitle() %>" method="POST">
			<input type="text" name="message">
			<br/>
			<button type="submit">Send</button>
		</form>
		<% } else { %>
		  <p><a href="/login">Login</a> to send a message.</p>
		<% } %>

	<% } else{ %>
		<script>
		window.alert("You are not part of this Group");
		window.location.replace("/conversations");		
		</script>
  <% } %>

<% } %>
	<%-- Now display the conversation  --%>
	<%if(conversation != null && group == null){ %>
	    <h1><%= conversation.getTitle() %>
		<a href="" style="float:right">+</a>
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
