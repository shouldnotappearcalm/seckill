<%--
  Created by IntelliJ IDEA.
  User: GZR
  Date: 2016/11/17
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- import jstl tag -->
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>product details</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
                <h1>${seckill.name}</h1>
            </div>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <!-- display time icon -->
                <span class="glyphicon glyphicon-time"></span>
                <!-- show the countdown -->
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>

</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
