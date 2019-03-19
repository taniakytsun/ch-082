<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--  Created by IntelliJ IDEA.
  User: user
  Date: 14.03.2019
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="head.jsp" %>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
</head>
<body>
<c:url value="/person/register" var="registerUrl"/>
<form action="${registerUrl}" method="post">
    <div>
        <h1>Candidates</h1>
    </div>
    <div>
        <c:forEach items="${personList}" var="person">
            <div class="bigdiv">
            <div class="card">
                <img src="https://lh3.googleusercontent.com/xZSvEJvwWakP5tOQvhMG7unU7-xAvkDxqE3NptPc7r2wGaRjbJZyVMGKpsYV7tdvS0qtE_8TFgoZZ37PBnTekyd7tUzR90fTWBTho2NDyWJvvGRBPEBzJy6Cu9f1UFF2FzY3Q0BBdRJf-9JSsXCMy70ilk83THGYGomfSG6JopqjxtiLz74KZymeZ-E7OntOiUAryZMZNmfgdAdmBQOf56RSqKcnOgHp34yOv3bYFY7ZeG1LRySZ5DAZWN2lYlUCscOvAwb3MxklOt8gKZ60fRTWkbqAoyuARafUKouti-FDTUbHiAW4s9diAtWObrDHdNFblM6af-8-fHwq2kmsVFM4bDANyD1ugfFnO-tTXM0_tshH5eIVSh4h1WuP47Pa_9YG3uKKCF38_3nkpXSBfaYCCC4pLFeGzmjqaRXc1xUqltfIjhqWZMb8Eu6ils2kU5tMEtebYVMKe2pd0Gkdi8jLnysd3mefb1xJhJgaeqykzu5Oqgle70E68yfUkysc4BgeDLvbWRs99BTs88Tuj3De_3742MtiQsxqEXSOx5bsyuYb05Th19o6gHIxfYDuF624Z7zQe-mSSlsN_c4u6WzYHxGozNej9UAe-R0qlHcRw3-bcoyM57dcg-zZ_R400MPpZhJgXyeMSUF_hqs2Z_9QRvfJzKdxl3OyvBYGHH3ffvQ9viO1K9mFfrG_P5O2pYZwtvEEtxrdJ803CTBU35bR=s512-no"
                     alt="person" style="width:100%">
                <div class="container">
                    <h4><b><a href="update" method="post" title="See details"> ${person.name}</a></b></h4>
                    <p>${person.specialization}</p>
                    <form action="<c:url value="/person/update"/>" method="post">
                        <input class="button" type="hidden" name="personId" value="${person.id}">
                        <input class="button" type="submit" value="See details">
                    </form>
                </div>
            </div>
            </div>
        </c:forEach>
    </div>
</form>
</body>
</html>
