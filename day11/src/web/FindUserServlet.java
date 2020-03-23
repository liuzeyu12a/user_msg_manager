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

/**
 * 查找单个用户
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取点击修改id
        String id = req.getParameter("id");

        UserService service = new UserServiceImpl();
        User one = service.findOne(Integer.parseInt(id));

        req.setAttribute("one",one);

//        User user = new User();
//        req.setAttribute("null",user);
        req.getRequestDispatcher("/update.jsp").forward(req,resp);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
