
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 19.03.2019
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Add new user</title>
</head>

<body>
<div>
    <h1>Super app!</h1>
</div>
<div>
        <%
                if (request.getAttribute("personList") != null) {
                    out.println("<p>Person '" + request.getAttribute("personList") + "' added!</p>");
                }
            %>
    <div>
        <div>
            <h2>Add user</h2>
        </div>
<form method="post">
    <label>Name:
        <input type="text" name="name"><br/>
    </label>

    <label>Age:
        <input type="text" name="age"><br/>
    </label>

    <label>BirthDay:
        <input type="date" name="birthday"><br/>
    </label>

    <label>Address:
        <input type="text" name="address"><br/>
    </label>

    <label>Email:
        <input type="email" name="email"><br/>
    </label>

    <label>PhoneNumber:
        <input type="text" name="phonenumber"><br/>
    </label>

    <label>Specialization:
        <input type="text" name="specialization"><br/>
    </label>

    <button type="submit">Submit</button>
</form>
    </div>
</div>

<div>
    <button onclick="location.href=''">Back to main</button>
</div>
</body>
</html></div>
</div>

<div>
    <button onclick="location.href=''">Back to main</button>
</div>
</body>
</html>