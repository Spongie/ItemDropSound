package com.itemdropsound;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@PluginDescriptor(
	name = "Item Drop Sound"
)
public class ItemDropSoundPlugin extends Plugin
{
	@Inject
	private Client client;
	
	@Inject
	private ItemDropSoundConfig config;
	
	private Clip clip = null;
	
	@Override
	protected void startUp() throws Exception
	{
		log.info("Item Dropper started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Item Dropper stopped!");
	}
	
	private List<Integer> getItemIds(){
		try{
			return Arrays.stream(config.itemIds().split(";")).map(Integer::parseInt).collect(Collectors.toList());
		} catch (NumberFormatException ex){
			log.error("Invalid item id configuration");
			return new ArrayList<>();
		}
	}

	@Subscribe
	public void onItemSpawned(ItemSpawned itemSpawned)
	{
		List<Integer> itemIds = getItemIds();
		
		TileItem item = itemSpawned.getItem();
		if(itemIds.contains(item.getId())) {
			if (clip == null) {
				InputStream fileStream = new BufferedInputStream(ItemDropSoundPlugin.class.getClassLoader().getResourceAsStream("item_drop.wav"));
				try (AudioInputStream sound = AudioSystem.getAudioInputStream(fileStream))
				{
					clip = AudioSystem.getClip();
					clip.open(sound);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
				{
					log.warn("Unable to load bird run alert sound", e);
				}
			}
			
			if (clip != null) {
				
				clip.setFramePosition(clip.getFrameLength());
				clip.loop(1);
			}
		}
	}

	@Provides
	ItemDropSoundConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ItemDropSoundConfig.class);
	}
}
