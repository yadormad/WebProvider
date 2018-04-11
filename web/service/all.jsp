<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 02.04.2018
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<c:if test="${empty param.serviceType}">
    <c:redirect url="menu.jsp?errorMessage=notype"/>
</c:if>


<c:set var="serviceClientMap" value="${userBean.getServiceClientMap(param.serviceType)}" scope="request"/>

<html>
<head>
    <title><c:out value="${param.serviceType}"/> services</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage"><c:out value="${param.serviceType}"/> services</h1>
<table>
    <tr>
        <th>Client</th>
        <th>Service name</th>
        <th>Provision date</th>
        <th>Disabling date</th>
    </tr>
    <c:forEach var="keyService" items="${userBean.getServiceClientMap(param.serviceType).keySet()}">
        <tr>
            <td>
                <a href="../client/services.jsp?getServices=${serviceClientMap.get(keyService).primaryKey}" class="tableform">
                    ${serviceClientMap.get(keyService).name}
                </a>
            </td>
            <td><c:out value="${keyService.name}"/></td>
            <td><c:out value="${keyService.startDate}"/></td>
            <td><c:out value="${keyService.endDate}"/></td>
        </tr>
    </c:forEach>
</table>
<a href="menu.jsp" class="stpage">Back</a>
</body>
</html>
