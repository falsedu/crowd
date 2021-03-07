<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>

<script>
    $(function () {
        initialPagination();
    })

    function initialPagination() {
        var totalRecord="${requestScope.pageInfo.total}";
        $("#totalRecordNum").html("总记录数:"+totalRecord);

        var properties = {
            num_edge_entries: 1, // 边缘页数
            num_display_entries: 5, // 主体页数
            callback: pageSelectCallback, // 用户点击“ 翻页” 按钮之后执行翻页操作的回调函数
            current_page: ${requestScope.pageInfo.pageNum-1}, // 当前页， pageNum 从 1 开始，必须-1 后才可以赋值
            prev_text: "上一页",
            next_text: "下一页",

            items_per_page:${requestScope.pageInfo.pageSize} // 每页显示 1 项
        };
        $("#pagination").pagination(totalRecord,properties);


    }
    function pageSelectCallback(pageIndex,jQuery) {

        var pageNo=pageIndex+1;

        window.location.href="admin/get/page.html?pageNo="+pageNo+"&keyword=${param.keyword}";
        return false;
    }

</script>
<body>

    <%@include file="/WEB-INF/include-nav.jsp"%>
    <div class="container-fluid">
        <div class="row">
            <%@include file="/WEB-INF/include-sidebar.jsp"%>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                    </div>
                    <div class="panel-body">
                        <form action="admin/get/page.html" method="post" class="form-inline" role="form" style="float:left;">
                            <div class="form-group has-feedback">
                                <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                    <input class="form-control has-success" name="keyword" type="text" placeholder="请输入查询条件" value="${param.keyword}">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                        <a href="admin/to/add/page.html" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</a>
                        <br>
                        <hr style="clear:both;">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input type="checkbox"></th>
                                    <th>账号</th>
                                    <th>名称</th>
                                    <th>邮箱地址</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${empty requestScope.pageInfo.list}">
                                    <td colspan="6" >抱歉没有您要查询的信息</td>
                                </c:if>
                                <c:if test="${!empty requestScope.pageInfo.list}">
                                    <c:forEach items="${requestScope.pageInfo.list}" var="a" varStatus="myStatus">
                                        <tr>
                                            <td>${myStatus.count}</td>
                                            <td><input type="checkbox"></td>
                                            <td>${a.loginAcct}</td>
                                            <td>${a.userName}</td>
                                            <td>${a.email}</td>
                                            <td>
<%--                                                <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                                <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                                <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
                                                <a href="" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                                <a href="admin/to/edit/page.html?aid=${a.id}&pageNo=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                                <a href="admin/remove/${a.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>


                                </tbody>
                                <tfoot>
                                    <tr >
                                        <td colspan="6" align="center">
<%--                                            <ul class="pagination">--%>
<%--                                                <li class="disabled"><a href="#">上一页</a></li>--%>
<%--                                                <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--%>
<%--                                                <li><a href="#">2</a></li>--%>
<%--                                                <li><a href="#">3</a></li>--%>
<%--                                                <li><a href="#">4</a></li>--%>
<%--                                                <li><a href="#">5</a></li>--%>
<%--                                                <li><a href="#">下一页</a></li>--%>
<%--                                            </ul>--%>
                                            <div id="pagination" class="pagination">

                                            </div>


                                        </td>

                                    </tr>

                                </tfoot>
                            </table>
                            <div id="totalRecordNum"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
