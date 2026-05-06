package dev.egg.modularattachments.client;

import dev.egg.modularattachments.ModularAttachments;
import dev.egg.modularattachments.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

public class ClientEvents {
    @EventBusSubscriber(modid = ModularAttachments.MODID, value = Dist.CLIENT)
    public static class ForgeBus {
        @SubscribeEvent
        public static void onRenderTooltip(final RenderTooltipEvent.Pre event) {
            event.getGraphics().pose().translate(0, 0, 500);
        }

        @SubscribeEvent
        public static void onClientTick(final ClientTickEvent.Pre event) {
            if (Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> containerScreen) {
                TooltipHandler.tick(containerScreen.hoveredSlot, containerScreen.getMenu().getCarried());
            } else {
                TooltipHandler.clear();
            }
        }

        @SubscribeEvent
        public static void onScreenRendered(final ContainerScreenEvent.Render.Foreground event) {
            TooltipHandler.render(event.getGuiGraphics(), ClientProxy.getPartialTick(), event.getMouseX() - event.getContainerScreen().getGuiLeft(), event.getMouseY() - event.getContainerScreen().getGuiTop());
        }
    }
}
