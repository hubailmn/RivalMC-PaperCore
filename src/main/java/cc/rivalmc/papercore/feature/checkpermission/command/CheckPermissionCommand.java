package cc.rivalmc.papercore.feature.checkpermission.command;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.annotation.Command;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import org.bukkit.command.CommandSender;

@Command(
        name = "checkpermission",
        permission = "rivalmc.checkpermission",
        aliases = {"checkperm", "cp"}
)
public class CheckPermissionCommand extends CommandBuilder {


    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("%s §cPlease type a permission to search on it".formatted(BasePlugin.getPrefix()));
            return true;
        }

        System.out.println("work");
        GrantManager.getUsersWithPermission(args[0]).forEach(user -> sender.sendMessage("%s &e> &a%s".formatted(BasePlugin.getPrefix(), user)));

        return true;
    }
}
