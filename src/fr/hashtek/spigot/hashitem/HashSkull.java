package fr.hashtek.spigot.hashitem;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class HashSkull extends HashItem {

    public HashSkull()
    {
        this(SkullType.PLAYER);
    }

    public HashSkull(int amount)
    {
        this(SkullType.PLAYER, amount);
    }

    public HashSkull(SkullType skullType)
    {
        this(skullType, 1);
    }

    public HashSkull(SkullType skullType, int amount)
    {
        super(Material.SKULL_ITEM, amount);
        super.setDurability((short) skullType.ordinal());
        super.setItemMeta((SkullMeta) super.getItemMeta());
    }

    public SkullMeta getSkullMeta()
    {
        return (SkullMeta) super.getItemMeta();
    }

    public HashSkull setOwner(String playerName)
    {
        this.getSkullMeta().setOwner(playerName);
        return this;
    }

    public HashSkull setTexture(String texture)
    {
        if (texture.isEmpty())
            return this;

        SkullMeta skullMeta = this.getSkullMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (Exception exception) {
            // log !! ! !! !
        }

        return this;
    }

}
