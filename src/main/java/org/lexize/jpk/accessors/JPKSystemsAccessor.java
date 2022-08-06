package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.docs.annotations.JDocsInclude;
import org.lexize.jpk.exceptions.JPKException;
import org.lexize.jpk.models.*;

@JDocsInclude
public class JPKSystemsAccessor extends JPKAbstractAccessor {


    /**
     * Default constructor of accessor
     * @param parent
     */
    public JPKSystemsAccessor(JPK parent) {
        super(parent);
    }

    /**
     * Accessor for GET /systems/{systemRef}
     * @param systemReference ID of system
     * @return model retrieved from PK API
     * @throws JPKException
     */
    public JPKSystemModel GetSystem(String systemReference) throws Exception {
        String path = AccessURL + "systems/%s".formatted(systemReference);

        //Sending request and getting response
        var response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKSystemModel.class);
    }

    /**
     * Accessor for GET /systems/@me.
     * Retrieves current system for this auth token
     * @return model retrieved from PK API
     * @throws JPKException
     */
    public JPKSystemModel GetSystem() throws Exception {
        return GetSystem("@me");
    }

    /**
     * Accessor for PATCH /systems/{systemRef}
     * @param systemReference ID of system
     * @param model Model object, that contains new data
     * @return edited model
     * @throws JPKException
     */
    public JPKSystemModel UpdateSystem(String systemReference, JPKSystemModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = AccessURL + "systems/%s".formatted(systemReference);

        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKSystemModel.class);
    }

    /**
     * Accessor for PATCH /systems/@me.
     * Updates current system for this auth token
     * @param model Model with new data
     * @return edited model
     * @throws JPKException
     */
    public JPKSystemModel UpdateSystem(JPKSystemModel model) throws Exception {
        return UpdateSystem("@me", model);
    }

    /**
     * Accessor for GET /systems/{systemRef}/settings
     * @param systemReference ID of system
     * @return model retrieved from PK API
     * @throws JPKException
     */
    public JPKSystemSettingsModel GetSystemSettings(String systemReference) throws Exception {
        String path = AccessURL + "systems/%s/settings".formatted(systemReference);

        //Sending request and getting response
        var response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        return ConvertOrThrowError(response, JPKSystemSettingsModel.class);
    }

    /**
     * Accessor for GET /systems/@me/settings
     * @return model
     * @throws JPKException
     */
    public JPKSystemSettingsModel GetSystemSettings() throws Exception {
        return GetSystemSettings("@me");
    }

    /**
     * Accessor for PATCH /systems/{systemRef}/settings.
     * @param model Model with new data
     * @return edited model
     * @throws JPKException
     */
    public JPKSystemSettingsModel UpdateSystemSettings(String systemReference, JPKSystemSettingsModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = AccessURL + "systems/%s/settings".formatted(systemReference);
        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKSystemSettingsModel.class);
    }
    /**
     * Accessor for PATCH /systems/@me/settings.
     * Updates settings of current system
     * @param model Model with new data
     * @return edited model
     * @throws JPKException
     */
    public JPKSystemSettingsModel UpdateSystemSettings(JPKSystemSettingsModel model) throws Exception {
        return UpdateSystemSettings("@me", model);
    }

    /**
     * Retrieves system guild settings
     * @param systemReference ID of system
     * @param guildId ID of guild
     * @return Model with settings data
     * @throws JPKException
     */
    public JPKSystemGuildSettingsModel GetSystemGuildSettings(String systemReference, String guildId) throws Exception {
        String path = AccessURL + "systems/%s/guilds/%s".formatted(systemReference, guildId);

        //Sending request and getting response
        var response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);
        return ConvertOrThrowError(response, JPKSystemGuildSettingsModel.class);
    }

    /**
     * Retrieves current system guild settings
     * @param guildId ID of guild
     * @return Model with settings data
     * @throws JPKException
     */
    public JPKSystemGuildSettingsModel GetSystemGuildSettings(String guildId) throws Exception {
        return GetSystemGuildSettings("@me", guildId);
    }

    /**
     * Updates system guild settings
     * @param systemReference ID of system
     * @param guildId ID of Guild
     * @param model Model with new data
     * @return Model with edited data
     * @throws JPKException
     */
    public JPKSystemGuildSettingsModel UpdateSystemGuildSettings(String systemReference, String guildId, JPKSystemGuildSettingsModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = AccessURL + "systems/%s/guilds/%s".formatted(systemReference, guildId);
        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKSystemGuildSettingsModel.class);
    }

    /**
     * Updates current system guild settings
     * @param guildId ID of Guild
     * @param model Model with new data
     * @return Model with edited data
     * @throws JPKException
     */
    public JPKSystemGuildSettingsModel UpdateSystemGuildSettings(String guildId, JPKSystemGuildSettingsModel model) throws Exception {
        return UpdateSystemGuildSettings("@me", guildId, model);
    }

    /**
     * Retrieves system autoproxy settings
     * @param systemReference ID of system
     * @param guild_id ID of guild
     * @return Autoproxy settings model
     * @throws JPKException
     */
    public JPKAutoproxySettingsModel GetSystemAutoproxySettings(String systemReference, String guild_id) throws Exception {
        String path = AccessURL + "systems/%s/autoproxy?guild_id=%s".formatted(systemReference, guild_id);

        //Sending request and getting response
        var response = JPK.Utils.GET(path, Parent.AuthorizationToken, Client);

        int statusCode = response.statusCode();

        return ConvertOrThrowError(response, JPKAutoproxySettingsModel.class);
    }

    /**
     * Retrieves current system autoproxy settings
     * @param guild_id ID of guild
     * @return Autoproxy settings model
     * @throws JPKException
     */
    public JPKAutoproxySettingsModel GetSystemAutoproxySettings(String guild_id) throws Exception {
        return GetSystemAutoproxySettings("@me", guild_id);
    }

    /**
     * Updates system autoproxy settings
     * @param systemReference ID of system
     * @param guild_id ID of guild
     * @param model New autoproxy settings
     * @return Updated autoproxy settings
     * @throws JPKException
     */
    public JPKAutoproxySettingsModel UpdateSystemAutoproxySettings(String systemReference, String guild_id, JPKAutoproxySettingsModel model) throws Exception {
        //Converting model to JSON
        String modelData = Json.toJson(model);

        String path = AccessURL + "systems/%s/autoproxy?guild_id=%s".formatted(systemReference, guild_id);
        var response = JPK.Utils.PATCH(path, Parent.AuthorizationToken, modelData, Client);

        return ConvertOrThrowError(response, JPKAutoproxySettingsModel.class);
    }

    /**
     * Updates current system autoproxy settings
     * @param guild_id ID of guild
     * @param model New autoproxy settings
     * @return Updated autoproxy settings
     * @throws JPKException
     */
    public JPKAutoproxySettingsModel UpdateSystemAutoproxySettings(String guild_id, JPKAutoproxySettingsModel model) throws Exception {
        return UpdateSystemAutoproxySettings("@me", guild_id, model);
    }
}
