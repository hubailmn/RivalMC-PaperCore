package cc.rivalmc.papercore.feature.gamemode.listener;

import cc.hubailmn.utility.annotation.RegisterListener;
import cc.hubailmn.utility.config.ConfigUtil;
import cc.rivalmc.papercore.feature.gamemode.config.GameModeConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RegisterListener
public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final var gameModeConfig = ConfigUtil.getConfig(GameModeConfig.class);
        if (!gameModeConfig.isSwitchModeEnabled()) return;

        final var player = e.getPlayer();
        if (player.hasPermission("rivalmc.gamemode.switchBypass")) return;
        player.setGameMode(gameModeConfig.getSwitchToGameMode());
    }
}
