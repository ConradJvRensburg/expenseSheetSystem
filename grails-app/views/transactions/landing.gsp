<%--
  Created by IntelliJ IDEA.
  User: Conrad
  Date: 12/16/2021
  Time: 11:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Landing Page</title>
</head>

<body>
    <h1>First time user?</h1>
    <g:link controller="landingPage" action="findUser">No</g:link>
    <g:link controller="users" action="create">Yes</g:link>
</body>
</html>