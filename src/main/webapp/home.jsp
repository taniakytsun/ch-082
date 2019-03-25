<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 22.03.2019
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Title</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
</head>
<body>
<c:url value="/person/register" var="registerUrl"/>

<form action="${registerUrl}" method="post">
    <table>
        <c:if test="${person.id ne null}">
            <tr>
                <td>" " </td>
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
<br>
<h1>List of Persons</h1>


<br>
<br>
<br>
<br>
    <table id="persons">
    <tr>
    <th>ID</th>
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
            <td type="hidden ">${person.id}</td>
            <form action="<c:url value="/person/update"/>" method="post">
                <input type="hidden" name="personId" value="${person.id}">
                <td><a href="person/update" title="See details">${person.name} </a></td>
            </form>
            <td>${person.age}</td>
            <td>${person.birthDay}</td>
            <td>${person.address}</td>
            <td>${person.email}</td>
            <td>${person.phoneNumber}</td>
            <td>${person.specialization}</td>
            <td>
                <form action="<c:url value="/person/update"/>" method="post">
                    <input type="hidden" name="personId" value="${person.id}">
                    <input  class="button" type="submit" value="Update">
                </form>
            <td>
                <form action="<c:url value="/person/delete"/>" method="post">
                    <input type="hidden" name="personId" value="${person.id}">
                    <input class="button"  type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    </table>
<div>
<c:forEach items="${personList}" var="person">
    <div class="card">
        <img src="https://lh3.googleusercontent.com/xZSvEJvwWakP5tOQvhMG7unU7-xAvkDxqE3NptPc7r2wGaRjbJZyVMGKpsYV7tdvS0qtE_8TFgoZZ37PBnTekyd7tUzR90fTWBTho2NDyWJvvGRBPEBzJy6Cu9f1UFF2FzY3Q0BBdRJf-9JSsXCMy70ilk83THGYGomfSG6JopqjxtiLz74KZymeZ-E7OntOiUAryZMZNmfgdAdmBQOf56RSqKcnOgHp34yOv3bYFY7ZeG1LRySZ5DAZWN2lYlUCscOvAwb3MxklOt8gKZ60fRTWkbqAoyuARafUKouti-FDTUbHiAW4s9diAtWObrDHdNFblM6af-8-fHwq2kmsVFM4bDANyD1ugfFnO-tTXM0_tshH5eIVSh4h1WuP47Pa_9YG3uKKCF38_3nkpXSBfaYCCC4pLFeGzmjqaRXc1xUqltfIjhqWZMb8Eu6ils2kU5tMEtebYVMKe2pd0Gkdi8jLnysd3mefb1xJhJgaeqykzu5Oqgle70E68yfUkysc4BgeDLvbWRs99BTs88Tuj3De_3742MtiQsxqEXSOx5bsyuYb05Th19o6gHIxfYDuF624Z7zQe-mSSlsN_c4u6WzYHxGozNej9UAe-R0qlHcRw3-bcoyM57dcg-zZ_R400MPpZhJgXyeMSUF_hqs2Z_9QRvfJzKdxl3OyvBYGHH3ffvQ9viO1K9mFfrG_P5O2pYZwtvEEtxrdJ803CTBU35bR=s512-no" alt="person" style="width:100%">
        <div class="container">
            <h4><b><a href="update" method="post" title="See details"> ${person.name}</a></b></h4>
            <p>${person.email}</p>
            <form action="<c:url value="/person/update"/>" method="post">
                <input class="button" type="hidden" name="personId" value="${person.id}">
                <input  class="button" type="submit" value="See details">
            </form>
        </div>
    </div>
</c:forEach>
</div>
    </body>
    </html>
