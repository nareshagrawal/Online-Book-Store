<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>User Home</title>
    <link rel="stylesheet" href="/HomeStyle.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        function myFunction(id,quan) {
            var x = id;
            var z =quan;
            var y = document.getElementById("q"+x).value;
            if(isNaN(y)|| y>z || y<1) {
                alert("Please add available quantity");
            }
            else {
                $.ajax({
                    method: "POST",
                    url: "/buyer/addcart",
                    data: {"id": x, "quantityAdd": y}
                });
                alert("Added to cart");
            }
        }
    </script>
</head>
<body>
<a class = "btn btn-primary" href="/updatedetail" >Account Details</a>
<a class = "btn btn-primary" href="/seller" >Seller Portal</a>
<a id="a1" class = "btn btn-danger" href="/logout" >Logout</a>
<a id="a1" class = "btn btn-info" href="${contextPath}/buyer/cart" >Cart</a>

<br>
<h3> Welcome,${user.firstName} </h3>
<br>
<c:choose>
            <c:when test="${empty buyerBooks}">
                        <h3>No books available</h3>
            </c:when>
    <c:otherwise>
        <h3>Books available on book store</h3>
        <br>
        <br>
        <table class="table-condensed" border="1">
            <tr>
                <td id="td1"> Title </td>
                <td id="td1"> ISBN </td>
                <td id="td1"> Author </td>
                <td id="td1"> Publication Date</td>
                <td id="td1"> Price </td>
                <td id="td1"> Available Quantity </td>
                <td id="td1"> Book Image </td>
                <td id="td1"> Select Quantity</td>
                <td id="td1"> Add to Cart </td>
            </tr>

            <c:forEach items="${buyerBooks}" var="book">
                <tr>
                    <td align="center"> ${book.title} </td>
                    <td align="center"> ${book.ISBN} </td>
                    <td align="center"> ${book.authors} </td>
                    <td align="center"> ${book.publicationDate} </td>
                    <td align="center"> ${book.price} </td>
                    <td align="center"> ${book.quantity} </td>
                    <td align="center"><a class = "btn btn-success" href="${contextPath}/seller/viewimage?id=${book.bookID}" > View </a></td>
                    <td align="center"><input id="q${book.bookID}" type="number" min="1" max="${book.quantity}" value="1" required="required" /> </td>
                    <td align="center"><input  class = "btn btn-success" id="${book.bookID}" type="button" onclick="myFunction(this.id,${book.quantity})" value="Add" /></td>
                </tr>

            </c:forEach>
        </table>

    </c:otherwise>
</c:choose>

</body>
</html>