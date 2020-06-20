<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
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
    if (user != null) {
%>

Welcome <%= user.getName() + " " + user.getSurname() %>

<% } %>

<br>

<a href="/logout">Logout</a> <br>

<div style="width: 800px">

    <div style="width: 50%; float: right; margin-right: 200px">
        Add User: <br>

        <form action="/addUser" method="post">

            <input type="text" name="name" placeholder="name..."> <br>
            <input type="text" name="surname" placeholder="surname..."> <br>
            <input type="text" name="email" placeholder="email..."> <br>
            <input type="password" name="password" placeholder="password..."> <br>
            <select name="type">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>
            </select> <br>
            <input type="submit" value="add User"> <br>
        </form>
    </div>

    <div style="width: 50%">
        Add Task: <br>

        <form action="/addTask" method="post">

            <input type="text" name="name" placeholder="name..."> <br>
            <textarea name="description" placeholder="description..."></textarea> <br>
            <input type="date" name="deadline"> <br>
            <select name="status">
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>
            </select> <br>

            <select name="user_id">
                <%
                    for (User user1 : users) { %>
                <option value="<%=user1.getId()%>"><%=user1.getName()%> <%=user1.getSurname()%>
                </option>
                <%
                    }
                %>
            </select> <br>
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
            </td>
            <td><a href="/removeUser?id=<%=user1.getId()%>">delete</a></td>
        </tr>

        <% } %>

    </table>
</div>

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
            <td><a href="/removeTask?id=<%=task.getId()%>">delete</a></td>
        </tr>

        <% } %>

    </table>
</div>

</body>
</html>