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
 * 删除选中deleteSelectedServlet
 */
@WebServlet("/deleteSelectedServlet")
public class deleteSelectedServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码
        req.setCharacterEncoding("utf-8");

        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");
        if(currentPage == null && rows == null){
            currentPage = "1";
            rows = "5";
        }

        //获取删除选中的的id
        String[] uids = req.getParameterValues("uid");

        UserService service = new UserServiceImpl();
        service.deleteSelected(uids);

        resp.sendRedirect(req.getContextPath()+"/findUserByPageServlet?currentPage="+currentPage+"&rows="+rows);

        req.getSession().setAttribute("deleteSeleted","批量删除完成！！");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
