package com.darkvoidstudios.mcchallenges;

import com.darkvoidstudios.mcchallenges.challenge.commands.ChallengeCommand;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeCompleteListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeFailListener;
import com.darkvoidstudios.mcchallenges.challenge.listeners.ChallengeListener;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import com.darkvoidstudios.mcchallenges.jumpinghotbar.listener.JumpingHotbarListener;
import com.darkvoidstudios.mcchallenges.pdare.listeners.PDAREListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPlugin extends JavaPlugin {
    ChallengeTimer challengeTimer = ChallengeTimer.getInstance();

    @Override
    public void onEnable() {
        listenerRegistration();
        commandRegistration();
        challengeTimer.run();
    }

    private void commandRegistration() {
        this.getCommand("challenge").setExecutor(new ChallengeCommand());
    }

    private void listenerRegistration() {
        getServer().getPluginManager().registerEvents(new ChallengeListener(), this);
        getServer().getPluginManager().registerEvents(new ChallengeCompleteListener(), this);
        getServer().getPluginManager().registerEvents(new ChallengeFailListener(), this);
        getServer().getPluginManager().registerEvents(new PDAREListener(), this);
        getServer().getPluginManager().registerEvents(new JumpingHotbarListener(), this);
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Challenge");
    }

}
