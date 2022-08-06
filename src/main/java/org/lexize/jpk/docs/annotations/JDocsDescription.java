package org.lexize.jpk.docs.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@JDocsDescription("Adds description of this class/var/etc to docs")
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JDocsDescriptions.class)
public @interface JDocsDescription {
    String value();
}
