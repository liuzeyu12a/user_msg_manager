<%--
  Created by IntelliJ IDEA.
  User: lzy
  Date: 2020/3/4
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>regsiter</title>
    <script>
        window.onload=function (ev) {
            var img = document.getElementById("checkCode");
            img.onclick=function (ev2) {
                var time = new Date().getTime();
                img.src="/day11/checkCode?"+time;
            }

            var change = document.getElementById("change");
            change.onclick=function (ev2) {
                var time = new Date().getTime();
                img.src="/day11/checkCode?"+time;
            }
        }
    </script>

    <style>
        #cc{
            color: red;
        }
        .up{
            color: red;
        }
    </style>
</head>
<body>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/jquery-migrate-1.0.0.js"></script>
<script>
    //在页面加载完成后
    $(function () {
        //给username绑定blur事件
        $("#username").blur(function () {
            //获取username文本输入框的值
            var username = $(this).val();
            //发送ajax请求
            //期望服务器响应回的数据格式：{"userExsit":true,"msg":"此用户名太受欢迎,请更换一个"}
            //                         {"userExsit":false,"msg":"用户名可用"}
            $.get("/day11/findUsernameServlet",{username:username},function (data) {
                //alert(data)
                var span = $("#s_username");
                if(data.userExist){
                    //用户名存在
                    span.css("color","red");
                    span.html(data.msg);
                }else{
                    //用户名不存在
                    span.css("color","green");
                    span.html(data.msg);
                }
            });

        });
    });

</script>
<script>
    function check() {
        var password = document.getElementById("password").value;
        var repassword = document.getElementById("repassword").value;
        if(password == repassword){
            return true;
        }else{
            window.alert("两次密码不同，请重新输入");
            return false;
        }
    }

</script>

<form action="/day11/regsiterServlet" method="post">

    <h1>注册界面</h1>
    <table>
        <tr>
            <td> 账号：</td>
            <td><input type="text" id="username" name="username"><span id="s_username"></span></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
        <tr>
            <td>再次输入密码：</td>
            <td><input type="password" name="passpword2" id="repassword"></td>
        </tr>
        <tr>
            <td>验证码：</td>
            <td><input type="text" name="checkcode"></td>
        </tr>
        <tr>
            <td> <img src="/day11/checkCode" id="checkCode"></td>
            <td> <a href="#" id="change">看不清...换一张</a></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="注册" onclick="return check()"></td>
        </tr>
    </table>
</form>

<div>${requestScope.regsiter_error}</div>
<div>${requestScope.rgs_error}</div>

</body>
</html>
