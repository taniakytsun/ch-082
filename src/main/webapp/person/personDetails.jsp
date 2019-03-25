<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.03.2019
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Details of ${person.id}</title>
</head>
<body>
<c:url var="registerUrl" >
    <value>/person/edit</value>
</c:url>
<form action="${registerUrl}" method="post">
        <input type="text" placeholder="Full name" name="name"><br/>

        <input type="text" placeholder="Age" name="age"><br/>

        <input type="date" placeholder="BirthDay" name="birthday"><br/>

        <input type="text"  placeholder="Address" name="address"><br/>

        <input type="email"  placeholder="Email" name="email"><br/>

        <input type="text" placeholder="PhoneNumber"  name="phonenumber"><br/>

        <input type="text" placeholder="Specialization" name="specialization"><br/>

    <button type="submit">Save</button>
    <button type="submit">Cancel</button>
</form>
</body>
</html>
