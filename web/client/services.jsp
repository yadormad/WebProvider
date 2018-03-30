<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@include file="getservices.jsp"%>

<html>
<head>
    <title><%=client.getName()%>'s services</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage"><%=client.getName()%>'s services</h1>
<p>Client info: <%=client.getInfo()%></p>
<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Provision date</th>
        <th>Disabling date</th>
        <th>Action</th>
    </tr>
    <%
        out.print(viewAsRows(request));
    %>
</table>
<c:if test="${!requestScope.get('unusedTypes').isEmpty()}">
    <form class=tableform action=../service/new.jsp method=post>
        <button class=inputform type=submit name=addServiceButton value=<%=clientId%>>Add service</button>
    </form>
</c:if>
<button class=inputform onclick='history.back()'>Back</button>
</body>
</html>
