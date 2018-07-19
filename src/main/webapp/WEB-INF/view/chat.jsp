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
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.time.Instant" %>

<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Group" %>
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>

<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
// Group group = (Group) request.getAttribute("group");
//This is the user's ID
UUID id = (UUID) request.getSession().getAttribute("id");
HashSet<UUID> allowedUsers = (HashSet<UUID>) conversation.getMembers(); //have to instantiate the set of allowed users globally
// ArrayList<UUID> allowedIds = null;

String title = conversation.getTitle();
// if (!(conversation.getConversationType().equals("PUBLIC"))){
// 	//Just doing some housekeeping with the rest of the code
// 	// title = conversation.getTitle();
// 	allowedUsers = (HashSet<UUID>) conversation.getMembers();
//
// 	//only for debugging purpose
// 	allowedIds = new ArrayList<UUID>();
// 	for(User allowedUser: allowedUsers){
// 		allowedIds.add(allowedUser.getId());
// 	}
// }

//need to distinguish between conversation or group
String name = (String) request.getSession().getAttribute("user");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= title %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
  <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll;
    }
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
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
	  <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
		<div class="mdl-layout__header-row">
			<span class="mdl-layout-title">YACA</span>
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

<div id="container">
<main class="mdl-layout__content">
	<div class="content-grid">
		<div class="page-content">
			<div id="checkIsActive">
				<% String NewDate = conversation.getDeletionInstant().toString(); %>

				<script>
				// Set the date we're counting down to
				let newDate = "<%=NewDate%>";
				var countDownDate = new Date(newDate).getTime();
				// console.log("hello");
				console.log(newDate);

				// Update the count down every 1 second
				var x = setInterval(function() {

				    // Get todays date and time
				    var now = new Date().getTime();

				    // Find the distance between now and the count down date
				    var distance = countDownDate - now;

				    // Time calculations for days, hours, minutes and seconds
				    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
				    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
				    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
				    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

				    // Output the result in an element with id="countdownTimer"
						if(days > 100){
							document.getElementById("countdownTimer").innerHTML = "&#8734";
						} else{
					    document.getElementById("countdownTimer").innerHTML = days + "d " + hours + "h "
					    + minutes + "m " + seconds + "s ";
						}

				    // If the count down is over, write some text
				    if (distance < 0) {
				        clearInterval(x);
				        document.getElementById("countdownTimer").innerHTML = "EXPIRED";
								let updateActive = "<% conversation.setActive(false); %>";
								window.alert("This Conversation has Self-Destructed! :P");
								window.location.replace("/conversations");
				    }
				}, 100); //1000
				</script>
			  <% if(conversation.getConversationVisibility().equals("GROUP") || conversation.getConversationVisibility().equals("DIRECT")){ %>

					<% if(!(conversation.isAccessAllowed(id))){ %>
							<script> //access denied, being redirected
							window.alert("You are not part of this Conversation");
							window.location.replace("/conversations");
							</script>
					<% } %>

					<%if(request.getSession().getAttribute("addedDirectMessageRecipient") != null){ //This is the code to display the other person's name as the conversation title in DIRECT messages
							
							if(id.equals(conversation.getMembers().get(0))){
								User otherUser = UserStore.getInstance().getUser(conversation.getMembers().get(1)); %>
								<h1> <%= otherUser.getName() %></h1>
					<%	} else if(id.equals(conversation.getMembers().get(1))){
									User otherUser = UserStore.getInstance().getUser(conversation.getMembers().get(0)); %>
									<h1> <%= otherUser.getName() %></h1>
				<%	} else{ %>
								<h1><%=conversation.getTitle()%></h1>
				<%	} %>

					<h5 id="countdownTimer"></h5>

					<%/*change this to user.getname ONLY for DIRECT conversations.*/%>

				<% if(conversation.getConversationVisibility().equals("GROUP")) {
					/* So we dont want to display the members and stuff for PUBLIC conversations...
					 * trying to address that now... Public conversations dont have members practically, but technically they do,
					 * so that could be a problem unless I handle this correctly.
					 */ %>
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


						<button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" onclick=displayAllowedUsers()>Show Members</button>
						<a href="" style="float: right">
							<i class="material-icons mdl-list__item-avatar">autorenew</i>
						</a></h1>
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
						<%-- <% if(!(allowedUsers.contains(UUID.fromString("00000000-0000-0000-0000-000000000000")))){ %> --%>
							<div id="display-allowed-users">
								<h4>Current Members</h4>
							<form action="/chat/<%= conversation.getTitle() %>" method="POST">
								<%
									// code for removing users
									int removeUserCounter = 0;
									%> <ul class="mdl-list">
										<%for(UUID uuid: allowedUsers){
											User user = UserStore.getInstance().getUser(uuid);
											String removeUsername = user.getName();
											if(conversation.isAccessAllowed(uuid)){
												%>
												<li class="mdl-list__item">
													<span class="mdl-list__item-primary-content">
														<i class="material-icons mdl-list__item-avatar">person</i>
														<a class="mdl-color-text--cyan" href="/user/<%=removeUsername%>"><%= removeUsername %></a>
													</span>
													<span class="mdl-list__item-secondary-action">
														<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="list-checkbox-1">
															<input type="checkbox" name="<%=removeUserCounter%>" value="<%=removeUsername%>" id="list-checkbox-1">
														</label>
													</span>
												</li>
												<% //TODO: make this more efficient for pete's sake :(
												request.getSession().setAttribute("removeUserCounter", removeUserCounter);
												removeUserCounter++;%>
												<br>
											<% }
										} %>
									</ul>
									<hr/>
									<h4>Add more members</h4>
									<div id="addMembers">
										<%  List<User> users = UserStore.getInstance().getUsers();
											int addUserCounter = 0; // already initialized
											%>
											<ul class="mdl-list">
											<% for(User registeredUser: users){
												if(registeredUser.getId() != id && !conversation.isAccessAllowed(registeredUser.getId())){
													String addUsername = registeredUser.getName();
													 %>
													<li class="mdl-list__item">
														<span class="mdl-list__item-primary-content">
															<i class="material-icons mdl-list__item-avatar">person_add</i>
															<a class="mdl-color-text--cyan" href="/user/<%=addUsername%>"><%= addUsername %></a>
														</span>
														<span class="mdl-list__item-secondary-action">
															<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="addMembers">
																<input type="checkbox" name="<%=addUserCounter%>" value="<%=addUsername%>" id="addMembers"/>
															</label>
														</span>
													</li>
													<%
													//TODO: make this more efficient for pete's sake :(
													request.getSession().setAttribute("addUserCounter", addUserCounter);
													addUserCounter++;%>
													<%-- <li><a href="/user/<%=user.getName()%>"><%= user.getName() %></a> --%>
												<% } %>
										   <% }%>
											</ul
											<% if(addUserCounter == 0){ %>
												<h3 style="color:green;">All users are currently in this group!</h3>
											<% }%>
									<hr/>
										<input class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" type="submit" name="addUsers" value="Add Checked Members">
										<input class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" type="submit" name="removeUsers" value="Remove Checked Members">
								</form>
									</div>
							</div>
					<%-- <%	} %> --%>

						<hr/>
						<% } %>

						<% if(request.getSession().getAttribute("addedDirectMessageRecipient") == null ) { %>
								<div id="selectDirectMessage">
								<form action="/chat/<%= conversation.getTitle() %>" method="POST">
								<%  List<User> users = UserStore.getInstance().getUsers();
									int addUserCounter = 0; // already initialized
									%>
									<ul class="mdl-list">
									<% for(User registeredUser: users){
										if(registeredUser.getId() != id && !conversation.isAccessAllowed(registeredUser.getId())){
											String addUsername = registeredUser.getName();
											 %>
											<li class="mdl-list__item">
												<span class="mdl-list__item-primary-content">
													<i class="material-icons mdl-list__item-avatar">person_add</i>
													<a class="mdl-color-text--cyan" href="/user/<%=addUsername%>"><%= addUsername %></a>
												</span>
												<span class="mdl-list__item-secondary-action">
													<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="addMembers">
														<input type="checkbox" name="<%=addUserCounter%>" value="<%=addUsername%>" id="addMembers"/>
													</label>
												</span>
											</li>
											<%
											//TODO: make this more efficient for pete's sake :(
											request.getSession().setAttribute("addUserCounter", addUserCounter);
											addUserCounter++;%>
											<%-- <li><a href="/user/<%=user.getName()%>"><%= user.getName() %></a> --%>
										<% } %>
									 <% }%>
									</ul
									<% if(addUserCounter == 0){ %>
										<h3 style="color:green;">All users are currently in this group!</h3>
									<% }%>
								<hr/>
								<button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" onclick=addDirectMessageRecipient()>Select User</button>
								<input class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" onclick=addDirectMessageRecipient() type="submit" name="addUsers" value="Add Checked Members">
								</form>
								</div>
						<% } %>
							<div id="chat">
							  <ul>
							<%
							  for (Message message : messages) {
									String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
								%>
									<% /* TODO: Change this.
											* Currently, I am only displaying message.getContent().getValue0().
											* Once I figure out how to use BlobStore and send message types other than text,
											* I'll actually have to check the ConversationType(), to see if im dealing with either,
											* text, media, or hybrid, and then figure out how to display those. But for now, only
											* text will be available.
											*/
									%>
								  <li><strong><a class="mdl-color-text--cyan" href="/user/<%=author%>"><%= author %></a>:</strong> <%= message.getContent().getValue0() %></li>
								<%
							  }
							%>
							  </ul>
							</div>

							<% if (request.getSession().getAttribute("user") != null) { %>
							<form autocomplete="off" action="/chat/<%= conversation.getTitle() %>" method="POST">
								</br>
								<input maxlength="75" type="text" name="messageText">
								<button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" type="submit">Send</button>
								<%-- <button type="submit">Send</button> --%>
							</form>
							<% } else { %>
							  <p><a class="mdl-color-text--cyan" href="/login">Login</a> to send a message.</p>
							<% } %>

					<% } else if(conversation.getConversationVisibility().equals("PUBLIC")){
						/* If the conversation is public, I dont want to display any info about who's allowed (everyone)
						 * or any functionality about adding/removing people. Thus I have to re-load the chat div and
						 * display it because I couldn't think of a more elegant way to do this.
						*/

						%>

						<h1> <%=conversation.getTitle()%>
						<h5 id="countdownTimer"></h5>
						<a href="" style="float: right">
							<i class="material-icons mdl-list__item-avatar">autorenew</i>
						</a></h1>
							<div id="chat">
								<ul>
							<%
								for (Message message : messages) {
									String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
								%>
									<li><strong><a class="mdl-color-text--cyan" href="/user/<%=author%>"><%= author %></a>:</strong> <%= message.getContent().getValue0() %></li>
								<%
								}
							%>
								</ul>
							</div>

							<% if (request.getSession().getAttribute("user") != null) { %>
							<form autocomplete="off" action="/chat/<%= conversation.getTitle() %>" method="POST">
								</br>
								<input maxlength="75" type="text" name="messageText">
								<button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" type="submit">Send</button>
							</form>
							<% } else { %>
								<p><a class="mdl-color-text--cyan" href="/login">Login</a> to send a message.</p>
							<% } %>
				  <% } %>

				<%-- <% } %> --%>
					<%-- Now display the conversation  --%>

					<script>
					//checks if the Conversation is Active in order to display it.


					</script>

				    <hr/>
				</div>
			</div>
		</div>
	</div>
</main>

</body>
</html>
