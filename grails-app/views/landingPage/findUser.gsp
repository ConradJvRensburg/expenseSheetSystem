<%--
  Created by IntelliJ IDEA.
  User: Conrad
  Date: 12/19/2021
  Time: 4:17 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<div>
    <h1>Login</h1>
    <g:form name="testUsername" controller="users" action="loadUser">
        <label>Username</label><br>
        <input type="text" id="usernameInput" name="usernameInput" value="${usernameInput}"><br>
        <g:submitButton name="Submit" value="Submit"/>
    </g:form>
    <a href="${createLink(uri: '/')}"><h3>Return</h3></a><br>
</div>

</body>
</html>