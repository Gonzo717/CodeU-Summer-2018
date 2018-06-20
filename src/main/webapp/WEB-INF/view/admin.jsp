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
	<link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.light_blue-deep_orange.min.css">
	<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
	<style>
	.content-grid {
	  max-width: 960px;
	  display: inline-block;
	}
	.page-content{
		margin-top: 50px;
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

</head>
<body>
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
	  <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
		<div class="mdl-layout__header-row">
			<span class="mdl-layout-title">Trill</span>
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
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/admin">Admin</a>

						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/activityfeed">Activity Feed</a>
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/about">About</a>
						<% if(request.getSession().getAttribute("user") != null){ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/logout">Logout</a>
						<% } %>
					</nav>
				</div>
				<span class="android-mobile-title mdl-layout-title">
					<img class="android-logo-image" src="/images/JavaChipsLogoMenu.png">
				</span>
				<button class="android-more-button mdl-button mdl-js-button mdl-button--icon mdl-js-ripple-effect" id="more-button">
					<i class="material-icons">more_vert</i>
				</button>
				<ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right mdl-js-ripple-effect" for="more-button">
					<li class="mdl-menu__item">Add something here!</li>
					<li class="mdl-menu__item">Perhaps another?</li>
					<li disabled class="mdl-menu__item">Another one</li>
					<li class="mdl-menu__item">Anotha 1</li>
				</ul>
			</div>
		</div>
	</div>

	<jsp:useBean id="theGame" class="codeu.model.data.Tictactoe" scope="session"/>

	<main class="mdl-layout__content">
	<a name="top"></a>
		<div class="page-content">
			<div class="content-grid mdl-grid">
			<!-- <div id="container"> -->
				<div class="mdl-cell--stretch">
					<div id="stats">
						<h2>Checkout these wonderful site statistics!</h2>
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

						<form action="/admin" method="POST">
							<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" type="refresh">
								Get Stats
							</button>
						</form>
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

					</div>
					<form action="/admin" method="POST">
						<!-- Textfield with Floating Label -->
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <input class="mdl-textfield__input" type="text" name="username" id="username">
							<label class="mdl-textfield__label" for="sample3">Username...</label>
						</div>


						<button id="demo-show-snackbar" class="mdl-button mdl-js-button mdl-button--raised" type="submit">Add Admin</button>
						<div id="demo-snackbar-example" class="mdl-js-snackbar mdl-snackbar">
						  <div class="mdl-snackbar__text"></div>
						  <button class="mdl-snackbar__action" type="button"></button>
						</div>
						<script>
						(function() {
							'use strict';
							var snackbarContainer = document.querySelector('#demo-snackbar-example');
							var showSnackbarButton = document.querySelector('#demo-show-snackbar');
							var handler = function(event) {
							showSnackbarButton.style.backgroundColor = '';
						};
						showSnackbarButton.addEventListener('click', function() {
							'use strict';
							showSnackbarButton.style.backgroundColor = '#' + Math.floor(Math.random() * 0xFFFFFF).toString(16);
							var data = {
							  message: 'Admin successfully added',
							  timeout: 2000,
							  actionHandler: handler,
							  actionText: 'Undo'
							};
							snackbarContainer.MaterialSnackbar.showSnackbar(data);
						});
						}());
						</script>
					</form>

				<!-- Game begins! -->

				<div id="game">
					<h2>Bored?</h2>
					<h2>Play a game!</h2>
					<!-- Creating game -->
					<form action="/admin" method="POST">
					<!-- Accent-colored raised button with ripple -->
						<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" name="playGame" value="Tic Tac Toe">
						  Tic Tac Toe
						</button>
						<%
						if(request.getSession().getAttribute("TicTacToe") != null) {
							 %>
							<table border=1>
								<%for(int row = 0; row < 3; row++) { %>
									<tr>
									<%for(int col= 0; col < 3; col++){ %>
										<span class="checkmark">
										<td>

										<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored" type="radio" name="board" value="<%=row%><%=col%>">
											<i class="material-icons">add</i>
										</button>
										</td>
										</span>
									<% } %>
								<%} %>
									</tr>
						<% } %>
					</form>
					<!-- Updating moves -->
					<% if(request.getSession().getAttribute("error") != null){%>
						<h3 style="color:red">Please select a tile</h3>
					<% request.getSession().setAttribute("error", null);
						} %>
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
						<button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" name="updateGame" value="Make Move">
						  Make Move
						</button>
						<%-- <input type="submit" name="updateGame" value="Make move"> --%>
						<% } %>
					<% } %>
					</form>
				</div>
		</div>
		</div>
	</main>

</body>
</html>
