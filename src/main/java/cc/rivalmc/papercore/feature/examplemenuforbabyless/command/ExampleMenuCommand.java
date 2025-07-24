package cc.rivalmc.papercore.feature.examplemenuforbabyless.command;

import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.annotation.Command;
import cc.hubailmn.utility.interaction.SoundUtil;
import cc.rivalmc.papercore.feature.examplemenuforbabyless.BasicMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(
        name = "exampleMenu",
        permission = "Babyless.example"
)
public class ExampleMenuCommand extends CommandBuilder {
    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        BasicMenu.open(player);
        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
        return true;
    }
}
