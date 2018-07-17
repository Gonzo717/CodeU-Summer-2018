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
<%@ page import="codeu.model.data.Conversation.Type" %>
<%@ page import="codeu.model.data.Conversation.Visibility" %>
<%@ page import="codeu.model.data.Group" %>



<!DOCTYPE html>
<html>
<head>
	<title>YACA</title>
	<link rel="stylesheet" href="/css/main.css">
	<link rel="shortcut icon" href="/images/YACA.png" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
	<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
	<style>
	.content-grid {
		width:960px;
		max-width: 960px;
	}
	.page-content{
		width: 800px;
		margin-top: 12%;
		margin-left: auto;
	    margin-right: auto;
	}
	#view-source {
	  position: fixed;
	  display: block;
	  right: 0;
	  bottom: 0;
	  margin-right: 40px;
	  margin-bottom: 40px;

	  z-index: 900;
	}
	</style>

</head>
<body>
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
	  <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
		<div class="mdl-layout__header-row">
			<span class="mdl-layout-title">YACA</span>
			<!-- Image card -->
				  <!-- Add spacer, to align navigation to the right in desktop -->
			<div class="android-header-spacer mdl-layout-spacer"></div>
				<div class="android-search-box mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right mdl-textfield--full-width">
					<label class="mdl-button mdl-js-button mdl-button--icon" for="search-field">
						<i class="material-icons">search</i>
					</label>
					<div class="mdl-textfield__expandable-holder">
						<input class="mdl-textfield__input" type="text" id="search-field">
					</div>
				</div>
				  <!-- Navigation -->
				<div class="android-navigation-container">
					<nav class="android-navigation mdl-navigation">
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/conversations">Conversations</a>
						<% if(request.getSession().getAttribute("user") != null){ %>
								<a class="mdl-navigation__link mdl-typography--text-uppercase">Hello <%= request.getSession().getAttribute("user") %>!</a>
							<a></a>
						<% } else{ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/login">Login</a>
						<% } %>
						<% if(request.getSession().getAttribute("admin") != null) { %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/admin">Admin</a>
						<% } %>
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/activityfeed">Activity Feed</a>
						<% if(request.getSession().getAttribute("user") != null){ %>
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href ="/user/<%=request.getSession().getAttribute("user")%>">My Profile</a>
						<% } %>
						<% if(request.getSession().getAttribute("user") != null){ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/logout">Logout</a>
						<% } %>
					</nav>
				</div>
				<span class="android-mobile-title mdl-layout-title">
					<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/about.jsp">
					<img class="android-logo-image" src="/images/JavaChipsLogoMenu.png">
					</a>
				</span>
			</div>
		</div>
	</div>

