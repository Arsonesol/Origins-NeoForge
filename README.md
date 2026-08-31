# Origins (NeoForge) – Personal Fork

**⚠️ This is a personal fork created exclusively for my own server. It is not intended for public distribution or use.**  
The changes below are tailored to my specific gameplay needs and may break compatibility with other mods or setups. Use at your own risk.


This is a personal fork of the **[Origins-NeoForge](https://github.com/IAFEnvoy/Origins-NeoForge)** mod by IAFEnvoy.  
The original project is a full rewrite of the [Origins Mod](https://github.com/Apace100/origins-fabric) on the NeoForge platform.

**This mod is still under construction, some functions may not work properly.**

This mod provides an "origin" system. Each origin has special effects, and you can select one when joining a world or by using  
`Orb of Origin` items. You can also use datapacks to customise origins.


## FAQ

### Some powers missing?

NeoForge and Additional Entity Attributes provide some attributes which can replace them – just use `AttributePower` instead.

### Are datapacks for the Fabric version compatible with this mod?

Sadly, no. The Fabric version uses its own logic to load datapacks, while this mod loads them with vanilla methods. They have different data structures. Also, many powers have changed parameters, so extra changes are needed.

You can follow [this guide](https://docs.iafenvoy.com/docs/mod/origins/guides/porting/) to port your datapack to this mod.  
You can also try the [Auto Converter](https://docs.iafenvoy.com/docs/mod/origins/guides/porting/converter) – it may not do everything, but it can save you a lot of time.

**You can also join our Discord server to ask for porting help. I’ll help if I have spare time (free).**


## Custom Changes for My Server

This fork introduces the following modifications to better suit my server’s environment, especially when combined with food‑expanding mods like **Farmer's Delight**:

- **Enhanced Dietary Tagging** – In addition to the standard `meat.json` tag for meat‑based dishes, I have added a new **`vegan.json`** tag to properly categorise meatless and plant‑based meals. This creates a more nuanced food classification system that feels more logical with the variety of recipes added by Farmer’s Delight.  
  *Note*: This expanded tagging may complicate or break compatibility with other mods that rely on the original, unified dietary structure. This is an intentional trade‑off for my server’s balance.

- **Phantom Origin Rebalance** – The **Phantom** origin has been changed to be a **carnivore** (meat‑eater). In my opinion, this adjustment improves overall game balance by giving the Phantom a distinct dietary limitation that offsets its mobility advantages and fits its thematic identity better.

These changes are **not officially supported** and are provided as‑is for my personal use only. They will not be merged upstream.


## Credit

Special thanks to the following developers for ideas and some code:

- **IAFEnvoy** – Author of the [Origins-NeoForge](https://github.com/IAFEnvoy/Origins-NeoForge) port, which this fork is based upon.  
- **Apace** – Author of the [Origins Mod](https://github.com/Apace100/origins-fabric), open source under the MIT license.  
- **EdwinMindcraft** – Author of the [Forge port of Origins Mod](https://github.com/EdwinMindcraft/origins-forge), open source under the MIT license.  
- **UltrusBot** – Author of [Alternate Origin GUI](https://github.com/UltrusBot/AltOriginGui), a better origin selection screen, open source under the MIT license.  
- **ChrysanthCow** – Author of [Apugli](https://github.com/MerchantCalico/apugli), which extends actions, conditions and more, open source under the LGPL‑3.0 license.
