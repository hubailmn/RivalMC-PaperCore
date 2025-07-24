package cc.rivalmc.papercore.corecommand.subcommand;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.command.SubCommandBuilder;
import cc.hubailmn.utility.command.annotation.SubCommand;
import cc.hubailmn.utility.config.ConfigUtil;
import cc.hubailmn.utility.interaction.SoundUtil;
import cc.rivalmc.papercore.corecommand.CoreCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(
        name = "reload",
        permission = "rivalmc.comman.reload",
        command = CoreCommand.class,
        requiresPlayer = false
)
public class ReloadCommand extends SubCommandBuilder {

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {

        ConfigUtil.reloadAll();
        sender.sendMessage(BasePlugin.getPrefix() + "§e Configs has been reloaded.");

        if (sender instanceof Player player) {
            SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
        }

        return true;
    }

}
