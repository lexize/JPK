package org.lexize.jpk.docs.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JDocsDescription("Is this class/var/etd should be in docs?")
@JDocsDescription("Completely overwrites visibility settings for element.")
@JDocsDescription("False value will make element not be contained in docs, even if there @JDocsDescription or @JDocsCategory annotations.")
@JDocsDescription("True will make element always be contained in docs, even if it is private, even without description and category annotation.")
public @interface JDocsInclude {
    public boolean value() default true;
}
