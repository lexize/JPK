package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.docs.annotations.JDocsInclude;
import org.lexize.jpk.models.JPKMemberModel;
import org.lexize.jpk.models.JPKSwitchModel;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

import org.lexize.jpk.exceptions.JPKException;

@JDocsInclude
public class JPKSwitchesAccessor extends JPKAbstractAccessor {
    /**
     * Default constructor of accessor
     *
     * @param parent
     */
    public JPKSwitchesAccessor(JPK parent) {
        super(parent);
    }

    /**
     * Retrieves switches in system
     * @param systemReference System ID
     * @param before Date to get latest switch from
     * @param limit Number of switches to get (100 if null)
     * @return Switch object containing a list of IDs
     * @throws JPKException
     */
    public JPKSwitchModel GetSystemSwitches(String systemReference, Instant before, Integer limit) throws Exception {
        if (limit == null) limit = 100;
        String path = AccessURL + "systems/%s/switches?before=%s&limit=%s".formatted(systemReference, before, limit);
        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKSwitchModel.class);
    }

    /**
     * Retrieves switches in system
     * @param before Date to get latest switch from
     * @param limit Number of switches to get (100 if null)
     * @return Switch object containing a list of IDs
     * @throws JPKException
     */
    public JPKSwitchModel GetSystemSwitches(Instant before, Integer limit) throws Exception {
        if (limit == null) limit = 100;
        return GetSystemSwitches("@me", before, limit);
    }


    /**
     *  Retrieves current system fronters
     * @param systemReference System ID
     * @return Switch object with list of member objects
     * @throws JPKException
     */
    public JPKSwitchModel GetCurrentSystemFronters(String systemReference) throws Exception {
        String path = AccessURL + "systems/%s/fronters".formatted(systemReference);
        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKSwitchModel.class);
    }

    /**
     *  Retrieves current system fronters
     * @return Switch object with list of member objects
     * @throws JPKException
     */
    public JPKSwitchModel GetCurrentSystemFronters() throws Exception {
        return GetCurrentSystemFronters("@me");
    }


    /**
     * Creates switch for system
     * @param systemReference
     * @param model
     * @return Created switch with list of member objects.
     * @throws JPKException
     */
    public JPKSwitchModel CreateSwitch(String systemReference, JPKSwitchModel model) throws Exception {
        String modelData = Json.toJson(model);
        String path = AccessURL + "systems/%s/switches".formatted(systemReference);

        HttpResponse<String> response = JPK.Utils.POST(path, Parent.AuthorizationToken, modelData, Client);
        return ConvertOrThrowError(response, JPKSwitchModel.class);
    }

    /**
     * Creates switch for system
     * @param model
     * @return Created switch with list of member objects.
     * @throws JPKException
     */
    public JPKSwitchModel CreateSwitch(JPKSwitchModel model) throws Exception {
        return CreateSwitch("@me", model);
    }

    /**
     * Retrieves switch with specified ID
     * @param systemReference System ID
     * @param switchReference Switch ID
     * @return Switch object with list of member objects.
     * @throws Exception
     */
    public JPKSwitchModel GetSwitch(String systemReference, String switchReference) throws Exception {
        String path = AccessURL + "systems/%s/switches/%s".formatted(systemReference,switchReference);
        HttpResponse<String> response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKSwitchModel.class);
    }

    /**
     * Retrieves switch with specified ID
     * @param switchReference Switch ID
     * @return Switch object with list of member objects.
     * @throws Exception
     */
    public JPKSwitchModel GetSwitch(String switchReference) throws Exception {
        return GetSwitch("@me", switchReference);
    }

    /**
     * Updates switch with specified ID
     * @param systemReference System ID
     * @param switchReference Switch ID
     * @param model Switch object
     * @return Updated switch object
     * @throws JPKException
     */
    public JPKSwitchModel UpdateSwitch(String systemReference, String switchReference, JPKSwitchModel model) throws Exception {
        String modelData = Json.toJson(model);
        String path = AccessURL + "systems/%s/switches/%s".formatted(systemReference, switchReference);

        HttpResponse<String> response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKSwitchModel.class);
    }

    /**
     * Updates switch with specified ID
     * @param switchReference Switch ID
     * @param model Switch object
     * @return Updated switch object
     * @throws JPKException
     */
    public JPKSwitchModel UpdateSwitch(String switchReference, JPKSwitchModel model) throws Exception {
        return UpdateSwitch("@me", switchReference, model);
    }

    /**
     * Updates members of switch
     * @param systemReference System ID
     * @param switchReference Switch ID
     * @param memberIDs List of member IDs
     * @return Updated switch
     * @throws JPKException
     */
    public JPKSwitchModel UpdateSwitchMembers(String systemReference, String switchReference, String... memberIDs) throws Exception {
        String data = Json.toJson(memberIDs);
        String path = AccessURL + "systems/%s/switches/%s/members".formatted(systemReference, switchReference);
        HttpResponse<String> response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, data, Client);

        return ConvertOrThrowError(response, JPKSwitchModel.class);
    }

    /**
     * Updates members of switch
     * @param systemReference System ID
     * @param switchReference Switch ID
     * @param members List of member models
     * @return Updated switch
     * @throws JPKException
     */
    public JPKSwitchModel UpdateSwitchMembers(String systemReference, String switchReference, JPKMemberModel... members) throws Exception {
        String[] ids = new String[members.length];

        for (int i = 0; i < members.length; i++) {
            ids[i] = members[i].Id;
        }

        return UpdateSwitchMembers(systemReference, switchReference, ids);
    }

    /**
     * Updates members of switch
     * @param switchReference Switch ID
     * @param memberIDs List of member IDs
     * @return Updated switch
     * @throws JPKException
     */
    public JPKSwitchModel UpdateSwitchMembers(String switchReference, String... memberIDs)  throws Exception {
        return UpdateSwitchMembers("@me", switchReference, memberIDs);
    }

    /**
     * Updates members of switch
     * @param switchReference Switch ID
     * @param members List of member models
     * @return Updated switch
     * @throws JPKException
     */
    public JPKSwitchModel UpdateSwitchMembers(String switchReference, JPKMemberModel members) throws Exception {
        return UpdateSwitchMembers("@me", switchReference, members);
    }

    /**
     * Deletes switch
     * @param systemReference System ID
     * @param switchReference Switch ID
     * @return True, if successful
     * @throws JPKException
     */
    public boolean DeleteSwitch(String systemReference, String switchReference) throws Exception {
        String path = AccessURL + "systems/%s/switches/%s".formatted(systemReference, switchReference);

        HttpResponse<String> response = JPK.Utils.DELETE(path, Parent.AuthorizationToken, Client);

        return IsCode(response, 204);
    }

    /**
     * Deletes switch
     * @param switchReference Switch ID
     * @return True, if successful
     * @throws JPKException
     */
    public boolean DeleteSwitch(String switchReference) throws Exception {
        return DeleteSwitch("@me", switchReference);
    }
}
