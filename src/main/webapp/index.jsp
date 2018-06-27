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
<%@ page errorPage="WEB-INF/view/error.jsp" %>
<html>
<head>

	<title>Trill</title>
	<link rel="stylesheet" href="/css/main.css">
	<link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
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
	</style>

</head>
<body>

	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
	  <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
		<div class="mdl-layout__header-row">
			<a class="mdl-navigation__link" href="/"><span class="mdl-layout-title">Trill</span></a>
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
				<%-- <button class="android-more-button mdl-button mdl-js-button mdl-button--icon mdl-js-ripple-effect" id="more-button">
					<i class="material-icons">more_vert</i>
				</button>
				<ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right mdl-js-ripple-effect" for="more-button">
					<li class="mdl-menu__item">Add something here!</li>
					<li class="mdl-menu__item">Perhaps another?</li>
					<li disabled class="mdl-menu__item">Another one</li>
					<li class="mdl-menu__item">Anotha 1</li>
				</ul> --%>
			</div>
		</div>
	</div>
	<main class="mdl-layout__content">
		<div class="page-content">
			<div class="mdl-cell--stretch">
			    <div
			      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
				  <div class="mdl-layout__title">
						<h1>CodeU Summer 2018</h1>
				  </div>
			      <h2>Welcome to our glorious app</h2>
			      <h3>Powered by JavaChips</h3>
			      <ul class="mdl-list">
			        <li class="mdl-list__item"><a href="/login">Login</a> to begin chatting</li>
			        <li class="mdl-list__item">
						<span>

						Go to the <a href="/conversations">conversations</a> page to
			            create or join a conversation.
						</span>
					</li>
			        <li class="mdl-list__item">View the <a href="/about.jsp">about</a> page to learn more about the
			            project and its devs.</li>
			      </ul>
			  	</div>
		  	</div>
	  	</div>
	</main>
</body>
</html>
