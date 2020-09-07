<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <link rel="stylesheet" href="/HomeStyle.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<a class = "btn btn-primary" href="${contextPath}/"> Login</a>
<h3>Register a New User</h3>
<br>
<br>
<form:form modelAttribute="user"  method="post">

    <table class="table">
        <tr>
            <td>First Name:</td>
            <td><form:input path="firstName" size="30" required="required" /> <font color="red"><form:errors path="firstName" /></font></td>
        </tr>

        <tr>
            <td>Last Name:</td>
            <td><form:input path="lastName" size="30" required="required" /> <font color="red"><form:errors path="lastName" /></font></td>
        </tr>

        <tr>
            <td>Email Id:</td>
            <td><form:input path="email" size="30" type="email" required="required"  /> <font color="red"><form:errors path="email" /></font></td>
        </tr>

        <tr>
            <td>Password:</td>
            <td><form:password path="password" size="30" required="required" /> <font color="red"><form:errors path="password" /></font></td>
        </tr>

        <tr>
            <td> Confirm Password:</td>
            <td><form:password path="confirmPassword" size="30" required="required" /> <font color="red"><form:errors path="confirmPassword" /></font></td>
        </tr>

        <tr>
            <td></td>
            <td colspan="2"><input class = "btn btn-success" type="submit" value="Register User" /></td>
        </tr>

    </table>
</form:form>

</body>
</html>