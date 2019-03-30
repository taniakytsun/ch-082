<%@ page import="com.softserve.javaweb.model.Person" %>
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
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2019.1.220/styles/kendo.common.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2019.1.220/styles/kendo.rtl.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2019.1.220/styles/kendo.silver.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2019.1.220/styles/kendo.mobile.all.min.css"/>

    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2019.1.220/js/kendo.all.min.js"></script>
</head>
<body>

<div id="example">


    <div id="grid"></div>

    <style>
        /*
        Use the DejaVu Sans font for display and embedding in the PDF file.
        The standard PDF fonts have no support for Unicode characters.
        */

        .k-grid {
            font-family: "DejaVu Sans", "Arial", sans-serif;
        }
    </style>


    <!-- Load Pako ZLIB library to enable PDF compression -->
    <script src="https://kendo.cdn.telerik.com/2017.3.913/js/pako_deflate.min.js"></script>


    <script>
        $(document).ready(function () {
            $("#grid").kendoGrid({
                toolbar: ["pdf", "Delete"],
                Delete:{

                },
                Delete: function (e) {
                    var rows = e.sender.table[0].rows;

                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        if (!$(row).hasClass("k-state-selected")) {
                            $(row).addClass("hiddenRow")
                        }
                        ;
                    }
                    ;
                    e.promise
                        .done(function () {
                            $(".hiddenRow").each(function () {
                                $(this).parents("tr").remove();
                            });
                        });
                },
                pdf: {
                    allPages: false,
                    avoidLinks: true,
                    paperSize: "A4",
                    margin: {
                        top: "2cm",
                        left: "1cm",
                        right: "1cm",
                        bottom: "1cm"
                    },
                    landscape: true,
                    repeatHeaders: true,
                    template: $("#page-template").html(),
                    scale: 0.8
                },
                pdfExport: function (e) {
                    var rows = e.sender.table[0].rows;

                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        if (!$(row).hasClass("k-state-selected")) {
                            $(row).addClass("hiddenRow")
                        }
                        ;
                    }
                    ;
                    e.promise
                        .done(function () {
                            $(".hiddenRow").each(function () {
                                $(this).parents("tr").removeClass("hiddenRow");
                            });
                        });
                },
                selectable: "multiple row",
                dataSource: {
                    transport: {
                        read: {
                            url: "http://localhost:8080/details",
                            type: "get",
                            dataType: "json"
                        }
                    },
                    pageSize: 20
                },
                height: 550,
                sortable: true,
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5
                },
                columns: [{
                    field: "name",
                    title: "Name"
                }, {
                    field: "age",
                    title: "Age"
                }, {
                    field: "birthDay",
                    title: "Date of Birth"
                }, {
                    field: "address",
                    title: "Address"
                }, {
                    field: "email",
                    title: "Email"
                }, {
                    field: "phoneNumber",
                    title: "Phone"
                }, {
                    field: "specialization",
                    title: "Specialization"
                }]
            });
        });
    </script>

    <style>
        .hiddenRow {
            display: none;
        }

        /* Page Template for the exported PDF */

        .page-template {
            font-family: "DejaVu Sans", "Arial", sans-serif;
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
        }

        .page-template .header {
            position: absolute;
            top: 30px;
            left: 30px;
            right: 30px;
            border-bottom: 1px solid #4c4c4c;
            color: #888;
        }

        .page-template .footer {
            position: absolute;
            bottom: 30px;
            left: 30px;
            right: 30px;
            border-top: 1px solid #888;
            text-align: center;
            color: #888;
        }

        .page-template .watermark {
            font-weight: bold;
            font-size: 400%;
            text-align: center;
            margin-top: 30%;
            color: #686366;
            opacity: 0.1;
            transform: rotate(-35deg) scale(1.7, 1.5);
        }

        /* Content styling */

        .customer-photo {
            display: inline-block;
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background-size: 32px 35px;
            background-position: center center;
            vertical-align: middle;
            line-height: 32px;
            box-shadow: inset 0 0 1px #999, inset 0 0 10px rgba(0, 0, 0, .2);
            margin-left: 5px;
        }

        kendo-pdf-document .customer-photo {
            border: 1px solid #414141;
        }

        .customer-name {
            display: inline-block;
            vertical-align: middle;
            line-height: 32px;
            padding-left: 3px;
        }
    </style>
</div>


<c:url value="/person/register" var="registerUrl"/>
<h1 class="txt">Candidates</h1>

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
            <td>${person.name} </td>
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

<h1 class="txt"> Candidate </h1>

<form action="${registerUrl}" method="post">
    <div class="details">
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
    </div>
    <div class="details">
        <table id="persons">
            <tr>
                <th>✔</th>
                <th>Place</th>
                <th>Date From</th>
                <th>Date To</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${experiencesByPerson}" var="exp">

                <tr>
                    <td><input contenteditable='true' type="checkbox" name="selectedItems" value="checked"/></td>
                    <td>${exp.place}</td>
                    <td>${exp.dateFrom}</td>
                    <td>${exp.dateTo}</td>
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
    </div>
</form>
<br>
<div class="bigdiv">
    <label class="txt"> Upload cv: </label><br>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <input class="button" name="data" type="file" accept=".json, .xml, .txt"><br><br>
        <input class="button" type="submit" value="Send"> <br>
        <label>${message}</label>
    </form>
</div>

</body>
</html>
