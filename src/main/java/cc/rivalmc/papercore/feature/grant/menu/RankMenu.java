package cc.rivalmc.papercore.feature.grant.menu;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiSlotButton;
import cc.hubailmn.utility.menu.type.MenuBuilder;
import cc.rivalmc.papercore.feature.grant.data.RankSelector;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Menu(
        title = "§6&lRank Menu",
        rows = 6
)
public class RankMenu extends MenuBuilder {

    List<String> lore = List.of("<aqua>LEFT-CLICK <gray>➜ <green>SET", "<aqua>DROP <gray>➜ <green>REMOVE", "<aqua>RIGHT-CLICK <gray>➜ <green>ADD");


    // Create this method
    public static void open(Player player) {
        new RankMenu().display(player);
        player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, 2);
    }

    @Override
    public void setupButtons() {
        int slot = 0; // start from slot 0
        String currentGroup = GrantManager.getUser(player.getUniqueId()).getPrimaryGroup();
        int playerWeight = GrantManager.getGroup(currentGroup).getWeight().orElse(0);
        boolean hasMaxPermission = player.hasPermission("rivalmc.rank.max");

        List<Group> sortedGroups = GrantManager.getGroupsSorted();

        for (Group group : sortedGroups) {
            boolean locked = !hasMaxPermission && group.getWeight().orElse(0) > playerWeight;
            ItemStack itemStack = buildGroupItem(group, locked);

            int currentSlot = slot++; // safely capture current slot for lambda
            addButtons(new GuiSlotButton(currentSlot, itemStack, player -> {
                if (locked) {
                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                    return;
                }
                handleGroupClick("LEFT", player, group);
            }));
        }
    }


    @Override
    public void setItems(Inventory inventory) {
    }








    private ItemStack buildGroupItem(Group group, boolean locked) {
        if (locked) {
            return new ItemBuilder(Material.BARRIER).name("&c&lLOCKED").build();
        }

        CachedMetaData metaData = group.getCachedData().getMetaData();
        String prefix = metaData.getPrefix() != null ? metaData.getPrefix() : "<gray>Unknown Rank";
        String itemColor = metaData.getMetaValue("item-color");

        Material material = Material.BEDROCK;
        if (itemColor != null) {
            Material matched = Material.matchMaterial(itemColor.toUpperCase());
            if (matched != null) material = matched;
        }

        return new ItemBuilder(material).name(prefix).lore(lore).build();
    }


    private void handleGroupClick(String clickType, Player player, Group group) {
        RankSelector rankSelector = GrantManager.get(player);
        rankSelector.setRank(group.getName());
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(rankSelector.getPlayerName());
        switch (clickType) {
            case "LEFT" -> {
                GrantManager.modifyGroup(target.getUniqueId(), rankSelector.getRank());
                SoundUtil.play(player, SoundUtil.SoundType.LEVEL_UP);
                GrantMenu.open(player);
            }
//            case "DROP" -> {
//                Bukkit.dispatchCommand(console,
//                        "lp user %s parent remove %s".formatted(rankSelector.getPlayerName(), group.getName()));
//                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1 ,2);
//                instance.getMenusManager().getGrantMenu().getMenu().open(player);
//            }
//            case "RIGHT" -> instance.getMenusManager().getDurationsMenu().getMenu().open(player);
        }
    }

}
