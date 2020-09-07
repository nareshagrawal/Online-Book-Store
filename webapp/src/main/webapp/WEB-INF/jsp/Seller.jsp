<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Seller Home</title>
    <link rel="stylesheet" href="/HomeStyle.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<body>
<a class = "btn btn-primary" href="${contextPath}/home" >Home</a>
<a class = "btn btn-primary" href="${contextPath}/seller/addbook" >Add Book</a>
<br>
<br>
<c:choose>
    <c:when test="${empty sellerBooks}">
    <h3>None of your books available on book store</h3>
</c:when>
  <c:otherwise>
    <h3>Below are the your books available on book store</h3>
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
            <td id="td1"> Update Book </td>
            <td id="td1"> Delete Book </td>
        </tr>

        <c:forEach items="${sellerBooks}" var="book">
            <tr>
                <td align="center"> ${book.title} </td>
                <td align="center"> ${book.ISBN} </td>
                <td align="center"> ${book.authors} </td>
                <td align="center"> ${book.publicationDate} </td>
                <td align="center"> ${book.price} </td>
                <td align="center"> ${book.quantity} </td>
                <td align="center"><a class = "btn btn-success" href="${contextPath}/seller/viewimage?id=${book.bookID}" > View </a></td>
                <td align="center"><a class = "btn btn-success" href="${contextPath}/seller/updatebook?id=${book.bookID}" > Update </a></td>
                <td align="center"><a class = "btn btn-danger" href="${contextPath}/seller/deletebook?id=${book.bookID}" onclick = "if (! confirm('Book will be deleted permanently, want to continue?')) return false;" > Delete Book </a></td>
            </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
</body>
</html>