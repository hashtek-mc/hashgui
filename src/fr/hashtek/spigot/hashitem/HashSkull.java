package fr.hashtek.spigot.hashitem;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class HashSkull extends HashItem {

    /**
     * Creates a new HashSkull.
     */
    public HashSkull()
    {
        this(SkullType.PLAYER);
    }

    /**
     * Creates a new HashSkull.
     *
     * @param   amount  Amount of items
     */
    public HashSkull(int amount)
    {
        this(SkullType.PLAYER, amount);
    }

    /**
     * Creates a new HashSkull, using a specific {@link SkullType}.
     *
     * @param   skullType   Skull type
     */
    public HashSkull(SkullType skullType)
    {
        this(skullType, 1);
    }

    /**
     * Creates a new HashSkull.
     *
     * @param   skullType   Skull type
     * @param   amount      Amount of items
     */
    public HashSkull(SkullType skullType, int amount)
    {
        super(Material.SKULL_ITEM, amount);
        super.setDurability((short) skullType.ordinal());
        super.setItemMeta((SkullMeta) super.getItemMeta());
    }

    /**
     * Creates a HashSkull from an existing {@link HashItem}.
     *
     * @param   item        Item
     * @param   skullType   Skull type
     */
    public HashSkull(HashItem item, SkullType skullType)
    {
        this(skullType, item.getItemStack().getAmount());
        super.setItemMeta((SkullMeta) item.getItemMeta());
    }

    /**
     * Creates a HashSkull from an existing {@link HashItem}.
     *
     * @param   item    Item
     */
    public HashSkull(HashItem item)
    {
        this(item.getItemStack().getAmount());
        super.setItemMeta((SkullMeta) item.getItemMeta());
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
     *
     * @param   playerName  Targeted player's name
     * @return  Returns itself.
     */
    public HashSkull setOwner(String playerName)
    {
        this.getSkullMeta().setOwner(playerName);
        return this;
    }

    /**
     * Sets skull's texture to a custom texture.
     * To set skull's texture to a player's head,
     * use {@link HashSkull#setOwner(String)}.
     *
     * @param   texture     Skull texture
     * @return  Returns itself.
     * @throws  Exception   Texture set fail
     */
    public HashSkull setTexture(String texture) throws Exception
    {
        if (texture.isEmpty())
            return this;

        SkullMeta skullMeta = this.getSkullMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", texture));

        Field profileField = skullMeta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        profileField.set(skullMeta, profile);

        return this;
    }

}
