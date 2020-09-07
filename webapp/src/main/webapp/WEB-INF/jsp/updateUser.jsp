<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Update User</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<a class = "btn btn-primary" href="/home" >Home</a>
<h2>Update user account details</h2>
<br/>
<br/>
<form action="/updatedetail" method="post">

    <table class="table" >
        <tr>
        <td>First Name:</td>
        <td><input name="firstName" size="30" value="${user.firstName}" pattern="[a-zA-Z ]+" title="Name contains only Alphabet" required />
        </td>
        </tr>

        <tr>
            <td>Last Name:</td>
            <td><input name="lastName" size="30" value="${user.lastName}" pattern="[a-zA-Z ]+" title="Name contains only Alphabet" required />
            </td>
        </tr>

        <tr>
            <td>Email Id:</td>
            <td><input name="email" size="30" value="${user.email}" readonly />
            </td>
        </tr>

        <tr>
            <td>Password:</td>
            <td><input name="password" type="password" size="30" pattern="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,15}$" title="Password must be greater than 8 charcter, must include at least one upper & lower case letter and one numeric digit" required/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2"><input class = "btn btn-success" type="submit" value="Update" /></td>
        </tr>

    </table>

</form>
</body>
</html>