package net.aboba.trialmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.aboba.trialmod.TrialMod;
import net.aboba.trialmod.common.item.UniversalEngineItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class UniversalItemRenderer extends GeoItemRenderer<UniversalEngineItem> {
    public UniversalItemRenderer(String modelName) {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(TrialMod.MOD_ID, modelName)));
    }
    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (displayContext.isFirstPerson()) {
            float weight = 1.0f;
            float inertia = 0.5f;

            // Extract values from the item if it's our engine item
            if (stack.getItem() instanceof UniversalEngineItem engineItem) {
                weight = engineItem.getWeight(); // You'll need a getter for this in your Item class
                inertia = engineItem.getInertia();
            }

            // Calculate Mouse Movement
            double mouseX = Minecraft.getInstance().mouseHandler.xpos();
            double mouseY = Minecraft.getInstance().mouseHandler.ypos();

            // Apply a "Lag" effect: Heavier items move slower and further
            float swayX = (float) (mouseX * 0.01f * inertia);
            float swayY = (float) (mouseY * 0.01f * weight);

            poseStack.pushPose();
            poseStack.translate(-swayX, swayY, 0); // Move the model based on mouse delta
            super.renderByItem(stack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
            poseStack.popPose();
        } else {
            super.renderByItem(stack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }
}
