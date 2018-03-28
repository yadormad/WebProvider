<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 20.03.2018
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Service Provider</title>
  <link rel="stylesheet" type="text/css" href="styles/mystyle1.css"/>
</head>
<body class = stpage>
<h1 class = stpage>Service Provider</h1>

<%
  if(request.getParameter("login") != null && request.getParameter("pass") != null)
      request.getRequestDispatcher("/auth.jsp").include(request, response);
%>

<form class="inputform" action="index.jsp" method=post>
  <div style="display: inline-block">
    <label for="login" class = inputform>Login</label>
    <input type="text", id="login", name="login", required="required", class = inputform value=""> <!--добавить валуе-->
  </div>
  <div style="display: inline-block">
    <label for="pass" class = inputform>Password</label>
    <input type="password", id="pass", name="pass", required="required", class = inputform value="">
  </div>
  <button name="performButton", type="submit", class=inputform>LogIn</button>
  <p class=error><%
  Boolean isAuthorized = (Boolean) request.getSession().getAttribute("authorized");
  if(isAuthorized != null && !isAuthorized) {
      out.print("Wrong login/pass");
  }%></p>
</form>
</body>
</html>