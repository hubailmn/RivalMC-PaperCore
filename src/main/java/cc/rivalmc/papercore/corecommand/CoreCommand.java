package cc.rivalmc.papercore.corecommand;

import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.annotation.Command;
import cc.hubailmn.utility.interaction.SoundUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(
        name = "rivalcore",
        permission = "rivalmc.command.core",
        aliases = {"core", "rvcore", "rvc"}
)
public class CoreCommand extends CommandBuilder {

    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            SoundUtil.play(player, SoundUtil.SoundType.SUCCESS);
        }

        return true;
    }

}
