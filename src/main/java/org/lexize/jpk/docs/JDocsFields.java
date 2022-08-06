package org.lexize.jpk.docs;

import com.google.gson.annotations.SerializedName;

public class JDocsFields extends JDocsAbstractElement {
    @SerializedName("ModifierString")
    public String ModifierString;
    @SerializedName("Name")
    public String Name;
    @SerializedName("TypeName")
    public String TypeName;
    @SerializedName("Reference")
    public String Reference;
}
