<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<<<<<<< HEAD
<head>
  <a class="mdl-navigation__link" href="/"><span class="mdl-layout-title">YACA</span></a>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav> <%-- the menu navbar --%>
    <a id="navTitle" href="/">YACA</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <!-- Add login checking for activity feed here -->
    <%-- <% if(request.getSession().getAttribute("admin") != null){ %> --%>
      <a href="/admin">Admin</a>
    <%-- <% } %> --%>
    <a href="/activityfeed">Activity Feed</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href ="/user/<%=request.getSession().getAttribute("user")%>">Your Profile Page</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/logout">Logout</a>
    <% } %>
  </nav>
=======
  <head>
		<link rel="stylesheet" href="/css/main.css">
		<link rel="shortcut icon" href="/images/YACA.png" />
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
>>>>>>> 7002f0b56e87485d9751c941cad259c57902fcf1

		  z-index: 900;
		}
		</style>
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
        </div>
      </div>
    </div>
	</head>
<body>
  <br><br><br><br><br>
  <h1 id="container">
    <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
      Oops! An error happened...<br/>
      Help report this error by raising an issue in our GitHub page.<br/>
      <a href="https://github.com/Gonzo717/CodeU-Summer-2018">GitHub Link</a><br/>
      Thank you and hope you're enjoying YACA!
      - JavaChips Team
      <br/>

    </h1>
  </div>
</body>
</html>