<main class="mdl-layout__content">
	<div class="content-grid">
		<div class="page-content">
		  <div id="container">
		    <% if ((request.getSession().getAttribute("user") == null) && (request.getSession().getAttribute("admin") == null)){ %>
		       <h3 class="mdl-typography--display-1" >
				   Login to start a conversation
			       </br>
				   <a class="mdl-navigation__link" href="/login">
					   <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent">Login</button>
				   </a>
		       </h3>
		    <% } %>

		    <% if(request.getAttribute("error") != null){ %>
		        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
		    <% } %>

		    <% if(request.getSession().getAttribute("user") != null){ %>
		      <h1>New Conversation</h1>
		      <form action="/conversations" method="POST">
					  <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						  <input class="mdl-textfield__input" type="text" name="conversationTitle" id="title">
						  <label class="mdl-textfield__label" for="">Conversation Title...</label> <!-- sample3 -->
						</div>
						</br>
						<!-- ALL THE OPTIONS FOR TYPE -->
						<h3>Select Conversation Type: </h3>
						<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="MEDIA">
						  <input type="radio" class="mdl-radio__button" id="MEDIA" name="conversationType" value="Media"></input>
						  <span class="mdl-radio__label">Media Messages Only</span>
						</label>
						<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="TEXT">
						  <input type="radio" class="mdl-radio__button" id="TEXT" name="conversationType" value="Text"></input>
						  <span class="mdl-radio__label">Text Only</span>
						</label>
						<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="HYBRID">
						  <input type="radio" class="mdl-radio__button" id="HYBRID" name="conversationType" value="Hybrid"></input>
						  <span class="mdl-radio__label">No Restrictions</span>
						</label>

						</br>
						<!-- ALL THE OPTIONS FOR VISIBILITY -->
						<h3>Select Conversation Visibility: </h3>
						<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="DIRECT">
						  <input class="mdl-radio__button" id="DIRECT" name="conversationVisibility" type="radio" value="Direct">
						  <span class="mdl-radio__label">Direct Message</span>
						</label>
						<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="PUBLIC">
						  <input checked class="mdl-radio__button" id="PUBLIC" name="conversationVisibility" type="radio" value="Public">
						  <span class="mdl-radio__label">Public Conversation</span>
						</label>
						<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="GROUP">
						  <input class="mdl-radio__button" id="GROUP" name="conversationVisibility" type="radio" value="Group">
						  <span class="mdl-radio__label">Group Message</span>
						</label>
						<%-- <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" name="conversationVisibility" id="visibility">
							<label class="mdl-textfield__label" for="sample3">Visibility...</label>
						</div> --%>

						</br>
						<!-- THE DROPDOWN FOR THE VALID TIME -->
						<h3>Select Valid Time for the Conversation: </h3>

				</br>
				<label for="conversationValidTimeDigit" class="mdl-textfield__label">Digit</label>
				<select name="conversationValidTimeDigit">
					<option selected value="1000000">inf</option>
					<option value="1">1</option>
			    <option value="2">2</option>
			    <option value="3">3</option>
			    <option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
			  </select>

				<label for="conversationValidTimeUnit" class="mdl-textfield__label">Unit of Time</label>
				<select name="conversationValidTimeUnit">
					<option value="DAYS" selected>Forever</option>
					<option value="SECONDS">Seconds</option>
					<option value="MINUTES">Minutes</option>
					<option value="HOURS">Hours</option>
					<option value="DAYS">Days</option>
				</select>
			</br>

						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" name="conversationDescription" id="description">
							<label class="mdl-textfield__label" for="description">Short Description...</label>
						</div>
						</br>
					 	<button name="conversationParameters" value="conversationParameters" class="mdl-button mdl-js-button mdl-button--raised" type="submit">Create</button>
		      </form>

			  <%-- <form action="/conversations" method="POST">
				  <h1>Group Message</h1>
				  <button type="submit" name="group" value="group">Private Group</button>
			   </form> --%>
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
		      <ul class="mdl-list--text-center">
		    <% //Display 10 conversations at a time
		    for(Conversation conversation : conversations){
		    %>
			<li class="mdl-list__item">
				<span class="mdl-list__item-primary-content">
					<i class="material-icons mdl-list__item-avatar">group</i>
					<a class="mdl-navigation__link" href="/chat/<%= conversation.getTitle() %>">
 					   <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent"><%= conversation.getTitle() %></button>
 				   </a>
					<%-- <a class="mdl-navigation__link" href="/chat/<%= conversation.getTitle() %>">
					<%= conversation.getTitle() %></a> --%>
				</span>
			</li>
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
						if(group.isAccessAllowed(id)){
					//only display the private conversation that the user is a part of
			    %>
			        <li class="mdl-list__item">
						<span class="mdl-list__item-primary-content">
							<i class="material-icons mdl-list__item-avatar">group</i>
							<a class="mdl-navigation__link" href="/chat/<%= group.getTitle() %>">
				          	<%= group.getTitle() %></a>
						</span>
				  	</li>
			    <%
			        	}
					}
			    %>
			        </ul>
				<% } %>
			<% } %>
		    <hr/>
		  </div>
	  </div>
</div>
</main>
</body>
</html>
