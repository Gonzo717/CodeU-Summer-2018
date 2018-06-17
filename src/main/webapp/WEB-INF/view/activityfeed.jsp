<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.store.basic.ActivityStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<%
List<Activity> activities = (List<Activity>) request.getAttribute("activities");
%>
<!DOCTYPE>
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
		<h1 id="container">
			Activity Feed
		</h1>
		<hr/>
		<div id="infinitescroll">
			<%
			if(activities == null || activities.isEmpty()) {
			%>
				<p>No activities, start exploring!</p>
			<%
			} else {
			%>
				<%
				for(Activity activity : activities) {
					if(activity != null) {
						if(activity.getType().equals("newUser")) {
						%>
							<span><b><%=activity.getCreationTimeFormatted()%></b> New user has been created! Welcome, <%=UserStore.getInstance()
							.getUser(activity.getOwner()).getName()%></span>
							<hr/>
						<%
						}
						else if(activity.getType().equals("newConvo")) {
						%>
							<span><b><%=activity.getCreationTimeFormatted()%></b>New conversation has been created by <%=UserStore.getInstance()
							.getUser(activity.getOwner()).getName()%></span>
							<hr/>
						<%
						}
						else if(activity.getType().equals("newMessage")) {
						%>
							<span><b><%=activity.getCreationTimeFormatted()%></b>New message has been sent by <%=UserStore.getInstance()
							.getUser(activity.getOwner()).getName()%></span>
							<hr/>
						<%
						}
					}
				}
				%>
			<%
			}
			%>
			<hr/>
		</div>
	</body>
</html>
