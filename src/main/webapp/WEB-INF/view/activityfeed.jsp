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
			<a href="/activityfeed">Activity Feed</a>
			<a href="/about.jsp">About</a>
		</nav>
		<h1>
			This is the activity feed.
			It is currently under construction!
		</h1>
	</body>
</html>