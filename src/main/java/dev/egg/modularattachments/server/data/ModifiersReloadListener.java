package dev.egg.modularattachments.server.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.egg.modularattachments.ModularAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModifiersReloadListener extends SimplePreparableReloadListener<Map<String, JsonObject>> {
    private static final Gson GSON_INSTANCE = new Gson();
    private static final ResourceLocation LOCATION = ModularAttachments.resource("modifiers.json");
    @Nullable
    private static ModifiersReloadListener INSTANCE;

    private final Map<Item, Modifiers> modifiers = new HashMap<>();

    public static ModifiersReloadListener create() {
        INSTANCE = new ModifiersReloadListener();
        return INSTANCE;
    }

    public static Optional<Modifiers> getModifiers(ItemStack stack) {
        if (INSTANCE != null && INSTANCE.modifiers.containsKey(stack.getItem())) {
            return Optional.of(INSTANCE.modifiers.get(stack.getItem()));
        }
        return Optional.empty();
    }

    @Override
    protected Map<String, JsonObject> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return Map.of();
    }

    @Override
    protected void apply(Map<String, JsonObject> stringJsonObjectMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

    }
}