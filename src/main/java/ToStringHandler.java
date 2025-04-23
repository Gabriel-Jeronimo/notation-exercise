import java.lang.reflect.Field;

public class ToStringHandler {
    public static String generate(Object obj) {
        Class<?> targetClass = obj.getClass();

        System.out.println("Hello, world");

        ToStringNotation notation = targetClass.getAnnotation(ToStringNotation.class);
        if (notation == null) {
            return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
        }

        StringBuilder output = new StringBuilder(targetClass.getName()).append(" {");

        boolean firstField = true;
        for (Field field : targetClass.getDeclaredFields()) {
            if (!firstField) {
                output.append(", ");
            } else {
                firstField = false;
            }

            field.setAccessible(true);

            try {
                output.append(field.getName()).append("=").append(field.get(obj));
            } catch (IllegalAccessException e) {
                output.append(field.getName()).append("=[access denied]");
            }
        }

        output.append("}");
        return output.toString();
    }
}
