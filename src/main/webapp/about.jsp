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
  <title>About</title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <link rel="shortcut icon" href="/images/YACA.png" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-yellow.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll;
    }
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
	.fa:hover {
	    opacity: 0.7;
	}

  </style>
  <style>
  .demo-card-square.mdl-card {
    width: 320px;
    height: 430px;
  }
  .demo-card-square > .mdl-card__title{
    color: #fff;
    background:
  }
  #Luis{
	  background-size: auto;
	  background:
        url('/images/luisphoto.JPG') left no-repeat #46B6AC;
	  max-width: 100%;
	  max-height: 100%;

  }

	#Crista{
		background-size: auto;
		background:
				url('/images/crista.jpeg') center no-repeat #46B6AC;
		max-width: 100%;
		max-height: 100%;

	}

	#Gonzalo{
		background-size: auto;
		background:
				url('/images/gonzo.jpeg') right no-repeat #46B6AC;
		max-width: 100%;
		max-height: 100%;
		/* align: right; */

	}

	.demo-card-square.mdl-card {
		width: 85%;
		height: 500px;
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

	<main class="mdl-layout__content" style="display:-webkit-box;">
		<div class="content-grid">
			<div class="page-content">
		  <div id="container">
		    <div
		      style="width:100%; margin-left:auto; margin-right:auto; margin-top: 50px;">

		      <h1 class="mdl-typography--text-center">Yet Another Chat App</h1>
		      <h5>
		        This chat app started as a playground for us Google CodeU participants to
		        practice our java and team working skills. We've decided to call this project YACA
						and hope to fill it with amazing features in the future!
		      </h5>

		      <h1 class="mdl-typography--text-center mdl-typography--display-2"><Strong> Meet Team JavaChips:</strong></h1>
		  </br>
		  	<div class="mdl-cell--stretch dev-cards">
			  <div class="demo-card-square mdl-card mdl-shadow--2dp">
			    <div class="mdl-card__title mdl-card--expand" id="Gonzalo">
			      <h2 class="mdl-card__title-text">Gonzalo Rosales</h2>
			    </div>
			    <div class="mdl-card__supporting-text">
					Gonzalo is a rising sophomore at Boston University
					and is happy to finally be done with his discrete math class.
					He is from Chicago and enjoys reading, playing the trumpet,
					and riding his bike around the city. This summer he will also be teaching
					kids how to code at Northwestern!
			    </div>
			    <div class="mdl-card__actions mdl-card--border">
					<a href="https://www.linkedin.com/in/gonzalo-rosales17/">
						<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
	  			  	<i class="fa fa-linkedin"></i>
	  			  </button>
					</a>
					<a href="https://github.com/Gonzo717s">
						<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
							<i class="fa fa-github"></i>
						</button>
					</a>
			    </div>
			  </div>
		  </br></br>
			  <div class="demo-card-square mdl-card mdl-shadow--2dp">
			    <div class="mdl-card__title mdl-card--expand" id="Crista">
			      <h2 class="mdl-card__title-text">Crista Mondragon</h2>
			    </div>
			    <div class="mdl-card__supporting-text">
					  Crista is a rising sophomore at the University of
					  Illinois at Chicago. Unlike Gonzalo, she seems to be pretty good at discrete
					  and is actually a TA for the course. She is from the NW suburbs of Chicago
					  and enjoys listening to alt, rock, and many other types of music, as well as
					  working with robotics, teaching, and playing Overwatch.
			    </div>
			    <div class="mdl-card__actions mdl-card--border">
			      <a href="https://www.linkedin.com/in/cristamondragon/">
						  <button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
								<i class="fa fa-linkedin"></i>
					    </button>
			  	  </a>
						<a href="https://github.com/cmondragon15117">
							<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
								<i class="fa fa-github"></i>
							</button>
						</a>
			    </div>
			  </div>
		  </br></br>
			  <div class="demo-card-square mdl-card mdl-shadow--2dp">
			    <div class="mdl-card__title mdl-card--expand" id="Luis">
			      <h2 class="mdl-card__title-text">Luis Clague</h2>
			    </div>
			    <div class="mdl-card__supporting-text">
					Also a rising sophomore at Rice University.
					  Instead of sitting coding all day he mixes it up by playing soccer for Rice and
					  is a huge soccer fanatic. Other than working on this chat app, he will be doing
					  research on genome visualization and bioinformatics at the Baylor College of Medicine.
					  If that wasn't personal enough, his favorite color is blue. :D
			    </div>
			    <div class="mdl-card__actions mdl-card--border">
					<a class="mdl-color--white" href="https://www.linkedin.com/in/luisclague/" style="text-decoration:none;">
					<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
					  <i class="fa fa-linkedin"></i>
					</button>
					</a>
					<a href="https://github.com/clague17">
						<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
							<i class="fa fa-github"></i>
						</button>
					</a>
			    </div>
			  </div>
		  </div>
		  </br></br>

		    </div>
		  </div>
	  	</div>
  	</div>
</main>
</body>
</html>
