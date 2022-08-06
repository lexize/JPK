package org.lexize.jpk.docs.annotations;

import java.lang.annotation.*;

@JDocsDescription("Category of class/method/field/etc")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JDocsCategory {
    String value();
}
