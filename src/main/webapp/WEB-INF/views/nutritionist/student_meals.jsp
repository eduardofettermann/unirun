<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Andamento da Dieta</title>
        </head>

        <body>
            <h1>Andamento da Dieta de ${runnerName}</h1>
            <h2>Refeições desta semana</h2>
            <ul>
                <c:forEach var="m" items="${meals}">
                    <li>${m.data} - ${m.descricao} - ${m.calorias} kcal</li>
                </c:forEach>
            </ul>
            <h2>Refeições predefinidas (Propostas)</h2>
            <ul>
                <c:forEach var="m" items="${predefined}">
                    <li>${m.data} - ${m.descricao} - ${m.calorias} kcal (P:${m.proteinas}g C:${m.carboidratos}g
                        G:${m.gorduras}g)</li>
                </c:forEach>
            </ul>
            <a href="/nutritionist/plan/add?runnerId=${runnerId}">Adicionar Dieta Planejada</a>
            <br />
            <a href="/nutritionist/dashboard?nutritionistId=${sessionScope.loggedUserId}">Voltar</a>
        </body>

        </html>