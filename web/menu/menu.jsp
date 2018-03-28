<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 23.03.2018
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" type="text/css" href="styles/mystyle1.css"/>
</head>
<h1 class = stpage>Activity menu</h1>
<body class = stpage>
<form class="inputform" action="client/all.jsp" method=post>
    <button name="performButton", type="submit", class=inputform>View all clients</button>
</form>
<form class="inputform" action="../services.jsp" method=post>
    <button name="performButton" type="submit" class=inputform>View all services</button>
</form>
<button class=inputform onclick='history.back()'>Back</button>
</body>
</html>
