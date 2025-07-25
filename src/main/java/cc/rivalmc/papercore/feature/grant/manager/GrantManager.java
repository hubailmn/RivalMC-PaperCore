package cc.rivalmc.papercore.feature.grant.manager;

import cc.rivalmc.papercore.CorePlugin;
import cc.rivalmc.papercore.feature.grant.data.RankSelector;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GrantManager {

    public static GroupManager groupManager;
    public static UserManager userManager;
    public static QueryOptions staticQueryOptions;
    public static  ConcurrentHashMap<UUID, RankSelector> selector;
    public GrantManager() {
        LuckPerms luckPerms = CorePlugin.getCore().getLuckPerms();
        groupManager = luckPerms.getGroupManager();
        userManager = luckPerms.getUserManager();
        staticQueryOptions = luckPerms.getContextManager().getStaticQueryOptions();
        selector = new ConcurrentHashMap<>();
    }

    public static List<String> getGroups() {
       return groupManager.getLoadedGroups().stream().map(Group::getName).toList();
    }
    public static Group getGroup(String group) {
        return groupManager.getGroup(group);
    }
    public static List<String> getStaffGroups() {
        Group staffGroup = groupManager.getGroup("staff");
        return groupManager.getLoadedGroups().stream()
                .filter(group -> group.getInheritedGroups(staticQueryOptions).contains(staffGroup))
                .sorted((g1, g2) -> {
                    int w1 = g1.getWeight().orElse(0);
                    int w2 = g2.getWeight().orElse(0);
                    return Integer.compare(w2, w1);
                })
                .map(Group::getName)
                .toList();
    }

    public static void modifyGroup(UUID uuid, String groupName) {
        userManager.loadUser(uuid).thenAcceptAsync(user -> {
            user.data().clear(NodeType.INHERITANCE::matches);

            InheritanceNode node = InheritanceNode.builder(groupName).build();
            user.data().add(node);
            user.setPrimaryGroup(groupName);
            userManager.saveUser(user);
        });
    }
    public static List<Group> getGroupsSorted() {
        return groupManager.getLoadedGroups().stream()
                .sorted((g1, g2) -> {
                    int w1 = g1.getWeight().orElse(0);
                    int w2 = g2.getWeight().orElse(0);
                    return Integer.compare(w2, w1);
                })
                .toList();
    }
    public static User getUser(UUID uuid) {
        return userManager.getUser(uuid);
    }

    public static List<String> getUsersWithPermission(String permission) {
        List<String> usersWithPerm = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            userManager.loadUser(offlinePlayer.getUniqueId()).thenAcceptAsync(user -> {
                if(hasDirectPermission(user, permission)) {
                    usersWithPerm.add(user.getUsername());
                }
            });
        }
        return usersWithPerm;
    }

    public static boolean hasDirectPermission(User user, String permission) {
        return user.getNodes().stream()
                .anyMatch(node -> node.getKey().equalsIgnoreCase(permission) && node.getValue());
    }

    public static void selectGrant(Player player, OfflinePlayer target) {
        if(selector.get(player.getUniqueId()) == null) {
            selector.put(player.getUniqueId(), new RankSelector(target.getName(), "default"));
            return;
        }
        get(player).setPlayerName(target.getName());
    }
    public static void remove(Player player) {
        selector.remove(player.getUniqueId());
    }
    public static RankSelector get(OfflinePlayer player) {
        return selector.get(player.getUniqueId());
    }
}
