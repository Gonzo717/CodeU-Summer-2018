<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>
<!DOCTYPE html>
<html>
	<head>
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
      <a href="/user">Profile Page</a>
			<a href="/about.jsp">About</a>
		</nav>
		<h1>
        <h1>This is a sample profile page</h1>
      <% } %>
		</h1>
	</body>
</html>
