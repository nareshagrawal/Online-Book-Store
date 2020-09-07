<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Password reset</title>
    <link rel="stylesheet" href="/HomeStyle.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<a class = "btn btn-primary" href="${contextPath}/"> Login</a>
<h2>Existing User <small>Please Enter email id</small></h2>
<br/>
<form:form action="/passwordreset" modelAttribute="password"  method="post">

    <table class="table">
        <tr>
            <td>Email Id:</td>
            <td><form:input path="email" size="30" type="email" required="required"  /> <font color="red"><form:errors path="email" /></font></td>
        </tr>

        <tr>
            <td></td>
            <td colspan="2"><input class = "btn btn-success" type="submit" value="Submit" /></td>
        </tr>

    </table>
</form:form>

</body>
</html>