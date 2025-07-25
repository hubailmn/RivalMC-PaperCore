package cc.rivalmc.papercore.feature.grant.menu;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiElement;
import cc.hubailmn.utility.menu.type.PagedMenuBuilder;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import cc.rivalmc.papercore.feature.grant.menu.submenu.ActionsMenu;
import lombok.Getter;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Menu(
        title = "§8Rank Menu",
        rows = 6
)
@Getter
public class RankMenu extends PagedMenuBuilder {

    private final OfflinePlayer targetPlayer;

    public RankMenu(OfflinePlayer targetPlayer) {
        super();
        this.targetPlayer = targetPlayer;
    }

    public static void open(Player player, OfflinePlayer targetPlayer) {
        new RankMenu(targetPlayer).display(player);
        player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, 2);
    }

    @Override
    public void setupButtons() {
    }

    @Override
    public void setItems(Inventory inventory) {
        loadItems();
    }

    private void loadItems() {
        String currentGroup = GrantManager.getUser(targetPlayer.getUniqueId()).getPrimaryGroup();

        int playerWeight = GrantManager.getGroup(currentGroup).getWeight().orElse(0);
        boolean hasMaxPermission = getPlayer().hasPermission("rivalmc.rank.max");

        List<Group> sortedGroups = GrantManager.getGroupsSorted();

        for (Group group : sortedGroups) {
            boolean locked = !hasMaxPermission && group.getWeight().orElse(0) > playerWeight;
            ItemStack itemStack = buildGroupItem(group, locked);

            addItems(new GuiElement(itemStack, player -> {
                if (locked) {
                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                    return;
                }

                ActionsMenu.open(player, getTargetPlayer());
            }));
        }
    }

    private ItemStack buildGroupItem(Group group, boolean locked) {
        if (locked) {
            return new ItemBuilder(Material.BARRIER).name("&c&lLOCKED").build();
        }

        CachedMetaData metaData = group.getCachedData().getMetaData();
        String prefix = metaData.getPrefix() != null ? metaData.getPrefix() : "§8Unknown Rank";
        String itemColor = metaData.getMetaValue("item-color");

        Material material = Material.GRAY_CONCRETE;
        if (itemColor != null) {
            Material matched = Material.matchMaterial(itemColor.toUpperCase());
            if (matched != null) material = matched;
        }

        return new ItemBuilder(material).
                name(prefix)
                .lore(List.of(
                        "",
                        "§eClick To manage player"
                ))
                .build();
    }

}
