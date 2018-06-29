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
	  <link rel="stylesheet" href="/css/main.css" type="text/css">
	  <link rel="shortcut icon" href="/images/JavaChipsLogo.png" />


		<style>
			#chat {
				background-color: white;
				height: 500px;
				overflow-y: scroll
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

		<nav>
			<a id="navTitle" href="/">YACA</a>
			<a href="/conversations">Conversations</a>
				<% if (request.getSession().getAttribute("user") != null) { %>
			<a>Hello <%= request.getSession().getAttribute("user") %>!</a>
			<% } else { %>
				<a href="/login">Login</a>
			<% } %>
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
		<% if(request.getAttribute("error") != null){ %>
				<h1 style="color:red"><%= request.getAttribute("error")%></h1>
		<% }else{%>

					<h1><%=request.getAttribute("currentProfile")%>'s Profile Page</h1>
					<hr/>
					<h2>About <%=request.getAttribute("currentProfile")%>:</h2>
					<P>
					<%=request.getAttribute("about")%>
					<% if(request.getSession().getAttribute("user") != null) {%>
						<% if(request.getSession().getAttribute("user").equals(request.getAttribute("currentProfile"))) { %>
							<h3> Want to edit your about page? (only you can see this)</h3>
							<form action="/user/<%=request.getAttribute("currentProfile")%>" method="POST">
								<div class="form-group">
									<textarea  name="about me" id="about me" rows="10" cols="100" placeholder="Tell me about yourself" style="font-size:14pt"></textarea>
									<br>
									<input type="Submit">
								</div>
 						<% } %>
					<% } %>
			</p>
		</div>

		<div id="container">
			<hr/>
			<% if(messages.size() != 0) { %>
				<h2> <%=request.getAttribute("currentProfile")%>'s Sent Messages: </h2>
				<div id="chat">
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
					<h2> <%=request.getAttribute("currentProfile")%> Hasn't Sent Messages </h2>
				<%}%>
				<hr/>
			</div>

	<% } %>
	</body>
</html>
