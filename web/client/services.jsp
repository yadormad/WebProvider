<%@ page import="javax.ejb.RemoveException" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="../error_page.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<c:choose>
    <c:when test="${not empty param.getServices}">
        <c:set var="client" value="${userBean.getClient(param.getServices)}" scope="request"/>
    </c:when>
    <c:when test="${not empty param.deleteService}">
        <c:set var="client" value="${userBean.getClientOfService(param.deleteService)}" scope="page"/>
    </c:when>
</c:choose>

<c:if test="${empty client}">
    <c:redirect url="all.jsp?errorMessage=noclient"/>
</c:if>

<c:if test="${not empty param.deleteService}">
    <c:set var="deleteServiceMessage" value="${userBean.deleteService(param.deleteService)}" scope="request"/>
</c:if>
<c:set var="unusedTypes" value="${userBean.getUnusedServiceTypes(client.primaryKey)}" scope="request"/>

<html>
<head>
    <title><c:out value="${client.name}"/>'s services</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage"><c:out value="${client.name}"/>'s services</h1>
<p>Client info: <c:out value="${client.info}"/></p>
<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Provision date</th>
        <th>Disabling date</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${userBean.getServicesByClientId(client.primaryKey)}" var="service">
        <tr>
            <td><c:out value="${service.type}"/></td>
            <td><c:out value="${service.name}"/></td>
            <td><c:out value="${service.startDate}"/></td>
            <td><c:out value="${service.endDate}"/></td>
            <td>
                <form class="tableform" action="services.jsp" method="get">
                    <button class="inputform" name="deleteService" value="<c:out value="${service.primaryKey}"/>" type="submit">Delete</button>
                </form>
                <form class="tableform" action="../service/update.jsp" method="get">
                    <button class="inputform" name="updateService" value="<c:out value="${service.primaryKey}"/>" type="submit">Update</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:forEach items="${unusedTypes}" var="type">
        <tr>
            <td><c:out value="${type.name()}"/></td>
        </tr>
    </c:forEach>
</table>

<p><c:out value="${deleteServiceMessage}"/></p>

<c:if test="${not empty unusedTypes}">
    <form class=tableform action=../service/new.jsp method=post>
        <button class=inputform type=submit name=addService value="<c:out value="${client.primaryKey}"/>">Add service</button>
    </form>
</c:if>

<a href="all.jsp" class="stpage">Back</a>

</body>
</html>
