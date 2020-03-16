<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: lzy
  Date: 2020/3/6
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>案例</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script>
        function deleteUser(id) {
            if(confirm("您确定要删除吗？")){
                location.href="${pageContext.request.contextPath}/deleteUserServlet?id="+id;
            }
        }

        //删除选择联系人，页面加载后执行
        window.onload=function (ev) {
            document.getElementById("deleteSelected").onclick=function (ev2) {
                if(confirm("您确定要删除吗？")){
                    //标识是否被选中
                    var flag = flag;
                    var names = document.getElementsByName('uid');
                    //遍历查询是否有框被选中
                    for (var i = 0;i < names.length;i++){
                        //如果有被选中
                        if(names[i].checked){
                            flag = true;
                            break;  //跳出循环，准备批量删除
                        }
                    }
                    //有条目被选中了
                    if(flag == true){
                        document.getElementById("form").submit();
                    }
                }
            }
            //点击第一次checkbox全选
            document.getElementById("checkFirst").onclick=function(ev) {
                var names = document.getElementsByName('uid');
                //遍历并且全选
                for (var i = 0;i < names.length;i++){
                    names[i].checked = this.checked;
                }
            }
        }

    </script>
</head>
<body>
<%
    List list = new ArrayList();
    List userList = (List)request.getAttribute("userList");
%>

<div class="container">
    <h1 style="text-align: center">欢迎${sessionScope.admin}，来到用户信息列表！！</h1>

    <div style="float: left;margin-top: 10px">
        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet">
            <div class="form-group">
                <label for="exampleInputName">姓名：</label>
                <input type="text" class="form-control" value="${requestScope.conditions.name[0]}" name="name" id="exampleInputName">
            </div>
            <div class="form-group">
                <label for="exampleInputAddress">籍贯：</label>
                <input type="text" class="form-control"  value="${requestScope.conditions.address[0]}" name="address" id="exampleInputAddress" >
            </div>
            <div class="form-group">
                <label for="exampleInputEmail">邮箱：</label>
                <input type="text" class="form-control" name="email"  value="${requestScope.conditions.email[0]}" id="exampleInputEmail">
            </div>
            <button type="submit" class="btn btn-default" style="margin-left: 5px">查询</button>
        </form>
    </div>

    <div style="float: right">
        <a href="add.jsp" class="btn btn-primary">添加联系人</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="deleteSelected">删除联系人</a>
    </div>


    <form id="form" action="${pageContext.request.contextPath}/deleteSelectedServlet" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="checkFirst">&nbsp;全选</th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>地址</th>
                <th>qq</th>
                <th>邮箱</th>
                <th>
                    操作
                </th>
            </tr>
            <c:forEach items="${requestScope.userByPage.list}" var="user" varStatus="s">
                <tr>
                    <th><input type="checkbox" name="uid" value="${user.id}"></th>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <th>
                        <a href= "${pageContext.request.contextPath}/findUserServlet?id=${user.id}" class="btn btn-default" name="update">修改</a>
                        <a href= "javascript:deleteUser(${user.id})" name="delete" class="btn btn-default">删除</a>

                    </th>
                </tr>
            </c:forEach>
        </table>
    </form>

    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${requestScope.userByPage.currentPage == 1}">
                    <li class="disabled">
                </c:if>
                <c:if test="${requestScope.userByPage.currentPage != 1}">
                <li>
                </c:if>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${requestScope.userByPage.currentPage-1}&rows=5&name=${requestScope.conditions.name[0]}&address=${requestScope.conditions.address[0]}&email=${requestScope.conditions.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${requestScope.userByPage.totalPage}" var="i">
                    <c:if test="${requestScope.userByPage.currentPage == i}">
                        <li class ="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${requestScope.conditions.name[0]}&address=${requestScope.conditions.address[0]}&email=${requestScope.conditions.email[0]}">${i}</a></li>
                    </c:if>

                    <c:if test="${requestScope.userByPage.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${requestScope.conditions.name[0]}&address=${requestScope.conditions.address[0]}&email=${requestScope.conditions.email[0]}">${i}</a></li>
                    </c:if>
                </c:forEach>

                <c:if test="${requestScope.userByPage.currentPage == requestScope.userByPage.totalPage}">
                    <li class="disabled">
                </c:if>
                <c:if test="${requestScope.userByPage.currentPage != requestScope.userByPage.totalPage}">
                    <li>
                </c:if>

                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${requestScope.userByPage.currentPage +1}&rows=5" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <span style="font-size: 25px;margin-left: 25px">
                   共${requestScope.userByPage.totalCount}条记录 共${requestScope.userByPage.totalPage}页
                </span>
            </ul>
        </nav>
    </div>
    <c:if test="${not empty sessionScope.add}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" >
                <span>&times;</span></button>
            <strong>${sessionScope.add}</strong>
        </div>
    </c:if>
    <c:if test="${not empty sessionScope.delete}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" >
                <span>&times;</span></button>
            <strong>${sessionScope.delete}</strong>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.update}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" >
                <span>&times;</span></button>
            <strong>${sessionScope.update}</strong>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.deleteSeleted}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" >
                <span>&times;</span></button>
            <strong>${sessionScope.deleteSeleted}</strong>
        </div>
    </c:if>
    <c:if test="${not empty sessionScope.select_error}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" >
                <span>&times;</span></button>
            <strong>${sessionScope.select_error}</strong>
        </div>
    </c:if>

</div>

<%
    session.removeAttribute("deleteSeleted");
    session.removeAttribute("update");
    session.removeAttribute("add");
    session.removeAttribute("delete");
%>
</body>
</html>
