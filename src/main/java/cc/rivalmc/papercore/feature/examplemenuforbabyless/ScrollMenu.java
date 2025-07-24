package cc.rivalmc.papercore.feature.examplemenuforbabyless;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiElement;
import cc.hubailmn.utility.menu.type.ScrollableMenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Menu(
        title = "§8Very cool menu",
        rows = 6
)
public class ScrollMenu extends ScrollableMenuBuilder {

    public ScrollMenu() {
        super();

        List<GuiElement> guiElementList = new ArrayList<>();

        for (Material material : Material.values()) {
            guiElementList.add(
                    new GuiElement(
                            new ItemStack(material),
                            player -> {
                                SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
                            }
                    )
            );
        }

        addItems(guiElementList.toArray(new GuiElement[0]));
    }

    public static void open(Player player) {
        new ScrollMenu().display(player);
        SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
    }

    @Override
    public void setupButtons() {

    }

    @Override
    public void setItems(Inventory inventory) {

    }
}
