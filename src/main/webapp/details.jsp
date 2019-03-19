<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 22.03.2019
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Person details</title>
</head>
<body>
<c:url value="/person/register" var="registerUrl"/>

<form action="${registerUrl}" method="post">
    <table>
        <c:if test="${person.id ne null}">
            <tr>
                <td>" "</td>
                <td><input type="hidden" name="id" value="${person.id}" readonly="readonly"></td>
            </tr>
        </c:if>
        <tr>
            <td>Full name:</td>
            <td><input type="text" name="name" value="${person.name}" required></td>
        </tr>
        <tr>
            <td>Age:</td>
            <td><input type="text" name="age" value="${person.age}" required></td>
        </tr>
        <tr>
            <td>BirthDay:</td>
            <td><input type="date" data-min-year="1870" data-max-year="2000" name="birthDay" value="${person.birthDay}"
                       required></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address" value="${person.address}" required></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="email" name="email" value="${person.email}" required></td>
        </tr>
        <tr>
            <td>Mobile Phone:</td>
            <td><input type="tel" name="phoneNumber" value="${person.phoneNumber}" required></td>
        </tr>
        <tr>
            <td>Specialization:</td>
            <td><input type="text" name="specialization" value="${person.specialization}" required></td>
        </tr>


        <c:if test="${person.id ne null}">
            <tr>
                <td colspan="2"><input class="button" type="submit" value="Update"></td>
            </tr>
        </c:if>
        <c:if test="${person.id eq null}">
            <tr>
                <td colspan="2"><input class="button" type="submit" value="Save"></td>
            </tr>
        </c:if>
    </table>
</form>

<h1>Candidates</h1>


<table id="persons">
    <tr>
        <th>ID</th>
        <th>Place</th>
        <th>Date From</th>
        <th>Date To</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${experiences}" var="experience">

        <tr>
            <td type="hidden ">${experience.id}</td>
            <form action="<c:url ><value>/experience/update</value></c:url>" method="post">
                <input type="hidden" name="personId" value="${experience.id}">
                <td><a href="person/update" title="See details">${experience.place} </a></td>
            </form>
            <td>${experience.dateFrom}</td>
            <td>${experience.dateTo}</td>
            <td>
                <form action="<c:url ><value>/experience/update</value></c:url>" method="post">
                    <input type="hidden" name="personId" value="${person.id}">
                    <input class="button" type="submit" value="Update">
                </form>
            <td>
                <form action="<c:url ><value>/experience/delete</value></c:url>" method="post">
                    <input type="hidden" name="personId" value="${person.id}">
                    <input class="button" type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
