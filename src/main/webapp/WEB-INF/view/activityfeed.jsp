<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.store.basic.ActivityStore" %>
<%
List<Activity> activities = (List<Activity>) request.getAttribute("activities");    
%>
<!DOCTYPE>
<html>
  <head>
    <link rel="stylesheet" href="/css/main.css" type="text/css">

  </head>
  
  <body>
    <nav>
      <a id="navTitle" href="/">Trill</a>
      <a href="/conversations">Conversations</a>
        <% if (request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <% } else { %>
        <a href="/login">Login</a>
      <% } %>
      <!-- Add login checking for activity feed here -->
      <a href="/activityfeed">Activity Feed</a>
      <a href="/about.jsp">About</a>
    </nav>
    
    <div id="container">
      <h1>
        Activity Feed
      </h1>
      <hr/>
      <%
      if(activities == null || activities.isEmpty()) {
      %>
        <p>No activities, start exploring!</p>      
      <%
      } else {
      %>
        <ul class="mdl-list">
        <%
        for(Activity activity : activities) {
          if(activity != null) {
          	if(activity.getType().equals("newUser")) {
          	%>
          		<li>New user has been created</li>
          	<%
          	}
            else if(activity.getType().equals("newConvo")) {
            %>
            	<li>New conversation has been created</li>
            <%
            }
            else if(activity.getType().equals("newMessage")) {
            %>
							<li>New message has been sent</li>            
            <%
            }
          }
        }
        %>
        </ul>
      <%
      }
      %>  

      <hr/>
    </div>
  </body>
</html>