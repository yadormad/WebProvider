<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="../error_page.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<c:if test="${param.addButton.equals('submitted')}">
    <c:set var="addMessage" value="${userBean.addClient(param.name, param.info)}" scope="request"/>
</c:if>
<html>
<head>
    <title>Add client</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Add client</h1>
<form class="inputform" action="new.jsp" method=post>
    <div style="display: inline-block">
        <label for=name class = inputform>Client name</label>
        <input type=text id=name name=name required=required class = inputform value="">
    </div>
    <div style="display: inline-block">
        <label for=info class = inputform>Client info</label>
        <input type=text id=info name=info required=required class = inputform value="">
    </div>
    <button name=addButton type=submit class=inputform value="submitted">Add</button>
</form>

<p><c:out value="${addMessage}"/></p>

<a href="all.jsp" class="stpage">Back</a>
</body>
</html>
