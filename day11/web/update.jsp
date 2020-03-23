<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>修改联系人</title>


    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">



    <form action="${pageContext.request.contextPath}/updateUserServlet" method="post">
        <center><h3>修改联系人</h3></center>
        <%--设置隐藏域，存储id 必须写到form表的内部才能发送给后台--%>
        <input type="hidden" name="id" value="${requestScope.one.id}">
        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" name="name" id="name" placeholder="请输入姓名..." value="${requestScope.one.name}">
        </div>

        <div class="form-group">
            <label>性别：</label>
            <c:if test="${requestScope.one.gender == '男'}">
                <input type="radio" name="sex" value="男" checked="checked">男
                <input type="radio" name="sex" value="女" >女
            </c:if>
            <c:if test="${requestScope.one.gender  == '女'}">
                 <input type="radio" name="sex" value="男">男
                <input type="radio" name="sex" value="女" checked="checked">女
            </c:if>
        </div>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age" name="age" placeholder="请输入年龄" value="${requestScope.one.age}">
        </div>

        <div class="form-group">
            <label for="address">籍贯：</label>
            <select name="address" class="form-control" id="address">
                <c:if test="${requestScope.one.address  == '广东'}">
                    <option selected value="广东">广东</option>
                    <option  value="广西">广西</option>
                    <option value="湖南">湖南</option>
                </c:if>
                <c:if test="${requestScope.one.address  == '广西'}">
                    <option value="广东">广东</option>
                    <option  selected value="广西">广西</option>
                    <option value="湖南">湖南</option>
                </c:if>
                <c:if test="${requestScope.one.address  == '湖南'}">
                    <option value="广东">广东</option>
                    <option  value="广西">广西</option>
                    <option  selected value="湖南">湖南</option>
                </c:if>
            </select>
        </div>

        <div class="form-group">
            <label for="qq">qq：</label>
            <input type="text" class="form-control" id="qq" name="qq" placeholder="请输入QQ" value="${requestScope.one.qq}">
        </div>

        <div class="form-group">
            <label for="email">Email：</label>
            <input type="text" class="form-control" id="email" name="email" placeholder="年龄" value="${requestScope.one.email}">
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-default" type="submit" value="提交">
            <input class="btn btn-default" type="reset" value="重置" />
            <input class="btn btn-default" type="button" value="返回"/>
        </div>
    </form>
</div>

</body>
</html>