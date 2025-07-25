package cc.rivalmc.papercore.commands;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.TabComplete;
import cc.hubailmn.utility.command.annotation.Command;
import cc.hubailmn.utility.interaction.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Command(name = "teleport", permission = "rivalmc.teleport", aliases = {"tp", "goto"})
public class TeleportCommand extends CommandBuilder {

    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(BasePlugin.getPrefix() + "§c Only players can use this command.");
            return true;
        }

        if (!player.hasPermission("rivalmc.teleport.admin")) {
            player.sendMessage("%s §cYou need to be on vanish mode to use this!".formatted(BasePlugin.getPrefix()));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("%s §cChoose a player to teleport to".formatted(BasePlugin.getPrefix()));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("%s §cPlayer Not Found!".formatted(BasePlugin.getPrefix()));
            return true;
        }

        Location loc = target.getLocation();
        player.teleport(loc);
        player.sendMessage("%s §7Teleported to §a%s".formatted(BasePlugin.getPrefix(), target.getName()));
        SoundUtil.play(player, SoundUtil.SoundType.TELEPORT);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        TabComplete tabComplete = new TabComplete(sender, command, label, args);
        if (args.length == 1) {
            tabComplete.add(1, Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        }
        return tabComplete.build();
    }
}
