package net.shortninja.nearme.util;

import net.shortninja.nearme.NearMe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message
{
	private Options options = NearMe.get().options;
	
	public Message()
	{
		sendConsoleMessage("NearMe has been enabled!", false);
		sendConsoleMessage("Plugin created by Shortninja.", false);
	}
	
	public final String colorize(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
	
	public String message(String message)
	{
		String prefix = options.messagePrefix.length() > 0 ? options.messagePrefix + " " : "";
				
		return colorize(prefix + message);
	}
	
	public void sendMessage(Player player, String message, boolean prefix)
	{
		if(player == null || message.length() == 0)
		{
			return;
		}
		
		if(prefix)
		{
			player.sendMessage(message(message));
		}else player.sendMessage(message);
	}
	
	public void sendMessage(CommandSender sender, String message, boolean prefix)
	{
		if(sender == null || message.length() == 0)
		{
			return;
		}
		
		if(prefix)
		{
			sender.sendMessage(message(message));
		}else sender.sendMessage(message);
	}
	
	public void sendGlobalMessage(String message, boolean prefix)
	{
		if(message.length() == 0)
		{
			return;
		}
		
		if(prefix)
		{
			Bukkit.broadcastMessage(message(message));
		}else Bukkit.broadcastMessage(message);
	}
	
	public void sendConsoleMessage(String message, boolean isError)
	{
		String prefix = isError ? "&4[NearMe] &c" : "&2[NearMe] &a";
		
		Bukkit.getServer().getConsoleSender().sendMessage(colorize(prefix + message));
	}
	
	public String longLine()
	{
		return colorize("&7&m------------------------------------------------");
	}
	
	public String build(String[] args, int index)
	{
		StringBuilder builder = new StringBuilder();
		
		for(int i = index; i < args.length; i++)
		{
			builder.append(args[i] + " ");
		}
		
		return builder.toString().trim();
	}
}