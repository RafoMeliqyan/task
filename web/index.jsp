<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Welcome</title>
  </head>
  <body>
  <link rel="stylesheet" href="css/style.css">

  <%
    String msg = "";
    if (session.getAttribute("msg") != null) {
    msg = (String) session.getAttribute("msg");
    session.removeAttribute("msg");
  }
  %>
<div class="div1">
  <p style="color: red">
    <%=msg%>
  </p>
  Login: <br>
  <form action="/login" method="post">
    <input type="text" name="email" placeholder="Input email" required> <br>
    <input type="password" name="password" placeholder="Input password" required> <br>
    <input type="submit" value="login">
  </form> <br>
</div>
  </body>
</html>
