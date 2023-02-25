package com.darkvoidstudios.mcchallenges.randomitem;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomItem {
    static Challenge challenge = Challenge.getInstance();

    static Random random = new Random();

    static List<Material> allMaterials = Arrays.asList(Material.values());

    public static void distributeRandomItemsToAllPlayers() {
        if (challenge.isChallengeActive() && challenge.isRandomItemChallengeActive()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getInventory().addItem(generateRandomItemStack());
            }
        }
    }

    public static ArrayList<ItemStack> generateRandomItemStacks(int amount) {
        ArrayList<ItemStack> generatedItems = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            generatedItems.add(generateRandomItemStack());
        }
        return generatedItems;
    }

    public static ItemStack generateRandomItemStack() {
        int maxAmount = (int) (Math.random() * (64 - 1)) + 1;

        Material randomItem = allMaterials.get(random.nextInt(allMaterials.size()));
        int materialId = random.nextInt(allMaterials.indexOf(randomItem));

        switch(randomItem) {
            case BEDROCK:
            case WARDEN_SPAWN_EGG:
            case ENDER_DRAGON_SPAWN_EGG:
            case AIR:
            case LIGHT:
            case CAVE_AIR:
            case VOID_AIR:
            case STRUCTURE_VOID:
            case END_PORTAL_FRAME:
            case COMMAND_BLOCK:
            case CHAIN_COMMAND_BLOCK:
            case REPEATING_COMMAND_BLOCK:
            case COMMAND_BLOCK_MINECART:
            case BARRIER:
            case DEBUG_STICK:
            case ENCHANTED_BOOK:
                return generateRandomItemStack();
        }
        return (new ItemStack(Material.values()[materialId], Math.min(Material.values()[materialId].getMaxStackSize(), maxAmount)));
    }
}
