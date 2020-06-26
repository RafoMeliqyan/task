<%@ page import="model.User" %>
<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Tasks</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    List<Comment> comments = (List<Comment>) request.getAttribute("allComments");
%>

<a style="text-decoration: none" href="/userHome">User Home</a> <br>

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
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname()%>
            </td>
            <td><% if (task.getPictureUrl() != null) { %>
                <img src="/image?path=<%=task.getPictureUrl()%>" width="70"/> <% } %>
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

    Add comment: <br>
    <form action="/addCommentUser" method="post">
        <select name="taskId">
        <% for (Task task : tasks) { %>
            <option value="<%=task.getId()%>"><%=task.getName()%>
            </option>
        <% } %>
        </select> <br>
        <% for (Task task : tasks) { %>
        <input type="hidden" name="userId" value="<%=task.getUser_id()%>">
        <% } %>
        <input type="text" name="comment" placeholder="Comment"> <br>
        <input type="submit" value="Add comment">
    </form>

    All comments: <br>
    <% for (Comment comment : comments) { %>

    <%=comment.getUser().getName() + " " + comment.getUser().getSurname()%> <br>
    Avatar: <br> <% if (comment.getUser().getPictureUrl() != null) { %>
    <img src="/image?path=<%=comment.getUser().getPictureUrl()%>" width="70"/> <% } %> <br>
    Comment - <%=comment.getComment()%> <br>
    Task: <br>
    Name - <%=comment.getTask().getName()%> <br>
    Description - <%=comment.getTask().getDescription()%> <br>
    Deadline - <%=comment.getTask().getDeadline()%> <br>
    Status - <%=comment.getTask().getTaskStatus().name()%> <br>
    Image - <% if (comment.getTask().getPictureUrl() != null) { %>
    <img src="/image?path=<%=comment.getTask().getPictureUrl()%>" width="40"/> <% } %> <br>
    Date - <%=comment.getDate()%>
    <% if (comment.getUserId() == user.getId()) { %>
    <a href="/removeCommentUser?id=<%=comment.getId()%>">
        <p style="display: inline-block; color: red">X</p></a> <br>
    <% } else { %>
        <br> <br>
    <% } %>

    <% } %>
</div>
</body>
</html>
