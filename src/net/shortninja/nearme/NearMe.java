package net.shortninja.nearme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.shortninja.nearme.near.ClosePlayer;
import net.shortninja.nearme.near.MessageBuilder;
import net.shortninja.nearme.near.RadiusFactory;
import net.shortninja.nearme.util.Message;
import net.shortninja.nearme.util.Options;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NearMe extends JavaPlugin
{
	private static NearMe plugin;
	private static Map<String, Long> lastNear = new HashMap<String, Long>();
	public Options options;
	public Message message;

	@Override
	public void onEnable()
	{
		plugin = this;
		saveDefaultConfig();
		
		options = new Options();
		message = new Message();
	}

	@Override
	public void onDisable()
	{
		message.sendConsoleMessage("NearMe has been disabled", true);
		
		plugin = null;
		lastNear = null;
		options = null;
		message = null;
	}

	public static NearMe get()
	{
		return plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(label.equalsIgnoreCase("near"))
		{
			String argument = args.length == 0 ? "" : args[0];
			int maxRadius = getMaxRadius(sender, argument);
			
			if(maxRadius != 0)
			{
				Player player = (Player) sender;
				long last = lastNear.containsKey(player.getUniqueId().toString()) ? lastNear.get(player.getUniqueId().toString()) : 0;
				long now = System.currentTimeMillis();
				 
				if(now - last < options.cooldown && !sender.hasPermission(options.permissionBypass))
				{
					long remaining = (options.cooldown + 1000) - (now - last);
					message.sendMessage(sender, options.messageNotReady.replace("%seconds%", String.format("%d", TimeUnit.MILLISECONDS.toSeconds(remaining))), true);
				}else
				{
					String nearbyPlayersMessage = options.messageNear.replace("%players%", getNearbyPlayers(player, maxRadius)).replace("%radius%", Integer.toString(maxRadius));
					
					message.sendMessage(sender, nearbyPlayersMessage, true);
					lastNear.put(player.getUniqueId().toString(), now);
				}
			}
		}

		return true;
	}
	
	private int getMaxRadius(CommandSender sender, String argument)
	{
		int maxRadius = 0;
		
		if(!(sender instanceof Player))
		{
			message.sendMessage(sender, options.messageOnlyPlayers, true);
			return 0;
		}else
		{
			RadiusFactory factory = new RadiusFactory(sender);
			maxRadius = factory.getRadius();
			
			if(!(argument.equals("")))
			{
				if(isInteger(argument))
				{
					int radius = Integer.valueOf(argument);
					radius = radius <= 0 ? 1 : radius;
					
					if(factory.hasRadius(radius))
					{
						maxRadius = radius;
					}
				}else
				{
					message.sendMessage(sender, options.messageInvalidArguments, true);
					return 0;
				}
			}
		}
		
		if(maxRadius == 0)
		{
			message.sendMessage(sender, options.messageNoPermission, true);
		}
		
		return maxRadius;
	}
	
	private boolean isInteger(String string)
	{
		boolean isInteger = true;
		
		try
		{
			Integer.parseInt(string);
		}catch(NumberFormatException exception)
		{
			isInteger = false;
		}
		
		return isInteger;
	}
	
	private String getNearbyPlayers(Player player, int radius)
	{
		List<ClosePlayer> nearbyPlayers = new ArrayList<ClosePlayer>();
		Collection<? extends Player> playersCollection = Bukkit.getOnlinePlayers();
		Player[] playersArray = playersCollection.toArray(new Player[playersCollection.size()]);
		Location location = player.getLocation();

		int radiusSquared = radius * radius;
		
		for(int i = 0; i < playersArray.length; i++)
		{
			if((i - 1) == options.maxPlayers)
			{
				break;
			}
			
			int distanceSquared = (int) playersArray[i].getLocation().distanceSquared(location);
			
			if(distanceSquared <= radiusSquared && !playersArray[i].getName().equals(player.getName()) && !playersArray[i].hasPermission(options.permissionBypass))
			{
				nearbyPlayers.add(new ClosePlayer(playersArray[i].getName(), (int) Math.sqrt(distance)));
			}
		}
		
		Collections.sort(nearbyPlayers, new Comparator<ClosePlayer>()
		{
			@Override
			public int compare(ClosePlayer first, ClosePlayer second)
			{
				Integer firstDistance = first.getDistance();
				Integer secondDistance = second.getDistance();
				
				return firstDistance.compareTo(secondDistance);
			}
		});
		
		return new MessageBuilder(nearbyPlayers).getMessage();
	}
}