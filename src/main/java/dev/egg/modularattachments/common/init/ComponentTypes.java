package dev.egg.modularattachments.common.init;

import dev.egg.modularattachments.ModularAttachments;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ModularAttachments.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CompoundTag>> UPGRADE =
            DATA_COMPONENT_TYPES.registerComponentType("upgrade", builder ->
                    builder.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));
}
