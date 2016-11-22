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
        <div class="panel-body text-center">
            <h2 class="text-danger">
                <!-- display time icon -->
                <span class="glyphicon glyphicon-time"></span>
                <!-- show the countdown -->
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
    <!-- login pop-up layer,input phone number -->
    <div id="killPhoneModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>seckill phone number:
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey" placeholder="please input phone number" class="form-control" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer text-center">
                    <!-- validate information -->
                    <span id="killPhoneMessage" class="glyphicon"></span>
                    <button type="button" id="killPhoneBtn" class="btn  btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        Submit
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.js"></script>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
       //using el expression incoming parameters
        seckill.detail.init({
           seckillId:${seckill.seckillId},
            startTime:${seckill.startTime.time},
            endTime:${seckill.endTime.time},
            createTime:${seckill.createTime.time}
        });
    });
</script>
</html>