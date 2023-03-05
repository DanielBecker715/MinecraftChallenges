package com.darkvoidstudios.mcchallenges.damageeffects;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.models.Messages;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.potion.PotionEffectType.*;

@Getter
@Setter
public class DamageEffectsListener implements Listener {

    final Challenge challenge = Challenge.getInstance();

    int potionDuration;

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (challenge.isChallengeActive() && challenge.isDamageEffectsChallengeActive()) {
            if (!(((EntityDamageByEntityEvent)event).getDamager() instanceof Player)) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    boolean triggerEvent = true;

                    switch(event.getCause()) {
                        case WITHER:
                        case POISON:
                        case FIRE:
                        case DROWNING:
                        case LAVA:
                        case FIRE_TICK:
                            triggerEvent = false;
                            break;
                    }

                    if (triggerEvent) {
                        PotionEffect potionEffect = generatePotionEffect();
                        player.addPotionEffect(potionEffect);
                        player.sendMessage(Messages.prefix + "You got the effect §e" + potionEffect.getType().getName() + "§7 for §e" + getPotionDuration() /20 + " seconds");
                    }
                }
            }
        }
    }

    private PotionEffect generatePotionEffect() {
        int rndPotion = ThreadLocalRandom.current().nextInt(PotionEffectType.values().length);
        PotionEffectType potionEffectType = PotionEffectType.values()[rndPotion];

        if (potionEffectType.equals(HARM)) {
            return generatePotionEffect();
        }

        Random random = new Random();
        if (potionEffectType.equals(WITHER) || potionEffectType.equals(POISON)) {
            setPotionDuration(random.nextInt(10 - 5) + 5);
            return new PotionEffect(potionEffectType, getPotionDuration() * 20, 1);
        }
        setPotionDuration(random.nextInt(30 - 10) + 10);
        return new PotionEffect(potionEffectType, getPotionDuration() * 20, 1);
    }
}
