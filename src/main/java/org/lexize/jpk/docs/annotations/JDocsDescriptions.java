package org.lexize.jpk.docs.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JDocsDescriptions {
    JDocsDescription[] value();
}
