package rpcdemo.proxy;

import rpcdemo.rpc.MyDispatcher;
import rpcdemo.rpc.protocol.MyContent;
import rpcdemo.rpc.transport.ClientFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

public class MyProxy {

    /**
     * 泛型方法的基本介绍
     * @param clazz 传入的泛型实参
     * @return T 返回值为T类型
     * 说明：
     *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    public static <T> T doProxy(Class<T> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] methodInfo = {clazz};

        MyDispatcher dispatcher = MyDispatcher.getDispatcher();

        return (T) Proxy.newProxyInstance(classLoader, methodInfo, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                // 用dispatcher判断到底是本地调用，还是远程调用
                Object obj = dispatcher.get(clazz.getName());
                if (null != obj) {
                    // 本地调用
                    System.out.println("local");
                    Class<?> clazz = obj.getClass();
                    try {
                        Method clazzMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
                        result = clazzMethod.invoke(obj, args);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 远程调用
                    // 1. 调用服务，方法，参数 -> 最后封装成message
                    String name = clazz.getName();
                    String methodName = method.getName();
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    // todo Content里面是调用服务的具体数据（方法名，入参等）
                    MyContent content = new MyContent();
                    content.setArgs(args);
                    content.setName(name);
                    content.setMethodName(methodName);
                    content.setParameterTypes(parameterTypes);

                    // todo 注册发现（zookeeper）
                    // 第一层负载面向的provider
                    CompletableFuture res = ClientFactory.transport(content);
                    // 阻塞的
                    result = res.get();
                }
                return result;
            }
        });
    }
}
