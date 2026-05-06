package dev.egg.modularattachments.common;

import dev.egg.modularattachments.ModularAttachments;
import dev.egg.modularattachments.client.TooltipHandler;
import dev.egg.modularattachments.common.init.Attributes;
import dev.egg.modularattachments.proxy.ClientProxy;
import dev.egg.modularattachments.proxy.ItemUtils;
import dev.egg.modularattachments.server.data.ModifiersReloadListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = ModularAttachments.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onItemStackedOnOther(final ItemStackedOnOtherEvent event) {

        if (event.getClickAction() != ClickAction.SECONDARY) {
            return;
        }

        ItemStack slotItem = event.getSlot().getItem();
        if (!ItemUtils.isModular(slotItem)) {
            return;
        }

        ItemStack carried = event.getCarriedItem();
        ItemStack currentUpgrade = ItemUtils.getUpgrade(slotItem, event.getPlayer().registryAccess());

        boolean equipped = true;
        if (ItemUtils.isUpgrade(carried)) {
            if (currentUpgrade.isEmpty()) { // add upgrade
                ItemUtils.setUpgrade(slotItem, carried.copyWithCount(1), event.getPlayer().registryAccess());
                event.getCarriedSlotAccess().get().shrink(1);
                event.setCanceled(true);
            } else if (carried.getCount() == 1) { //swap upgrades
                ItemUtils.setUpgrade(slotItem, carried.copyWithCount(1), event.getPlayer().registryAccess());
                event.getCarriedSlotAccess().set(currentUpgrade.copy());
                event.setCanceled(true);
            } else if (ItemStack.isSameItemSameComponents(carried, currentUpgrade)) { //remove upgrade into stack
                int transferAmount = Math.min(carried.getMaxStackSize() - carried.getCount(), currentUpgrade.getCount());
                ItemUtils.setUpgrade(slotItem, currentUpgrade.copyWithCount(currentUpgrade.getCount() - transferAmount), event.getPlayer().registryAccess());
                event.getCarriedSlotAccess().get().grow(transferAmount);
                event.setCanceled(true);
                equipped = false;
            }
        } else if (!currentUpgrade.isEmpty() && carried.isEmpty()) { // remove upgrade
            ItemUtils.setUpgrade(slotItem, ItemStack.EMPTY, event.getPlayer().registryAccess());
            event.getCarriedSlotAccess().set(currentUpgrade.copy());
            event.setCanceled(true);
            equipped = false;
        }

        if (event.isCanceled()) {
            if (event.getPlayer().level().isClientSide && FMLEnvironment.dist.isClient()) {
                TooltipHandler.addShake(event.getSlot(), equipped);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltipLowPriority(final ItemTooltipEvent event) {
        if (ItemUtils.isModular(event.getItemStack())) {
            if (event.getEntity() != null) {
                ItemStack upgrade = ItemUtils.getUpgrade(event.getItemStack(), event.getEntity().registryAccess());

                if (upgrade.isEmpty()) {
                    event.getToolTip().add(Component.translatable("tooltip.modular_attachments.no_upgrade")
                            .withStyle(ModularAttachments.LIGHT_COLOR));
                } else {
                    event.getToolTip().add(Component.translatable("tooltip.modular_attachments.upgrade", upgrade.getDisplayName().copy().withStyle(ModularAttachments.LIGHT_COLOR))
                            .withStyle(ModularAttachments.DARK_COLOR));
                }
            }
        }

        ModifiersReloadListener.getModifiers(event.getItemStack()).ifPresent(modifiers -> {
            if (!event.getToolTip().getLast().getString().isEmpty()) {
                event.getToolTip().add(Component.empty());
            }

            if (ClientProxy.isShiftDown()) {
                event.getToolTip().add(Component.translatable("tooltip.modular_attachments.modifier").withStyle(ModularAttachments.LIGHT_COLOR));
                modifiers.appendTooltip(event.getToolTip());
            } else {
                event.getToolTip().add(Component.translatable("tooltip.modular_attachments.modifier_shift").withStyle(ModularAttachments.LIGHT_COLOR));
            }
        });
    }

    @SubscribeEvent
    public static void onAddReloadListeners(final AddReloadListenerEvent event) {
        event.addListener(ModifiersReloadListener.create());
    }

    @SubscribeEvent
    public static void onAttributeCreation(final EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, Attributes.TEST_ATTRIBUTE);
    }
}
