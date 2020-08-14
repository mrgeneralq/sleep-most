package me.qintinator.sleepmost.repositories;

import me.qintinator.sleepmost.interfaces.IUpdateRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateRepository implements IUpdateRepository {

	private final String resourceId;

	public UpdateRepository(String resourceId) 
	{
		this.resourceId = resourceId;
	}

	@Override
	public String getLatestVersion() {
		try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
				Scanner scanner = new Scanner(inputStream)) {
			if (scanner.hasNext()) {
				return scanner.next();
			}
		}
		catch (IOException exception) {
			return null;
		}
		return null;
	}
}