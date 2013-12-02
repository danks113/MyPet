package de.Keyle.MyPet.util.support.arenas;

import com.pauldavdesign.mineauz.minigames.events.JoinMinigameEvent;
import com.pauldavdesign.mineauz.minigames.events.SpectateMinigameEvent;
import de.Keyle.MyPet.MyPetPlugin;
import de.Keyle.MyPet.entity.types.MyPet.PetState;
import de.Keyle.MyPet.util.MyPetPlayer;
import de.Keyle.MyPet.util.locale.Locales;
import de.Keyle.MyPet.util.logger.DebugLogger;
import de.Keyle.MyPet.util.support.PluginSupportManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Minigames implements Listener {
    public static boolean DISABLE_PETS_IN_MINIGAMES = true;

    private static com.pauldavdesign.mineauz.minigames.Minigames plugin;
    private static boolean active = false;

    public static void findPlugin() {
        if (PluginSupportManager.isPluginUsable("Minigames")) {
            plugin = PluginSupportManager.getPluginInstance(com.pauldavdesign.mineauz.minigames.Minigames.class);
            Bukkit.getPluginManager().registerEvents(new Minigames(), MyPetPlugin.getPlugin());
            active = true;
        }
        DebugLogger.info("Minigames support " + (active ? "" : "not ") + "activated.");
    }

    public static boolean isInMinigame(MyPetPlayer owner) {
        if (active) {
            try {
                if (plugin != null) {
                    Player p = owner.getPlayer();
                    return plugin.pdata.playersInMinigame().contains(p);
                }
            } catch (Exception e) {
                active = false;
            }
        }
        return false;
    }

    @EventHandler
    public void onJoinMinigame(JoinMinigameEvent event) {
        if (active && DISABLE_PETS_IN_MINIGAMES && MyPetPlayer.isMyPetPlayer(event.getPlayer())) {
            MyPetPlayer player = MyPetPlayer.getMyPetPlayer(event.getPlayer());
            if (player.hasMyPet() && player.getMyPet().getStatus() == PetState.Here) {
                player.getMyPet().removePet(true);
                player.getPlayer().sendMessage(Locales.getString("Message.No.AllowedHere", player.getPlayer()));
            }
        }
    }

    @EventHandler
    public void onSpectateMinigame(SpectateMinigameEvent event) {
        if (active && DISABLE_PETS_IN_MINIGAMES && MyPetPlayer.isMyPetPlayer(event.getPlayer())) {
            MyPetPlayer player = MyPetPlayer.getMyPetPlayer(event.getPlayer());
            if (player.hasMyPet() && player.getMyPet().getStatus() == PetState.Here) {
                player.getMyPet().removePet(true);
                player.getPlayer().sendMessage(Locales.getString("Message.No.AllowedHere", player.getPlayer()));
            }
        }
    }
}