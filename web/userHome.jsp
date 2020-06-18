<%@ page import="model.Task" %>
<%@ page import="manager.TaskManager" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    List<Task> tasks = (List<Task>) request.getAttribute("allTasks");
    if (user != null) {
%>

Welcome <%=user.getName() + " " + user.getSurname()%> <br> <br>

<%}%>
<a href="/logout">Logout</a> <br>

My Tasks: <br>

<table border="1">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Desc</td>
        <td>Deadline</td>
        <td>Status</td>
        <td>UserId</td>
    </tr>

    <% for (Task task : tasks) { %>

    <tr>
        <td><%=task.getId()%>
        </td>
        <td><%=task.getName()%>
        </td>
        <td><%=task.getDesc()%>
        </td>
        <td><%=task.getDeadline()%>
        </td>
        <td><%=task.getStatus()%>
        </td>
        <td><%=task.getUser_id()%>
        </td>
    </tr>
    <% } %>

</table>
</body>
</html>
