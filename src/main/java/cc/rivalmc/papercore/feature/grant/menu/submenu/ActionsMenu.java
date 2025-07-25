package cc.rivalmc.papercore.feature.grant.menu.submenu;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.menu.MenuLayout;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiSlotButton;
import cc.hubailmn.utility.menu.type.MenuBuilder;
import cc.rivalmc.papercore.feature.grant.data.RankSelector;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import cc.rivalmc.papercore.feature.grant.menu.GrantMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Menu(
        title = "§8Rank Actions",
        rows = 3
)
@Getter
public class ActionsMenu extends MenuBuilder {

    private final OfflinePlayer targetPlayer;

    public ActionsMenu(OfflinePlayer targetPlayer) {
        super();
        this.targetPlayer = targetPlayer;
    }

    public static void open(Player player, OfflinePlayer targetPlayer) {
        new ActionsMenu(targetPlayer).display(player);
        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
    }

    @Override
    public void setupButtons() {
        addButtons(
                new GuiSlotButton(MenuLayout.getSlot(3, 2),
                        new ItemBuilder(Material.LIME_CONCRETE)
                                .name("§a§lSET RANK")
                                .lore(List.of("§7Click to set this rank", "§7for the player"))
                                .build(),
                        player -> handleGroupAction("SET", player)
                ),
                new GuiSlotButton(MenuLayout.getSlot(5, 2),
                        new ItemBuilder(Material.RED_CONCRETE)
                                .name("§c§lREMOVE RANK")
                                .lore(List.of("§7Click to remove this rank", "§7from the player"))
                                .build(),
                        player -> handleGroupAction("REMOVE", player)
                ),
                new GuiSlotButton(MenuLayout.getSlot(7, 2),
                        new ItemBuilder(Material.YELLOW_CONCRETE)
                                .name("§e§lDURATION")
                                .lore(List.of("§7Click to set duration", "§7for this rank"))
                                .build(),
                        player -> handleGroupAction("DURATION", player)
                )
        );
    }

    private void handleGroupAction(String action, Player player) {
        RankSelector rankSelector = GrantManager.get(player);
        String rankName = rankSelector.getRank();
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(rankSelector.getPlayerName());

        switch (action) {
            case "SET" -> {
                GrantManager.modifyGroup(target.getUniqueId(), rankName);
                SoundUtil.play(player, SoundUtil.SoundType.LEVEL_UP);
                GrantMenu.open(player, getTargetPlayer());
            }
            case "REMOVE" -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "lp user %s parent remove %s".formatted(rankSelector.getPlayerName(), rankName));
                SoundUtil.play(player, SoundUtil.SoundType.LEVEL_UP);
                GrantMenu.open(player, getTargetPlayer());
            }
            case "DURATION" -> {
                DurationsMenu.open(player, getTargetPlayer());
            }
        }
    }

    @Override
    public void setItems(Inventory inventory) {

    }

}
