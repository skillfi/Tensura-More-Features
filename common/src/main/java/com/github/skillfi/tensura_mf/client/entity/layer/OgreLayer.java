package com.github.skillfi.tensura_mf.client.entity.layer;

import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import com.github.skillfi.tensura_mf.entity.variant.OgreVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.manasmods.tensura.client.TensuraColors;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.EnumMap;

@NoArgsConstructor
public class OgreLayer {
    private static RenderType getRenderType(ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }


    public static class Hair extends GeoRenderLayer<OgreEntity> {

        public Hair(GeoRenderer<OgreEntity> renderLayer){
            super(renderLayer);
        }

        public void render(PoseStack poseStack, OgreEntity ogre, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = ogre.getTopColor() != 0 ? ogre.getTopColor() : -1;
            if (ogre.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || ogre.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(this.getHairTexture(ogre));
            this.getRenderer().reRender(this.getDefaultBakedModel(ogre), poseStack, bufferSource, ogre, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }

        private ResourceLocation getHairTexture(OgreEntity entity) {
            return OgreVariant.Hair.getTextureLocation(entity);
        }
    }

    public static class Face extends GeoRenderLayer<OgreEntity> {

        public Face(GeoRenderer<OgreEntity> renderLayer){
            super(renderLayer);
        }

        public void render(PoseStack poseStack, OgreEntity ogre, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = ogre.getTopColor() != 0 ? ogre.getTopColor() : -1;
            if (ogre.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || ogre.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(ogre.getFace().getTextureLocation(ogre));
            this.getRenderer().reRender(this.getDefaultBakedModel(ogre), poseStack, bufferSource, ogre, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }


    }

    public static class Top extends GeoRenderLayer<OgreEntity> {
        public Top(GeoRenderer<OgreEntity> renderer) {
            super(renderer);
        }

        public void render(PoseStack poseStack, OgreEntity ogre, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = ogre.getTopColor() != 0 ? ogre.getTopColor() : -1;
            if (ogre.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || ogre.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(OgreVariant.Top.getTextureLocation(ogre));
            this.getRenderer().reRender(this.getDefaultBakedModel(ogre), poseStack, bufferSource, ogre, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }
    }

    public static class Bottom extends GeoRenderLayer<OgreEntity> {
        public Bottom(GeoRenderer<OgreEntity> renderer) {
            super(renderer);
        }

        public void render(PoseStack poseStack, OgreEntity ogre, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = ogre.getBottomColor() != 0 ? ogre.getBottomColor() : -1;
            if (ogre.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || ogre.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(OgreVariant.Bottom.getTextureLocation(ogre));
            this.getRenderer().reRender(this.getDefaultBakedModel(ogre), poseStack, bufferSource, ogre, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }
    }
}
