# PercentSleep

Simple Spigot (Minecraft server) plugin to automatically skip the night if a set percentage of the people in the world are sleeping.

## Features:  
- Ignores vanished players (VanishNoPacket) and AFK players (Esentials)  
- You don't need VanishNoPacket or Essentials, it'll support them if they exist on the server.
- Separate percentage per world (only the overworld counts, people in other dimensions aren't counted)  
- Option to stop downfall when the night is skipped  
- Option to ignore certain worlds (won't calculate or broadcast for these worlds)  
- Configurable display names for broadcasts in worlds

## Todo:  
- Add options for ignoring vanished/afk players.  
- Customizable messages  
- Ratelimit for messages to prevent spam
- Per-world broadcast instead of broadcasting to everyone
- Options for more AFK plugins?  
- Commands? 
- Permissions?  

## Changelog

v0.4:  
- Added support for SuperVanish/PremiumVanish! (Thanks a lot, MyzelYam)

v0.3:  
- Fixed a bug where Essentials/Vanish didn't get hooked
- More final variables
- Fixed a bug where vanished/afk players could sleep and mess up the calculation (dividing by zero, oh noes)

v0.2:  
### Please delete your old configuration before upgrading to take advantage of the new configuration options.  
**Features**  
- Added per-world config for skipping storms
- Added options for ignoring vanished/afk players when counting  
- Tracks config version, should be able to auto-upgrade config after this  

**Code/bugfixes**
- Don't make 2 plugin objects for each world ya dingus
- Less hacky way of getting the config for the world class
- Don't broadcast on player leave


v0.1:  
- Initial release (working state)
