package cc.rivalmc.papercore;

import cc.hubailmn.utility.BasePlugin;

public final class CorePlugin extends BasePlugin {

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
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
