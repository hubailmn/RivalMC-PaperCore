package cc.rivalmc.papercore.commands;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.TabComplete;
import cc.hubailmn.utility.command.annotation.Command;
import cc.hubailmn.utility.interaction.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Command(name = "heal", permission = "rivalmc.heal", aliases = {"feed"})
public class HealCommand extends CommandBuilder {

    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(BasePlugin.getPrefix() + "§c Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            heal(player);
            player.sendMessage("%s §7Healed §a%s §c20 ♥".formatted(BasePlugin.getPrefix(), player.getName()));
            SoundUtil.play(player, SoundUtil.SoundType.EXPERIENCE_ORB);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("%s §cPlayer Not Found".formatted(BasePlugin.getPrefix()));
            return true;
        }

        heal(target);
        target.sendMessage("%s §7Healed By §a%s §c20 ♥".formatted(BasePlugin.getPrefix(), player.getName()));
        SoundUtil.play(target, SoundUtil.SoundType.EXPERIENCE_ORB);
        return true;
    }

    public void heal(Player player) {
        AttributeInstance maxHealthAttr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttr != null) {
            player.setHealth(maxHealthAttr.getValue());
            player.setSaturation(20);
        }
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
