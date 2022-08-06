package org.lexize.jpk.docs;

import com.google.gson.annotations.SerializedName;

public class JDocsClass extends JDocsAbstractElement {

    @SerializedName("ModifierString")
    public String ModifierString;
    @SerializedName("Name")
    public String ClassName;
    @SerializedName("Reference")
    public String ClassRef;
    public JDocsMethod[] Methods;
    @SerializedName("TypeParameters")
    public JDocsFields[] Fields;

}
