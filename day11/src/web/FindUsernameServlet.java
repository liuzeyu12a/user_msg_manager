package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Admin;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/findUsernameServlet")
public class FindUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应消息的编码格式
        response.setContentType("application/json;charset=utf-8");
        //获取username
        String username = request.getParameter("username");
        System.out.println(username);
        //调用service判断用户名是否存在

//             *      {"userExist":true,"msg":"此用户名太受喜欢了，更换一个吧"}  //用户名重复
//             *      {"userExist":false,"msg":"此用户名可用"}                  //用户名不重复
//             */

        Map<String,Object> map = new HashMap<String,Object>();
        //调用service层查询用户名是否已经有了
        AdminService service = new AdminServiceImpl();
        Admin user = service.findUsername(username);
        if(username != null && user!= null && username.equals(user.getUsername()) && !"".equals(username)){
            map.put("userExist",true);
            map.put("msg","此用户名太受喜欢了，更换一个吧");
        }else{
            map.put("userExist",false);
            map.put("msg","此用户名可用");
        }
        //最后将map转换成json,传送给客户端浏览器
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
