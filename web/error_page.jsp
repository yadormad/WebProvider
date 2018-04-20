<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" type="text/css" href="/WebProviderWeb/styles/mystyle1.css"/>
</head>
<body class = stpage>
<p class="error">An error occured</p>
<%exception.printStackTrace(System.out);%>
<p class="error"><%=exception.getMessage()%></p><br>
<a href="/WebProviderWeb/index.jsp" class="stpage">Back</a>
</body>
</html>
