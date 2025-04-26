package notation;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

public class NotationProxyFactory {

    public static <T> T createProxy(T target) {
        try {
            Class<?> clazz = target.getClass();

            if (!clazz.isAnnotationPresent(ToStringNotation.class)) {
                throw new IllegalArgumentException("Class not annotated with @ToStringNotation");
            }

            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setSuperclass(clazz);

            Class<?> proxyClass = proxyFactory.createClass();
            MethodHandler handler = (self, thisMethod, proceed, args) -> {
                if ("toString".equals(thisMethod.getName()) && (args == null || args.length == 0)) {
                    return ToStringHandler.generate(target);
                }
                return thisMethod.invoke(target, args);
            };

            T proxyInstance = (T) proxyClass.getDeclaredConstructor().newInstance();
            ((javassist.util.proxy.Proxy) proxyInstance).setHandler(handler);

            return proxyInstance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create proxy for class: " + target.getClass().getName(), e);
        }
    }
}
