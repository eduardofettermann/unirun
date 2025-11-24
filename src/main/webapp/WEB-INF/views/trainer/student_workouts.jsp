<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Treinos do Aluno</title>
        </head>

        <body>
            <h1>Treinos de ${runnerName} - Semana Atual</h1>

            <h2>Plano Semanal</h2>
            <table border="1">
                <tr>
                    <th>Dia</th>
                    <th>Treino</th>
                </tr>
                <c:forEach var="entry" items="${weekWorkouts}">
                    <tr style="${entry.value.concluido ? 'background-color: #90EE90;' : ''}">
                        <td>${entry.key}</td>
                        <td>${entry.value.descricao}</td>
                    </tr>
                </c:forEach>
            </table>

            <br />
            <a href="/trainer/student/${runnerId}/plan/add">Adicionar Treino Planejado</a>
            <br />
            <a href="/trainer/dashboard?trainerId=${sessionScope.loggedUserId}">Voltar</a>
        </body>

        </html>