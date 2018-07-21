<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.store.basic.ActivityStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.Activity.ActivityType" %>

<%
List<Activity> activities = (List<Activity>) request.getAttribute("activities");
%>
<!DOCTYPE>
<html>
	<head>
		<link rel="stylesheet" href="/css/main.css">
		<link rel="shortcut icon" href="/images/JavaChipsLogo.png" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
		<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
		<style>
		.content-grid {
			width:960px;
			max-width: 960px;
		}
		.page-content{
			width: 800px;
			margin-top: 12%;
			margin-left: auto;
		    margin-right: auto;
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
								<a></a>
							<% } else{ %>
								<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/login">Login</a>
							<% } %>
							<% if(request.getSession().getAttribute("admin") != null) { %>
								<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/admin">Admin</a>
							<% } %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href="/activityfeed">Activity Feed</a>
							<% if(request.getSession().getAttribute("user") != null){ %>
							<a class="mdl-navigation__link mdl-typography--text-uppercase" href ="/user/<%=request.getSession().getAttribute("user")%>">My Profile</a>
							<% } %>
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
	<main class="mdl-layout__content">
		<div class="content-grid">
			<div class="page-content">
				<h1 id="container">
					Activity Feed
				</h1>
				<hr/>
				<div id="infinitescroll">
					<%
					if(activities == null || activities.isEmpty()) {
					%>
						<p>No activities, start exploring!</p>
					<%
					} else {
					%>
						<%
						for(Activity activity : activities) {
							if(activity != null) {
								if(activity.getType().equals(ActivityType.USER)) {
								%>
									<span><b><%=activity.getCreationTimeFormatted()%></b> New user has been created! Welcome, <%=UserStore.getInstance()
									.getUser(activity.getOwnerId()).getName()%></span>
									<hr/>
								<%
								}
								else if(activity.getType().equals(ActivityType.CONVERSATION)) {
								%>
									<span><b><%=activity.getCreationTimeFormatted()%></b>New conversation has been created by <%=UserStore.getInstance()
									.getUser(activity.getOwnerId()).getName()%></span>
									<hr/>
								<%
								}
								else if(activity.getType().equals(ActivityType.MESSAGE)) {
								%>
									<span><b><%=activity.getCreationTimeFormatted()%></b>New message has been sent by <%=UserStore.getInstance()
									.getUser(activity.getOwnerId()).getName()%></span>
									<hr/>
								<%
								}
							}
						}
						%>
					<%
					}
					%>
					<hr/>
				</div>
			</div>
		</div>
	</main>
</body>
</html>
