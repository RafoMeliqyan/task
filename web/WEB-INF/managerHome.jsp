<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page import="model.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager Page</title>
</head>
<body>

<%
    String msg = "";
    if (session.getAttribute("msg") != null) {
        msg = (String) session.getAttribute("msg");
        session.removeAttribute("msg");
    }
%>

<p style="color: red">
    <%=msg%>
</p>

<%
    User user = (User) session.getAttribute("user");
    List<User> users = (List<User>) request.getAttribute("allUsers");
    List<Task> tasks = (List<Task>) request.getAttribute("allTasks");
    List<Comment> comments = (List<Comment>) request.getAttribute("allComments");
    if (user != null) {
%>

Welcome <%=user.getName()%> <br> <% if (user.getPictureUrl() != null) { %>
<img src="/image?path=<%=user.getPictureUrl()%>" width="70"/> <% } %> <br>

<% } %>

<br>

<a href="/logout" style="text-decoration: none">Logout</a> <br>

<div style="width: 800px">

    <div style="width: 50%; float: right; margin-right: 100px">
        Add User: <br>

        <form action="/addUser" method="post" enctype="multipart/form-data">

            <input type="text" name="name" placeholder="name..."> <br>
            <input type="text" name="surname" placeholder="surname..."> <br>
            <input type="text" name="email" placeholder="email..."> <br>
            <input type="password" name="password" placeholder="password..."> <br>
            <input type="file" name="image"> <br>
            <select name="type">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>
            </select> <br>
            <input type="submit" value="add User"> <br>
        </form>
    </div>

    <div style="width: 50%">
        Add Task: <br>

        <form action="/addTask" method="post" enctype="multipart/form-data">

            <input type="text" name="name" placeholder="name..."> <br>
            <textarea name="description" placeholder="description..."></textarea> <br>
            <input type="date" name="deadline"> <br>
            <select name="status">
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>
            </select> <br>

            <select name="user_id">
                <% for (User user1 : users) { %>
                <option value="<%=user1.getId()%>"><%=user1.getName()%> <%=user1.getSurname()%>
                </option>
                <% } %>
            </select> <br>
            <input type="file" name="image"> <br>
            <input type="submit" value="add Task"> <br>
        </form>
    </div>

</div>

<div>
    All Users: <br>
    <table border="1">
        <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Surname</td>
            <td>Email</td>
            <td>UserType</td>
            <td>Image</td>
            <td>Delete</td>
        </tr>

        <% for (User user1 : users) { %>

        <tr>
            <td><%=user1.getId()%>
            </td>
            <td><%=user1.getName()%>
            </td>
            <td><%=user1.getSurname()%>
            </td>
            <td><%=user1.getEmail()%>
            </td>
            <td><%=user1.getUserType().name()%>
            <td><% if (user1.getPictureUrl() != null) { %>
                <img src="/image?path=<%=user1.getPictureUrl()%>" width="70"/> <% } %>
            </td>
            <td><a href="/removeUser?id=<%=user1.getId()%>" style="text-decoration: none">Delete</a></td>
        </tr>

        <% } %>

    </table>
</div>

<br>

<div>
    All Tasks: <br>
    <table border="1">
        <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Description</td>
            <td>Deadline</td>
            <td>Status</td>
            <td>User</td>
            <td>Image</td>
            <td>Delete</td>
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
            <td><a href="/removeTask?id=<%=task.getId()%>" style="text-decoration: none">Delete</a></td>
        </tr>

        <% } %>

    </table>
</div>

Add comment: <br>
<form action="/addCommentManager" method="post">
    <select name="taskId">
    <% for (Task task : tasks) { %>
        <option value="<%=task.getId()%>"><%=task.getName()%>
        </option>
    <% } %>
    </select> <br>
    <input type="hidden" name="userId" value="<%=user.getId()%>">
    <input type="text" name="comment" placeholder="Comment"> <br>
    <input type="submit" value="Add comment">
</form>

All Comments: <br>
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
Date - <%=comment.getDate()%> <a href="/removeCommentManager?id=<%=comment.getId()%>"><p
        style="display: inline-block; color: red">X</p></a> <br>
<% } %>

</body>
</html>
