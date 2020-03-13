package web;


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

/**
 * 注册servlet
 */
@WebServlet("/regsiterServlet")
public class RegsiterServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //1.设置编码
        resp.setCharacterEncoding("utf-8");
        //2.获取参数Map(先获取输入的参数)
        Map<String, String[]> parameterMap = req.getParameterMap();
        Admin loginUser = new Admin();
        try {
            BeanUtils.populate(loginUser,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用userDao方法
        AdminService adminService = new AdminServiceImpl();

        //获取输入的二维码
        String checkCode = req.getParameter("checkcode");

        //3.先判断验证码是否正确
        HttpSession session = req.getSession();
        String inputCode = (String)session.getAttribute("checkCode");
        //3.1删除存储在session中的验证码,如果登录成功返回，确保验证码不是同一个
        session.removeAttribute("checkCode");
        req.removeAttribute("regsiter_error");
        session.removeAttribute("success_user");
        //4.输入与显示的验证码相符
        if(checkCode != null && checkCode.equalsIgnoreCase(inputCode)){
            Admin regsiter = adminService.regsiter(loginUser.getUsername(), loginUser.getPassword());
            if(regsiter == null){ //注册失败
                req.setAttribute("regsiter_error","注册失败! 用户名密码为空或用户名重复！！");
                req.getRequestDispatcher("/regsiter.jsp").forward(req,resp);
            }else {
                //注册成功
                session.setAttribute("success_user","注册成功，请登录!");
                resp.sendRedirect(req.getContextPath()+"/login.jsp");
            }
        }else{
            //跳转到注册界面  提示验证码错误
            req.setAttribute("rgs_error","验证码错误！注册失败");
            req.getRequestDispatcher("/regsiter.jsp").forward(req,resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
