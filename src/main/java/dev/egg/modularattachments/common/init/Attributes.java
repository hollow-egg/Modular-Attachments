package dev.egg.modularattachments.common.init;

import dev.egg.modularattachments.ModularAttachments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Attributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ModularAttachments.MODID);

    public static final DeferredHolder<Attribute, Attribute> TEST_ATTRIBUTE = ATTRIBUTES.register("test",
            () -> new RangedAttribute("attribute.name.modular_attachments.test", 0, 0, 1));
}
