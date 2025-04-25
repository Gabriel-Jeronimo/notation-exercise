import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import javassist.*;

class ProxyAgent {
    // Entry point for Java Agents
    // Explain what is a Java agent
    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer(new AutoStringTransformer());
    }

    static class AutoStringTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            // Skip JDK and agent classes
            if (className.startsWith("java/") ||
                    className.startsWith("javax/") ||
                    className.startsWith("sun/") ||
                    className.startsWith("com/sun/") ||
                    className.startsWith("ToStringNotation")) {
                return null;
            }

            // Explain a lil bit of Javaassisst
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

                Object[] annotations = cc.getAnnotations();
                boolean hasAnnotations = false;

                for (Object annotation : annotations) {
                    if (annotation.toString().contains("AutoString")) {
                        hasAnnotations = true;
                        break;
                    }
                }

                if (!hasAnnotations) {
                    return null;
                }

                boolean hasToString = false;
                for (CtMethod method : cc.getDeclaredMethods()) {
                    if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
                        hasToString = true;
                        break;
                    }
                }

                if (!hasToString) {
                    CtMethod toStringMethod = new CtMethod(cp.get("java.lang.String"), "toString", new CtClass[0], cc);

                    toStringMethod.setBody("{ return ToStringHandler.generateToString(this); }");
                    cc.addMethod(toStringMethod);
                }

                return cc.toBytecode();
            } catch (IOException | ClassNotFoundException | NotFoundException | CannotCompileException e) {
                e.printStackTrace();
            }

            return null;

        }
    }
}