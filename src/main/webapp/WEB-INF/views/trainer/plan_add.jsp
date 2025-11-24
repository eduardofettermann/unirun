<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Adicionar Plano de Treino</title>
            <script>
                window.onload = function () {
                    const today = new Date().toISOString().split('T')[0];
                    document.getElementById('dateInput').value = today;
                };
            </script>
        </head>

        <body>
            <h1>Adicionar Plano de Treino para o Aluno</h1>
            <form action="/trainer/student/${runnerId}/plan/add" method="post">
                <input type="hidden" name="runnerId" value="${runnerId}" />
                <label>Data: <input type="date" name="date" id="dateInput" /></label><br />
                <label>Descrição: <input type="text" name="descricao" /></label><br />
                <button type="submit">Salvar</button>
            </form>
            <a href="/trainer/dashboard?trainerId=${sessionScope.loggedUserId}">Voltar</a>
        </body>

        </html>