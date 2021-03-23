<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
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

        $("#roleBody").on("click",".assignBtn",function () {


            $("#assignRoleAndAuthID").val(this.id);
            fillAuthTree();
            $("#assignModal").modal("show");
        })


        $("#assignBtn").click(function () {
            var authIdArray=[];

            var zTreeObject=$.fn.zTree.getZTreeObj("authTreeDemo");

            var checkedNodes=zTreeObject.getCheckedNodes();

            for(var i=0;i<checkedNodes.length;i++){
                var checkedNode=checkedNodes[i];
                var authId=checkedNode.id;
                authIdArray.push(authId);
            }

            var requestBody={
                "authIdArray":authIdArray,
                "roleId":[$("#assignRoleAndAuthID").val()]
            };
            requestBody=JSON.stringify(requestBody);

            $.ajax({
                url:"assign/do/role/assign/auth.json",
                type:"post",
                data:requestBody,
                contentType: "application/json;charset=UTF-8",
                dataType:"json",
                success:function (resp) {
                    var result=resp.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功");
                    }
                    if(result=="FAILED"){
                        layer.msg("操作失败!"+resp.message);
                    }

                }
            });
            $("#assignModal").modal("hide");


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
    function fillAuthTree() {

        var ajaxReturn = $.ajax({
            "url":"assgin/get/all/auth.json",
            "type":"post",
            "dataType":"json",
            "async":false
        });

        console.log(ajaxReturn);
        if(ajaxReturn.status != 200) {
            layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： "+ajaxReturn.status+" 说 明 是 ："+ajaxReturn.statusText);
            return ;
        } // 2.从响应结果中获取 Auth 的 JSON 数据
        // 从服务器端查询到的 list 不需要组装成树形结构， 这里我们交给 zTree 去组装
        var authList = ajaxReturn.responseJSON.data;
        // console.log(authList);
// 3.准备对 zTree 进行设置的 JSON 对象
        var setting = {
            "data": {
                "simpleData": {
// 开启简单 JSON 功能
                    "enable": true,
// 使用 categoryId 属性关联父节点， 不用默认的 pId 了
                    "pIdKey": "categoryId"
                },
                "key": {
// 使用 title 属性显示节点名称， 不用默认的 name 作为属性名了
                    "name": "title"
                }
            },
            "check": {
                "enable": true
            }
        };
        $.fn.zTree.init($("#authTreeDemo"), setting, authList);
        var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
        // console.log(zTreeObj);
// 调用 zTreeObj 对象的方法， 把节点展开
        zTreeObj.expandAll(true);

        // 5.查询已分配的 Auth 的 id 组成的数组
        ajaxReturn = $.ajax({
            "url":"assign/get/assigned/auth/id/by/role/id.json",
            "type":"post",
            "data":{
                "roleId":$("#assignRoleAndAuthID").val()
            },
            "dataType":"json",
            "async":false
        });
        if(ajaxReturn.status != 200) {
            layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： "+ajaxReturn.status+" 说 明 是 ："+ajaxReturn.statusText);
            return ;
        } // 从响应结果中获取 authIdArray
        var authIdArray = ajaxReturn.responseJSON.data;
// 6.根据 authIdArray 把树形结构中对应的节点勾选上
// ①遍历 authIdArray
        for(var i = 0; i < authIdArray.length; i++) {
            var authId = authIdArray[i];
// ②根据 id 查询树形结构中对应的节点
            var treeNode = zTreeObj.getNodeByParam("id", authId);
// ③将 treeNode 设置为被勾选
// checked 设置为 true 表示节点勾选
            var checked = true;
// checkTypeFlag 设置为 false， 表示不“联动”， 不联动是为了避免把不该勾选的勾选上
            var checkTypeFlag = false;
// 执行
            zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
        }

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
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>

</body>
</html>
