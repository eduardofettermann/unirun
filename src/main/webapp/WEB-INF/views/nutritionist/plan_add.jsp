<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Nova dieta predefinida</title>
            <script>
                window.onload = function () {
                    const today = new Date().toISOString().split('T')[0];
                    document.getElementById('dateInput').value = today;
                };
            </script>
        </head>

        <body>
            <h1>Adicionar Refeição Predefinida</h1>
            <form action="/nutritionist/plan/add" method="post">
                <input type="hidden" name="nutricionistaId" value="${sessionScope.loggedUserId}" />
                <label>Runner ID: <input type="text" name="runnerId" value="${runnerId}" readonly /></label><br />
                <label>Data: <input type="date" name="date" id="dateInput" /></label><br />
                <label>Descrição: <input type="text" name="descricao" /></label><br />
                <label>Calorias: <input type="number" name="calorias" /></label><br />
                <label>Proteínas: <input type="number" step="0.1" name="proteinas" /></label><br />
                <label>Carboidratos: <input type="number" step="0.1" name="carboidratos" /></label><br />
                <label>Gorduras: <input type="number" step="0.1" name="gorduras" /></label><br />
                <button type="submit">Salvar</button>
            </form>
            <a href="/nutritionist/dashboard?nutritionistId=${sessionScope.loggedUserId}">Voltar</a>
        </body>

        </html>