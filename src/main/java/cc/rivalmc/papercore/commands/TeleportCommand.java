package cc.rivalmc.papercore.commands;

import cc.hubailmn.utility.command.CommandBuilder;
import cc.hubailmn.utility.command.annotation.Command;
import org.bukkit.command.CommandSender;

@Command(name = "teleport", permission = "rivalmc.teleport", aliases = {"tp", "goto"})
public class TeleportCommand extends CommandBuilder {

    @Override
    public boolean perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        return true;
    }
}
