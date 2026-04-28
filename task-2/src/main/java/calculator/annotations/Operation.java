package calculator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // Важно: чтобы рефлексия видела её при запуске
public @interface Operation {
    String name();
    int arity() default 0;
}