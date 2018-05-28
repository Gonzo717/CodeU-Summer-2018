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
<!DOCTYPE html>
<html>
<head>
	<title>Trill</title>
	<link rel="stylesheet" href="/css/main.css">
</head>
<body>
	<nav> <%-- the menu navbar --%>
		<a id="navTitle" href="/">Trill</a>
		<a href="/conversations">Conversations</a>
		<% if(request.getSession().getAttribute("user") != null){ %>
			<a>Hello <%= request.getSession().getAttribute("user") %>!</a>
		<% } else{ %>
			<a href="/login">Login</a>
		<% } %>
		<a href="/admin">Admin</a>
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
		<div
			style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
			<h2>Welcome to our glorious ADMIN PAGE!</h2>
			<h3>Powered by JavaChips</h3>
		</div>

		<div id="stats"
			style="width:85%; margin-left:auto; margin-right:auto; margin-top: 50px;">
			<h2>Checkout these wonderful site statistics!</h2>
			<form action="/admin" method="POST">
				<button type="refresh">Get Stats</button>
			</form>
			<ul>
				<% if(request.getSession().getAttribute("numUsers")!= null){ %>
					<li><a>Total Users: <%= request.getSession().getAttribute("numUsers") %></a></li>
				<% } %>

				<% if(request.getSession().getAttribute("numConvos")!= null){ %>
					<li><a>Total Conversations: <%= request.getSession().getAttribute("numConvos") %></a></li>
				<% } %>

				<% if(request.getSession().getAttribute("numMessages")!= null){ %>
					<li><a>Total Messages: <%= request.getSession().getAttribute("numUsers") %></a></li>
				<% } %>
			</ul>
		</div>
		<div id="graphs">
			<h3>A graph displaying new users as a function of time in months goes here!</h3>
			<h3>A graph displaying new messages as a function of time in hours goes here!</h3>
		</div>
		<div id="addAdmins">
			<h2>Feeling lonely?</h2>
			<h3>Add more admins:</h3>

		    <% if(request.getAttribute("error") != null){ %>
		        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
		    <% } %>
		    <% if(request.getAttribute("success") != null){ %>
		        <h2 style="color:green"><%= request.getAttribute("success") %></h2>
		        <h3 style="color:green">Now use that distinct username to register as an Administrator!</h3>
		    <% } %>
		    <form action="/admin" method="POST">
		      <label for="username">Username: </label>
		      <br/>
		      <input type="text" name="username" id="username">
		      <br/>
		      <button type="submit">Create</button>
		    </form>
		</div>
	</div>

</body>
</html>
