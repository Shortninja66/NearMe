package net.shortninja.nearme.near;

import java.util.List;

import net.shortninja.nearme.NearMe;
import net.shortninja.nearme.util.Options;

public class MessageBuilder
{
	private Options options = NearMe.get().options;
	private List<ClosePlayer> list;
	
	public MessageBuilder(List<ClosePlayer> list)
	{
		this.list = list;
	}
	
	public String getMessage()
	{
		StringBuilder builder = new StringBuilder();
		String suffix = options.messageSeparator + ", ";
		
		if(list.isEmpty())
		{
			return options.messagePlayer + "None";
		}
		
		for(int i = 0; i < list.size(); i++)
		{
			ClosePlayer player = list.get(i);
			
			if((i + 2) == list.size())
			{
				suffix = list.size() == 2 ? options.messageSeparator + " and " : options.messageSeparator + ", and ";
			}else if((i + 1) == list.size())
			{
				suffix = options.messageSeparator;
			}
			
			builder.append(options.messagePlayer + player.getName() + "(" + player.getDistance() + ")" + suffix);
		}
		
		return builder.toString().trim();
	}
}