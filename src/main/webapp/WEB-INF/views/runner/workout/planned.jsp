<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Treinos planejados</title>
</head>
<body>
    <h1>Treinos Planejados - Semana Atual</h1>
    <table border="1">
        <tr>
            <th>Dia</th>
            <th>Treino</th>
            <th>Ação</th>
        </tr>
        <c:forEach var="workout" items="${weekWorkouts}">
            <tr style="${workout.completed() ? 'background-color: #90EE90;' : ''}">
                <td>${workout.day()}</td>
                <td>${workout.description()}</td>
                <td>
                    <c:if test="${not workout.completed() and workout.id() != null}">
                        <form action="/runner/workouts/${workout.id()}/complete" method="post">
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
