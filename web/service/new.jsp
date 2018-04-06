<%@ page import="entity.impl.Client" %>
<%@ page errorPage="../error_page.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 2:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%!
    private Client client;
%>
<%@include file="controller/viewnew.jsp"%>
<%@include file="controller/submitnew.jsp"%>
<%
    Integer clientId ;
    if(request.getParameter("addServiceButton") != null) {
        clientId = Integer.parseInt(request.getParameter("addServiceButton"));
        session.setAttribute("clientId", clientId);
    }
    checkId(request, response);
    if(request.getParameter("addButton") != null) {
        addService(request, response);
    }
%>



<html>
<head>
    <title>Add service to <%=client.getName()%></title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Add service to <%=client.getName()%></h1>
<p>Client info: <%=client.getInfo()%></p>
<form class="inputform" action="new.jsp" method=post>
    <div style="display: inline-block">
        <%=typesRadio(request, response)%>
    </div>
    <div style="display: inline-block">
        <label for=name class = inputform>Service name</label>
        <input type=text id=name name=name required=required class = inputform value="">
    </div>
    <div style="display: inline-block">
        <label for=provisionDate class = inputform>Provision date</label>
        <input type=date id=provisionDate name=provisionDate required=required class = inputform value="">
    </div>
    <div style="display: inline-block">
        <label for=disablingDate class = inputform>Disabling date</label>
        <input type=date id=disablingDate name=disablingDate required=required class = inputform value="">
    </div>
    <c:choose>
        <c:when test="${requestScope.get('blocked') != null}">
            <p class=error><%=request.getAttribute("blocked")%></p>
        </c:when>
        <c:otherwise>
            <button name=addButton type=submit class=inputform value="submitted">Add</button>
        </c:otherwise>
    </c:choose>
</form>
<c:choose>
    <c:when test="${requestScope.get('error') != null}">
        <p class=error><%=request.getAttribute("error")%></p>
    </c:when>
</c:choose>


<form class="inputform" action="new.jsp" method=post>
    <jsp:include page="../back/backbutton.jsp"/>
</form>

</body>
</html>
