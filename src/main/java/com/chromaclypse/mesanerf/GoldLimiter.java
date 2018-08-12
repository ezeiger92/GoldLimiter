package com.chromaclypse.mesanerf;

import java.util.EnumSet;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldLimiter extends JavaPlugin implements Listener {
	private final Random engine = new Random();

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll((JavaPlugin)this);
	}
	
	EnumSet<Biome> mesaBiomes = EnumSet.of(Biome.BADLANDS, Biome.BADLANDS_PLATEAU,
			Biome.ERODED_BADLANDS, Biome.MODIFIED_BADLANDS_PLATEAU,
			Biome.MODIFIED_WOODED_BADLANDS_PLATEAU, Biome.WOODED_BADLANDS_PLATEAU);
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() != Material.GOLD_ORE || event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		
		Location loc = event.getBlock().getLocation();
		
		if(loc.getY() < 32 || loc.getY() > 79 || !mesaBiomes.contains(loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()))) {
			return;
		}
		
		event.setDropItems(false);
		
		int iron = (int)(2.4 * engine.nextDouble() + 0.5);
		int gold = (int)(0.6 * engine.nextDouble() + 0.5);
		
		loc.add(0.5, 0.5, 0.5);
		
		if(iron > 0) {
			loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.IRON_NUGGET, iron));
		}
		
		if(gold > 0) {
			loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.GOLD_NUGGET, gold));
		}
	}
}
