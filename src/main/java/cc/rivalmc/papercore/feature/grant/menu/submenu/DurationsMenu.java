package cc.rivalmc.papercore.feature.grant.menu.submenu;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiElement;
import cc.hubailmn.utility.menu.type.PagedMenuBuilder;
import cc.rivalmc.papercore.feature.grant.data.RankSelector;
import cc.rivalmc.papercore.feature.grant.manager.EDurations;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import cc.rivalmc.papercore.feature.grant.menu.GrantMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Menu(
        title = "§8Durations",
        rows = 6
)
@Getter
public class DurationsMenu extends PagedMenuBuilder {

    private final OfflinePlayer targetPlayer;

    public DurationsMenu(OfflinePlayer targetPlayer) {
        super();
        this.targetPlayer = targetPlayer;

        for (EDurations duration : EDurations.values()) {
            addItems(
                    new GuiElement(
                            new ItemBuilder(Material.CLOCK)
                                    .name(duration.getPrefix())
                                    .build(),
                            player -> {
                                RankSelector rankSelector = GrantManager.get(getTargetPlayer());
                                OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(rankSelector.getPlayerName());

                                if (duration == EDurations.LIFETIME) {
                                    GrantManager.modifyGroup(target.getUniqueId(), rankSelector.getRank());
                                } else {
                                    GrantManager.modifyGroup(target.getUniqueId(), rankSelector.getRank(), duration.getDuration());
                                }

                                SoundUtil.play(player, SoundUtil.SoundType.LEVEL_UP);
                                GrantMenu.open(player, getTargetPlayer());
                            }
                    )
            );
        }

    }

    public static void open(Player player, OfflinePlayer targetPlayer) {
        new DurationsMenu(targetPlayer).display(player);
        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
    }

    @Override
    public void setupButtons() {

    }

    @Override
    public void setItems(Inventory inventory) {

    }
}
