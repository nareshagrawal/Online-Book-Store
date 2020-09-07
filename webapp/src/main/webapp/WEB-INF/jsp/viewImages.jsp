<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Book Images</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<body>
<a class = "btn btn-primary" href="${contextPath}/home" >Home</a>
<br>
<br>
<h2 class="text-center"> Book Images</h2>
<br>
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

<script>
    $(document).ready(function() {
        $('.carousel-indicators').children().first().addClass('active');
        $('.carousel-inner').children().first().addClass('active');
    });
</script>

</body>
</html>