<%@ page errorPage="../error_page.jsp" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 23.03.2018
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<html>
<head>
    <title>Clients table</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Clients table</h1>
<c:if test="${not empty param.deleteClient }">
    <c:set var="deleteClientMessage" value="${userBean.deleteClient(param.deleteClient)}" scope="request"/>
</c:if>
<table>
    <tr>
        <th>Name</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${userBean.allClients}" var="client">
        <tr>
            <td><c:out value="${client.name}"/></td>
            <td>
                <form class="tableform" action="services.jsp" method="get">
                    <button class="inputform" name="getServices" value="<c:out value="${client.primaryKey}"/>" type="submit">Services</button>
                </form>
                <form class="tableform" action="all.jsp" method="get">
                    <button class="inputform" name="deleteClient" value="<c:out value="${client.primaryKey}"/>" type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form class="inputform" action="../client/new.jsp" method=get>
    <button name="addButton" type="submit" class=inputform>Add client</button>
</form>
<p><c:out value="${deleteClientMessage}"/></p>
<c:if test="${not empty param.errorMessage && param.errorMessage.equals('noclient')}">
    <p class="error">No client was chosen</p>
</c:if>
<a href="../index.jsp" class="stpage">Back</a>
</body>
</html>
