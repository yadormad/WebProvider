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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<c:choose>
    <c:when test="${not empty param.addService}">
        <c:set var="client" value="${userBean.getClient(param.addService)}" scope="request"/>
    </c:when>
    <c:when test="${not empty param.addButton}">
        <c:set var="client" value="${userBean.getClient(param.addButton)}" scope="request"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="../client/all.jsp?errorMessage=noclient"/>
    </c:otherwise>
</c:choose>

<c:if test="${not empty param.addButton}">
    <fmt:parseDate pattern="yyyy-MM-dd" var="parsedProvisionDate" value="${param.provisionDate}" scope="request"/>
    <fmt:parseDate pattern="yyyy-MM-dd" var="parsedDisablingDate" value="${param.disablingDate}" scope="request"/>
    <c:set var="addSericeMessage" value="${userBean.addService(client.primaryKey, param.name, param.type, parsedProvisionDate, parsedDisablingDate)}"/>
</c:if>

<c:set var="unusedTypes" value="${userBean.getUnusedServiceTypes(client.primaryKey)}" scope="request"/>

<c:if test="${empty unusedTypes}">
    <c:redirect url="../client/services.jsp?getServices=${client.primaryKey}"/>
</c:if>

<html>
<head>
    <title>Add service to <c:out value="${client.name}"/></title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Add service to <c:out value="${client.name}"/></h1>
<p>Client info: <c:out value="${client.info}"/></p>
<form class="inputform" action="new.jsp" method=get>
    <div>
        <label for=type class = inputform>Service type</label>
        <c:forEach var="type" items="${unusedTypes}">
            <input type="radio" id="type" name="type" value="<c:out value="${type}"/>" checked> <c:out value="${type}"/> <br>
        </c:forEach>
    </div>
    <div>
        <label for=name class = inputform>Service name</label>
        <input type=text id=name name=name required=required class = inputform value="">
    </div>
    <div>
        <label for=provisionDate class = inputform>Provision date</label>
        <input type=date id=provisionDate name=provisionDate required=required class = inputform value="">
    </div>
    <div>
        <label for=disablingDate class = inputform>Disabling date</label>
        <input type=date id=disablingDate name=disablingDate required=required class = inputform value="">
    </div>
    <button name=addButton type=submit class=inputform value="<c:out value="${client.primaryKey}"/>">Add service</button>
</form>
<c:if test="${not empty addSericeMessage}">
    <p><c:out value="${addSericeMessage}"/></p>
</c:if>

<a href="../client/services.jsp?getServices=${client.primaryKey}" class="stpage">Back</a>

</body>
</html>
