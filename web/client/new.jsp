<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="controller/submitnew.jsp"%>
<c:if test="${requestScope.get('responseEntity') != null}">
    <c:redirect url="all.jsp"/>
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
        <label for=info class = inputform>Client infp</label>
        <input type=text id=info name=info required=required class = inputform value="">
    </div>
    <button name=addButton type=submit class=inputform value="submitted"> Add</button>
</form>
<button class=inputform onclick='history.back()'>Back</button>
</body>
</html>
