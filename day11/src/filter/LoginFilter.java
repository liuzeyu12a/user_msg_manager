package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        //System.out.println(uri);
        //System.out.println(((HttpServletRequest) req).getRequestURL());

        //登录的过滤掉
        if(uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/regsiter.jsp") ||
                uri.contains("/js/") || uri.contains("/css/") || uri.contains("/fonts/") || uri.contains("/checkCode")
                ||  uri.contains("/findUsernameServlet")){ //放行
            chain.doFilter(req,resp);  //放行
        }else{
            Object user = ((HttpServletRequest) req).getSession().getAttribute("admin");  //获取是否登录的user
            //判断有没有登录
            if(user == null){ //没有登录
                req.setAttribute("login_error","您尚未登录，请登录");
                req.getRequestDispatcher("/login.jsp").forward(request,resp);
            }else{
                //已经登录
                chain.doFilter(req,resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
