<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Profile Page</title>
	  <link rel="stylesheet" href="/css/main.css">
	  <link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
		<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
		<style>
			.content-grid {
				width:960px;
				max-width: 960px;
				display: inline-block;
			}
			.page-content{
				padding-top: 50px;
				margin-left: 8.33%;
			    margin-right: 8.33%;
			    width: 800px;
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

	<body onload="scrollChat()">
			<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
			  <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
				<div class="mdl-layout__header-row">
					<a class="mdl-navigation__link" href="/index.jsp"><span class="mdl-layout-title">YACA</span></a>
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

		<main class="mdl-layout__content" style="display:-webkit-box;">
			<div class="content-grid">
				<div class="page-content">
					</br>
					</br>
						<% if(request.getAttribute("error") != null){ %>
							<h1 style="color:red"><%= request.getAttribute("error")%></h1>
						<% }else{%>
						<h1 id="container">
							<%=request.getAttribute("currentProfile")%>'s Profile Page
						</h1>
					<hr/>
					<h3>About <%=request.getAttribute("currentProfile")%>:</h3>
					<P>
					<div style="width:75%; margin-left: 50px; margin-right: auto;margin-top:50px;">
					<%=request.getAttribute("about")%>
					<% if(request.getSession().getAttribute("user") != null) {%>
						<% if(request.getSession().getAttribute("user").equals(request.getAttribute("currentProfile"))) { %>
							<h4> Want to edit your about page? (only you can see this)</h4>
							<form action="/user/<%=request.getAttribute("currentProfile")%>" method="POST">
								<div class="form-group">
									<textarea  name="about me" id="about me" rows="10" cols="100" placeholder="Tell me about yourself" style="font-size:14pt"></textarea>
									<br>
									<button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" id="submitButton">Submit</button>
								</div>
							</div>
 						<% } %>
					<% } %>
			</p>
		</div>

		<div id="container">
			<hr/>
			<% if(messages.size() != 0) { %>
				<h3> <%=request.getAttribute("currentProfile")%>'s Sent Messages: </h3>
				<div id="infinitescroll">
					<ul>
						<%
						for (Message message : messages) {
							%>
								<li><strong><%= message.getFormattedTime().toString()%>:</strong> <%= message.getContent() %></li>
							<%
						}
						%>
					</ul>
				</div>
				<%} else{ %>
					<h3> <%=request.getAttribute("currentProfile")%> Hasn't Sent Messages </h3>
				<%}%>
				<hr/>
			</div>

	<% } %>
	</body>
</html>
