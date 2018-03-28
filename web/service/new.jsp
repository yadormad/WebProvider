<%@ page import="entity.impl.Client" %>
<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="java.util.Collection" %>
<%@ page import="entity.impl.ServiceType" %>
<%@ page import="java.io.IOException" %>
<%@ page import="entity.impl.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page errorPage="../error_page.jsp" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 2:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private Client client;

    private String typesRadio(HttpServletRequest request) {
        Collection<ServiceType>unusedServiceTypes = (Collection<ServiceType>) request.getSession().getAttribute("unusedTypes");
        StringBuilder typesRadioString = new StringBuilder();
        typesRadioString.append("        <label for=serviceType class = inputform>Service type:</label>");
        for (ServiceType serviceType:unusedServiceTypes) {
            typesRadioString.append("        <input type=radio id=serviceType name=serviceType value=").append(serviceType.toString()).append(">").append(serviceType.toString()).append("</br>");
        }
        typesRadioString.insert(typesRadioString.indexOf(">")-1, "checked");
        return String.valueOf(typesRadioString);
    }

    private String checkId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("addServiceButton"));
        request.setAttribute("command", "getclient");
        request.setAttribute("requestObject", clientId);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        client = (Client) ((TransportEntity) request.getSession().getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
        request.setAttribute("command", "checkId");
        request.setAttribute("message", "addservice");
        request.setAttribute("requestObject", clientId);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String message = ((TransportEntity) request.getSession().getAttribute("responseEntity")).getMessage();
        String errorMessage = "";
        if (!message.equalsIgnoreCase("true")) {
            errorMessage = message;
        }
        return errorMessage;
    }

    private String addService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        Service service = new Service();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        service.setType(ServiceType.valueOf(request.getParameter("serviceType").toUpperCase()));
        service.setName(request.getParameter("name"));
        service.setServiceProvisionDate(dateFormat.parse(request.getParameter("provisionDate")));
        service.setServiceDisablingDate(dateFormat.parse(request.getParameter("disablingDate")));
        service.setClientId(client.getId());
        request.setAttribute("command", "addservice");
        request.setAttribute("requestObject", service);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String message = ((TransportEntity) request.getSession().getAttribute("responseEntity")).getMessage();
        if(!message.startsWith("Error")) {
            request.getRequestDispatcher("/client/all.jsp").forward(request, response);
        }
        return message;
    }
%>
<%
    if(request.getParameter("addButton") != null) {
        addService(request, response);
    } else {
        checkId(request, response);
    }
%>



<html>
<head>
    <title>Add service to <%=client.getName()%></title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Add service to <%=client.getName()%></h1>
<p>Client info: <%=client.getInfo()%></p>
<form class="inputform" action="new.jsp" method=post>
    <div style="display: inline-block">
        <%=typesRadio(request)%>
    </div>
    <div style="display: inline-block">
        <label for=name class = inputform>Service name</label>
        <input type=text id=name name=name required=required class = inputform value="">
    </div>
    <div style="display: inline-block">
        <label for=provisionDate class = inputform>Provision date</label>
        <input type=date id=provisionDate name=provisionDate required=required class = inputform value="">
    </div>
    <div style="display: inline-block">
        <label for=disablingDate class = inputform>Disabling date</label>
        <input type=date id=disablingDate name=disablingDate required=required class = inputform value="">
    </div>
    <button name=addButton type=submit class=inputform value="submitted"> Add</button>

</form>
<button class=inputform onclick='history.back()'>Back</button>
</body>
</html>
