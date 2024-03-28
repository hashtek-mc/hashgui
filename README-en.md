# 🎨 ️HashGui

Library that lets you create customized GUIs and items quickly and easily.

This library has two main classes:
* **HashItem:** Item builder
* **HashGui:** GUI builder

[🇫🇷 Egalement disponible en Français !](https://github.com/hashtek-mc/hashgui/blob/main/README.md)

## HashItem

### Usage

**Custom item creation:**

```java
HashItem item = new HashItem(Material.ENDER_PEARL, 10)
  .setName("Hi")
  .setLore(Arrays.asList(
    "Line 1",
    "Line 2"
  ))
  .addLore("Line 3")
  .setFlags(Arrays.asList(
    ItemFlag.HIDE_ATTRIBUTES,
    ItemFlag.HIDE_ENCHANTS
  ))
  .addFlag(ItemFlag.HIDE_UNBREAKABLE)
  .addEnchant(Enchantment.DURABILITY, 3)
  .setUnbreakable(true)
  .build();

Inventory#addItem(item.getItemStack());
```

`⚠️` Don't forget to `build()` the item before using it!

`⚠️` Always import from `org.bukkit` package.

`ℹ️` Also see [Material](https://helpch.at/docs/1.8/org/bukkit/Material.html), [ItemFlag](https://helpch.at/docs/1.8/index.html?org/bukkit/inventory/ItemFlag.html) and [Enchantment](https://helpch.at/docs/1.8/index.html?org/bukkit/enchantments/Enchantment.html) enums.
(from `org.bukkit`).

### Features

* `setType()`: Item
* `setAmount()`: Item amount
* `setDurability()`: Item's durability
* `setData()`: Item's data
* `setName()`: Item's name
* `setLore()`: Replaces item's description
* `addLore()`: Appends a string to item's description
* `setFlags()`: Replaces item's flags
* `addFlag()`: Adds a flag to the item
* `setUnbreakable()`: Makes the item unbreakable
* `addEnchant()`: Adds an enchantment to the item
* `removeEnchant()`: Removes an enchantment from the item
* `build()`: Builds the item so it can be used

### 🖱️ Click handler

It is possible to define the action executed when an item is clicked in an inventory.

**Example:**
```java
ClickHandler clickHandler = new ClickHandler()
    .addClickType(ClickType.LEFT)
    .setClickAction((Player player, HashGui gui, ItemStack clickedItem, int clickedSlot) -> {
        // Actions to do when item is clicked.
    });

HashItem item = new HashItem(Material.COMPASS)
    .addClickHandler(clickHandler);
    .build(guiManager);
```

`⚠️` `guiManager` (in the `build()` function) must be an instance of [HashGuiManager](#hashguimanager),
which must be stored at the root of your plugin.
This instance takes care of detecting clicks and executing the appropriate action depending on the item.

`⚠️` Item targeting is based on its `displayName`, so be careful not to give the same name to two items
if you don't want them to execute the same thing.
If two items must have the same name but a different click handler, then mess with the color codes so that it's not
visible from the player's point of view 😉 (`"§cHi"` and `"§r§cHi"` are different but look the same on player's POV)

`ℹ️` See [ClickType](https://helpch.at/docs/1.8/org/bukkit/event/inventory/ClickType.html) enum (from `org.bukkit`).

### 🫱 Interaction handler

It is possible to define the action executed when a player interacts with an item.

**Example:**
```java
InteractionHandler interactionHandler = new InteractionHandler()
    .addInteractionType(Action.RIGHT_CLICK_AIR)
    .setClickAction((Player player, ItemStack clickedItem, int clickedSlot) -> {
        // Actions to do when item is interacted with.
    });
    
HashItem item = new HashItem(Material.COMPASS)
    .addInteractionHandler(interactionHandler)
    .build(guiManager);
```

`⚠️` Just like ClickHandler, `guiManager` (in the `build()` function) must be an instance of
[HashGuiManager](#hashguimanager), which must be stored at the root of your plugin.
This instance takes care of detecting clicks and executing the appropriate action depending on the item.

`⚠️` Item targeting is based on its `displayName`, so be careful not to give the same name to two items
if you don't want them to execute the same thing.
If two items must have the same name but a different click handler, then mess with the color codes so that it's not
visible from the player's point of view 😉 (`"§cHi"` and `"§r§cHi"` are different but look the same on player's POV)

`ℹ️` See [Action](https://helpch.at/docs/1.8/index.html?org/bukkit/event/block/Action.html) enum (from `org.bukkit`).

### HashGuiManager

In order to make click & interaction handlers working, you need to create an instance of `HashGuiManager` at the root
of your plugin and give this instance to your custom item build.

**Example:**
```java
public class Test extends JavaPlugin {
    
    private PluginManager pluginManager;
    private HashGuiManager guiManager;
    
    
    /* Variable initialization at server startup */
    @Override
    public void onEnable()
    {
        this.pluginManager = this.getServer().getPluginManager();
        this.setupGuiManager();
        
        // Your code here
    }
    
    /* Manager initialization and configuration */
    private void setupGuiManager()
    {
        this.guiManager = new HashGuiManager(this, this.pluginManager);
        this.guiManager.setup();
    }
    
    
    /* GUI Manager's getter */
    public HashGuiManager getGuiManager()
    {
        return this.guiManager;
    }
    
}
```

`⚠️` It is strongly recommended to only have one instance of HashGuiManager instance in your plugin
(in fact, I forbid you to have more than one).

### HashSkull

**Creation of a player's skull:**
```java
HashItem playerSkull = new HashSkull()
    .setOwner("Shuvly") // HashSkull
    .setName("Shuvly's head") // HashItem
    .build();
```

`⚠️` Make all changes to `HashSkull` first before making changes to `HashItem`.

**Creation of a custom skull:**
```java
HashItem customSkull = new HashSkull()
    .setTexture("...") // Your texture here
    .setName("Custom head")
    .build();
```

Texture example (in `base64`): `eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWY0OGMxOTU1NTUwZWFlZGU1NzEzYTdiY2NjOWE0YWUyZTZlMTcxZTI5YWMxYzcxYzBlM2QxYWJiNzI5MGNjYSJ9fX0=`

`⚠️` Make all changes to `HashSkull` first before making changes to `HashItem`.

`ℹ️` You can find custom skulls on [Minecraft Heads](https://minecraft-heads.com/).\
When you're on the head page, scroll down to the "For developers" section to find the texture value in `base64`.

## HashGui

### Usage

**GUI creation:**
```java
HashItem item = new HashItem(Material.SIGN)
  .setName("Settings")
  .addLore("Right click to access parameters")
  .build();

HashGui gui = new HashGui("Menu", 5)
    .setItem(3, item);
    
gui.open(player);
```

### Features

* `open()`: Opens the GUI to a player
* `close()`: Closes the GUI to a player
* `update()`: Refreshes a player's GUI
* `setItem()`: Sets an item in the GUI

### Masks

Masks (`Mask`) are used to create a GUI layout.

For example, if you want to make a border like this :

![](https://cdn.discordapp.com/attachments/1201670734095859812/1204883310489378907/image.png?ex=65d65a06&is=65c3e506&hm=85681f65261a9a6a3cd8a45c6018a3b2efbaa1d6c5cd1e6ec83f836e6edbf44a&)

... a normal code would be :

```java
ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
ItemStack grass = new ItemStack(Material.GRASS);
ItemStack glowstone = new ItemStack(Material.GLOWSTONE);
ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);

for (int i = 0 ; i < 10 ; i++) {
    menu.setItem(i, glass);
}
for (int i = 17 ; i < 26 ; i++) {
    menu.setItem(i, glass);
}
menu.setItem(11, grass);
menu.setItem(13, glowstone);
menu.setItem(15, bookshelf);
```

But it's not really effective...\
Here comes the masks. The code above then becomes:

```java
ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
ItemStack grass = new ItemStack(Material.GRASS);
ItemStack glowstone = new ItemStack(Material.GLOWSTONE);
ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);

/*                   ⬇️ Must be a HashGUI instance. */
Mask mask = new Mask(menu);

mask.setItem('s', glass);
    .setItem('g', grass);
    .setItem('l', glowstone);
    .setItem('b', bookshelf);
    
/*            ⬇️ Must be 9 characters long. */
mask.pattern("sssssssss")
    .pattern("s g l b s")
    .pattern("sssssssss");
    
mask.apply();
```

Way better isn't it?

If a letter has no assigned item, this one will be used:

![](https://cdn.discordapp.com/attachments/1201670734095859812/1204886714057752636/image.png?ex=65d65d32&is=65c3e832&hm=f396f4b9e3373d56fe0bd5c34b2f4d21429b7bbe04653fcfc44040f88412114a&)

`⚠️` Space character (` `) can't be used, as it serves as void.

## Made with 💜 by [Lysandre B.](https://github.com/Shuvlyy) ・ [![wakatime](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d7a18-67ef-47e3-a6c4-5c8cc4b45021.svg)](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d7a18-67ef-47e3-a6c4-5c8cc4b45021) + [![wakatime](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d794b-8bf6-46ef-acb3-549287335474.svg)](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d794b-8bf6-46ef-acb3-549287335474)
