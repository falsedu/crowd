<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script>
    $(function () {

        window.pageNo=1;
        window.pageSize=5;
        window.keyword="";
        generatePage();
        $("#searchBtn").click(function () {
            window.keyword=$("#searchContent").val();
            window.pageNo=1;
            generatePage();
        })

        $("#showAddBtn").click(function () {
            $("#addModal").modal("show");
        })
        $("#saveRoleBtn").click(function () {
            var roleName=$.trim($("#roleName").val());
            if(roleName==null||roleName.length==0){
                $("#msg").html("不能为空");
                return ;
            }
            $.ajax({
                url:"role/add.json",
                data:{
                    "name":roleName
                },
                type:"post",
                dataType:"json",
                success:function (resp) {
                    if(resp.result=="SUCCESS"){

                        window.pageNo=9999999;
                        generatePage();
                    }
                    if(resp.result == "FAILED") {
                        layer.msg("操作失败！ "+resp.message);
                    }

                    $("#addModal").modal("hide");
                    $("#roleName").val("");
                }
            })

        });

        $("#roleBody").on("click",".pencilBtn",function () {
            $("#roleName1").val("");
            $("#editModal").modal("show");
            var roleName=$(this).parent().prev().text();
            window.roleId=this.id;
            $("#oldroleName").val(roleName);
        });

        $("#updateRoleBtn").click(function () {
            var roleName=$.trim($("#roleName1").val());
            if(roleName==null||roleName.length==0){
                $("#msg1").html("不能为空");
                return ;
            }
            $.ajax({
                url:"role/update.json",
                data:{
                    "id":window.roleId,
                    "name":roleName
                },
                type: "post",
                dataType: "json",
                success:function (resp) {
                    if(resp.result=="FAILED"){
                        layer.msg("操作失败！ "+resp.message);


                    }if(resp.result=="SUCCESS"){


                        generatePage();
                    }
                    $("#editModal").modal("hide");
                    $("#roleName1").val("");

                }
            })
        })
        $("#roleBody").on("click",".removeBtn",function () {

            var roleName=$(this).parent().prev().text();
            var roleArray=[{roleId:this.id,roleName:roleName}];
            showConfirmModal(roleArray);




            window.param=JSON.stringify(window.roleIdArray);






        });

        $("#removeBtn").click(function () {
            if($(".itemBox:checked").length==0){
                layer.msg("请至少选择一个角色");
                return false;
            }

            var roleArray=[];
            $(".itemBox:checked").each(function () {


                var roleId=this.id;
                var roleName=$(this).parent().next().text();

                var entry={roleId:roleId,roleName:roleName};
                roleArray.push(entry);

            });

            showConfirmModal(roleArray);

            window.param=JSON.stringify(window.roleIdArray);


        })

        $("#removeRoleBtn").click(function () {
            deleteAjax(window.param);

        });

        $("#qx").click(function () {
            $(".itemBox").prop("checked",this.checked);
        })

        $("#roleBody").on("click",".itemBox",function () {
            $("#qx").prop("checked",$(".itemBox").length==$(".itemBox:checked").length)
        })



    })

    function deleteAjax(param) {

        $.ajax({
            url:"role/remove.json",
            data:param,
            contentType:"application/json;charset=UTF-8",
            type: "post",
            dataType: "json",
            success:function (resp) {
                if(resp.result=="FAILED"){
                    layer.msg("操作失败！ "+resp.message);


                }if(resp.result=="SUCCESS"){


                    generatePage();
                }
                $("#removeconfirmModal").modal("hide");


            }
        })
    }
</script>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" id="searchContent" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="removeBtn" class="btn btn-danger removeBtn" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>

                    <button type="button" class="btn btn-primary" style="float:right;margin-left:10px;" id="showAddBtn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                   <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="qx" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleBody">

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="4"  align="center">
                                    <div id="pageBody" class="pagination"></div>

                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/modal-role-add.jsp"%>
<%@include file="/WEB-INF/modal-role-edit.jsp"%>
<%@include file="/WEB-INF/modal-role-removecomfirm.jsp"%>

</body>
</html>
