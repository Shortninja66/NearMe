package net.shortninja.nearme.near;

import java.util.HashSet;
import java.util.Set;

import net.shortninja.nearme.NearMe;
import net.shortninja.nearme.util.Options;

import org.bukkit.command.CommandSender;

public class RadiusFactory
{
	private Options options = NearMe.get().options;
	private CommandSender sender;
	private int highestRadius = 0;
	private String currentPermission = options.permissionNear.replace("%radius%", Integer.toString(10));
	private Set<Integer> radiuses = new HashSet<Integer>();
	
	public RadiusFactory(CommandSender sender)
	{
		this.sender = sender;
	}
	
	public int getRadius()
	{
		for(int i = 10; i <= options.maxCheck; i += 10)
		{
			if(sender.hasPermission(currentPermission))
			{
				highestRadius = i > 10 ? i - 10 : i;
				radiuses.add(highestRadius);
			}
			
			currentPermission = options.permissionNear.replace("%radius%", Integer.toString(i));
		}
		
		if(highestRadius == (options.maxCheck - 10) && sender.hasPermission(options.permissionNear.replace("%radius%", Integer.toString(options.maxCheck))))
		{
			highestRadius = options.maxCheck;
		}
		
		return highestRadius;
	}
	
	public boolean hasRadius(int radius)
	{
		boolean hasRadius = false;
		
		for(int r : radiuses)
		{
			if(radius <= r)
			{
				hasRadius = true;
				break;
			}
		}
		
		return hasRadius;
	}
}