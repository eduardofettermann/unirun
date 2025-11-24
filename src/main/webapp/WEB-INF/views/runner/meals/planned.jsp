<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Refeições planejadas</title>
</head>
<body>
    <h1>Refeições Planejadas - Semana Atual</h1>
    <table border="1">
        <tr>
            <th>Dia</th>
            <th>Refeição</th>
            <th>Ação</th>
        </tr>
        <c:forEach var="meal" items="${weekMeals}">
            <tr style="${meal.completed() ? 'background-color: #90EE90;' : ''}">
                <td>${meal.day()}</td>
                <td>${meal.description()}</td>
                <td>
                    <c:if test="${not meal.completed() and meal.id() != null}">
                        <form action="/runner/meals/${meal.id()}/complete" method="post">
                            <input type="hidden" name="runnerId" value="${runner.id()}"/>
                            <button type="submit">Concluir</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="/runner/dashboard?runnerId=${runner.id()}">Voltar</a>
</body>
</html>
