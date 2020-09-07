<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
    <link rel="stylesheet" href="/HomeStyle.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<a class = "btn btn-primary" href="${contextPath}/seller">Back</a>
<h3>Add new Book</h3>
<br>
<br>
<form action="${contextPath}/seller/addbook" method="post" enctype="multipart/form-data">

    <table class="table">
        <tr>
            <td>Title:</td>
            <td><input name="title" size="15" required="required" pattern="[a-zA-Z0-9 ]+" title="Title contains only alphanumeric" /> </td>
        </tr>

        <tr>
            <td>ISBN:</td>
            <td><input name="ISBN" size="15" required="required" pattern="[0-9]{10}" title="ISBN should be of ten numeric character"/></td>
        </tr>

        <tr>
            <td>Author:</td>
            <td><input name="authors" size="15" required="required" /></td>
        </tr>

        <tr>
            <td>Publication Date:</td>
            <td><input name="publicationDate" type="date" size="10" required="required" /></td>
        </tr>

        <tr>
            <td>Price:</td>
            <td><input  id="price" name="price" size="15" onchange="myFunction()" required="required" /></td>
            <td><label id="check"></label></td>
        </tr>

        <tr>
            <td>Quantity:</td>
            <td><input name="quantity" size="20" type="number" min="0" max="999" required="required" /> </td>
        </tr>

        <tr>
            <td>Upload Images:</td>
            <td> <input type="file" size="15" name="image" multiple="multiple" accept="image/*" /></td>
        </tr>

        <tr>
            <td></td>
            <td colspan="2"><input class = "btn btn-success" type="submit" id="submit" value="Add Book" /></td>
        </tr>

    </table>
</form>
<script>
    function myFunction() {
        var x, text;

        x = document.getElementById("price").value;
        if (isNaN(x) || x < 0.01 || x > 9999.99) {
            text = "Price must be in between 0.01 to 9999.99";
            document.getElementById("check").innerHTML = text;
            document.getElementById("submit").disabled = true;
        }else{
            text = null;
            document.getElementById("check").innerHTML = text;
            document.getElementById("submit").disabled = false;
        }
    }
</script>

</body>
</html>