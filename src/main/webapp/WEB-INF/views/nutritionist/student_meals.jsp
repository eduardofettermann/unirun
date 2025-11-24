<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Andamento da Dieta</title>
        </head>
        <body>
            <h1>Andamento da Dieta de ${runnerName}</h1>
            <h2>Refeições desta semana</h2>
            <table border="1">
                <tr>
                    <th>Dia</th>
                    <th>Refeição</th>
                </tr>
                <c:forEach var="meal" items="${weekMeals}">
                    <tr style="${meal.completed() ? 'background-color: #90EE90;' : ''}">
                        <td>${meal.day()}</td>
                        <td>${meal.description()}</td>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <a href="/nutritionist/plan/add?runnerId=${runnerId}">Adicionar Dieta Planejada</a>
            <a href="/nutritionist/dashboard?nutritionistId=${sessionScope.loggedUserId}">Voltar</a>
        </body>

        </html>