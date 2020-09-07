<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="/HomeStyle.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<a class = "btn btn-primary" href="/register" >Register User</a>
<h2>Existing User <small>Please Enter Your Credentials</small></h2>
<br/>
<form action="/home" method="post">

    <table class="table" >
        <tr>
            <td>User Name:</td>
            <td><input name="username" size="30" required="required" placeholder="Email ID"/></td>
        </tr>

        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" size="30" required="required" placeholder="Password"/></td>
        </tr>

        <tr>
            <td></td>
            <td colspan="2"><input class = "btn btn-success" type="submit" value="Login" /></td>
        </tr>
        <tr>
            <td></td>
            <td><a href="/passwordreset" >Forget password</a></td>
        </tr>

    </table>

</form>
</body>
</html>