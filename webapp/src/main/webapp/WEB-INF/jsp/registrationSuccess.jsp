<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html><html>
<head>
    <title>User Register Success</title>
    <link rel="stylesheet" href="/HomeStyle.css"/>
    <link rel="stylesheet" href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>">
</head>
<body>
<br>
<h4 style="font-size:30px;"><b>Hi, <i> ${user.firstName}</i></b></h4>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h5>Thank you for Registering</h5>
<br>
<h5>  Please go back to the login page</h5>
<a class = "btn btn-primary" href="${contextPath}/" > Login </a> <br />

</body>
</html>