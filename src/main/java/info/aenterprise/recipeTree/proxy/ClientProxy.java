package info.aenterprise.recipeTree.proxy;

import info.aenterprise.recipeTree.Keybindings;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();
		Keybindings.init();
	}
}
