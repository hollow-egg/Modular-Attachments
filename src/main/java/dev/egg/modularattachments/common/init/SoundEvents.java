package dev.egg.modularattachments.common.init;

import dev.egg.modularattachments.ModularAttachments;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ModularAttachments.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP = registerSound("equip");
    public static final DeferredHolder<SoundEvent, SoundEvent> UNEQUIP = registerSound("unequip");
    public static final DeferredHolder<SoundEvent, SoundEvent> DWOP = registerSound("dwop");
    public static final DeferredHolder<SoundEvent, SoundEvent> DWOP_REVERSE = registerSound("dwop_reverse");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ModularAttachments.resource(name)));
    }
}
