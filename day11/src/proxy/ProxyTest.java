package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        //1.创建真实对象
        Lenvov lenvov = new Lenvov();

        //动态增强lenvov对象:
        /**
         * 参数：
         *  1.真实对象类加载器：lenvov.getClass().getClassLoader()
         *  2.接口数组：保证了真实对象和代理对象实现相同的接口：lenvov.getClass().getInterfaces()
         *  3.
         *
         * 可以强制转为SaleComputer是因为：代理对象和正式对象实现了相同的接口
         */
        SaleComputer proxy_sc = (SaleComputer) Proxy.newProxyInstance(lenvov.getClass().getClassLoader(), lenvov.getClass().getInterfaces(), new InvocationHandler() {
            /**
             * 代理逻辑编写的方法：每一次代理对象调用方法，都会执行这个函数
             * @param proxy: 代理对象=proxy_sc
             * @param method ：代理对象调用的方法，会被封装成Method对象
             * @param args ：代理对象调用的方法实际传递的参数
             * @return ：代理对象调用方法时的返回值
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke...被执行了");

                if(method.getName().equals("sale")){
                    System.out.println("专车接送过来买电脑...");
                    //1.增强参数(实际参数money)
                    double money = (double)args[0];
                    money = money* 0.85;
                    Object invoke = method.invoke(lenvov, money);
                    //2.增强执行的方法体
                    System.out.println("专车接送回去...");
                   //3.增强返回值
                    return invoke+"鼠标垫";
                }else{
                    Object invoke = method.invoke(lenvov, args);
                    return null;
                }

            }
        });

        //3.调用方法
        String sale = proxy_sc.sale(7000);
        System.out.println(sale);
        //proxy_sc.show();
    }
}
