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
      url('../assets/demos/dog.png') bottom right 15% no-repeat #46B6AC;
  }
  #Luis{
	  background-size: cover;
	  background:
        url('/images/luisphoto.JPG') no-repeat #46B6AC;
	  max-width: 100%;
	  max-height: 100%;

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
		  <div id="container">
		    <div
		      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

		      <h1 class="mdl-typography--text-center mdl-typography--display-1">About the Trill Chat App</h1>
		      <p>
		        This chat app started as a playground for us Google CodeU participants to
		        practice our java and team working skills. We have decided to call this chat app
		        Trill and hope to fill it with amazing features in the future!
		      </p>

		      <h1 class="mdl-typography--text-center mdl-typography--display-2"><Strong> Meet Team JavaChips:</strong></h1>
		  </br>
		  	<div class="mdl-cell--stretch">
			  <div class="demo-card-square mdl-card mdl-shadow--2dp">
			    <div class="mdl-card__title mdl-card--expand" id="Gonzalo">
			      <h2 class="mdl-card__title-text">Gonzalo Rosales</h2>
			    </div>
			    <div class="mdl-card__supporting-text">
					Rising sophomore at Boston University
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
			    </div>
			  </div>
		  </div>
		  </br></br>
		  <div class="mdl-cell--stretch">
			  <div class="demo-card-square mdl-card mdl-shadow--2dp">
			    <div class="mdl-card__title mdl-card--expand" id="Crista">
			      <h2 class="mdl-card__title-text">Crista Mondragon</h2>
			    </div>
			    <div class="mdl-card__supporting-text">
					Rising sophomore at the University of
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

			    </div>
			  </div>
		  </div>
		  </br></br>
		  <div class="mdl-cell--stretch">
			  <div class="demo-card-square mdl-card mdl-shadow--2dp">
			    <div class="mdl-card__title mdl-card--expand" id="Luis">
			      <h2 class="mdl-card__title-text">Luis Clague</h2>
			    </div>
			    <div class="mdl-card__supporting-text">
					Also a rising sophomore at Rice University.
					  Instead of sitting coding all day he mixes it up by playing soccer for Rice and
					  is a huge soccer fanatic. Other than working on this chat app, he will be doing
					  research on genome visualization and bioinformatics at the Baylor College of Medicine.
					  If that wasn't personal enough, his favorite color is blue.
			    </div>
			    <div class="mdl-card__actions mdl-card--border">
					<a class="mdl-color--white" href="https://www.linkedin.com/in/luisclague/" style="text-decoration:none;">
					<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect mdl-color--cyan">
					  <i class="fa fa-linkedin"></i>
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
