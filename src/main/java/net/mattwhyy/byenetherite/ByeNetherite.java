package net.mattwhyy.byenetherite;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ByeNetherite extends JavaPlugin implements Listener {

    private final Map<Material, Boolean> disabledUpgrades = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadDisabledItems();
        getServer().getPluginManager().registerEvents(this, this);
        removeNetheriteSmithingRecipes();
        getLogger().info("ByeNetherite enabled!");
    }


    @Override
    public void onDisable() {
        getLogger().info("ByeNetherite disabled.");
    }

    private void loadDisabledItems() {
        disabledUpgrades.put(Material.NETHERITE_HELMET, getConfig().getBoolean("bye-netherite.helmet", false));
        disabledUpgrades.put(Material.NETHERITE_CHESTPLATE, getConfig().getBoolean("bye-netherite.chestplate", false));
        disabledUpgrades.put(Material.NETHERITE_LEGGINGS, getConfig().getBoolean("bye-netherite.leggings", false));
        disabledUpgrades.put(Material.NETHERITE_BOOTS, getConfig().getBoolean("bye-netherite.boots", false));
        disabledUpgrades.put(Material.NETHERITE_SWORD, getConfig().getBoolean("bye-netherite.sword", false));
        disabledUpgrades.put(Material.NETHERITE_PICKAXE, getConfig().getBoolean("bye-netherite.pickaxe", false));
        disabledUpgrades.put(Material.NETHERITE_AXE, getConfig().getBoolean("bye-netherite.axe", false));
        disabledUpgrades.put(Material.NETHERITE_SHOVEL, getConfig().getBoolean("bye-netherite.shovel", false));
        disabledUpgrades.put(Material.NETHERITE_HOE, getConfig().getBoolean("bye-netherite.hoe", false));
    }


    private void removeNetheriteSmithingRecipes() {
        Iterator<Recipe> it = getServer().recipeIterator();
        while (it.hasNext()) {
            Recipe recipe = it.next();
            if (recipe instanceof SmithingRecipe) {
                SmithingRecipe smithingRecipe = (SmithingRecipe) recipe;
                ItemStack result = smithingRecipe.getResult();
                if (result != null && disabledUpgrades.getOrDefault(result.getType(), false)) {
                    it.remove();
                    getLogger().info("Removed Netherite upgrade for: " + result.getType().name());
                }
            }
        }
    }
}