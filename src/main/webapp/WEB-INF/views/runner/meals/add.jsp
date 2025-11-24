<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Nova refeição</title>
            <script>
                window.onload = function () {
                    const today = new Date().toISOString().split('T')[0];
                    document.getElementById('dateInput').value = today;
                };
            </script>
        </head>

        <body>
            <h1>Adicionar Refeição</h1>
            <form action="/runner/meals/add" method="post">
                <label>Runner ID: <input type="text" name="runnerId" value="${runnerId}" readonly /></label><br />
                <label>Data: <input type="date" name="date" id="dateInput" /></label><br />
                <label>Descrição: <input type="text" name="descricao" /></label><br />
                <button type="submit">Salvar</button>
            </form>
            <a href="/runner/dashboard?runnerId=${runnerId}">Voltar</a>
        </body>

        </html>