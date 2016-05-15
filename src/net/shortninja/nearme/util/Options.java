package net.shortninja.nearme.util;

import net.shortninja.nearme.NearMe;

import org.bukkit.configuration.file.FileConfiguration;

public class Options
{
	private static FileConfiguration config = NearMe.get().getConfig();

	public String permissionNear = config.getString("permission-near");
	public String permissionBypass = config.getString("permission-bypass");
	public boolean showInvisible = config.getBoolean("show-invisible");
	public int maxCheck = config.getInt("max-check");
	public int maxPlayers = config.getInt("max-players");
	public long cooldown = config.getInt("cooldown") * 1000;
	
	public String messagePrefix = config.getString("prefix");
	public String messageSeparator = config.getString("separator-color");
	public String messagePlayer = config.getString("player-color");
	public String messageNear = config.getString("near");
	public String messageNotReady = config.getString("not-ready");
	public String messageNoPermission = config.getString("no-permission");
	public String messageInvalidArguments = config.getString("invalid-arguments");
	public String messageOnlyPlayers = config.getString("only-players");
}