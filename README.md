# 🎨 ️HashGui

Librairie qui permet de créer des menus et des items personnalisés simplement
et rapidement.

Cette librairie est constituée de deux classes principales :
* **HashItem :** Constructeur d'item
* **HashGui :** Constructeur de GUI

[🇬🇧 Also available in English!](https://github.com/hashtek-mc/hashgui/blob/main/README-en.md)

## Installation

Après installation, le .jar se trouve dans le dossier `builds/libs/`.

### Linux / MacOS :

```shell
./gradlew make
```

### Windows

```shell
.\gradlew.bat make
```

## HashItem

### Utilisation

**Création d'un item personnalisé :**

```java
HashItem item = new HashItem(Material.ENDER_PEARL, 10)
  .setName("Bonjour")
  .setLore(Arrays.asList(
    "Ligne 1",
    "Ligne 2"
  ))
  .addLore("Ligne 3")
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

> [!WARNING]
> Ne pas oublier de `build()` l'item avant de l'utiliser !\
> Importez toujours depuis le package `org.bukkit`.

> [!TIP]
> Renseignez-vous sur les enums [Material](https://helpch.at/docs/1.8/org/bukkit/Material.html), [ItemFlag](https://helpch.at/docs/1.8/index.html?org/bukkit/inventory/ItemFlag.html) et [Enchantment](https://helpch.at/docs/1.8/index.html?org/bukkit/enchantments/Enchantment.html)
(du package `org.bukkit`).

### Fonctionnalités

* `setType()` : Item
* `setAmount()` : Nombre d'items
* `setDurability()` : Durabilité de l'item
* `setData()` : Données (byte) de l'item
* `setName()` : Nom de l'item
* `setLore()` : Remplace la description de l'item
* `addLore()` : Ajoute une ligne à la description de l'item
* `setFlags()` : Remplace les flags de l'item
* `addFlag()` : Ajoute un flag aux flags de l'item
* `setUnbreakable()` : Rend l'item incassable
* `addEnchant()` : Ajoute un enchantement à l'item
* `removeEnchant()` : Retire un enchantement à l'item
* `build()` : Construit l'item pour qu'il soit utilisable

### Handlers

Un Handler est un morceau de code qui va être exécuté lorsqu'un joueur va faire une certaine\
action avec un item.

### Utilisation (exemple : Click handler)

```java
ClickHandler clickHandler = new ClickHandler()
    .addClickType(ClickType.LEFT)
    .setClickAction((Player player, HashGui gui, ItemStack clickedItem, int clickedSlot) -> {
        // Actions à faire lors du clic.
    });

HashItem item = new HashItem(Material.COMPASS)
    .addClickHandler(clickHandler);
    .build(guiManager);
```

> [!WARNING]
> `guiManager` (dans la fonction `build()`) doit être une instance de
[HashGuiManager](#hashguimanager), qui doit être stocké à la racine de votre plugin.
Cette instance s'occupe de détecter les clics et d'exécuter ce qu'il faut en
fonction de l'item.

> [!WARNING]
> Le ciblage de l'item se fait à partir de son `displayName`, donc faites
bien attention à ne pas donner le même nom à deux items si vous ne voulez pas
qu'ils exécutent la même chose.
Si deux items doivent avoir le même nom mais
un click handler différent, alors jouez avec les codes couleurs pour que ça ne
soit pas visible du point de vue du joueur 😉
(`"§cTest"` et `"§r§cTest"` sont différents mais rendent pareil à l'écran)

### Click handler

Il est possible de définir l'action exécutée lors d'un clic sur l'item dans un
inventaire.

#### Fonctionnalités :

- Type de click (clic gauche, clic droit, shift + clic...)
```java
ClickHandler#addClickType(ClickType.LEFT);
ClickHandler#addClickTypes(ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT); // Ajoute plusieurs types de clics en une fois.
ClickHandler#addAllClickTypes(); // Ajoute tous les types de clic possibles.
```

> [!TIP]
> Renseignez-vous sur l'enum [ClickType](https://helpch.at/docs/1.8/org/bukkit/event/inventory/ClickType.html) (du package `org.bukkit`).

### 🫱 Interaction handler

Il est possible de définir l'action exécutée lors d'une interaction avec l'item.

#### Fonctionnalités :

- Type d'interaction (clic gauche, clic droit, dans l'air, ou pas...)
```java
InteractHandler#addInteractType(Action.LEFT_CLICK_AIR);
InteractHandler#addInteractTypes(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK); // Ajoute plusieurs types d'interactions en une fois.
InteractHandler#addAllInteractTypes(); // Ajoute tous les types d'interaction possibles.
```

> [!TIP]
> Renseignez-vous sur l'enum [Action](https://helpch.at/docs/1.8/index.html?org/bukkit/event/block/Action.html) (du package `org.bukkit`).

### 🫱 Hold handler

Il est possible de définir l'action exécutée lorsqu'un joueur prend (ou retire) un item dans sa main.

#### Fonctionnalités :

- Action de maintien (action exécutée lorsque le joueur prend l'item dans sa main)
```java
HoldAction action = (Player player, ItemStack item, int slot) -> {
    // ...
};

HoldHandler#setHoldAction(action);
```

- Action de non-maintien (action exécutée lorsque le joueur retire l'item de sa main)
```java
HoldAction action = (Player player, ItemStack item, int slot) -> {
    // ...
};

HoldHandler#setNotHoldAction(action);
```

> [!CAUTION]
> Les armures sont compatibles avec `HoldHandler`.\
> Cependant, si vous utilisez `PlayerInventory#setHelmet` (ou une autre méthode pour définir une pièce d'armure),
> `HoldHandler` ne le détectera pas automatiquement.\
> Vous devrez appeler la fonction `HashGuiHold#refreshArmorState(Player player)` pour actualiser la détection.

### 🫱 Hit handler

Il est possible de définir l'action exécutée lorsqu'un joueur tape un autre joueur.

#### Fonctionnalités :

- Mort uniquement (n'exécute l'action que si le coup tue l'autre joueur)
```java
HitHandler#setOnlyKill(true);
```

### 🫱 Destroy handler

Il est possible de définir l'action exécutée lorsqu'un joueur casse un bloc.


### HashGuiManager

Pour que les différents handlers fonctionnent,
vous devez créer une instance de `HashGuiManager` à la racine de votre plugin
et donner cette instance lors du build de votre item personnalisé.

**Exemple :**
```java
public class Test extends JavaPlugin {
    
    private PluginManager pluginManager;
    private HashGuiManager guiManager;
    
    
    /* Initialisation des variables au lancement du serveur */
    @Override
    public void onEnable()
    {
        this.pluginManager = this.getServer().getPluginManager();
        this.setupGuiManager();
        
        // Reste de votre code
    }
    
    
    /* Initialisation et configuration du manager */
    private void setupGuiManager()
    {
        this.guiManager = new HashGUIManager(this, this.pluginManager);
        this.guiManager.setup();
    }
    
    
    /* Getter du manager */
    public HashGuiManager getGuiManager()
    {
        return this.guiManager;
    }
    
}
```

> [!TIP]
> Il est très fortement recommandé de n'avoir qu'une seule instance de
HashGuiManager dans votre plugin (en fait je vous interdis d'en avoir plusieurs).

### HashSkull

**Création de la tête d'un joueur :**
```java
HashItem playerSkull = new HashSkull()
    .setOwner("Shuvly") // HashSkull
    .setName("Tête de Shuvly") // HashItem
    .build();
```

> [!TIP]
> Exécutez d'abord toutes les modifications relatives à `HashSkull` avant d'exécuter
les modifications relatives à `HashItem`.

**Création d'une tête personnalisée :**
```java
HashItem customSkull = new HashSkull()
    .setTexture("...") // Votre texture
    .setName("Tête personnalisée")
    .build();
```

Exemple de texture (en `base64`) : `eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWY0OGMxOTU1NTUwZWFlZGU1NzEzYTdiY2NjOWE0YWUyZTZlMTcxZTI5YWMxYzcxYzBlM2QxYWJiNzI5MGNjYSJ9fX0=`

> [!TIP]
> Exécutez d'abord toutes les modifications relatives à `HashSkull` avant d'exécuter
les modifications relatives à `HashItem`.

> [!TIP]
> Vous pouvez trouver des têtes personnalisées sur [Minecraft Heads](https://minecraft-heads.com/).\
Quand vous êtes sur la page d'une tête, descendez jusqu'à la section "For developers" pour trouver la valeur
de la texture en `base64`.

## Guis

## HashGui

### Utilisation

**Création d'un menu personnalisé :**
```java
HashItem item = new HashItem(Material.SIGN)
  .setName("Paramètres")
  .addLore("Cliquez pour accéder aux paramètres")
  .build();

String title = "Menu";
int linesAmount = 5;

HashGui gui = new HashGui(title, linesAmount)
    .setItem(3, item);
    
gui.open(player);
```

### Fonctionnalités

* `open()` : Ouvre une GUI à un joueur
* `close()` : Ferme la GUI d'un joueur
* `update()` : Rafraîchit la GUI d'un joueur
* `setItem()` : Place un item dans la GUI

## Masks

Les masques (`Mask`) sont utilisés pour créer un agencement d'items dans une GUI.

Par exemple, si vous voulez faire une bordure comme ceci :

![](https://cdn.discordapp.com/attachments/1201670734095859812/1204883310489378907/image.png?ex=65d65a06&is=65c3e506&hm=85681f65261a9a6a3cd8a45c6018a3b2efbaa1d6c5cd1e6ec83f836e6edbf44a&)

... un code normal serait :

```java
Inventory gui;

ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
ItemStack grass = new ItemStack(Material.GRASS);
ItemStack glowstone = new ItemStack(Material.GLOWSTONE);
ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);

for (int i = 0 ; i < 10 ; i++) {
    gui.setItem(i, glass);
}
for (int i = 17 ; i < 26 ; i++) {
    gui.setItem(i, glass);
}
gui.setItem(11, grass);
gui.setItem(13, glowstone);
gui.setItem(15, bookshelf);
```

Pas dingue, n'est-ce pas ? Et c'est là qu'interviennent les masques. Le code
ci-dessus devient alors :

```java
HashGui gui; // Peut être une instance de Inventory.

ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
ItemStack grass = new ItemStack(Material.GRASS);
ItemStack glowstone = new ItemStack(Material.GLOWSTONE);
ItemStack bookshelf = new ItemStack(Material.BOOKSHELF);

Mask mask = new Mask(gui);

mask.setItem('s', glass);
    .setItem('g', grass);
    .setItem('l', glowstone);
    .setItem('b', bookshelf);
    
/*            ⬇️ Doit faire 9 caractères de long obligatoirement (taille d'une ligne). */
mask.pattern("sssssssss")
    .pattern("s g l b s")
    .pattern("sssssssss");
    
mask.apply();
```

Bien plus propre n'est-ce pas ?

Si une lettre n'a pas d'item assigné, celui-ci sera placé :

![](https://cdn.discordapp.com/attachments/1201670734095859812/1204886714057752636/image.png?ex=65d65d32&is=65c3e832&hm=f396f4b9e3373d56fe0bd5c34b2f4d21429b7bbe04653fcfc44040f88412114a&)

> [!CAUTION]
> Le caractère espace (` `) ne peut pas être assigné à un item, étant donné qu'il
sert de vide.


## PaginatedHashGui

`PaginatedHashGui` est une `HashGui` avec un système de pages.

### Utilisation

```java
String title = "Menu paginé";
int linesAmount = 6;

PaginatedHashGui gui = new PaginatedHashGui(title, linesAmount, guiManager); // guiManager doit être une instance de HashGuiManager.

HashItem previousPage = new HashItem(Material.ARROW)
    .setName("Page précédente");

HashItem nextPage = new HashItem(Material.ARROW)
    .setName("Page suivante");

gui.setPreviousPageItem(previousPage); // Lors du clic sur previousPage, la GUI se rafraîchira à la page précédente.
gui.setNextPageItem(nextPage); // Lors du clic sur nextPage, la GUI se rafraîchira à la page suivante.
```

#### Fonctionnalités

* `setPreviousPageItem(HashItem item)` : Actualise la GUI à la page précédente (si possible)
* `setNextPageItem(HashItem item)` : Actualise la GUI à la page suivante (si possible)
* `update(Player player)` : Rafraîchit la GUI (pour les pages)
* `clearPageContent()` : Vide visuellement la page actuelle (utilisé pour le rafraîchissement)
* `addPage(Page page)` : Ajoute une page
* `createNewPage()` : Crée une nouvelle page et l'ajoute
* `clearPages()` : Supprime toutes les pages

### Pages

#### Création d'une page

```java
PaginatedHashGui gui;

Page page = gui.createNewPage(); // Crée une nouvelle page et l'ajoute à la GUI

/* OU */

Page page = new Page(gui); // Crée une nouvelle page
gui.addPage(page); // L'ajoute à la GUI
```

#### Fonctionnalités
* `addItem(HashItem item)` : Ajoute un item dans la page au premier slot libre
* `setItem(int slot, HashItem item)` : Ajoute un item dans la page à un endroit précis
* `removeItem(int slot)` : Retire un item d'un slot
* `clearItems()` : Retire tous les items de la page

> [!TIP]
> Par défaut, à la création d'une `PaginatedHashGui`, une nouvelle page vierge se crée automatiquement.

#### Gestion de page

```java
Page page;

HashItem item1 = new HashItem(Material.BED);
HashItem item2 = new HashItem(Material.BEDROCK);

page.addItem(item1);
page.setItem(8, item2);
page.removeItem(8);
```

> [!WARNING]
> * Pour `Page#addItem()`, si aucun slot n'est disponible, une exception de type `IllegalArgumentException` sera renvoyée.
> * Pour `Page#setItem()` ou `Page#removeItem()`, si le slot donné n'est pas disponible, la même exception sera renvoyée.
> * **Un slot est considéré non disponible s'il n'est pas valide (en dessous de 0 ou au dessus de la capacité maximum de la GUI)
> ou si un item est déjà présent sur ce slot dans la GUI parente.**

## Fait avec 💜 par [Lysandre B.](https://github.com/Shuvlyy) ・ [![wakatime](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d794b-8bf6-46ef-acb3-549287335474.svg)](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d794b-8bf6-46ef-acb3-549287335474) + [![wakatime](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d7a18-67ef-47e3-a6c4-5c8cc4b45021.svg)](https://wakatime.com/badge/user/2f50fe6c-0368-4bef-aa01-3a67193b63f8/project/018d7a18-67ef-47e3-a6c4-5c8cc4b45021)