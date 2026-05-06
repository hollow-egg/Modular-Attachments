package dev.egg.modularattachments.proxy;

import dev.egg.modularattachments.ModularAttachments;
import dev.egg.modularattachments.common.init.ComponentTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemUtils {
    public static ItemStack getUpgrade(ItemStack item, HolderLookup.Provider registryAccess) {
        return item.has(ComponentTypes.UPGRADE) ?
                ItemStack.parseOptional(registryAccess, Objects.requireNonNull(item.get(ComponentTypes.UPGRADE))) :
                ItemStack.EMPTY;
    }

    public static boolean isModular(ItemStack stack) {
        return stack.is(ModularAttachments.MODULAR);
    }

    public static boolean isUpgrade(ItemStack stack) {
        return stack.is(ModularAttachments.UPGRADES);
    }

    public static void setUpgrade(ItemStack item, ItemStack upgrade, HolderLookup.Provider registryAccess) {
        if (upgrade.isEmpty()) {
            item.remove(ComponentTypes.UPGRADE);
        } else {
            item.set(ComponentTypes.UPGRADE, (CompoundTag) upgrade.save(registryAccess, new CompoundTag()));
        }
    }

    public static void damageUpgrade(ItemStack item, ServerPlayer player) {

    }


    public static List<ItemStack> getAllModifierItems(ItemStack item, HolderLookup.Provider registryAccess) {
        List<ItemStack> modifiers = new ArrayList<>();
        modifiers.add(item);
        ItemStack upgrade = ItemUtils.getUpgrade(item, registryAccess);
        if (!upgrade.isEmpty()) {
            modifiers.add(upgrade);
        }
        return modifiers;
    }
}
