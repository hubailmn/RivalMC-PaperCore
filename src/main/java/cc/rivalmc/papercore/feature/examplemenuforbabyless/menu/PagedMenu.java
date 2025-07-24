package cc.rivalmc.papercore.feature.examplemenuforbabyless.menu;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiElement;
import cc.hubailmn.utility.menu.type.PagedMenuBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

@Menu(
        title = "§8Paged Menu",
        rows = 4
)
@Getter
public class PagedMenu extends PagedMenuBuilder {

    private final int headsAmount;

    // you can make constructor and pass some values to it, dont forget to call super
    public PagedMenu(int headsAmount) {
        super();
        this.headsAmount = headsAmount;

        List<GuiElement> guiElementList = new ArrayList<>();

        for (int i = 0; i < getHeadsAmount(); i++) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            if (skullMeta != null) {
                skullMeta.setDisplayName("§eHead #" + (i + 1));
                skullMeta.setLore(List.of(
                        "§7Click this item to open a ver cool scroll menu"
                ));
                head.setItemMeta(skullMeta);
            }

            guiElementList.add(
                    new GuiElement(head, ScrollMenu::open)
            );
        }

        addItems(guiElementList.toArray(new GuiElement[0]));
    }

    public static void open(Player player, int headsAmount) {
        new PagedMenu(headsAmount).display(player);
        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
    }

    @Override
    public void setupButtons() {

    }

    @Override
    public void setItems(Inventory inventory) {

    }
}
