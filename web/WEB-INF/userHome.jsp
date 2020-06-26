<%@ page import="model.Task" %>
<%@ page import="manager.TaskManager" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    List<Comment> comments = (List<Comment>) request.getAttribute("allComments");
    if (user != null) {
%>

Welcome <%=user.getName()%> <br> Avatar: <br> <% if (user.getPictureUrl() != null) { %>
<img src="/image?path=<%=user.getPictureUrl()%>" width="70"/> <% } %> <br>


<% } %>
<a href="/logout" style="text-decoration: none">Logout</a> <br> <br>

My Tasks: <br>

<div>

    <table border="1">
        <tr>
            <td>Name</td>
            <td>Description</td>
            <td>Status</td>
            <td>User</td>
            <td>Image</td>
        </tr>

        <% for (Task task : tasks) { %>
        <tr>
            <td><a style="text-decoration: none" href="/userTasks"><%=task.getName()%>
            </a></td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getTaskStatus().name()%>
            </td>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname()%>
            </td>
            <td><% if (task.getPictureUrl() != null) { %>
                <img src="/image?path=<%=task.getPictureUrl()%>" width="70"/> <% } %>
            </td>
        </tr>
        <% } %>
    </table>
    <br>

</div>
</body>
</html>
