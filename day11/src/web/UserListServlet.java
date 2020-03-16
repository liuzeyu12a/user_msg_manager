package web;

import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 项目后期没用了
 */
@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.调用Service去查询
        UserService service = new UserServiceImpl();
        List<User> users = service.findAll();
        //2.讲查询到的user共享到request域中
        req.setAttribute("userList",users);
        req.getRequestDispatcher("/list.jsp").forward(req,resp);

        req.getSession().removeAttribute("add");
        req.getSession().removeAttribute("delete");
    }
}
