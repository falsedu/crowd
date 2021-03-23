function myAddDiyDom(treeId,treeNode) {


    $("#"+treeNode.tId+"_ico").removeClass();
    $("#"+treeNode.tId+"_ico").addClass(treeNode.icon);
}

function myAddHoverDom(treeId,treeNode){

    var btnGroup=treeNode.tId+"_btnGroup";

    var anchorId=treeNode.tId+"_a";
    if($("#"+btnGroup).length>=1){
        return ;
    }
    var addBtn = "<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs addBtn'  style='margin-left:10px;padding-top:0px;'  title='addSubNode'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '/></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs removeBtn'  style='margin-left:10px;padding-top:0px;'  title='deleteNode'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '/></a>";
    var editBtn = "<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs editBtn'  style='margin-left:10px;padding-top:0px;'  title='editNode'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '/></a>";
    var level = treeNode.level;

    var btnHTML = "";
    // 判断当前节点的级别
    if(level == 0) {
    // 级别为 0 时是根节点， 只能添加子节点
        btnHTML = addBtn;
    } if
    (level == 1) {
    // 级别为 1 时是分支节点， 可以添加子节点、 修改
        btnHTML = addBtn + " " + editBtn;
    // 获取当前节点的子节点数量
        var length = treeNode.children.length;
    // 如果没有子节点， 可以删除
        if(length == 0) {
            btnHTML = btnHTML + " " + removeBtn;
        }
    } if
    (level == 2) {
    // 级别为 2 时是叶子节点， 可以修改、 删除
        btnHTML = editBtn + " " + removeBtn;
    }
    // 找到附着按钮组的超链接
    var anchorId = treeNode.tId + "_a";

    $("#"+anchorId).after("<span id='"+btnGroup+"'>"+btnHTML+"</span>");

}
function myRemoveHoverDom(treeId,treeNode){
    var btnGroup=treeNode.tId+"_btnGroup";

    $("#"+btnGroup).remove();

}