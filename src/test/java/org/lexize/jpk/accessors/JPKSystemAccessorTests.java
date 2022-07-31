package org.lexize.jpk.accessors;

import org.lexize.jpk.JPK;
import org.lexize.jpk.enums.JPKAutoproxyModeEnum;
import org.lexize.jpk.exceptions.JPKException;
import org.lexize.jpk.models.JPKAutoproxySettingsModel;
import org.lexize.jpk.models.JPKSystemGuildSettingsModel;
import org.lexize.jpk.models.JPKSystemModel;
import org.lexize.jpk.models.JPKSystemSettingsModel;

import java.util.concurrent.TimeUnit;

public class JPKSystemAccessorTests {

    private JPKSystemsAccessor accessor;
    public JPKSystemAccessorTests(JPK jpk) {
        accessor = jpk.getSystemsAccessor();
    }

    private void Log(String str) {
        System.out.println(str);
    }

    public void Tests() throws Exception {
        JPKSystemModel model;
        try {
            model = accessor.GetSystem();
        } catch (Exception e) {
            Log("Failed to get system:");
            throw e;
        }
        Log("System object successfully received. Name of system: %s".formatted(model.Name));
        Log("Updating system");
        model.Name = "hui";
        try {
            model = accessor.UpdateSystem(model);
        } catch (Exception e) {
            Log("Failed to update system:");
            throw e;
        }
        Log("System successfully updated. New name of system: %s".formatted(model.Name));
        Log("Trying to get system settings");
        JPKSystemSettingsModel settingsModel;
        try {
            settingsModel = accessor.GetSystemSettings();
        } catch (Exception e) {
            Log("Failed to get system settings:");
            throw e;
        }
        Log("Settings successfully received. Timezone of system: %s".formatted(settingsModel.Timezone));
        Log("Updating system settings");
        settingsModel.ShowPrivateInfo = false;
        try {
            settingsModel = accessor.UpdateSystemSettings(settingsModel);
        } catch (Exception e) {
            Log("Failed to update system settings:");
            throw e;
        }
        Log("Settings successfully update. ShowPrivateInfo of system: %s".formatted(settingsModel.ShowPrivateInfo));
        JPKSystemGuildSettingsModel guildSettingsModel;
        Log("Trying to get guild system settings");
        try {
            guildSettingsModel = accessor.GetSystemGuildSettings("1002883501953126450");
        }
        catch (Exception e) {
            Log("Failed to get guild settings:");
            throw e;
        }
        Log("Settings successfully received. Guild ID: %s".formatted(guildSettingsModel.GuildId));
        Log("Updating guild system settings");
        guildSettingsModel.ProxyingEnabled = false;
        try {
            guildSettingsModel = accessor.UpdateSystemGuildSettings("1002883501953126450", guildSettingsModel);
        }
        catch (Exception e) {
            Log("Failed to update guild system settings:");
            throw e;
        }
        Log("Settings successfully updated. Proxying: %s".formatted(guildSettingsModel.ProxyingEnabled));
        Thread.sleep(10000);
        JPKAutoproxySettingsModel autoproxySettingsModel;
        Log("Trying to get autoproxy settings for guild");
        try {
            autoproxySettingsModel = accessor.GetSystemAutoproxySettings("1002883501953126450");
        }
        catch (Exception e) {
            Log("Failed to get autoproxy settings:");
            throw e;
        }
        Log("Settings successfully retrieved. Proxy mode: %s".formatted(autoproxySettingsModel.AutoproxyMode));
        autoproxySettingsModel.AutoproxyMode = JPKAutoproxyModeEnum.Front;
        Log("Updating autoproxy settings");
        try {
            autoproxySettingsModel = accessor.UpdateSystemAutoproxySettings("1002883501953126450", autoproxySettingsModel);
        }
        catch (Exception e) {
            Log("Failed to update autoproxy settings:");
            throw e;
        }
        Log("Autoproxy settings successfully updated. Proxy mode: %s".formatted(autoproxySettingsModel.AutoproxyMode));
        Log("All tests are successful.");
    }
}
