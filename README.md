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
- Options for more AFK plugins?  
- Customizable messages  
- Commands?  
- Permissions?  
- Per-world option for skipping storms

## Changelog

v0.1:  
- Initial release (working state)