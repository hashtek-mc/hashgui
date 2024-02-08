# 🎨 ️HashGUI

Librairie qui permet de créer des menus et des items personnalisés simplement
et rapidement.

Cette librairie est constituée de deux classes principales :
* **HashItem :** Constructeur d'item
* **HashGUI :** Constructeur de GUI


## HashItem

### Utilisation

**Création d'un item personnalisé :**

```java
HashItem item = new HashItem()
  .setType(Material.ENDER_PEARL)
  .setAmount(10)
  .setTitle("Bonjour")
  .setLore(Arrays.asList(
    "Ligne 1",
    "Ligne 2",
  ))
  .addLore("Ligne 3")
  .setFlags(Arrays.asList(
    ItemFlag.HIDE_ATTRIBUTES,
    ItemFlag.HIDE_ENCHANTS,
  ))
  .addFlag(ItemFlag.HIDE_UNBREAKABLE)
  .setUnbreakable(true)
  .build();

Inventory#addItem(item.getItemStack());
```

`⚠️` Ne pas oublier de `build()` l'item avant de l'utiliser !\
`⚠️` Importez toujours depuis le package `org.bukkit`

### Fonctionnalités

* `setType()` : Item
* `setAmount()` : Nombre d'items
* `setDurability()` : Durabilité de l'item
* `setData()` : Données (byte) de l'item
* `setTitle()` : Titre de l'item
* `setLore()` : Remplace la description de l'item
* `addLore()` : Ajoute une ligne à la description de l'item
* `setFlags()` : Remplace les flags de l'item
* `addFlag()` : Ajoute un flag aux flags de l'item
* `setUnbreakable()` : Rend l'item incassable
* `build()` : Construit l'item pour qu'il soit utilisable.

### Click Handler

Il est possible de définir l'action exécutée lors d'un clic sur l'item.

**Exemple :**
```java
ClickHandler clickHandler = new ClickHandler()
    .addClickType(ClickType.LEFT)
    .setClickAction((Player player, HashGUI gui, ItemStack clickedItem, int clickedSlot) -> {
        // Actions à faire lors du clic.
    });

HashItem item = new HashItem(Material.COMPASS)
    .addClickHandler(clickHandler);
    .build();
```

## HashGUI

### Utilisation

**Création d'un menu personnalisé :**
```java
HashItem item = new HashItem()
  .setType(Material.SIGN)
  .setTitle("Paramètres")
  .addLore("Cliquez pour accéder aux paramètres")
  .build();

HashGUI gui = new HashGUI("Menu", 5)
    .setItem(3, item);
    
gui.open(player);
```

### Masks

Les masques (Mask) sont utilisés pour créer un agencement d'items dans une GUI.

Par exemple, si vous voulez faire une bordure comme ceci :

![](https://cdn.discordapp.com/attachments/1201670734095859812/1204883310489378907/image.png?ex=65d65a06&is=65c3e506&hm=85681f65261a9a6a3cd8a45c6018a3b2efbaa1d6c5cd1e6ec83f836e6edbf44a&)

... un code normal serait :

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

Pas dingue, n'est-ce pas ? Et c'est là qu'interviennent les masques. Le code
ci-dessus deviendrait :

```java
ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
ItemStack grass = new ItemStack(Material.GRASS);
ItemStack glowstone = new ItemStack(Material.GLOWSTONE);
ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);

/*                   ⬇️ Doit être une instance de HashGUI. */
Mask mask = new Mask(menu);

mask.setItem('s', glass);
    .setItem('g', grass);
    .setItem('l', glowstone);
    .setItem('b', bookshelf);
    
mask.pattern("sssssssss")
    .pattern("s g l b s")
    .pattern("sssssssss");
    
mask.apply();
```

Bien plus propre n'est-ce pas ?

Si une lettre n'a pas de bloc assigné, cet item sera placé :

![](https://cdn.discordapp.com/attachments/1201670734095859812/1204886714057752636/image.png?ex=65d65d32&is=65c3e832&hm=f396f4b9e3373d56fe0bd5c34b2f4d21429b7bbe04653fcfc44040f88412114a&)

`⚠️` Le caractère espace (` `) ne peut pas être assigné à un item, étant donné qu'il
sert de vide.

## Mises à jour à venir

* Gestion des enchantements
* Clics hors d'une GUI (avec la main)

## Fait avec 💜 par [Lysandre B.](https://github.com/Shuvlyy) ・ [![wakatime](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d7a18-67ef-47e3-a6c4-5c8cc4b45021.svg)](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d7a18-67ef-47e3-a6c4-5c8cc4b45021) + [![wakatime](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d794b-8bf6-46ef-acb3-549287335474.svg)](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d794b-8bf6-46ef-acb3-549287335474)