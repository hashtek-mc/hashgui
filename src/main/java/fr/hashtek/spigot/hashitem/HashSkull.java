package fr.hashtek.spigot.hashitem;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.hashtek.spigot.hashitem.common.DefaultItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class HashSkull extends HashItem
{

    /**
     * Creates a new HashSkull.
     */
    public HashSkull()
    {
        this(1);
    }

    /**
     * Creates a new HashSkull.
     *
     * @param   amount  Amount of items
     */
    public HashSkull(int amount)
    {
        super(new ItemStack(Material.PLAYER_HEAD, amount));
    }

    /**
     * Creates a HashSkull from an existing {@link HashItem}.
     *
     * @param   item    Item
     */
    public HashSkull(HashItem item)
    {
        this(item.getItemStack().getAmount());
        super.setItemMeta(item.getItemMeta());
    }


    /**
     * @return  Item's meta as {@link SkullMeta}.
     */
    private SkullMeta getSkullMeta()
    {
        return (SkullMeta) super.getItemMeta();
    }

    /**
     * Sets skull's texture to a player's head.
     * To set skull's texture to a custom texture,
     * use {@link HashSkull#setTexture(String)}.
     * </p>
     * {@link Bukkit#getOfflinePlayer(String)} is used because
     * no other options are available right now.
     * One solution is to get targeted player's UUID from
     * Mojang's API, but it is kinda overkill.
     *
     * @param   playerName  Targeted player's name
     * @return  Itself
     */
    public HashSkull setOwner(String playerName)
    {
        this.getSkullMeta().setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        return this;
    }

    /**
     * Sets skull's texture to a custom texture.
     * To set skull's texture to a player's head,
     * use {@link HashSkull#setOwner(String)}.
     *
     * @param   texture     Skull texture
     * @return  Itself
     */
    public HashItem setTexture(String texture)
    {
        if (texture.isEmpty()) {
            return this;
        }

        try {
            final SkullMeta skullMeta = this.getSkullMeta();
            final GameProfile profile = new GameProfile(UUID.randomUUID(), null);

            profile.getProperties().put("textures", new Property("textures", texture));

            final Field profileField = skullMeta.getClass().getDeclaredField("profile");

            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);

            return this;
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            return DefaultItems.ITEM_BUILD_FAIL.getItem();
        }
    }

}
