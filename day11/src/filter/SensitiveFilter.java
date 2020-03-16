package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用方法：
 * 登录后，访问/sensitiveServlet?name=liu&value=你是很傻逼
 * 来测试敏感词汇的过滤
 * 还可以用于过滤getParameterMap和getParameterValues
 */
@WebFilter("/*")
public class SensitiveFilter implements Filter {
    //存储敏感词汇
    private List<String> list = new ArrayList<String>();
    public void destroy() {
    }
    //创建代理对象，增强getParamter方法
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        ServletRequest servletRequest= (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParamter方法
                //1.判断是否为getParamter方法
                if(method.getName().equals("getParameter")){  //是
                    //增强返回值
                    String value = (String)method.invoke(req,args);  //执行request.getParameter()的返回值
                    if(value != null){
                        for (String str : list) {
                            if(value.contains(str)){
                                value = value.replaceAll(str,"***");
                            }
                        }
                    }
                    return value;
                }
                return method.invoke(req,args);
            }
        });

        //放行
        chain.doFilter(servletRequest,resp);
    }

    public void init(FilterConfig config) throws ServletException {

        try {
            //1.获取敏感词汇真实路径
            ServletContext context = config.getServletContext();
            String realPath = context.getRealPath("/WEB-INF/classes/sensitive.txt");
            //2.读取文件
            BufferedReader br = new BufferedReader(new FileReader(realPath));  //默认编码gbk
            //3.将每一行添加到list集合中
            String line = null;
            while ((line = br.readLine())!= null){
                list.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(list);
    }

}
