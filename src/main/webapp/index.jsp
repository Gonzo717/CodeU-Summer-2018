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
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
	<style>
	.content-grid {
		margin-left: auto;
		margin-right: auto;
	  width: 1000px;
	}

	.main-text-pane {
    display: flex;
    min-height: 512px;
    overflow: hidden;
		flex-flow: column;
    width: 100%;
	}

	#dark {
		background-color: rgb(243,243,243);
	}

	#light {
		background-color: #f6f6f6;
	}

	#color {
		background-color: #46B6AC;
	}

	.mdl-typography--text-left h1{
		padding-left: 20px;
	}

	.welcome-to-YACA-right{
		display: flex;
		box-flex: 3;
		flex-grow: 3;
	}

	.mdl-card {
		width: auto;
	}

	.demo-card-wide.mdl-card {
	  width: 450px;
	}

	.demo-card-codeU > .mdl-card__title {
	  color: #fff;
	  height: 256px;
	  background: url('/images/CodeU.png') center / cover;
		background-color: #46B6AC;
		/* background-size: contain; */
	}

	.demo-card-Javachips > .mdl-card__title {
		color: #fff;
		height: 300px;
		background: url('/images/JavaChipsLogo.png') center / cover;
		background-color: #46B6AC;
		/* background-size: contain; */
	}

	.demo-card-conversation > .mdl-card__title {
		color: #fff;
	  height: 256px;
	  background: url('/images/conversations1.png') left no-repeat;
		background-color: #46B6AC;
		background-size: contain;
	}

	.demo-card-profPage > .mdl-card__title {
		color: #fff;
		height: 256px;
		background: url('/images/profPage.png') left no-repeat;
		background-color: #46B6AC;
		background-size: contain;
	}

	.demo-card-activityFeed > .mdl-card__title {
		color: #fff;
		height: 256px;
		background: url('/images/activityFeed.png') left no-repeat;
		background-color: #46B6AC;
		background-size: contain;
	}

	.demo-card-adminStats > .mdl-card__title {
		color: #fff;
		height: 256px;
		background: url('/images/adminStats.png') left no-repeat;
		background-color: white;
		background-size: contain;
	}

	.demo-card-addAdmin > .mdl-card__title {
		color: #fff;
		height: 256px;
		background: url('/images/addAdmin.png') left no-repeat;
		background-color: white;
		background-size: contain;
	}

	.demo-card-playGame > .mdl-card__title {
		color: #fff;
		height: 256px;
		background: url('/images/playGame.png') left no-repeat;
		background-color: white;
		background-size: contain;
	}

	.demo-card-sentMsg > .mdl-card__title {
		color: #fff;
	  height: 235px;
	  background: url('/images/sentMsg.png') left no-repeat;
		background-color: #46B6AC;
		background-size: contain;
	}

	.demo-card-chat > .mdl-card__title {
		color: #fff;
		height: 300px;
		background: url('/images/chat1.png') top no-repeat;
		background-color: #46B6AC;
		background-size: cover;
	}

	.demo-card-wide > .mdl-card__menu {
	  color: #46B6AC;
	}
	/* #login a:hover > #login p{
		display: flex;
	} */

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

						<div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--full-width">
							<label class="mdl-button mdl-js-button mdl-button--icon" for="conversations">
								<a id="conversations" class="mdl-navigation__link" href="/conversations">
									<i class="material-icons">textsms</i>
								</a>
							</label>
							<div class="mdl-textfield__expandable-holder">
								<a id="conversations" class="mdl-navigation__link mdl-typography--text-uppercase" href="/conversations"></a>
							</div>
						</div>

						<%-- <a class="mdl-navigation__link mdl-typography--text-uppercase" href="/conversations">Conversations</a> --%>
						<% if(request.getSession().getAttribute("user") != null){ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase">Hello <%= request.getSession().getAttribute("user") %>!</a>
						<% } else{ %>
						<label class="mdl-button mdl-js-button mdl-button--icon" for="conversations">
							<a id="login" class="mdl-navigation__link" href="/login">
								<i class="fa fa-sign-in" aria-hidden="true"></i>
								<%-- <p id="loginDescription" style="">Login</p> --%>
							</a>
						</label>

						<% } %>
						<%-- <% if(request.getSession().getAttribute("admin") != null){ %> --%>
						<label class="mdl-button mdl-js-button mdl-button--icon" for="conversations">
							<a href="/admin">
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

	<!--Main content starts here-->
	<body>
		<main class="mdl-layout__content" style="display:-webkit-box;">
				<div class="content-grid">
					<div class="page-content">

						<div class="main-text-pane" id="light">
							<div class="mdl-typography--text-left" style="padding-left: 10px; margin-top: 50px;" >
								<h1>Welcome to YACA</h1>
							</div>
							<div class="content-grid mdl-grid">
								<div class="mdl-cell mdl-cell--6-col">
									<div class="demo-card-codeU mdl-card mdl-shadow--2dp">
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											YACA was developed for the Google CodeU Summer 2018 program by our team, JavaChips.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/about.jsp">
												About JavaChips
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- add content here -->
								</div>
								<div class="mdl-cell mdl-cell--6-col">
									<div class="demo-card-Javachips mdl-card mdl-shadow--2dp" >
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Built with <i class="fa fa-heart" style="color:red"></i> by Gonzalo, Crista, and Luis.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/about.jsp">
												Learn More
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- also here -->
								</div>
							</div>
						</div>

					<div class="main-text-pane" id="dark">
							<div class="mdl-typography--text-left" style="padding-left: 10px;">
								<h1>Yet Another</h1>
							</div>
							<div class="content-grid mdl-grid">
								<div class="mdl-cell mdl-cell--4-col">
									<div class="demo-card-profPage mdl-card mdl-shadow--2dp">
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											You'll be able to view and edit your bio; your window to the world.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/login">
												view your profile
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- add content here -->
								</div>
								<div class="mdl-cell mdl-cell--4-col">
									<div class="demo-card-sentMsg mdl-card mdl-shadow--2dp" >
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											After registering, you'll be able to see your own sent messages!
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/login">
												Check Your Messages
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- also here -->
								</div>
								<div class="mdl-cell mdl-cell--4-col">
									<div class="demo-card-activityFeed mdl-card mdl-shadow--2dp" >
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Keep track of what's going on. Check your Activity Feed and see who's doing what.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/login">
												Login
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- also here -->
								</div>
							</div>

						</div>

						<div class="main-text-pane" id="light">
							<div class="mdl-typography--text-left" style="padding-left:10px; height: 100px; width: 365px;">
								<h1>Chat App.</h1>
							</div>
							<div class="content-grid mdl-grid">
								<div class="mdl-cell mdl-cell--3-col">
									<div class="demo-card-conversation mdl-card mdl-shadow--2dp" >
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Try out our ground-breaking take on Public, Direct, and Group Conversations.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/conversations">
												Try Conversations
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- also here -->
								</div>
							  <div class="mdl-cell mdl-cell--9-col">
									<div class="demo-card-chat mdl-card mdl-shadow--2dp">
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Add/Remove Members and set an Expiry time. Conversations can self-destruct!
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/conversations">
												Go Converse
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
							    <!-- add content here -->
							  </div>
							  <div class="mdl-cell">
							    <!-- and probably also here -->
							  </div>
							</div>
						</div>

						<div class="main-text-pane" id="color">
							<div class="mdl-typography--text-left" style="padding-left: 10px;">
								<h1 style="color: white;">Try it out. </h1>
							</div>
							<div class="content-grid mdl-grid">
								<div class="mdl-cell">
									<div class="demo-card-addAdmin mdl-card mdl-shadow--2dp" >
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Try out our ground-breaking take on Public, Direct, and Group Conversations.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/conversations">
												Try Conversations
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
									<!-- also here -->
								</div>
							  <div class="mdl-cell">
									<div class="demo-card-adminStats mdl-card mdl-shadow--2dp">
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Add/Remove Members and set an Expiry time. Conversations can self-destruct!
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/conversations">
												Go Converse
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
							    <!-- add content here -->
							  </div>
								<div class="mdl-cell">
									<div class="demo-card-playGame mdl-card mdl-shadow--2dp">
										<div class="mdl-card__title">
											<%-- <h2 class="mdl-card__title-text">Yet Another Chat App</h2> --%>
										</div>
										<%-- <div class="mdl-card__subtitle">
											<h2 class="mdl-card__subtitle-text">Welcome to YACA.</h2>
										</div> --%>
										<div class="mdl-card__supporting-text">
											Even play a game if you get bored! Coming soon to all users.
										</div>
										<div class="mdl-card__actions mdl-card--border">
											<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/login">
												Check it out.
											</a>
										</div>
										<div class="mdl-card__menu">
										</div>
								</div>
							    <!-- add content here -->
							  </div>
							</div>
						</div>

						<%-- <div class="main-text-pane" id="light">
							<div class="mdl-typography--text-left" style="padding-left: 10px;">
								<h1>Coming Soon... </h1>
							</div>
							<h2 style="text-align: center;">Lots of things</h2>
						</div> --%>

				  </div>

				</div>
		</main>
	</body>
</html>
