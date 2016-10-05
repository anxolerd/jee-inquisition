<%@page import="java.util.Iterator"%>
<%@page import="edu.anxolerd.inquisition.core.entities.Person"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    DateFormat df = (DateFormat) request.getAttribute("formatter");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sinful persons</title>
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
        table {
            background: #FFFFFF;
            box-shadow: 2px 2px 5px #AAAAAA;
            border: 1px solid #212121;
            border-spacing: 0px;
        }
        td, th {
            padding: 4px;
            border: 1px solid #CCCCCC;
        }
        th {
            background: #CCCCCC;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <h1>Sinful persons</h1>
        <a href="${pageContext.request.contextPath}/persons/new">Register one more sinful person</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Middle Name</th>
                    <th>Last Name</th>
                    <th>Birth date</th>
                    <th>Death date</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Person> persons = (List<Person>)request.getAttribute("persons");
                for (Iterator<Person> iter = persons.iterator(); iter.hasNext(); ) {
                Person person = iter.next();
            %>
                <tr>
                    <td><a href="${pageContext.request.contextPath}/persons/edit?id=<%= person.getId().toString() %>"><%= person.getId().toString() %></a></td>
                    <td><%= person.getFirstName() %></td>
                    <td><%= person.getMiddleName() == null ? "" : person.getMiddleName() %></td>
                    <td><%= person.getLastName() %></td>
                    <td><%= person.getBirthDate() == null ? "-" : df.format(person.getBirthDate()) %></td>
                    <td><%= person.getDeathDate() == null ? "-" : df.format(person.getDeathDate()) %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>