package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.docs.annotations.JDocsInclude;
import org.lexize.jpk.models.JPKMessageModel;

import java.net.http.HttpResponse;

@JDocsInclude
public class JPKMiscAccessor extends JPKAbstractAccessor{
    /**
     * Default constructor of accessor
     * @param parent
     */
    public JPKMiscAccessor(JPK parent) {
        super(parent);
    }

    /**
     * Retrieves information about proxied message
     * @param messageID MessageID of proxied message, or message that sent the proxy
     * @return Message model
     * @throws Exception
     */
    public JPKMessageModel GetProxiedMessage(String messageID) throws Exception {
        String path = AccessURL + "messages/%s".formatted(messageID);

        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKMessageModel.class);
    }
}
