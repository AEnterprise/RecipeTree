package info.aenterprise.recipeTree.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

import javax.annotation.Nonnull;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
@JEIPlugin
public class Plugin extends BlankModPlugin {
	public static IJeiRuntime runtime;

	@Override
	public void register(@Nonnull IModRegistry registry) {

	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
		runtime = jeiRuntime;
	}
}
