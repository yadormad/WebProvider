<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 20.04.2018
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="error_page.jsp" %>
<html>
<head>
    <title>Import xml</title>
    <link rel="stylesheet" type="text/css" href="styles/mystyle1.css"/>
</head>
<body class = stpage>
<h1 class = stpage>Import xml</h1>
<form class="inputform" action="import.xml" method=post enctype="multipart/form-data">
    <input type="file" required="required" class="inputform" name="xmlfile">
    <button type="submit" class="inputform">Submit</button>
</form>
<p><c:out value="${param.ImportMessage}"/></p>
<p class="error"><c:out value="${param.ErrorMessage}"/></p>
<a href="index.jsp" class="stpage">Back</a>
</body>
</html>
