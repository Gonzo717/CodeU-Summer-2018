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
  <title>Login</title>
  <link rel="stylesheet" href="/css/main.css">
  <link rel="shortcut icon" href="/images/YACA.png" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
  <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
  <style>
  .content-grid {
	max-width: 960px;
	display: inline-block;
  }
  .page-content{
	  padding-top: 50px;
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
  #submitButton {
	  display: inline-block;
  }
  </style>

</head>
<body>

	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
	  <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
		<div class="mdl-layout__header-row">
			<a class="mdl-navigation__link" href="/"><span class="mdl-layout-title">YACA</span></a>
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
						<% } else{ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/login">Login</a>
						<% } %>
						<% if(request.getSession().getAttribute("admin") != null){ %>
						  <a href="/admin">Admin</a>
						<% } %>
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/activityfeed">Activity Feed</a>
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

	<main class="mdl-layout__content" style="display:-webkit-box;">
	  <div class="page-content">
		  <div class="mdl-cell--stretch">
			  <div
				  style="width:75%; margin-left: auto; margin-right: auto;margin-top:50px;">
	  				<div class="mdl-layout__title">
				    	<h1>Login</h1>
					</div>
				    <% if(request.getAttribute("error") != null){ %>
				        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
				    <% } %>

				    <form action="/login" method="POST">
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" name="username" id="username">
						    <label class="mdl-textfield__label" for="username">Username...</label>
						</div>
					<%-- <label for="username">Username: </label> --%>
					<br/>
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="password" name="password" id="password">
							<label class="mdl-textfield__label" for="password">Password...</label>
						</div>
					<br/>
					<button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" id="submitButton">Login</button>
				    </form>
					</br>
					<form action="/register" method="get">
						<button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" id="submitButton">Register</button>
					</form>
          <br>

			</div>
		</div>
	</main>
</body>
</html>
