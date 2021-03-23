<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        menudisplay();

        $("#treeDemo").on("click",".addBtn",function () {
            $("#pid").val(this.id);

            $("#menuAddModal").modal("show");

        });
        $("#menuSaveBtn").click(function () {
            var pid=$("#pid").val();
            var name=$.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $("#menuAddModal [name=icon]:checked").val();

            // alert(pid);
            $.ajax({
                url:"menu/save.json",
                data:{
                    "pid":pid,
                    "name":name,
                    "url":url,
                    "icon":icon
                },
                type:"post",

                dataType:"json",
                success:function (resp) {
                    if(resp.result=="SUCCESS"){
                        menudisplay();
                    }
                    else if(resp.result=="FAILED"){
                        layer.msg(resp.message);
                    }
                    $("#menuAddModal").modal("hide");

                    $("#menuResetBtn").click();
                }
            })
        })

        $("#treeDemo").on("click",".removeBtn",function () {
            // alert(this.id);
            $("#removeId").val(this.id);
            $("#menuConfirmModal").modal("show");

        });

        $("#confirmBtn").click(function(){

            $.ajax({
                url:"menu/remove.json",
                data:{
                    "id":$("#removeId").val()
                },
                type:"post",
                dataType:"json",
                success:function (resp) {
                    if(resp.result=="SUCCESS"){
                        menudisplay();
                    }
                    else if(resp.result=="FAILED"){
                        layer.msg(resp.message);
                    }
                    $("#menuConfirmModal").modal("hide");


                }

            })
        })

        $("#treeDemo").on("click",".editBtn",function () {
            // alert(this.id);
            $("#editId").val(this.id);
           var zTreeObj=$.fn.zTree.getZTreeObj("treeDemo");
           var key="id";
           var value=this.id;
           var currentNode=zTreeObj.getNodeByParam(key,value);
           $("#menuEditModal [name=name]").val(currentNode.name);
           $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
            $("#menuEditModal").modal("show");

        });

        $("#menuEditBtn").click(function(){
            var name=$("#menuEditModal [name=name]").val();
            var url=$("#menuEditModal [name=url]").val();
            var icon=$("#menuEditModal [name=icon]:checked").val();


            // alert(icon);
            $.ajax({
                url:"menu/edit.json",
                data:{
                    "id":$("#editId").val(),
                    "name":name,
                    "url":url,
                    "icon":icon

                },
                type:"post",
                dataType:"json",
                success:function (resp) {
                    if(resp.result=="SUCCESS"){
                        menudisplay();
                    }
                    else if(resp.result=="FAILED"){
                        layer.msg(resp.message);
                    }
                    $("#menuEditModal").modal("hide");


                }

            })
        })





    });

    function menudisplay() {
        $.ajax({
            "url": "menu/get/whole/tree.json",
            "type":"post",
            "dataType":"json",
            "success":function(response){
                var result = response.result;
                if(result == "SUCCESS") {
                    console.log(response.data);
                    // 2.创建 JSON 对象用于存储对 zTree 所做的设置
                    var  setting = {
                        "view": {
                            "addDiyDom":myAddDiyDom,
                            "addHoverDom":myAddHoverDom,
                            "removeHoverDom":myRemoveHoverDom
                        },
                        "data":{
                            "key":{
                                "url":"menu类中不存在的属性名"
                            }
                        },

                    };
                    // 3.从响应体中获取用来生成树形结构的 JSON 数据
                    var zNodes = response.data;
                    // 4.初始化树形结构
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                } if
                (result == "FAILED") {
                    layer.msg(response.message);
                }
            }
        });



    }



</script>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

</div>

<%@include file="/WEB-INF/modal-menu-add.jsp"%>
<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
<%@include file="/WEB-INF/modal-menu-edit.jsp"%>

</body>
</html>
