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

    static final Challenge challenge = Challenge.getInstance();

    static List<Material> materialList = Arrays.asList(Material.values());

    static Random random = new Random();

    public static boolean isMaterialAllowed(Material material) {
        if (material.name().contains("BAMBOO")) {
            return false;
        }

        switch (material) {
            case BEDROCK:
            case WARDEN_SPAWN_EGG:
            case ENDER_DRAGON_SPAWN_EGG:
            case END_PORTAL_FRAME:
            case COMMAND_BLOCK:
            case CHAIN_COMMAND_BLOCK:
            case REPEATING_COMMAND_BLOCK:
            case COMMAND_BLOCK_MINECART:
            case BARRIER:
            case DEBUG_STICK:
            case ENCHANTED_BOOK:
                return false;
            default:
                return true;
        }
    }

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

        Material randomItem = materialList.get(random.nextInt(materialList.size()));
        if (!randomItem.isItem() && isMaterialAllowed(randomItem)) {
            return generateRandomItemStack();
        }
        int materialId = materialList.indexOf(randomItem);
        return (new ItemStack(Material.values()[materialId], Math.min(Material.values()[materialId].getMaxStackSize(), maxAmount)));
    }
}
