<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Nutritionist Dashboard</title>
        </head>

        <body>
            <h1>Nutritionist Dashboard</h1>
            <p>Nutricionista: ${nutritionist.nome()}</p>
            <h2>Alunos</h2>
            <ul>
                <c:forEach var="student" items="${students}">
                    <li>${student.nome()} - Assessoria: ${student.assessoriaCorridaNome()}
                        <a href="/nutritionist/student/${student.id()}/meals">Ver andamento da dieta</a>
                    </li>
                </c:forEach>
            </ul>
        </body>

        </html>