<%@ page import="entity.impl.Client" %>
<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="entity.impl.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.io.IOException" %>
<%@ page errorPage="../error_page.jsp" %> <%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private int serviceId;

    private void updateService(HttpServletRequest request, HttpServletResponse response) throws ServletException, ParseException, IOException {
        Service service = new Service();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        service.setId(serviceId);
        service.setName(request.getParameter("name"));
        service.setServiceProvisionDate(dateFormat.parse(request.getParameter("provisionDate")));
        service.setServiceDisablingDate(dateFormat.parse(request.getParameter("disablingDate")));
        request.setAttribute("command", "updservice");
        request.setAttribute("requestObject", service);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String message = ((TransportEntity) request.getSession().getAttribute("responseEntity")).getMessage();
        if(message.startsWith("Error")) {
            request.setAttribute("error", message);
            request.getRequestDispatcher("/client/all.jsp").forward(request, response);
        }
    }
%>
<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if(request.getParameter("updateServiceButton") != null) {
        serviceId = Integer.parseInt(request.getParameter("updateServiceButton"));
    }
    if(request.getParameter("updButton") != null) {
        updateService(request, response);
    }
    request.setAttribute("command", "getservice");
    request.setAttribute("requestObject", serviceId);
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    Service service = (Service) ((TransportEntity) request.getSession().getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
    int clientId = service.getClientId();
    request.setAttribute("command", "getclient");
    request.setAttribute("requestObject", clientId);
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    Client client = (Client) ((TransportEntity) request.getSession().getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
%>
<html>
<head>
    <title>Update <%=client.getName()%>'s service</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Update <%=client.getName()%>'s service</h1>
<form class="inputform" action="update.jsp" method=post>
    <div style="display: inline-block">
        <label for=name class = inputform>Service name</label>
        <input type=text id=name name=name required=required class = inputform value=<%=service.getName()%>>
    </div>
    <div style="display: inline-block">
        <label for=provisionDate class = inputform>Provision date</label>
        <input type=date id=provisionDate name=provisionDate required=required class = inputform value=<%=dateFormat.format(service.getServiceProvisionDate())%>>
    </div>
    <div style="display: inline-block">
        <label for=disablingDate class = inputform>Disabling date</label>
        <input type=date id=disablingDate name=disablingDate required=required class = inputform value=<%=dateFormat.format(service.getServiceDisablingDate())%>>
    </div>
    <button name=updButton type=submit class=inputform value="submitted"> Update</button>
</form>
<button class=inputform onclick='history.back()'>Back</button>
</body>
</html>
