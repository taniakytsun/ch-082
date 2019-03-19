<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 22.03.2019
  Time: 12:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<%@include file="head.jsp" %>
<head>
    <meta charset="utf-8">
    <title>Home</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
</head>
<body>
<c:url value="/person/register" var="registerUrl"/>


<h1 class="txt" >Candidates</h1>

<table id="persons">
    <tr>
        <th>✔</th>
        <th>Full Name</th>
        <th>Age</th>
        <th>BirthDay</th>
        <th>Address</th>
        <th>Email</th>
        <th>Mobile</th>
        <th>Specialization</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${personList}" var="person">

        <tr>
            <td><input contenteditable='true' type="checkbox" name="selectedItems" value="checked"/></td>
            <td>${person.name} </a></td>
            <td>${person.age}</td>
            <td>${person.birthDay}</td>
            <td>${person.address}</td>
            <td>${person.email}</td>
            <td>${person.phoneNumber}</td>
            <td>${person.specialization}</td>
            <td>
                <form action="<c:url value="/person/update"/>" method="post">
                    <input type="hidden" name="personId" value="${person.id}">
                    <input class="button" type="submit" value="Update">
                </form>
            <td>
                <form action="<c:url value="/person/delete"/>" method="post">
                    <input type="hidden" name="personId" value="${person.id}">
                    <input class="button" type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>


<br>
<br>
<div class="bigdiv">
    <h1 class="txt"> Candidate </h1>

    <form action="${registerUrl}" method="post">
        <table>
            <c:if test="${person.id ne null}">
                <tr>
                    <td></td>
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
                <td><input type="date" data-min-year="1870" data-max-year="2000" name="birthDay"
                           value="${person.birthDay}"
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
                <td><input type="text" name="phoneNumber" value="${person.phoneNumber}" required></td>
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
</div>
<div class="bigdiv">
    <label class="txt"> Upload cv: </label><br>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <input class="button" name="data" type="file" accept=".json, .xml, .txt" ><br><br>
        <input class="button" type="submit" value="Send"> <br>
        <label>${message}</label>
    </form>
</div>
</body>
</html>
