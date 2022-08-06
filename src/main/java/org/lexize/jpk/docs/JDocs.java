package org.lexize.jpk.docs;

import com.google.gson.annotations.SerializedName;
import org.lexize.jpk.docs.annotations.JDocsCategory;
import org.lexize.jpk.docs.annotations.JDocsDescription;
import org.lexize.jpk.docs.annotations.JDocsDescriptions;
import org.lexize.jpk.docs.annotations.JDocsInclude;

import java.lang.constant.ClassDesc;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class JDocs {

    @SerializedName("Classes")
    @JDocsDescription("Keypair of class reference and info about class")
    public HashMap<String, JDocsClass> Classes;
    @SerializedName("Categories")
    @JDocsDescription("Keypair of category name and string array with references of classes")
    public HashMap<String, List<String>> Categories;

    public static JDocs GenerateTreeFromRoots(Class... roots) {
        JDocs docs = new JDocs();
        docs.Classes = new HashMap<>();
        docs.Categories = new HashMap<>();
        List<String> alreadyVisited = new LinkedList<>();
        for (Class root:
             roots) {
            IterateOverClass(root, docs, alreadyVisited);
        }

        return docs;
    }

    private static JDocsMethod MethodToJDocsMethod(Method m, JDocs jDocs, List<String> alreadyVisited) {

        int mods = m.getModifiers();
        boolean addToDocs = true;
        if (m.isAnnotationPresent(JDocsInclude.class))
            addToDocs = m.getDeclaredAnnotation(JDocsInclude.class).value();
        if (!addToDocs) return null;
        JDocsMethod method = new JDocsMethod();
        method.Name = m.getName();
        method.ModifierString = Modifier.toString(mods);
        Class returnType = m.getReturnType();
        IterateOverClass(returnType, jDocs, alreadyVisited);
        method.ReturnTypeName = returnType.getSimpleName();
        if (jDocs.Classes.containsKey(returnType.getName())) {
            method.ReturnTypeReference = returnType.getName();
        }
        JDocsDescription[] descAnnotations = m.getAnnotationsByType(JDocsDescription.class);
        String[] descriptions = new String[descAnnotations.length];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = descAnnotations[i].value();
        }
        method.Descriptions = descriptions;

        List<JDocsMethod.JDocsArgument> jDocsArguments = new LinkedList<>();
        for (Parameter v:
                m.getParameters()) {
            JDocsMethod.JDocsArgument argument = new JDocsMethod.JDocsArgument();

            JDocsDescription[] argDescAnnotations = v.getAnnotationsByType(JDocsDescription.class);
            String[] argDescriptions = new String[argDescAnnotations.length];
            for (int i = 0; i < descriptions.length; i++) {
                argDescriptions[i] = argDescAnnotations[i].value();
            }

            argument.Descriptions = argDescriptions;
            argument.Name = v.getName();
            Class argType = v.getType();
            argument.TypeName = argType.getSimpleName();
            if (jDocs.Classes.containsKey(argType.getName())) {
                argument.TypeReference = argType.getName();
            }

            jDocsArguments.add(argument);
        }

        method.Arguments = jDocsArguments.toArray(new JDocsMethod.JDocsArgument[jDocsArguments.size()]);


        List<JDocsMethod.JDocsException> exceptions = new LinkedList<>();

        for (Class cl:
                m.getExceptionTypes()) {
            IterateOverClass(cl, jDocs, alreadyVisited);
            JDocsMethod.JDocsException exception = new JDocsMethod.JDocsException();
            String name = cl.getSimpleName();
            String ref = cl.getName();
            exception.TypeName = name;
            if (jDocs.Classes.containsKey(ref)) {
                exception.TypeReference = ref;
            }
            exceptions.add(exception);
        }
        method.ThrowableExceptions = exceptions.toArray(new JDocsMethod.JDocsException[exceptions.size()]);


        return method;
    }

    private static void IterateOverClass(Class root, JDocs jDocs, List<String> alreadyVisited) {
        JDocsClass docsClass = new JDocsClass();
        int mods = root.getModifiers();
        docsClass.ModifierString = Modifier.toString(mods);
        docsClass.ClassName = root.getSimpleName();
        docsClass.ClassRef = root.getName();
        if(jDocs.Classes.containsKey(docsClass.ClassRef) || alreadyVisited.stream().anyMatch((s) -> s == docsClass.ClassRef)) return;

        boolean addToDocs;
        if (root.isAnnotationPresent(JDocsInclude.class))
            addToDocs = ((JDocsInclude) root.getDeclaredAnnotation(JDocsInclude.class)).value();
        else if (!Modifier.isPrivate(mods))
            addToDocs = root.isAnnotationPresent(JDocsDescription.class) | root.isAnnotationPresent(JDocsCategory.class);
        else
            addToDocs = false;
        alreadyVisited.add(docsClass.ClassRef);
        if (addToDocs) {
            JDocsDescription[] descAnnotations = (JDocsDescription[]) root.getAnnotationsByType(JDocsDescription.class);
            String[] descriptions = new String[descAnnotations.length];
            for (int i = 0; i < descriptions.length; i++) {
                descriptions[i] = descAnnotations[i].value();
            }
            docsClass.Descriptions = descriptions;



            List<JDocsMethod> jDocsMethods = new LinkedList<>();

            for (Method m:
                    root.getDeclaredMethods()) {
                JDocsMethod me = MethodToJDocsMethod(m, jDocs, alreadyVisited);

                if (me != null) jDocsMethods.add(me);
            }

            docsClass.Methods = jDocsMethods.toArray(new JDocsMethod[jDocsMethods.size()]);

            jDocs.Classes.put(docsClass.ClassRef, docsClass);

            for (Class childClass:
                    root.getDeclaredClasses()) {
                IterateOverClass(childClass, jDocs, alreadyVisited);
            }
        }
    }
}
