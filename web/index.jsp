<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 20.03.2018
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error_page.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Service Provider</title>
  <link rel="stylesheet" type="text/css" href="styles/mystyle1.css"/>
</head>
<body class = stpage>
<h1 class = stpage>Service Provider</h1>
<c:if test="${param.login != null && param.pass != null}">
  <%@include file="auth.jsp"%>
</c:if>

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
  <c:choose>
    <c:when test="${sessionScope.get('authorized') != null && sessionScope.get('authorized').equals(true)}">
      <c:redirect url="menu.jsp"/>
    </c:when>
    <c:when test="${sessionScope.get('authorized') != null && !sessionScope.get('authorized').equals(true)}">
      <p class="error">Wrong login/password</p>
    </c:when>
  </c:choose>
</form>
</body>
</html>