<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <html>

        <head>
            <title>Erro - Unirun</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    max-width: 600px;
                    margin: 50px auto;
                    padding: 20px;
                    background-color: #f5f5f5;
                }

                .error-container {
                    background-color: white;
                    padding: 30px;
                    border-radius: 8px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }

                h1 {
                    color: #d32f2f;
                    margin-top: 0;
                }

                .error-details {
                    background-color: #fff3e0;
                    padding: 15px;
                    border-left: 4px solid #ff9800;
                    margin: 20px 0;
                }

                .error-code {
                    font-size: 48px;
                    font-weight: bold;
                    color: #d32f2f;
                    margin: 20px 0;
                }

                .back-link {
                    display: inline-block;
                    margin-top: 20px;
                    padding: 10px 20px;
                    background-color: #1976d2;
                    color: white;
                    text-decoration: none;
                    border-radius: 4px;
                }

                .back-link:hover {
                    background-color: #1565c0;
                }

                .timestamp {
                    color: #666;
                    font-size: 14px;
                }
            </style>
        </head>

        <body>
            <div class="error-container">
                <h1>Ops! Algo deu errado</h1>

                <div class="error-code">${status}</div>

                <div class="error-details">
                    <p><strong>Erro:</strong> ${error}</p>
                    <p><strong>Mensagem:</strong> ${message}</p>
                    <c:if test="${not empty path}">
                        <p><strong>Caminho:</strong> ${path}</p>
                    </c:if>
                </div>

                <p class="timestamp">Timestamp: ${timestamp}</p>

                <a href="/login" class="back-link">Voltar para o Login</a>
            </div>
        </body>

        </html>