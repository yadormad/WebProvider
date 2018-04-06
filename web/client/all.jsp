<%@ page errorPage="../error_page.jsp" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 23.03.2018
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@include file="controller/viewall.jsp"%>
<html>
<head>
    <title>Clients table</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Clients table</h1>
<table>
    <tr>
        <th>Name</th>
        <th>Action</th>
    </tr>
    <%
        out.print(viewAsRows());
    %>
</table>
<form class="inputform" action="../client/new.jsp" method=post>
    <button name="addButton" type="submit" class=inputform>Add client</button>
</form>

<form class="inputform" action="all.jsp" method=post>
    <jsp:include page="../back/backbutton.jsp"/>
</form>

<c:if test="${requestScope.get('error') !=null}">
    <p class=error>
        <c:out value="${requestScope.get('error')}"/>
    </p>
</c:if>
</body>
</html>
