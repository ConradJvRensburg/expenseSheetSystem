<%--
  Created by IntelliJ IDEA.
  User: Conrad
  Date: 12/21/2021
  Time: 12:52 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Show user transactions</title>
</head>

<style>
    table{
        border: 3px solid black;
    }
    tr{
        border: 3px solid black;
    }

    th{
        border: 2px solid gray;
    }

    td{
        border: 2px solid gray;
    }
</style>

<body>
    <g:if test="${toets}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Cost</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Balance</th>
                    <th>USD</th>
                    <th>User</th>
                </tr>
            </thead>
            <tbody>
        <g:each in="${toets}" status="i" var="userTransaction">
            <tr>
                <td>${userTransaction.traID}</td>
                <td>${userTransaction.traCost}</td>
                <td>${userTransaction.traDate}</td>
                <td>${userTransaction.traTime}</td>
                <td>${userTransaction.traBal}</td>
                <td>${userTransaction.traUSD}</td>
                <td>${userTransaction.traUser}</td>
            </tr>
        </g:each>
            </tbody>
        </table>
        <g:form action="csv" method="post">
            <div class="buttons">
                <span class="button"><g:submitButton name="export" action="csv"/></span>
            </div>
        </g:form>
    </g:if>
</body>
</html>