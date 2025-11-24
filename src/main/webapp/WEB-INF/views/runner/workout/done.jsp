<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Treinos feitos</title>
        </head>

        <body>
            <h1>Treinos Feitos</h1>
            <ul>
                <c:forEach var="w" items="${workouts}">
                    <li>${w.data} - ${w.descricao}</li>
                </c:forEach>
            </ul>
            <a href="/runner/dashboard?runnerId=${runner.id()}">Voltar</a>
        </body>

        </html>