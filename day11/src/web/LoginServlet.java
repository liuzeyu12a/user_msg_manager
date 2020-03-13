package web;

import dao.AdminDao;
import dao.impl.AdminDaoImpl;
import domain.Admin;
import org.apache.commons.beanutils.BeanUtils;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码
        req.setCharacterEncoding("utf-8");

//        //2.获取请求参数
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        //3.创建User对象
//        User loginUser = new User();
//        //4.封装User对象
//        loginUser.setUsername(username);
//        loginUser.setPassword(password);

        //以上注释部分可以使用BeanUtils替换，如下
        Map<String, String[]> map = req.getParameterMap();
        Admin loginAdmin = new Admin();
        try {
            BeanUtils.populate(loginAdmin,map);  //populate封装
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用userDao方法
        AdminService adminService = new AdminServiceImpl();
        Admin admin = adminService.login(loginAdmin);

        String checkCode = req.getParameter("verifycode");
        //3.先判断验证码是否正确
        HttpSession session = req.getSession();
        String inputCode = (String)session.getAttribute("checkCode");
        //3.1删除存储在session中的验证码,如果登录成功返回，确保验证码不是同一个
        session.removeAttribute("checkCode");   //保证验证码的一次性使用
        session.removeAttribute("success_user");
        if(checkCode ==null || !checkCode.equalsIgnoreCase(inputCode)){
            //跳转到登录界面  提示验证码错误
            req.setAttribute("login_error","验证码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }

        //4再次判断账号和密码是否正确

        if(admin != null){
            //4.1如果相同 ,登录成功
            //一次会话的多次请求  使用session共享数据
            req.getSession().setAttribute("admin",admin.getUsername());
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }else{  //不相同
            //跳转到登录界面，提示账号或密码错误
            req.setAttribute("login_error","账号或密码错误!");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
