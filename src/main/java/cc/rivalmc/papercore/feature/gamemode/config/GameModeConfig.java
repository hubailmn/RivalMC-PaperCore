package cc.rivalmc.papercore.feature.gamemode.config;

import cc.hubailmn.utility.config.ConfigBuilder;
import cc.hubailmn.utility.config.annotation.LoadConfig;
import cc.hubailmn.utility.interaction.CSend;
import lombok.Getter;
import org.bukkit.GameMode;

@LoadConfig(
        path = "feature/GameMode.yml"
)
@Getter
public class GameModeConfig extends ConfigBuilder {

    private final String GAMEMODE_PREFIX = "GameMode.";

    private boolean switchModeEnabled;
    private GameMode switchToGameMode;

    @Override
    public void reloadCache() {
        this.switchModeEnabled = getConfig().getBoolean(GAMEMODE_PREFIX + "switch-on-join.enabled");

        GameMode gameMode = GameMode.SURVIVAL;
        try {
            String gameModeString = getConfig().getString(GAMEMODE_PREFIX + "switch-on-join.gamemode");

            if (gameModeString == null) {
                CSend.error("GameMode.yml is missing gamemode value. Defaulting to SURVIVAL.");
                return;
            }
            gameModeString = gameModeString.toUpperCase();
            gameMode = GameMode.valueOf(gameModeString);
        } catch (Exception ignored) {
            CSend.error("Invalid game mode value in GameMode.yml. Defaulting to SURVIVAL.");
        }

        this.switchToGameMode = gameMode;
    }

}
