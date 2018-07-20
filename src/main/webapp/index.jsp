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

	<title>YACA</title>
	<link rel="stylesheet" href="/css/main.css">
	<link rel="shortcut icon" href="/images/YACA.png" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
	<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
	<style>
	.content-grid {
		margin-left: auto;
		margin-right: auto;
	  width: 1000px;
	}

	.welcome-to-YACA {
    background-color: rgb(243,243,243);
    display: flex;
    min-height: 512px;
    overflow: hidden;
    width: 100%;
	}

	/* .welcome-to-YACA-left{
    display: flex;
    box-flex: 3;
    flex-grow: 3;
	} */

	.welcome-to-YACA-right{
		display: flex;
		box-flex: 3;
		flex-grow: 3;
	}

	/* #view-source {
	  display: block;
	  right: 0;
	  bottom: 0;
	  margin-right: 40px;
	  margin-bottom: 40px;
	  z-index: 900;
	} */
	.demo-card-wide.mdl-card {
	  width: 450px;
	}
	.demo-card-wide > .mdl-card__title {
	  color: #fff;
	  height: 176px;
	  background: url('/images/CodeU.png') center / cover #46B6AC;
	}
	.demo-card-wide > .mdl-card__menu {
	  color: #46B6AC;
	}
	</style>

</head>
<%-- <boy> --%>

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
						<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/conversations">Conversations</a>
						<% if(request.getSession().getAttribute("user") != null){ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase">Hello <%= request.getSession().getAttribute("user") %>!</a>
						<% } else{ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/login">Login</a>
						<% } %>
						<% if(request.getSession().getAttribute("admin") != null){ %>
								<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/admin">Admin</a>
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

	<!--Main content starts here-->
	<body>
		<main class="mdl-layout__content" style="display:-webkit-box;">
				<div class="content-grid">
					<div class="page-content">
						<div class="welcome-to-YACA">

							<div class="welcome-to-YACA-left">
								<div class="demo-card-wide mdl-card mdl-shadow--2dp">
									<div class="mdl-card__title">
										<h2 class="mdl-card__title-text">Yet Another Chat App</h2>
									</div>
									<div class="mdl-card__subtitle">
										<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
									</div>
									<div class="mdl-card__supporting-text">
										YACA was developed for the Google CodeU Summer 2018 program by our team, JavaChips.
									</div>
									<div class="mdl-card__actions mdl-card--border">
										<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
											About JavaChips
										</a>
									</div>
									<div class="mdl-card__menu">
										<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
											<i class="material-icons">share</i>
										</button>
									</div>
							</div>
							<div class="welcome-to-YACA-right">
								<div class="mdl-layout__title">
									<h1>Welcome to YACA</h1>
								</div>
								<br>
								<div class="mdl-layout__title">
										YACA was developed for the Google CodeU Summer program by our team, JavaChips. Meet the JavaChips team&nbsp<a class="mdl-color-text--cyan" href="/about.jsp">here</a>.
										<br>
										Our goal was not only to develop a web chat app for the CodeU program, but also to enhance the chat user experience by providing a new conversational
										organization to chats. Some of these features are outlined below and are currently being implemented by the JavaChips team!
								</div>
										<ul class="mdl-list">
											<li class="mdl-list__item">Chat threads - Talk about any subject with a large amount of people in a forum-like chat.</li>
											<li class="mdl-list__item">Conversation tagging - Organize and search for conversations based on tags that describe them.</li>
											<li class="mdl-list__item">Disappearing chat threads and subconversations -
												Does your group chat ever get off topic which results in an infinite amount of scrolling to figure out what happened while you were gone?
												With disappearing subconversations, you never need to worry about it again! Subconversations allow for group conversations to separate topics into a
												disappearing chat thread. After the chat disappears, a summery of the conversation is made so you don't have to worry about what you missed.
											</li>
											<li class="mdl-list__item">Unique talk only feature - Have fun conversations by limiting what type of messages users can send. Have a conversation in exclusively gifs
												or images-or even limit word usage to one word. Watch the creative madness unroll!
											</li>
								</div>
							</div>
						</div>

						<div class="page-content mdl-cell mdl-cel--2-col">
						<br><br><br>
					    <ul class="mdl-list">
					       <li class="mdl-list__item"><a class="mdl-color-text--cyan" href="/login">Login </a>&nbsp to begin chatting</li>
					       <li class="mdl-list__item"><span>
									Go to the &nbsp<a class="mdl-color-text--cyan" href="/conversations">conversations</a>&nbsp page to
				          create or join a conversation.
								</span>
								</li>
				        <li class="mdl-list__item">View the &nbsp<a class="mdl-color-text--cyan" href="/about.jsp">about</a>&nbsp page to learn more about the JavaChips team.</li>
					    </ul>
						</div>

				  </div>

				</div>
		</main>
	</body>
</html>
