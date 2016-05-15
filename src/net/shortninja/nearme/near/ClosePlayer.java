package net.shortninja.nearme.near;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClosePlayer
{
	private String playerName;
	private int distance;
	
	public ClosePlayer(String playerName, int distance)
	{
		this.playerName = playerName;
		this.distance = distance;
	}
	
	public String getName()
	{
		return playerName;
	}
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(playerName);
	}
	
	public int getDistance()
	{
		return distance;
	}
}