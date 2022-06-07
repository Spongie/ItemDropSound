package com.itemdropsound;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ItemDropSoundPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ItemDropSoundPlugin.class);
		RuneLite.main(args);
	}
}