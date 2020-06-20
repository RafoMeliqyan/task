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
    <input type="text" name="email" placeholder="Input email"> <br>
    <input type="password" name="password" placeholder="Input password"> <br>
    <input type="submit" value="login">
  </form> <br>

  </body>
</html>
