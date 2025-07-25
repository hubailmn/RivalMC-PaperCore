package cc.rivalmc.papercore.feature.grant.command;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.TabComplete;
import cc.hubailmn.utility.command.annotation.Command;
import cc.hubailmn.utility.interaction.SoundUtil;
import cc.rivalmc.papercore.CorePlugin;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import cc.rivalmc.papercore.feature.grant.menu.GrantMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Command(name = "grant", permission = "rivalmc.grant")
public class GrantCommand extends CommandBuilder {

    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length == 0) {
            player.sendMessage("%s §cPlease Choose a player".formatted(BasePlugin.getPrefix()));
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
        GrantManager.selectGrant(player, offlinePlayer);
            GrantMenu.open(player);
        SoundUtil.play(player, SoundUtil.SoundType.OPEN_MENU);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        TabComplete tabComplete = new TabComplete(sender, command, label, args);
        if(args.length == 1) {
            tabComplete.add(1, Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        }
        return tabComplete.build();
    }
}
