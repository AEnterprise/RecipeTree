package info.aenterprise.recipeTree.jei;

import mezz.jei.api.*;

import javax.annotation.Nonnull;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
@JEIPlugin
public class Plugin extends BlankModPlugin {
	public static IJeiRuntime runtime;
	public static IJeiHelpers helpers;

	@Override
	public void register(@Nonnull IModRegistry registry) {
		helpers = registry.getJeiHelpers();
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
		runtime = jeiRuntime;
	}
}
