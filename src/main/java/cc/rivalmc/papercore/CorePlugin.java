package cc.rivalmc.papercore;

import cc.hubailmn.utility.BasePlugin;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public final class CorePlugin extends BasePlugin {
    @Getter
    private static CorePlugin core;
    private LuckPerms luckPerms;
    private GrantManager grantManager;

    @Override
    protected void preEnable() {
        setSendPluginUsage(false);
        setBasePackage(CorePlugin.class);
        setScanFullPackage(true);

        setDatabase(true);
        setDiscord(true);

        setForceDebug(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        registerHandlers();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerHandlers() {
        core = this;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
        this.grantManager = new GrantManager();
    }
}
