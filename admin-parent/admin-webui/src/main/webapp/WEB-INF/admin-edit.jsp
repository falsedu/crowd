<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<script>

</script>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form action="admin/do/update.html" role="form">
                        <input type="hidden" name="id" value="${requestScope.a.id}">
                        <input type="hidden" name="pageNo" value="${param.pageNo}">

                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆账号</label>
                            <div style="color: red">${requestScope.exception.message}</div>
                            <input type="text" class="form-control" name="loginAcct" id="loginAcct" value="${requestScope.a.loginAcct}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户名称</label>
                            <input type="text" class="form-control" name="userName" id="userName" value="${requestScope.a.userName}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" class="form-control" name="email" id="email" value="${requestScope.a.email}">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>

                        <input type="hidden" name="keyword" value="${param.keyword}">
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                        <button type="reset" id="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
