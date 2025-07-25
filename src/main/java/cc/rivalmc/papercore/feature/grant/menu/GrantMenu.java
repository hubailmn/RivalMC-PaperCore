package cc.rivalmc.papercore.feature.grant.menu;

import cc.hubailmn.utility.item.ItemBuilder;
import cc.hubailmn.utility.menu.MenuLayout;
import cc.hubailmn.utility.menu.annotation.Menu;
import cc.hubailmn.utility.menu.interactive.GuiSlotButton;
import cc.hubailmn.utility.menu.type.MenuBuilder;
import cc.rivalmc.papercore.feature.grant.manager.GrantManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;

@Menu(
        title = "§6§lGrant Menu",
        rows = 3
)
public class GrantMenu extends MenuBuilder {

    // Create this method
    public static void open(Player player) {
        new GrantMenu().display(player);
    }

    @Override
    public void setupButtons() {
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(GrantManager.get(player).getPlayerName());
        AtomicReference<List<String>> lore = null;
        GrantManager.userManager.loadUser(target.getUniqueId()).thenAcceptAsync(offlineUser -> {
             lore.set(Stream.of(
                             "§c§lUUID§8: §6%s",
                             "§c§lRank§8: §6%s"
                     ).map(new FormatterIterator(target.getUniqueId().toString(), offlineUser.getPrimaryGroup()))
                     .toList());
        });
        ItemStack itemStack = new ItemBuilder().material(Material.CHEST).name("§3§lModify Player Rank").build();
        ItemStack head = new ItemBuilder().material(Material.PLAYER_HEAD).lore(lore.get()).name("§6§l%s".formatted(target.getName())).build();
        addButtons(new GuiSlotButton(MenuLayout.getSlot(4, 2), itemStack, RankMenu::open));
        addButtons(new GuiSlotButton(MenuLayout.getSlot(5, 1), head));



    }

    @Override
    public void setItems(Inventory inventory) {

    }

    public static class FormatterIterator implements Function<String, String> {
        private final Iterator<String> args;

        FormatterIterator(String... args) {
            this.args = Arrays.asList(args).iterator();
        }

        @Override
        public String apply(String format) {
            return format.formatted(args.next());
        }
    }
}
