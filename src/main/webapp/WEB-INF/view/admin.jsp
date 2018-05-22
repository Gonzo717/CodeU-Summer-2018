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
		<a href="/about.jsp">About</a>
		<% if(request.getSession().getAttribute("user") != null){ %>
			<a href="/logout">Logout</a>
		<% } %>				 
	</nav>

	<div id="container">
		<div
			style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

			<h1>CodeU Summer 2018</h1>
			<h2>Welcome to our glorious ADMIN PAGE!</h2>
			<h3>Powered by JavaChips</h3>
		</div>

		<div id="stats"
			style="width:85%; margin-left:auto; margin-right:auto; margin-top: 50px;">
			<h2>Checkout these wonderful site statistics!</h2>
			<form action="/admin" method="POST">
				<button type="refresh">Refesh Stats</button>
			</form>
			<ul>
				<li><a>Total Users: <%= request.getSession().getAttribute("numUsers") %></a></li>
				<li><a>Total Conversations: <%= request.getSession().getAttribute("numConvos") %></a></li>
				<li><a>Total Messages: <%= request.getSession().getAttribute("numMessages") %></a></li>
			</ul>
			<h3>A graph displaying new users as a function of time in months goes here!</h3>
			<h3>A graph displaying new messages as a function of time in hours goes here!</h3>

			<h3>Add admins:</h3>
		    <form action="/admin" method="addAdmin">
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
