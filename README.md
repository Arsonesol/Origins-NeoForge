# Origins (NeoForge)

This fork is a custom adaptation designed specifically for my personal server, with a primary focus on refining the dietary system to better complement food-expanding mods like Farmer's Delight.

Key changes include:

Enhanced Dietary Tags: I've retained the standard meat.json tag for meat-based dishes while introducing a new vegan.json tag to properly categorize meatless and plant-based meals. This creates a much more nuanced food classification system that feels more logical when dealing with diverse recipes.

Disclaimer: Please be aware that this expanded tagging system may complicate or break compatibility with other mods that rely on the conventional, unified dietary structure. Use this fork with that in mind.

Origin Balancing (Phantom): I've rebalanced the Phantom origin by making it a carnivore (meat-eater). In my opinion, this adjustment significantly improves overall game balance by giving the Phantom a distinct and fair dietary limitation that aligns better with its thematic identity and offsets its mobility advantages. 

**This mod is still under construction, some functions may not work properly.**

This mod is a full rewrite of the [Origins Mod](https://github.com/Apace100/origins-fabric) on NeoForge platform.

This mod provide an "origin" system. Each of them have special effects, and you can select them when join world or use
`Orb of Origin` items. Also, you can use datapacks to customize origins.

## FAQ

### Some powers missing?

NeoForge and Additional Entity Attributes provides some attributes which can replace them, just use AttributePowers
instead.

### Are datapacks for Fabric version capable with this mod?

Sadly not, Fabric version use their own logic to load datapacks but this mod load them with vanilla methods. They have
different data structure. Also, a lot of powers changed parameters, so you need extra changes to make them work.

You can follow [this guide](https://docs.iafenvoy.com/docs/mod/origins/guides/porting/) to port your datapack to this
mod. You can also try to use [Auto Converter](https://docs.iafenvoy.com/docs/mod/origins/guides/porting/converter) to
convert your datapack, it may not do everything but can save you a lot of time.

**You can also join our Discord server to ask for porting help. I'll help if I have spare time (Free).**

## Credit

Special thanks to the following developers for ideas and some code:

- Apace: Author of the [`Origins Mod`](https://github.com/Apace100/origins-fabric), open source under `MIT` license.
- EdwinMindcraft: Author of the [`Forge` port of `Origins Mod`](https://github.com/EdwinMindcraft/origins-forge), open
  source under `MIT` license.
- UltrusBot: Author of [`Alternate Origin GUI`](https://github.com/UltrusBot/AltOriginGui), a better choose origin
  screen, open source under `MIT` license.
- ChrysanthCow: Author of [`Apugli`](https://github.com/MerchantCalico/apugli), extends actions, conditions and so on,
  open source under `LGPL-3.0` license.

## Discord

https://discord.gg/NDzz2upqAk
