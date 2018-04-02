<%@ page import="java.util.LinkedList" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 02.04.2018
  Time: 6:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<body>
<c:choose>
    <c:when test="${sessionScope.get('historySize') != null && sessionScope.get('historySize') > 1}">
        <button class=inputform name=backButton value=pressed>Back</button>
    </c:when>
</c:choose>
</body>
