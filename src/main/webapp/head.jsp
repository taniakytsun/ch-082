<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--  Created by IntelliJ IDEA.
  User: user
  Date: 14.03.2019
  Time: 19:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Persons</title>
    <script src="<c:url value="/javascript/menu.js"/>"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icon/candidate.png" type="image/x-icon">
    <link href="<c:url value="/css/mainStyle.css"/>" rel="stylesheet">
</head>
<body>
<!-- header -->
<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <a href="index">Home</a>
    <a href="home">Candidates</a>
    <a href="cards">Cards</a>
</div>
<div id="main">
    <span style="font-size:30px;cursor:pointer" onclick="openNav()">☰ </span>
</div>
</body>
</html>
