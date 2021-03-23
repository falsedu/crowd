<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>

<body>

<%@include file="/WEB-INF/include-nav.jsp"%>
<div class="container-fluid">



    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">控制面板</h1>
            <p>对象</p>
<%--            <security:authentication property="credentials.class.name"></security:authentication>--%>
<%--           这个不让访问，会被清空 <security:authentication property="credentials.class"></security:authentication>--%>
            <br></br>loginAcct:<security:authentication property="principal"></security:authentication>
            <br></br>loginAcct:<security:authentication property="principal.originalAdmin"></security:authentication>
            <br></br>loginAcct:<security:authentication property="principal.originalAdmin.loginAcct"></security:authentication>
           <br>userName:<security:authentication property="principal.originalAdmin.userName"></security:authentication>
           <br>userPswd:<security:authentication property="principal.originalAdmin.userPswd"></security:authentication>
           <br>email:<security:authentication property="principal.originalAdmin.email"></security:authentication>
           <br>createTime:<security:authentication property="principal.originalAdmin.createTime"></security:authentication>
           <br>:<security:authentication property="principal.originalAdmin.createTime"></security:authentication>
           <br>createTime:<security:authentication property="principal.originalAdmin.createTime"></security:authentication>
<%--           <br>createTime:<security:authentication property="dingding"></security:authentication>--%>


            <div class="row placeholders">
                <security:authorize access="hasRole('经理')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
                        <h4>Label1</h4>
                        <span class="text-muted">Something else</span>
                    </div>
                </security:authorize>
                <security:authorize access="hasAnyAuthority('role:delete')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
                        <h4>Label2</h4>
                        <span class="text-muted">Something else</span>
                    </div>
                </security:authorize>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label3</h4>
                    <span class="text-muted">Something else</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label4</h4>
                    <span class="text-muted">Something else</span>
                </div>
            </div>
            <br/>
            <br/>
            <br/>
            <br/>
        </div>


    </div>
</div>




</body>
</html>

