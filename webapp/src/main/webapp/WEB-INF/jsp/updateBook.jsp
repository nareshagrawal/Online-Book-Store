<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Book</title>
    <link rel="stylesheet" href="/HomeStyle.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<a class = "btn btn-primary" href="${contextPath}/seller">Back</a>
<h3>Update book with title ${book.title}</h3>
<br>
<div class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
<c:choose>
    <c:when test="${empty images}">
    <h3> No images present for this book yet</h3>
    </c:when>
    <c:otherwise>

        <div id="myCarousel" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">

                <c:forEach items="${images}" var="image" varStatus="loop">
                    <li data-target="#myCarousel" data-slide-to="${loop.index}" ></li>
                    <%--                    class="active"--%>
                </c:forEach>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <c:forEach items="${images}" var="image" varStatus="loop">
                    <div class="item ">
                            <%--                        active--%>
                        <img src="${image.value}" style="width:100%;">
                                <div class="carousel-caption">
                                    <a class = "btn btn-danger" href="${contextPath}/seller/deleteimage?id=${book.bookID}&imageName=${image.key}" onclick = "if (! confirm('Image will be deleted permanently, want to continue?')) return false;"> Delete Images   </a>
                                </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Left and right controls -->
            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>



    </c:otherwise>
</c:choose>


        </div>
        <div class="col-md-3"></div>
    </div>
</div>

<br>
<br>
<form action="${contextPath}/seller/updatebook" method="post" enctype="multipart/form-data">

    <table class="table">
        <tr>
            <td>Title:</td>
            <td><input name="title" size="15"  value="${book.title}" required="required" pattern="[a-zA-Z0-9 ]+" title="Title contains only alphanumeric" /> </td>
        </tr>

        <tr>
            <td>ISBN:</td>
            <td><input name="ISBN" size="15" value="${book.ISBN}" required="required" pattern="[0-9]{10}" title="ISBN should be of ten numeric character"/></td>
        </tr>

        <tr>
            <td>Author:</td>
            <td><input name="authors" size="15" value="${book.authors}"  required="required" /></td>
        </tr>

        <tr>
            <td>Publication Date:</td>
            <td><input name="publicationDate" type="date" value="${book.publicationDate}" size="10" required="required" /></td>
        </tr>

        <tr>
            <td>Price:</td>
            <td><input  id="price" name="price" value="${book.price}" size="15"   onchange="myFunction()" required="required" /></td>
            <td><label id="check"></label></td>
        </tr>

        <tr>
            <td>Quantity:</td>
            <td><input name="quantity" size="20" type="number" value="${book.quantity}" min="0" max="999" required="required" /> </td>
        </tr>
        <tr>
            <td>Upload Images:</td>
            <td> <input type="file" size="15" name="image" multiple="multiple" accept="image/*" /></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2"><input type="hidden" name="id"  value="${book.bookID}"><input class = "btn btn-success" type="submit" id="submit" value="Update Book" /></td>
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


<script>
    $(document).ready(function() {
        $('.carousel-indicators').children().first().addClass('active');
        $('.carousel-inner').children().first().addClass('active');
    });
</script>

</body>
</html>