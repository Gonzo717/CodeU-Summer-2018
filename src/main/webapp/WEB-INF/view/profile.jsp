<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Profile Page</title>
	  <link rel="stylesheet" href="/css/main.css" type="text/css">

	</head>

	<body>
		<nav>
			<a id="navTitle" href="/">Trill</a>
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
	      <a href ="/user/<%=request.getSession().getAttribute("user")%>">Profile Page</a>
	    <% } %>
			<a href="/about.jsp">About</a>
			<% if(request.getSession().getAttribute("user") != null){ %>
				<a href="/logout">Logout</a>
			<% } %>
		</nav>
		<div id="container">
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
	<hr/>
	</body>
</html>
