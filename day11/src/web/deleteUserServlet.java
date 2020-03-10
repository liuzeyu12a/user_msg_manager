package web;



import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除单个用户的信息
 */
@WebServlet("/deleteUserServlet")
public class deleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取删除的id
        String id = req.getParameter("id");

        UserService service = new UserServiceImpl();
        service.delete(id);
        req.getSession().setAttribute("delete","删除成功！！");
       resp.sendRedirect(req.getContextPath()+"/userListServlet");
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
