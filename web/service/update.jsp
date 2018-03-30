
<%@ page errorPage="../error_page.jsp" %> <%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%! private int serviceId; %>

<%@include file="controller/submitupdate.jsp"%>
<%@include file="controller/viewupdate.jsp"%>

<html>
<head>
    <title>Update <%=client.getName()%>'s service</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Update <%=client.getName()%>'s service</h1>
<form class="inputform" action="update.jsp" method=post>
    <div style="display: inline-block">
        <label for=name class = inputform>Service name</label>
        <input type=text id=name name=name required=required class = inputform value=<%=service.getName()%>>
    </div>
    <div style="display: inline-block">
        <label for=provisionDate class = inputform>Provision date</label>
        <input type=date id=provisionDate name=provisionDate required=required class = inputform value=<%=dateFormat.format(service.getServiceProvisionDate())%>>
    </div>
    <div style="display: inline-block">
        <label for=disablingDate class = inputform>Disabling date</label>
        <input type=date id=disablingDate name=disablingDate required=required class = inputform value=<%=dateFormat.format(service.getServiceDisablingDate())%>>
    </div>
    <c:choose>
        <c:when test="${requestScope.get('blocked') != null}">
            <p class=error><%=request.getAttribute("blocked")%></p>
        </c:when>
        <c:otherwise>
            <button name=updButton type=submit class=inputform value=<%=serviceId%>> Update</button>
        </c:otherwise>
    </c:choose>
</form>
<c:choose>
    <c:when test="${requestScope.get('error') != null}">
        <p class=error><%=request.getAttribute("error")%></p>
    </c:when>
</c:choose>
<button class=inputform onclick='history.back()'>Back</button>
</body>
</html>
