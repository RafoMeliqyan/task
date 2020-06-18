<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ManagerModel" %>
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
    ManagerModel manager = (ManagerModel) session.getAttribute("manager");
    List<User> users = (List<User>) request.getAttribute("allUsers");
    List<Task> tasks = (List<Task>) request.getAttribute("allTasks");
    if (manager != null) {
%>

Welcome <%= manager.getName() + " " + manager.getSurname() %>

<% } %>

<br>

<a href="/logout">Logout</a> <br>

<table border="1" style="display: inline-block">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Surname</td>
        <td>Email</td>
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
        <td><a href="/removeUser?id=<%=user1.getId()%>">delete</a></td>
    </tr>

    <% } %>

</table>

<table border="1" style="display: inline-block; margin-left: 30px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Desc</td>
        <td>Deadline</td>
        <td>Status</td>
        <td>UserId</td>
        <td>Delete</td>
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
        <td><a href="/removeTask?id=<%=task.getId()%>">delete</a></td>
    </tr>

    <% } %>

</table> <br>

Add User: <br>

<form action="/addUser" method="post">

    <input type="text" name="name" placeholder="name..."> <br>
    <input type="text" name="surname" placeholder="surname..."> <br>
    <input type="text" name="email" placeholder="email..."> <br>
    <input type="password" name="password" placeholder="password..."> <br>
    <input type="submit" value="add User"> <br>

</form>

Add Task: <br>

<form action="/addTask" method="post">

    <input type="text" name="name" placeholder="name..."> <br>
    <input type="text" name="desc" placeholder="description..."> <br>
    <input type="date" name="deadline" placeholder="deadline..."> <br>
<%--    <select name="User Id">--%>
<%--        <option>--%>
<%--            <% for (User user1 : users) { %>--%>
<%--            <tr>--%>
<%--                <td><%=user1.getId()%>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <% } %>--%>
<%--        </option>--%>
<%--        <option></option>--%>
<%--    </select> <br>--%>
    <input type="submit" value="add Task"> <br>

</form>

</body>
</html>
