<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Trainer Dashboard</title>
        </head>

        <body>
            <h1>Trainer Dashboard</h1>
            <p>Treinador: ${trainer.nome()}</p>
            <h2>Alunos</h2>
            <ul>
                <c:forEach var="student" items="${students}">
                    <li>${student.nome()} - Assessoria: ${student.assessoriaCorridaNome()} - Nutri:
                        ${student.nutricionistaNome()}
                        <a href="/trainer/student/${student.id()}/workouts">acessar treinos do aluno</a>
                    </li>
                </c:forEach>
            </ul>
        </body>

        </html>