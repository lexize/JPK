package org.lexize.jpk.docs;

import com.google.gson.annotations.SerializedName;

public class JDocsMethod extends JDocsAbstractElement {
    @SerializedName("ModifierString")
    public String ModifierString;
    @SerializedName("Name")
    public String Name;
    @SerializedName("TypeName")
    public String ReturnTypeName;
    @SerializedName("TypeReference")
    public String ReturnTypeReference;
    @SerializedName("Arguments")
    public JDocsArgument[] Arguments;
    @SerializedName("TypeParameters")
    public String[] TypeParameters;
    @SerializedName("ThrowableExceptions")
    public JDocsException[] ThrowableExceptions;

    public static class JDocsArgument extends JDocsAbstractElement {
        @SerializedName("Name")
        public String Name;
        @SerializedName("TypeName")
        public String TypeName;
        @SerializedName("TypeReference")
        public String TypeReference;
    }
    public static class JDocsException {
        @SerializedName("TypeName")
        public String TypeName;
        @SerializedName("TypeReference")
        public String TypeReference;
    }
}
