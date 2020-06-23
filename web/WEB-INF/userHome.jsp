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
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    if (user != null) {
%>

Welcome <%=user.getName()%> <br> <% if (user.getPictureUrl() != null) { %>
<img src="/image?path=<%=user.getPictureUrl()%>" width="70" /> <% } %> <br>


<% } %>
<a href="/logout">Logout</a> <br>

My Tasks: <br>

<div>
    <table border="1">
        <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Description</td>
            <td>Deadline</td>
            <td>Status</td>
            <td>User</td>
            <td>Image</td>
            <td>Update Status</td>
        </tr>

        <% for (Task task : tasks) { %>

        <tr>
            <td><%=task.getId()%>
            </td>
            <td><%=task.getName()%>
            </td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getTaskStatus().name()%>
            </td>
            <td><%=task.getUser().getName()%>
            </td>
            <td><% if (task.getPictureUrl() != null) { %>
                <img src="/image?path=<%=task.getPictureUrl()%>" width="70" /> <% } %>
            </td>
            <td>
                <form action="/changeTaskStatus" method="post">
                    <input type="hidden" name="taskId" value="<%=task.getId()%>">
                    <select name="status">
                        <option value="NEW">NEW</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                        <option value="FINISHED">FINISHED</option>
                    </select> <input type="submit" value="OK"> <br>
                </form>
            </td>
        </tr>

        <% } %>

    </table>
</div>
</body>
</html>
