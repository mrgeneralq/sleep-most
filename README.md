![sleep most banner](https://www.spigotmc.org/attachments/sleepmost-jpg.369756/)

# sleep-most

Sleep most is a bukkit/spigot plugin that allows server administrators to configure required
percentages for turning into day again.

## Features

Below you can find a list of all the implemented features in the plugin.

- configure how many percentage has to be reached
- configure all messages including colors
- configure the prefix for each message
- skip thunderstorms based on voting
- different messages for storm and night
- configure percentage required for each world (**new**)
-   **Anti spam**: configure amount of time for the messages to cool down before showing up again per player (**much requested**)

## How to install

In order to install the plugin, please follow the instructions below very carefully.

 1. Stop your server
 2. Put the .jar file into your plugins folder
 3. Start your server
 4. (optional*) Stop the server and change the config.yml in the sleep most folder. After that, start server again
 5. Start server
 6. Done!

## Updating existing sleep most (to new config file)

Every time an update is coming out with a more recent version of the plugin that contains a new property in the config file, the plugin will have to generate a new config file. Therefore, whenever you are updating your existing sleep-most plugin, use the following steps:

 1. Stop your server
 2. Remove the config.yml in the sleep most folder
 3. Start your server again

*NOTE: all the earlier configurations have to be re-configured. I'm looking for a solution so in the future, the new properties will automatically generate
