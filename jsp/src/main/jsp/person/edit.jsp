<%@page import="edu.anxolerd.inquisition.core.entities.Person"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Person person = (Person) request.getAttribute("person");
    DateFormat df = (DateFormat) request.getAttribute("formatter");
    boolean isCreate = person == null;

    String firstName = isCreate ? "" : person.getFirstName();
    String middleName = isCreate ? "" : person.getMiddleName();
    String lastName = isCreate ? "" : person.getLastName();
    String birthDate = isCreate ? "" : df.format(person.getBirthDate());
    String deathDate = isCreate ? "" : person.getDeathDate() == null ? "" : df.format(person.getDeathDate());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit sinful person</title>
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
        <h1>Sinful <%= person != null ? person.getFirstName() : "Person" %></h1>
        <a href="${pageContext.request.contextPath}/persons/list">&larr;All persons</a>
        <% if (!isCreate) { %>
            <a href="${pageContext.request.contextPath}/interests/list?personId=<%= person.getId().toString() %>">Interests&rarr;</a>
        <% } %>

        <form method="POST">
            <% if (!isCreate) { %>
                <div><input type="hidden" id="id" name="id" value="<%= person.getId().toString() %>"/></div>
            <% } %>

            <div>
                <label for="firstName">First Name:</label>
                <input id="firstName" name="firstName" type="text" value="<%= firstName %>"/>
            </div>
            <div>
                <label for="middleName">Middle Name:</label>
                <input id="middleName" name="middleName" type="text" value="<%= middleName %>"/>
            </div>
            <div>
                <label for="lastName">Last Name:</label>
                <input id="lastName" name="lastName" type="text" value="<%= lastName %>"/>
            </div>


            <div>
                <label for="birthDate">Birth Date:</label>
                <input id="birthDate" name="birthDate" type="date" value="<%= birthDate %>"/>
            </div>
            <div>
                <label for="birthDate">Death Date:</label>
                <input id="deathDate" name="deathDate" type="date" value="<%= deathDate %>"/>
            </div>
            <input type="submit" value="Save"/>
        </form>

        <% if (!isCreate) { %>
        <form method="POST" action="${pageContext.request.contextPath}/persons/delete">
            <input type="hidden" id="id" name="id" value="<%= person.getId().toString() %>"/>
            <input type="submit" value="Delete this person"/>
        </form>
        <% } %>
    </div>
</body>
</html>
