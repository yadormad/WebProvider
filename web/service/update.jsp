 <%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="../error_page.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<c:choose>
    <c:when test="${not empty param.updateService}">
        <c:set var="service" value="${userBean.getService(param.updateService)}" scope="request"/>
    </c:when>
    <c:when test="${not empty param.updButton}">
        <c:set var="service" value="${userBean.getService(param.updButton)}" scope="request"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="../client/all.jsp?errorMessage=noclient"/>
    </c:otherwise>
</c:choose>

<c:set var="clientName" value="${userBean.getClientOfService(service.primaryKey).name}" scope="request"/>

<fmt:formatDate pattern="yyyy-MM-dd" var="formattedProvisionDate" value="${service.startDate}" scope="request"/>
<fmt:formatDate pattern="yyyy-MM-dd" var="formattedDisablingDate" value="${service.endDate}" scope="request"/>

<c:if test="${not empty param.updButton}">
    <fmt:parseDate pattern="yyyy-MM-dd" var="parsedProvisionDate" value="${param.provisionDate}" scope="request"/>
    <fmt:parseDate pattern="yyyy-MM-dd" var="parsedDisablingDate" value="${param.disablingDate}" scope="request"/>
    <c:set var="updateServiceMessage" value="${userBean.updateService(param.updButton, param.name, parsedProvisionDate, parsedDisablingDate)}"/>
</c:if>
<html>
<head>
    <title>Update <c:out value="${clientName}"/>'s service</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Update <c:out value="${clientName}"/>'s service</h1>
<form class="inputform" action="update.jsp" method=get>
    <div style="display: inline-block">
        <label for=name class = inputform>Service name</label>
        <input type=text id=name name=name required=required class = inputform value="<c:out value="${service.name}"/>">
    </div>
    <div style="display: inline-block">
        <label for=provisionDate class = inputform>Provision date</label>
        <input type=date id=provisionDate name=provisionDate required=required class = inputform value="<c:out value="${formattedProvisionDate}"/>">
    </div>
    <div style="display: inline-block">
        <label for=disablingDate class = inputform>Disabling date</label>
        <input type=date id=disablingDate name=disablingDate required=required class = inputform value="<c:out value="${formattedDisablingDate}"/>">
    </div>
    <button name=updButton type=submit class=inputform value="<c:out value="${service.primaryKey}"/>">Update</button>
</form>
<c:if test="${not empty updateServiceMessage}">
    <p><c:out value="${updateServiceMessage}"/></p>
</c:if>
<a href="../client/services.jsp?getServices=${client.primaryKey}" class="stpage">Back</a>

</body>
</html>
