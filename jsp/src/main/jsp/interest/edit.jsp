<%@page import="edu.anxolerd.inquisition.core.entities.Interest"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Interest interest = (Interest) request.getAttribute("interest");
    boolean isCreate = interest == null;

    String title = isCreate ? "" : interest.getTitle();
    String description = isCreate ? "" : interest.getDescription();
    int sinRate = isCreate ? 42 : interest.getSinRate();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sinful interests</title>
    <style type="text/css">
        html,body {
            margin: 0;
            padding: 0;
            color: #212121;
            background: #DDDDDD;
            font-family: "sans";
        }
        label {
            font-weight: bold;
        }
        .wrapper {
            max-width: 800px;
            margin: 0 auto;
        }
        form {
            padding: 10px;
            background: #FFFFFF;
            box-shadow: 2px 2px 5px #AAAAAA;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <h1>Sinful <%= interest != null ? interest.getTitle() : "Interest" %></h1>
        <a href="${pageContext.request.contextPath}/interests/list">&larr;All interests</a>

        <form method="POST">
            <% if (!isCreate) { %>
                <div><input type="hidden" id="id" name="id" value="<%= interest.getId().toString() %>"/></div>
            <% } %>

            <div>
                <label for="title">Title:</label>
                <input id="title" name="title" type="text" value="<%= title %>"/>
            </div>
            <div>
                <label for="description">Descriprion:</label>
                <textarea name="description" id="description"><%= description %> </textarea>
            </div>
            <div>
                <label for="sinRate">Sin Rate:</label>
                <input id="sinRate" name="sinRate" type="number" value="<%= sinRate %>"/>
            </div>
            <input type="submit" value="Save"/>
        </form>

        <% if (!isCreate) { %>
        <form method="POST" action="${pageContext.request.contextPath}/interests/delete">
            <input type="hidden" id="id" name="id" value="<%= interest.getId().toString() %>"/>
            <input type="submit" value="Delete this interest"/>
        </form>
        <% } %>
    </div>
</body>
</html>
