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
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Group" %>



<!DOCTYPE html>
<html>
<head>
  <title>Conversations</title>
  <link rel="stylesheet" href="/css/main.css">
  <link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
</head>
<body>

   <nav>
    <a id="navTitle" href="/">Trill</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>

    <% if(request.getSession().getAttribute("admin") != null){ %>
      <a href="/admin">Admin</a>
    <% } %>
    <!-- Add login checking for activity feed here -->
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
    <% if ((request.getSession().getAttribute("user") == null) && (request.getSession().getAttribute("admin") == null)){ %>
       <h3><a href="/login">Login</a> to start a conversation</h3>
    <% } %>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <h1>New Conversation</h1>
      <form action="/conversations" method="POST">
          <div class="form-group">
            <label class="form-control-label">Title:</label>
          <input type="text" name="conversationTitle">
          </div>

        <button type="submit" name="conversation" value="conversation">Create</button>
      </form>

	  <form action="/conversations" method="POST">
		  <h1>Group Message</h1>
		  <button type="submit" name="group" value="group">Private Group</button>
	   </form>
      <hr/>
    <% } %>

    <h1>Conversations</h1>

    <%
    List<Conversation> conversations =
      (List<Conversation>) request.getAttribute("conversations");
	List<Group> groups =
	  (List<Group>) request.getAttribute("groups");
    if(conversations == null || conversations.isEmpty()){
    %>
      <p>Create a conversation to get started.</p>
    <%
    }
    else if(conversations != null){
    %>
      <ul class="mdl-list">
    <% //Display 10 conversations at a time
      for(Conversation conversation : conversations){
    %>
      <li><a href="/chat/<%= conversation.getTitle() %>">
        <%= conversation.getTitle() %></a></li>
    <%
      }
    %>
      </ul>
    <%
    }
	%>
	</hr>
	<% if(request.getSession().getAttribute("uuid") != null){ // check if signed in!
		UUID id = (UUID) request.getSession().getAttribute("uuid"); %>
		<h1>Private Messages</h1>
		<% if(groups != null){
		%>
			<ul class="mdl-list">
	    <%
	        for(Group group : groups){
				if(group.isAccessAllowed(id)){ //only display the private conversation that the user is a part of
	    %>
	        <li><a href="/chat/<%= group.getTitle() %>">
	          <%= group.getTitle() %></a></li>
	    <%
	        	}
			}
	    %>
	        </ul>
		<% } %>
	<% } %>
    <hr/>
  </div>
</body>
</html>
