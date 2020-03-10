package web;

import domain.PageBean;
import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 分页查找findUserByPageServlet
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        //1.接收分页请求参数
        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");
        if(currentPage == null || "".equals(currentPage) || Integer.parseInt(currentPage) <= 0){
            currentPage ="1";
        }
        if(rows == null || "".equals(rows)){
            rows ="5";
        }
        //添加条件查询参数
        Map<String, String[]> conditions = req.getParameterMap();

        //2.调用service
        UserService service = new UserServiceImpl();
        PageBean<User> userByPage = service.findUserByPage(currentPage, rows,conditions);

        //3.将PageBean对象存入到request域中
        if( null != userByPage){
            req.setAttribute("userByPage",userByPage);
            req.setAttribute("conditions",conditions);
            System.out.println(userByPage);
            //转发到userListServlet刷新
            req.getRequestDispatcher("/list.jsp").forward(req,resp);
        }else{
            req.getSession().setAttribute("select_error","没有查到此用户的相关信息");
            req.getRequestDispatcher("/list.jsp").forward(req,resp);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
