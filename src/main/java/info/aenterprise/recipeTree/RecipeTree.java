package info.aenterprise.recipeTree;

import info.aenterprise.recipeTree.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
@Mod(modid = "recipetree", version = "@MODVERSION@", clientSideOnly = true)
public class RecipeTree {

	@SidedProxy(serverSide = "info.aenterprise.recipeTree.proxy.CommonProxy", clientSide = "info.aenterprise.recipeTree.proxy.ClientProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static RecipeTree INSTANCE;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		proxy.init();
	}
}
