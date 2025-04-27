package notation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GenericProxy {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new Handler<>(target)
        );
    }

    private static class Handler<T> implements InvocationHandler {
        private final T target;

        Handler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("toString".equals(method.getName()) && (args == null || args.length == 0)) {
                return ToStringHandler.generate(target);
            }

            return method.invoke(target, args);
        }
    }
}