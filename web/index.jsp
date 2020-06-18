<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Welcome</title>
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

  Login: <br>
  <form action="/login" method="post">
    email: <input type="text" name="email"> <br>
    password: <input type="password" name="password"> <br>
    <input type="submit" value="login">
  </form> <br>

  </body>
</html>
