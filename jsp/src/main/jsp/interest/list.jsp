<%@page import="java.util.Iterator"%>
<%@page import="edu.anxolerd.inquisition.core.entities.Interest"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <h1>Sinful interests</h1>
        <a href="${pageContext.request.contextPath}/interests/new">Register one more sinful thing</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Sin rate</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Interest> interests = (List<Interest>)request.getAttribute("interests");
                for (Iterator<Interest> iter = interests.iterator(); iter.hasNext(); ) {
                Interest interest = iter.next();
            %>
                <tr>
                    <td><a href="${pageContext.request.contextPath}/interests/edit?id=<%= interest.getId().toString() %>"><%= interest.getId().toString() %></a></td>
                    <td><%= interest.getTitle() %></td>
                    <td><%= interest.getDescription() %></td>
                    <td><%= interest.getSinRate() %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>