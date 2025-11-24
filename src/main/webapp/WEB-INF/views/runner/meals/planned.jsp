<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Refeições planejadas</title>
        </head>

        <body>
            <h1>Refeições Planejadas - Semana Atual</h1>
            <ul>
                <c:forEach var="m" items="${meals}">
                    <li>${m.data} - ${m.descricao} - ${m.calorias} kcal (P:${m.proteinas}g C:${m.carboidratos}g
                        G:${m.gorduras}g)</li>
                </c:forEach>
            </ul>
            <a href="/runner/dashboard?runnerId=${runner.id()}">Voltar</a>
        </body>

        </html>