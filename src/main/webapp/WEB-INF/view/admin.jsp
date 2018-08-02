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
	<title>YACA</title>
	<link rel="stylesheet" href="/css/main.css">
	<link rel="shortcut icon" href="/images/YACA.png" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
						<label class="mdl-button mdl-js-button mdl-button--icon" for="conversations">
							<a id="conversations" class="mdl-navigation__link" href="/conversations">
								<i class="material-icons">textsms</i>
							</a>
						</label>

						<%-- <a class="mdl-navigation__link mdl-typography--text-uppercase" href="/conversations">Conversations</a> --%>
						<% if(request.getSession().getAttribute("user") != null){ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase">Hello <%= request.getSession().getAttribute("user") %>!</a>
						<% } else{ %>
						<label class="mdl-button mdl-js-button mdl-button--icon" for="conversations">
							<a style="color: inherit;" id="login" href="/login">
								<i class="fa fa-sign-in" aria-hidden="true"></i>
							</a>
							<p style="display: none;">Login</p>
						</label>

						<% } %>
						<%-- <% if(request.getSession().getAttribute("admin") != null){ %> --%>
						<label class="mdl-button mdl-js-button mdl-button--icon" for="conversations">
							<a style="color:inherit;" href="/admin">
								<i class="fa fa-id-card" aria-hidden="true"></i>
							</a>
						</label>
					    <%-- <% } %> --%>
						<label class="mdl-button mdl-js-button mdl-button--icon" for="activity">
							<a id="activity" class="mdl-navigation__link" href="/activityfeed">
								<i class="material-icons">format_list_numbered</i>
							</a>
						</label>
						<%-- <a class="mdl-navigation__link mdl-typography--text-uppercase" href="/activityfeed">Activity Feed</a> --%>
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

	<jsp:useBean id="theGame" class="codeu.model.data.Tictactoe" scope="session"/>

	<main class="mdl-layout__content" style="display:-webkit-box;">
	<a name="top"></a>
		<div class="container page-content">
			<div class="content-grid mdl-grid">
			<!-- <div id="container"> -->
				<div class="mdl-cell--stretch">
					<div id="stats">
						<h2>Checkout these wonderful site statistics!</h2>
						<ul class="mdl-list">
							<% if(request.getSession().getAttribute("numUsers")!= null){ %>
								<li class="mdl-list__item">
									<span class="mdl-list__item-primary-content">
									<i class="material-icons mdl-list__item-icon">person</i>
									Total Users: <%= request.getSession().getAttribute("numUsers") %>
									</span>
								</li>
							<% } %>

							<% if(request.getSession().getAttribute("numConvos")!= null){ %>
								<li class="mdl-list__item">
									<span class="mdl-list__item-primary-content">
									<i class="material-icons mdl-list__item-icon">assessment</i>
									Total Conversations: <%= request.getSession().getAttribute("numConvos") %>
									</span>
								</li>
							<% } %>

							<% if(request.getSession().getAttribute("numMessages")!= null){ %>
								<li class="mdl-list__item">
									<span class="mdl-list__item-primary-content">
									<i class="material-icons mdl-list__item-icon">speaker_notes</i>
									Total Messages: <%= request.getSession().getAttribute("numMessages") %>
									</span>
								</li>
							<% } %>

							<% if(request.getSession().getAttribute("numAdministrators")!= null){ %>
								<li class="mdl-list__item">
									<span class="mdl-list__item-primary-content">
									<i class="material-icons mdl-list__item-icon">supervised_user_circle</i>
									Total Admin: <%= request.getSession().getAttribute("numAdministrators") %>
									</span>
								</li>
							<% } %>

							<% if(request.getSession().getAttribute("mostActiveUser")!= null){ %>
								<li class="mdl-list__item">
									<span class="mdl-list__item-primary-content">
									<i class="material-icons mdl-list__item-icon">how_to_reg</i>
									Most Active User: <%= request.getSession().getAttribute("mostActiveUser") %>
									</span>
								</li>
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

						<script>
						(function addAdmin() {
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

						<button id="demo-show-snackbar" class="mdl-button mdl-js-button mdl-button--raised" type="submit" onclick="addAdmin()">Add Admin</button>
						<%-- <div id="demo-snackbar-example" class="mdl-js-snackbar mdl-snackbar">
						  <div class="mdl-snackbar__text"></div>
						  <button class="mdl-snackbar__action" type="button">click this?</button>
						</div> --%>
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
						</br>
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
