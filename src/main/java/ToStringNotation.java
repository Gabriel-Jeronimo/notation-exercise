import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Determine how long annotations are to be retained - https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html
@Retention(RetentionPolicy.RUNTIME)
// Determine in which program elements the notation can be used on - https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/ElementType.html
@Target(ElementType.TYPE)
public @interface ToStringNotation {
}

// @interface is used to indicate that is gonna be a notation