package dev.egg.modularattachments.proxy;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ClientProxy {
    public static boolean isShiftDown() {
        return Screen.hasShiftDown();
    }

    public static float getPartialTick() {
        if (Minecraft.getInstance().getTimer() instanceof DeltaTracker.Timer timer) {
            return timer.deltaTickResidual;
        } else {
            return 1;
        }
    }
}
