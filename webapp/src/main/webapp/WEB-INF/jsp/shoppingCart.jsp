<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="/HomeStyle.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
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
                    url: "/buyer/updatecart",
                    data: {"id": x, "quantityAdd": y}
                });
                alert("Cart updated");
            }
        }
        // function remove(id) {
        //     var x = id;
        //     if (! confirm('Book will be deleted permanently, want to continue?')) return false;
        //     else {
        //         alert("hi");
        //         $.ajax({
        //             method: "POST",
        //             url: "/buyer/removeitem",
        //             data: {"id": x}
        //         });
        //     }
        // }
    </script>
</head>
<body>
<a class = "btn btn-primary" href="/home" >Home</a>
<br>
<br>
<c:choose>
    <c:when test="${empty cartItem}">
        <h3>Cart is empty</h3>
    </c:when>
    <c:otherwise>
        <h3>Items in cart</h3>
        <br>
        <br>
        <table class="table-condensed" border="1">
                <tr>
                    <td id="td1"> Title </td>
                    <td id="td1"> ISBN </td>
                    <td id="td1"> Author </td>
                    <td id="td1"> Publication Date</td>
                    <td id="td1"> Price </td>
                    <td id="td1"> Book Image </td>
                    <td id="td1"> Selected Quantity</td>
                    <td id="td1"> Update Cart</td>
                    <td id="td1"> Remove from cart</td>
                </tr>

                <c:forEach items="${cartItem}" var="item">
                    <tr>
                        <td align="center"> ${item.book.title} </td>
                        <td align="center"> ${item.book.ISBN} </td>
                        <td align="center"> ${item.book.authors} </td>
                        <td align="center"> ${item.book.publicationDate} </td>
                        <td align="center"> ${item.book.price} </td>
                        <td align="center"><a class = "btn btn-success" href="${contextPath}/seller/viewimage?id=${item.book.bookID}" > View </a></td>
                        <td align="center"><input id="q${item.cartItemID}" type="number" min="1" max="${item.book.quantity}" value="${item.quantityAdd}" required="required" /> </td>
                        <td align="center"><input  class = "btn btn-success" id="${item.cartItemID}" type="button" onclick="myFunction(this.id,${item.book.quantity})" value="Update" /></td>
<%--                        <td align="center"><input  class = "btn btn-danger"  type="button" onclick="remove(${item.cartItemID})" value="Remove" /></td>--%>
                        <td align="center"><a class = "btn btn-danger" href="${contextPath}/buyer/removeitem?id=${item.cartItemID}" onclick = "if (! confirm('Book will be deleted permanently, want to continue?')) return false;" > Remove </a></td>
                    </tr>

                </c:forEach>
            </table>
    </c:otherwise>
</c:choose>

</body>
</html>