<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
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
                <td><c:out value="${person.name}"/></td>
                <td><c:out value="${person.age}"/></td>
                <td><c:out value="${person.phoneNumber}"/></td>
                <td><c:out value="${person.birthDay}"/></td>
                <td><c:out value="${person.email}"/></td>
                <td><c:out value="${person.specialization}"/></td>
                <td><c:out value="${person.address}"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
<li><a href="index.jsp">Go back</a></li>
</body>
</html>