package com.darkvoidstudios.mcchallenges.delayeddamage;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Getter
@Setter
public class DelayedDamage {

    static final DelayedDamage delayedDamage = new DelayedDamage();

    public static DelayedDamage getInstance() {
        return delayedDamage;
    }
    static final Challenge challenge = Challenge.getInstance();
    private static final Server server = Bukkit.getServer();

    int delayInMinutes = 2;
    double damageOfAllPlayers = 0;
    boolean isStopListeningDamageEventActive = false;

    public void distributeDamage() {
        if (challenge.isChallengeActive() && challenge.isDelayedDamageActive()) {
            server.sendMessage(Component.text(Messages.prefix + "§aA total §cdamage §aof §e" + delayedDamage.getDamageOfAllPlayers()/2 + " Hearts §awas reached"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                double damagePerPlayer = Math.round(delayedDamage.getDamageOfAllPlayers() / Bukkit.getOnlinePlayers().size() * 100.0) / 100.0;
                if (damagePerPlayer >= player.getHealth()) {
                    player.damage(player.getHealth());
                } else {
                    player.damage(damagePerPlayer);
                }
            }
            setDamageOfAllPlayers(0);
        }
    }
}
