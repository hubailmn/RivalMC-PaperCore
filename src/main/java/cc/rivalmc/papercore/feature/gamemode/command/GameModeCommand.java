package cc.rivalmc.papercore.feature.gamemode.command;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.TabComplete;
import cc.hubailmn.utility.command.annotation.Command;
import cc.hubailmn.utility.interaction.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Command(
        name = "gamemode",
        permission = "rivalmc.gamemode",
        aliases = {"gma", "gms", "gmc", "gmsp", "creative", "survival", "adventure", "spectator"}
)
public class GameModeCommand extends CommandBuilder {

    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(BasePlugin.getPrefix() + "§c Only players can use this command.");
            return true;
        }

        GameMode mode = resolveGameMode(label, args);
        if (mode == null) {
            player.sendMessage(BasePlugin.getPrefix() + "§c Invalid gamemode. Valid: survival, creative, adventure, spectator.");
            SoundUtil.play(player, SoundUtil.SoundType.DENY);
            return true;
        }

        String gamemodePermission = "rivalmc.gamemode." + mode.name().toLowerCase();
        if (!player.hasPermission(gamemodePermission)) {
            player.sendMessage(BasePlugin.getPrefix() + "§c You don't have permission to use " + mode.name().toLowerCase() + " mode.");
            SoundUtil.play(player, SoundUtil.SoundType.DENY);
            return true;
        }

        Player target = player;

        if (isDirectGameModeCommand(label)) {
            if (args.length > 0) {
                if (!player.hasPermission("rivalmc.gamemode.others")) {
                    player.sendMessage(BasePlugin.getPrefix() + "§c You don't have permission to change others' gamemodes.");
                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                    return true;
                }

                target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(BasePlugin.getPrefix() + "§c Player not found: " + args[0]);
                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                    return true;
                }
            }
        } else {
            if (args.length > 1) {
                if (!player.hasPermission("rivalmc.gamemode.others")) {
                    player.sendMessage(BasePlugin.getPrefix() + "§c You don't have permission to change others' gamemodes.");
                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                    return true;
                }

                target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    player.sendMessage(BasePlugin.getPrefix() + "§c Player not found: " + args[1]);
                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                    return true;
                }
            }
        }

        target.setGameMode(mode);

        if (target == player) {
            player.sendMessage(BasePlugin.getPrefix() + "§e Your gamemode is now " + mode.name().toLowerCase() + ".");
        } else {
            player.sendMessage(BasePlugin.getPrefix() + "§e Changed " + target.getName() + "'s gamemode to " + mode.name().toLowerCase() + ".");
            target.sendMessage(BasePlugin.getPrefix() + "§e Your gamemode has been changed to " + mode.name().toLowerCase() + " by " + player.getName() + ".");
        }

        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
        if (target != player) {
            SoundUtil.play(target, SoundUtil.SoundType.CONFIRM);
        }

        return true;
    }

    private boolean isDirectGameModeCommand(String label) {
        String cleanLabel = label.toLowerCase();
        if (cleanLabel.contains(":")) {
            cleanLabel = cleanLabel.substring(cleanLabel.lastIndexOf(":") + 1);
        }

        return cleanLabel.equals("gmc") || cleanLabel.equals("gms") ||
                cleanLabel.equals("gma") || cleanLabel.equals("gmsp") ||
                cleanLabel.equals("creative") || cleanLabel.equals("survival") ||
                cleanLabel.equals("adventure") || cleanLabel.equals("spectator");
    }

    private GameMode resolveGameMode(String label, String[] args) {
        String cleanLabel = label.toLowerCase();
        if (cleanLabel.contains(":")) {
            cleanLabel = cleanLabel.substring(cleanLabel.lastIndexOf(":") + 1);
        }

        String input;

        if (isDirectGameModeCommand(cleanLabel)) {
            input = cleanLabel;
        } else {
            if (args.length == 0) {
                return null;
            }
            input = args[0].toLowerCase();
        }

        return switch (input) {
            case "0", "survival", "gms", "s" -> GameMode.SURVIVAL;
            case "1", "creative", "gmc", "c" -> GameMode.CREATIVE;
            case "2", "adventure", "gma", "a" -> GameMode.ADVENTURE;
            case "3", "spectator", "gmsp", "sp" -> GameMode.SPECTATOR;
            default -> null;
        };
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        TabComplete tabComplete = new TabComplete(sender, command, label, args);

        String cleanLabel = label.toLowerCase();
        if (cleanLabel.contains(":")) {
            cleanLabel = cleanLabel.substring(cleanLabel.lastIndexOf(":") + 1);
        }

        if (isDirectGameModeCommand(cleanLabel)) {
            if (args.length == 1) {
                tabComplete.add(1, Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
            }
        } else {
            if (args.length == 1) {
                tabComplete.add(1, new String[]{"survival", "creative", "adventure", "spectator"});
            } else if (args.length == 2) {
                tabComplete.add(2, Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
            }
        }

        return tabComplete.build();
    }
}