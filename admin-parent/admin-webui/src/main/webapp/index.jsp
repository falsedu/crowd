<%--
  Created by IntelliJ IDEA.
  User: 22945
  Date: 2020-8-11
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%--%>
<%--    String basePath = request.getScheme() + "://" +--%>
<%--            request.getServerName() + ":" + request.getServerPort() +--%>
<%--            request.getContextPath() + "/";--%>
<%--%>--%>

<html>
<head>
<%--    <base href="<%=basePath%>">--%>
    <title>Title</title>
</head>
<body>
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>

<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>

<script>

    $(function () {
        $("#sendArrayBtn1").click(function () {
            $.ajax({
                url:"send/array1.html",
                type:"post",
                data:{
                    "array":[1,2,3]
                },
                dataType:"text",
                success:function (response) {
                    alert(response);
                },
                error:function (response) {
                    alert(response);
                }
            })
        })

        $("#sendArrayBtn2").click(function () {
            $.ajax({
                url:"send/array2.html",
                type:"post",
                data:{

                    "array[0]":1,
                    "array[1]":2,
                    "array[2]":3
                },
                dataType:"text",
                success:function (response) {
                    alert(response);
                },
                error:function (response) {
                    alert(response);
                }
            })
        })
        $("#sendArrayBtn3").click(function () {

            var array=[1,2,3];
            console.log(array.length);
            var requestBody=JSON.stringify(array);
            console.log(requestBody.length);
            $.ajax({
                url:"send/array3.html",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=UTF-8",
                dataType:"text",
                success:function (response) {
                    alert(response);
                },
                error:function (response) {
                    alert(response);
                }
            })
        })
        $("#sendComplexBtn4").click(function () {

            var stundet={
                "id":1,
                "name":"?????????",
                "age":25,
                "address":{
                    "province":"??????",
                    "city":"??????",
                    "street":"dt"
                },
                "subjects":[
                    {"subjectName":"java","score":99},
                    {"subjectName":"??????","score":100}
                ],
                "map":{
                    "k1":"v1",
                    "k2":"v2"
                }

            }

            var requestBody=JSON.stringify(stundet);

            $.ajax({
                url:"send/complexSubject.html",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=UTF-8",
                dataType:"text",
                success:function (response) {
                    alert(response);
                },
                error:function (response) {
                    alert(response);
                }
            })
        })
        $("#sendComplexBtn5").click(function () {

            var stundet={
                "id":1,
                "name":"?????????",
                "age":25,
                "address":{
                    "province":"??????",
                    "city":"??????",
                    "street":"dt"
                },
                "subjects":[
                    {"subjectName":"java","score":99},
                    {"subjectName":"??????","score":100}
                ],
                "map":{
                    "k1":"v1",
                    "k2":"v2"
                }

            }

            var requestBody=JSON.stringify(stundet);

            $.ajax({
                url:"send/complexSubject2.json",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=UTF-8",
                dataType:"text",
                success:function (response) {
                    // alert(response);
                    console.log(response);
                },
                error:function (response) {
                    alert(response);
                }
            })
        })
        $("#ifError").click(function () {


            window.location.href="send/error.html"
        })
        $("#ifError2").click(function () {



            window.location.href="send/success.html";

        })

        $("#btn5").click(function () {
            // alert("aaaa");
            layer.msg("aaaaaaa");
        })




    })
</script>

<a href="test/ssm.html">??????ssm??????</a>
<a href="test/ssm1.html">??????ssm??????</a>
<br><button  id="sendArrayBtn1">send[1,2,3] One</button>
<br><button  id="sendArrayBtn2">send[1,2,3] Two</button>
<br><button  id="sendArrayBtn3">send[1,2,3] Three</button>
<br><button  id="sendComplexBtn4">send Object</button>
<br><button  id="sendComplexBtn5">send Object2</button>
<br>??????xml???????????????
<br><button  id="ifError">???????????????</button>
<br><button  id="ifError2">???????????????</button>
<br>
???????????????????????????
<br><a href="send/useAnno.html">???????????????????????????</a>
<%--<form action="test/ssm.html" >--%>

<%--    <input type="submit" value="??????">--%>
<%--</form>--%>
<br>
new

<br/>
<br/>
<button id="btn5" >????????????</button>

<a href="admin/to/login/page.html">?????????????????????</a>

</body>
</html>
