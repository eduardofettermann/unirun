<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>


        <html>

        <head>
            <title>View Train</title>
        </head>

        <body>
            <h1>Runner Dashboard</h1>
            <p>Nome: ${runner.nome()}</p>
            <p>Assessoria: ${runner.assessoriaCorridaNome()}</p>
            <p>Nutricionista: ${runner.nutricionistaNome()}</p>
            <p>Treinador: ${trainer.nome()}</p>

            <a href="/runner/workouts/done?runnerId=${runner.id()}">Ver treinos feitos</a>
            <br />
            <a href="/runner/workouts/planned?runnerId=${runner.id()}">Ver treinos planejados</a>
            <br />
            <a href="/runner/workouts/add?runnerId=${runner.id()}">Adicionar treino</a>
            <br />
            <hr />
            <a href="/runner/meals/done?runnerId=${runner.id()}">Ver refeições feitas</a>
            <br />
            <a href="/runner/meals/planned?runnerId=${runner.id()}">Ver dieta planejada</a>
            <br />
            <a href="/runner/meals/add?runnerId=${runner.id()}">Adicionar refeição</a>
        </body>

        </html>