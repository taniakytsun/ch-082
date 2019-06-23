<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.softserve.javaweb.model.Person" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 19.03.2019
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<section>
    <h3>Person info</h3>
    <table>
        <c:forEach items="${personList}" var="person">
            <tr>
                <td><c:out value="${person.name}"/></td><br>
                <td><c:out value="${person.age}"/></td><br>
                <td><c:out value="${person.phoneNumber}"/></td><br>
                <td><c:out value="${person.birthDay}"/></td><br>
                <td><c:out value="${person.email}"/></td><br>
                <td><c:out value="${person.specialization}"/></td><br>
                <td><c:out value="${person.address}"/></td><br>
            </tr>
        </c:forEach>
    </table>
</section>
<li><a href="index.jsp">Go back</a></li>
</body>
</html>