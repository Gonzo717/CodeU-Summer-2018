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

<%@ page import="codeu.model.data.Tictactoe"%>

<!DOCTYPE html>
<html>
<head>
	<title>Trill</title>
	<link rel="stylesheet" href="/css/main.css">
  	<link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
</head>
<body>

	<jsp:useBean id="theGame" class="codeu.model.data.Tictactoe" scope="session"/>

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

				<% if(request.getSession().getAttribute("numAdministrators")!= null){ %>
					<li><a>Total Admin: <%= (request.getSession().getAttribute("numAdministrators")) %></a></li>
				<% } %>

				<% if(request.getSession().getAttribute("mostActiveUser")!= null){ %>
					<li><a>Most Active User: <%= request.getSession().getAttribute("mostActiveUser") %></a></li>
				<% } %>
			</ul>
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
		<!-- Game begins! -->
		<div id="game" style="margin-bottom: 50px;">
			<h2>Bored?</h2>
			<h2>Play a game!</h2>
			<!-- Creating game -->
			<form action="/admin" method="POST">
				<input type="submit" name="playGame" value="Tic Tac Toe">
				<% 
				if(request.getSession().getAttribute("TicTacToe") != null) { 
					 %>
					<table border=1>
				    	<%for(int row = 0; row < 3; row++) { %>
				    		<tr>
				    		<%for(int col= 0; col < 3; col++){ %>
				        			<td><input type=radio name=board value="<%=row%><%=col%>"/></td>
				        	<% } %>
				    	<%} %>
				    		</tr>
				<% } %>
			<!-- Updating moves -->
			</form>
			<form action="admin" method="POST">
				<% if(request.getSession().getAttribute("player") != null) { 
					%>
					<table border=1>
						<%
				    	for(int row = 0; row < 3; row++) { %>
				    		<tr>
				    		<%for (int col= 0; col < 3; col++){ %>
				        		<%if(theGame.get(row, col) == 1){ %>
				        			<td>X</td>
				        		<% }else if(theGame.get(row, col) == -1){ %>
				        			<td>O</td>
				        		<% }else{ %>
				        			<td><input type=radio name=board value="<%=row%><%=col%>"/></td>
				        		<% } %>
				    		<% } %>
				    		</tr>
				    	<% }  %>
				<% if(request.getSession().getAttribute("boardFull") != null){ %>
		        	<h3 style="color:red">The board is full and nobody won!</h3>
		        	<h3>Click "Tic Tac Toe to play again"</h3>
				<% } else if(request.getSession().getAttribute("hasWon") != null){ %>
		        	<h3 style="color:green">Congrats! Player <%= request.getSession().getAttribute("hasWon") %> has won!</h3>
		        	<h3>Click "Tic Tac Toe to play again"</h3>
				<% } else { %> 
				<input type="submit" name="updateGame" value="Make move">
				<% } %>
			<% } %>
			</form>
		</div>
	</div>


</body>
</html>
