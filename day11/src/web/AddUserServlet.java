package web;

import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //设置相应编码
        req.setCharacterEncoding("utf-8");

        User user = new User();
        Map<String, String[]> parameterMap = req.getParameterMap();
        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");
        if(currentPage == null && rows == null){
            currentPage = "1";
            rows = "5";
        }

        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //处理业务逻辑
        UserService userService = new UserServiceImpl();
        userService.add(user);
        req.getSession().setAttribute("add","添加数据成功");
        resp.sendRedirect(req.getContextPath()+"/findUserByPageServlet?currentPage="+currentPage+"&rows="+rows);

    }





    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
