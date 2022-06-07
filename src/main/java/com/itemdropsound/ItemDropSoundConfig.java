package com.itemdropsound;

import net.runelite.api.ItemID;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ItemDropSoundConfig")
public interface ItemDropSoundConfig extends Config
{
	@ConfigItem(
			keyName = "item_ids",
			name = "Item Ids",
			description = "Item ids to play a drop sound for, enter multiple separated with ;"
	)
	default String itemIds()
	{
		return Integer.toString(ItemID.BLOOD_SHARD);
	}
}
