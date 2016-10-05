<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello World</title>
    <style type="text/css">
        html,body {
            margin: 0;
            padding: 0;
            color: #212121;
            background: #DDDDDD;
            font-family: "sans";
        }
        .wrapper {
            max-width: 800px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <h1>Explore sinners and sins</h1>
        <a href="${pageContext.request.contextPath}/persons/list">Sinners</a>
        <a href="${pageContext.request.contextPath}/interests/list">Sins</a>
    </div>
</body>
</html>
