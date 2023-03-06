package com.darkvoidstudios.mcchallenges;

import com.darkvoidstudios.mcchallenges.challenge.commands.ChallengeCommand;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeCompleteListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeFailListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeListener;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import com.darkvoidstudios.mcchallenges.damageeffects.DamageEffectsListener;
import com.darkvoidstudios.mcchallenges.delayeddamage.listener.DelayedDamageListener;
import com.darkvoidstudios.mcchallenges.delayeddamage.schedulers.DelayedDamageScheduler;
import com.darkvoidstudios.mcchallenges.icewalk.IceWalkListener;
import com.darkvoidstudios.mcchallenges.jumpinghotbar.JumpingHotbarListener;
import com.darkvoidstudios.mcchallenges.noarmor.listener.NoArmorListener;
import com.darkvoidstudios.mcchallenges.nopickup.NoPickupListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPlugin extends JavaPlugin {
    ChallengeTimer challengeTimer = ChallengeTimer.getInstance();
    DelayedDamageScheduler delayedDamageScheduler = new DelayedDamageScheduler();

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();
        schedulerRegistration();
    }

    private void commandRegistration() {
        this.getCommand("challenge").setExecutor(new ChallengeCommand());
    }

    private void listenerRegistration() {
        getServer().getPluginManager().registerEvents(new ChallengeListener(), this);
        getServer().getPluginManager().registerEvents(new ChallengeCompleteListener(), this);
        getServer().getPluginManager().registerEvents(new ChallengeFailListener(), this);

        getServer().getPluginManager().registerEvents(new NoPickupListener(), this);
        getServer().getPluginManager().registerEvents(new DamageEffectsListener(), this);
        getServer().getPluginManager().registerEvents(new JumpingHotbarListener(), this);
        getServer().getPluginManager().registerEvents(new IceWalkListener(), this);
        getServer().getPluginManager().registerEvents(new DelayedDamageListener(), this);
        getServer().getPluginManager().registerEvents(new NoArmorListener(), this);
    }

    private void schedulerRegistration() {
        challengeTimer.run();
        delayedDamageScheduler.run();
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Challenge");
    }

}
