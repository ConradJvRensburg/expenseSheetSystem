<%--
  Created by IntelliJ IDEA.
  User: Conrad
  Date: 12/20/2021
  Time: 2:40 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
    <div style="background-color: red;height: fit-content;width: auto; border: 2px solid #721c24; border-radius: 6px">
        <h3 style="color: white">Invalid username</h3>
    </div>
    <div>
        <h1>Login</h1>
        <g:form name="testUsername" controller="users" action="loadUser">
            <label>Username</label><br>
            <input type="text" id="usernameInput" name="usernameInput" value="${usernameInput}"><br>
            <g:submitButton name="Submit" value="Submit"/>
        </g:form>
    </div>

</body>
</html>