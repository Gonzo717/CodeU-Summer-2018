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
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Trill</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <% if(request.getSession().getAttribute("admin") != null){ %>
      <a href="/admin">Admin</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>About the Trill Chat App</h1>
      <p>
        This chat app started as a playground for us Google CodeU participants to
        practice our java and team working skills. We have decided to call this chat app
        Trill and hope to fill it with amazing features in the future!
      </p>

      <h1><Strong> Meet Team JavaChips:</strong></h1>

      <ul>
        <li><strong>Gonzalo Rosales:</strong> Rising sophomore at Boston University
          and is happy to finally be done with his discrete math class.
          He is from Chicago and enjoys reading, playing the trumpet,
          and riding his bike around the city. This summer he will also be teaching
          kids how to code at Northwestern!</li>
        <li><strong>Crista Mondragon:</strong> Rising sophomore at the University of
          Illinois at Chicago. Unlike Gonzalo, she seems to be pretty good at discrete
          and is actually a TA for the course. She is from the NW suburbs of Chicago
          and enjoys listening to alt, rock, and many other types of music, as well as
          working with robotics, teaching, and playing Overwatch.</li>
        <li><strong>Luis Clague:</strong> Also a rising sophomore at Rice University.
          Instead of sitting coding all day he mixes it up by playing soccer for Rice and
          is a huge soccer fanatic. Other than working on this chat app, he will be doing
          research on genome visualization and bioinformatics at the Baylor College of Medicine.
          If that wasn't personal enough, his favorite color is blue.
        </li>
      </ul>

    </div>
  </div>
</body>
</html>
