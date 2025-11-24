<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Login - Select user</title>
        </head>

        <body>
            <h1>Painel Administrativo</h1>

            <h2>Criar Assessoria</h2>
            <form action="/assessorias/create" method="post">
                <label>Nome da Assessoria: <input type="text" name="name" required /></label>
                <button type="submit">Criar Assessoria</button>
            </form>

            <hr />

            <h2>Criar Usuário</h2>
            <form action="/users/create" method="post">
                <label>Nome: <input type="text" name="name" required /></label>
                <label>Tipo:
                    <select name="type">
                        <option value="RUNNER">Corredor</option>
                        <option value="TRAINER">Treinador</option>
                        <option value="NUTRITIONIST">Nutricionista</option>
                    </select>
                </label>
                <label>Assessoria:
                    <select name="assessoriaId" required>
                        <c:forEach var="assessoria" items="${assessorias}">
                            <option value="${assessoria.id}">${assessoria.nome}</option>
                        </c:forEach>
                    </select>
                </label>
                <button type="submit">Criar</button>
            </form>

            <hr />

            <h2>Usuários Existentes</h2>
            <table>
                <tr>
                    <th>Tipo</th>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Ação</th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.type()}</td>
                        <td>${user.id()}</td>
                        <td>${user.name()}</td>
                        <td>
                            <form action="/login" method="post" style="margin:0;">
                                <input type="hidden" name="userId" value="${user.id()}" />
                                <input type="hidden" name="userType" value="${user.type()}" />
                                <button type="submit">Logar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <hr />
            <h2>Setup</h2>
            <p style="color: #666; font-size: 14px;">
                Este botão adiciona dados de demonstração ao banco de dados (assessoria, usuários e treinos de exemplo).
                Os dados existentes não serão apagados.
            </p>
            <form action="/setup/seed" method="post">
                <button type="submit" style="background-color: #4CAF50;">Popular Banco de Dados (Seed)</button>
            </form>
        </body>

        </html>