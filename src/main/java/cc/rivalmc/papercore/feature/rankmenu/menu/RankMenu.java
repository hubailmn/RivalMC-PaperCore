package cc.rivalmc.papercore.feature.rankmenu.menu;

import cc.hubailmn.utility.BasePlugin;
import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.interaction.player.PlayerMessageUtil;
import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.listener.ChatInputManager;
import cc.hubailmn.utility.menu.MenuLayout;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiSlotButton;
import cc.hubailmn.utility.menu.type.MenuBuilder;
import cc.rivalmc.papercore.feature.examplemenuforbabyless.menu.PagedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Menu(
        title = "§6&lRank Menu",
        rows = 3
)
public class RankMenu extends MenuBuilder {

    // Create this method
    public static void open(Player player) {
        new RankMenu().display(player);
        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
    }

    @Override
    public void setupButtons() {

        addButtons(
                new GuiSlotButton(
                        MenuLayout.getSlot(5, 2),
                        new ItemBuilder()
                                .material(Material.DIAMOND_SWORD)
                                .name("§eBelieve it or not but this is a menu! :smirk:")
                                .lore(List.of(
                                        "§7Click open a menu with your heads",
                                        "§7and see how cool it is! :D",
                                        ""
                                ))
                                .build(),
                player -> {
                            player.closeInventory();
                            SoundUtil.play(player, SoundUtil.SoundType.CLICK);
                            PlayerMessageUtil.title(player, "§aEnter your input in chat", ":D");
                            ChatInputManager.ask(player, "§eHow Many Heads you want to see?", input -> {
                                int heads;
                                try {
                                    heads = Integer.parseInt(input);
                                } catch (Exception ignored) {
                                    player.sendMessage(BasePlugin.getPrefix() + "§c Invalid Number.");
                                    SoundUtil.play(player, SoundUtil.SoundType.DENY);
                                    display(player);
                                    return;
                                }

                                PagedMenu.open(player, heads);
                            });

                        }
                )
        );

    }

    @Override
    public void setItems(Inventory inventory) {
        MenuLayout.checkerboard(
                inventory,
                new ItemBuilder(Material.GRASS_BLOCK).build(),
                new ItemBuilder(Material.DIRT).build()
        );
    }
}
