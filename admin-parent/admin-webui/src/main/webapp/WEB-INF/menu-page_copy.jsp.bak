<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function () {
        // $(".list-group-item").click(function(){
        //     if ( $(this).find("ul") ) {
        //         $(this).toggleClass("tree-closed");
        //         if ( $(this).hasClass("tree-closed") ) {
        //             $("ul", this).hide("fast");
        //         } else {
        //             $("ul", this).show("fast");
        //         }
        //     }
        // });

        var setting = {};


        //$.fn.zTree.init($("#treeDemo"), setting); //异步访问数据

        var zNodes = [
            {name:"总节点", open:true , children:[{name: "父节点1--折叠",open:true, children: [
                        {name: "子节点11--折叠",children:[{name:"子节点111"},{name:"子节点112"}]},
                        {name: "子节点12"}
                    ]},
                    {name: "父节点2--折叠", children: [
                            {name: "子节点21--折叠",children:[{name:"子节点211"},{name:"子节点212"}]},
                            {name: "子节点22"}
                        ]},
                    {
                        name:"父节点3--无子节点", isParent:true
                    }]}


        ];

        // $.ajax({
        //     "url": "menu/get/whole/tree.json",
        //     "type":"post",
        //     "dataType":"json",
        //     "success":function(response){
        //         var result = response.result;
        //         if(result == "SUCCESS") {
        //             console.log(response.data);
        //                 // 2.创建 JSON 对象用于存储对 zTree 所做的设置
        //             var setting = {};
        //                 // 3.从响应体中获取用来生成树形结构的 JSON 数据
        //             var zNodes = response.data;
        //                 // 4.初始化树形结构
        //             $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        //         } if
        //         (result == "FAILED") {
        //             layer.msg(response.message);
        //         }
        //     }
        // });


        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });


    function myAddDiyDom(treeId,treeNode) {

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

</body>
</html>
