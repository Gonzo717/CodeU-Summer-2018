<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
  <title>YACA</title>
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
    <% if(request.getSession().getAttribute("admin") != null){ %>
      <a href="/admin">Admin</a>
    <% } %>
    <a href="/activityfeed">Activity Feed</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href ="/user/<%=request.getSession().getAttribute("user")%>">Your Profile Page</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/logout">Logout</a>
    <% } %>
  </nav>

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
