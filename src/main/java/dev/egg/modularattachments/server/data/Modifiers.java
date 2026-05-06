package dev.egg.modularattachments.server.data;

import dev.egg.modularattachments.ModularAttachments;
import net.minecraft.network.chat.Component;

import java.util.*;

public final class Modifiers {
    private final Set<Type> modifierSet;
    private final List<Component> tooltip;

    public Modifiers(Type... modifiers) {
        this.modifierSet = new HashSet<>();

        this.modifierSet.addAll(Arrays.asList(modifiers));

        tooltip = makeTooltip();
    }

    public void appendTooltip(List<Component> list) {
        list.addAll(tooltip);
    }

    private List<Component> makeTooltip() {
        List<Component> tooltip = new ArrayList<>();

        for (var type : modifierSet) {
            String key;
            key = "tooltip.modular_attachments.modifier." + type;

            tooltip.add(Component.translatable(key).withStyle(ModularAttachments.LIGHTER_COLOR));
        }

        return tooltip;
    }

    public enum Type {
        test;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
