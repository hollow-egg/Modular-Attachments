package dev.egg.modularattachments;

import dev.egg.modularattachments.common.init.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforgespi.language.IModInfo;
import org.slf4j.Logger;

@Mod(ModularAttachments.MODID)
public class ModularAttachments {
    public static final String MODID = "modular_attachments";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Item> UPGRADES = TagKey.create(Registries.ITEM, resource("upgrades"));
    public static final TagKey<Item> MODULAR = TagKey.create(Registries.ITEM, resource("modifiable_items"));

    public static String MOD_NAME;

    public static final Style GREEN = Style.EMPTY.withColor(0xb4ce99);
    public static final Style RED = Style.EMPTY.withColor(0xca7d6c);
    public static final Style LIGHTER_COLOR = Style.EMPTY.withColor(0xcca06d);
    public static final Style LIGHT_COLOR = Style.EMPTY.withColor(0xaf7a3e);
    public static final Style DARK_COLOR = Style.EMPTY.withColor(0x7e582c);

    public ModularAttachments(IEventBus bus, ModContainer container) {
        IModInfo info = container.getModInfo();
        MOD_NAME = info.getDisplayName() + " " + info.getVersion();

        Attributes.ATTRIBUTES.register(bus);
        SoundEvents.SOUND_EVENTS.register(bus);
        ComponentTypes.DATA_COMPONENT_TYPES.register(bus);
    }
    
    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static <T> ResourceKey<T> resource(ResourceKey<Registry<T>> registryKey, String path) {
        return ResourceKey.create(registryKey, resource(path));
    }
}
