<%--
  Created by IntelliJ IDEA.
  User: Conrad
  Date: 12/19/2021
  Time: 2:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Transactions</title>
</head>

<body>
    <div>
       <h2>New Tranasaction</h2>
    </div>
    <div>
        <g:form name="createTransaction" controller="transactions" action="createTransaction">
            <label>Cost</label><br>
            <input type="number" id="cost" name="cost" min="0" step=".01" value="${cost}" required><br>
            <g:submitButton name="Submit" value="Submit"/>
        </g:form>
    </div>
</body>
</html>