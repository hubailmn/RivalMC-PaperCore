package cc.rivalmc.papercore.feature.grant.menu;

import cc.hubailmn.utility.interaction.SoundUtil;
import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.menu.MenuLayout;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiSlotButton;
import cc.hubailmn.utility.menu.type.MenuBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Menu(
        title = "§8Grant Menu",
        rows = 3
)
@Getter
public class GrantMenu extends MenuBuilder {

    private final OfflinePlayer targetPlayer;

    public GrantMenu(OfflinePlayer targetPlayer) {
        super();
        this.targetPlayer = targetPlayer;
    }

    public static void open(Player player, OfflinePlayer targetPlayer) {
        new GrantMenu(targetPlayer).display(player);
        SoundUtil.play(player, SoundUtil.SoundType.OPEN_MENU);
    }

    @Override
    public void setupButtons() {
        ItemStack itemStack = new ItemBuilder().material(Material.CHEST).name("§3Modify Player Rank").build();
        addButtons(new GuiSlotButton(MenuLayout.getSlot(5, 2), itemStack, player -> RankMenu.open(player, getTargetPlayer())));
    }

    @Override
    public void setItems(Inventory inventory) {

    }

}
