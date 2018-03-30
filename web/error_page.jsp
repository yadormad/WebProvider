<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" type="text/css" href="styles/mystyle1.css"/>
</head>
<body class = stpage>
<p class="error">An error occured</p>
<%exception.printStackTrace(System.out);%>
<p class="error"><%=exception.getMessage()%></p>
<a class="stpage" href="index.jsp">Back</a>
</body>
</html>
