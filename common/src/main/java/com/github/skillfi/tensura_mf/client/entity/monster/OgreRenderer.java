package com.github.skillfi.tensura_mf.client.entity.monster;

import com.github.skillfi.tensura_mf.client.entity.layer.OgreLayer;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.manasmods.tensura.item.tool.SimpleShieldItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class OgreRenderer extends GeoEntityRenderer<OgreEntity> {
    private static final String LEFT_HAND = "LeftHand";
    private static final String RIGHT_HAND = "RightHand";
    private static final String LEFT_BOOT = "LeftBootArmor";
    private static final String RIGHT_BOOT = "RightBootArmor";
    private static final String LEFT_ARMOR_LEG = "LeftLegArmor";
    private static final String RIGHT_ARMOR_LEG = "RightLegArmor";
    private static final String CHESTPLATE = "ChestArmor";
    private static final String RIGHT_SLEEVE = "RightArmArmor";
    private static final String LEFT_SLEEVE = "LeftArmArmor";
    private static final String HELMET = "HeadArmor";
    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;

    public OgreRenderer(EntityRendererProvider.Context renderManager) {
        this(renderManager, new OgreModel());
    }

    protected OgreRenderer(EntityRendererProvider.Context renderManager, GeoModel<OgreEntity> model) {
        super(renderManager, model);
        this.addRenderLayer(new OgreLayer.Hair(this));
        this.addRenderLayer(new OgreLayer.Face(this));
        this.addRenderLayer(new OgreLayer.Top(this));
        this.addRenderLayer(new OgreLayer.Bottom(this));
        this.addRenderLayer(new ItemArmorGeoLayer<>(this) {
            protected @Nullable ItemStack getArmorItemForBone(GeoBone bone, OgreEntity animatable) {
                ItemStack var10000;
                switch (bone.getName()) {
                    case "LeftBootArmor":
                    case "RightBootArmor":
                        var10000 = this.bootsStack;
                        break;
                    case "LeftLegArmor":
                    case "RightLegArmor":
                        var10000 = this.leggingsStack;
                        break;
                    case "ChestArmor":
                    case "RightArmArmor":
                    case "LeftArmArmor":
                        var10000 = this.chestplateStack;
                        break;
                    case "HeadArmor":
                        var10000 = this.helmetStack;
                        break;
                    default:
                        var10000 = null;
                }

                return var10000;
            }

            protected @NotNull EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, OgreEntity animatable) {
                EquipmentSlot var10000;
                switch (bone.getName()) {
                    case "LeftBootArmor":
                    case "RightBootArmor":
                        var10000 = EquipmentSlot.FEET;
                        break;
                    case "LeftLegArmor":
                    case "RightLegArmor":
                        var10000 = EquipmentSlot.LEGS;
                        break;
                    case "RightArmArmor":
                        var10000 = !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                        break;
                    case "LeftArmArmor":
                        var10000 = animatable.isLeftHanded() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                        break;
                    case "ChestArmor":
                        var10000 = EquipmentSlot.CHEST;
                        break;
                    case "HeadArmor":
                        var10000 = EquipmentSlot.HEAD;
                        break;
                    default:
                        var10000 = super.getEquipmentSlotForBone(bone, stack, animatable);
                }

                return var10000;
            }

            protected @NotNull ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, OgreEntity animatable, HumanoidModel<?> baseModel) {
                ModelPart var10000;
                switch (bone.getName()) {
                    case "LeftBootArmor":
                    case "LeftLegArmor":
                        var10000 = baseModel.leftLeg;
                        break;
                    case "RightBootArmor":
                    case "RightLegArmor":
                        var10000 = baseModel.rightLeg;
                        break;
                    case "RightArmArmor":
                        var10000 = baseModel.rightArm;
                        break;
                    case "LeftArmArmor":
                        var10000 = baseModel.leftArm;
                        break;
                    case "ChestArmor":
                        var10000 = baseModel.body;
                        break;
                    case "HeadArmor":
                        var10000 = baseModel.head;
                        break;
                    default:
                        var10000 = super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
                }

                return var10000;
            }
        });
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            protected @Nullable ItemStack getStackForBone(GeoBone bone, OgreEntity animatable) {
                ItemStack var10000;
                switch (bone.getName()) {
                    case "LeftHand" -> var10000 = animatable.isLeftHanded() ? OgreRenderer.this.mainHandItem : OgreRenderer.this.offhandItem;
                    case "RightHand" -> var10000 = animatable.isLeftHanded() ? OgreRenderer.this.offhandItem : OgreRenderer.this.mainHandItem;
                    default -> var10000 = null;
                }

                return var10000;
            }

            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, OgreEntity animatable) {
                ItemDisplayContext var10000;
                switch (bone.getName()) {
                    case "LeftHand":
                    case "RightHand":
                        var10000 = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                        break;
                    default:
                        var10000 = ItemDisplayContext.NONE;
                }

                return var10000;
            }

            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, OgreEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == OgreRenderer.this.mainHandItem || stack == OgreRenderer.this.offhandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                    if (stack == OgreRenderer.this.mainHandItem && !animatable.isLeftHanded() || stack == OgreRenderer.this.offhandItem && animatable.isLeftHanded()) {
                        if (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem) {
                            poseStack.translate((double) 0.0F, (double) 0.125F, (double) -0.25F);
                        }
                    } else if ((stack == OgreRenderer.this.mainHandItem && animatable.isLeftHanded() || stack == OgreRenderer.this.offhandItem && !animatable.isLeftHanded()) && (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem)) {
                        poseStack.translate((double) 0.0F, (double) 0.125F, (double) 0.25F);
                        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    protected float getShadowRadius(OgreEntity entity) {
        return entity.isBaby() ? 0.25F : 0.5F;
    }

    public void preRender(PoseStack poseStack, OgreEntity ogre, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        this.mainHandItem = ogre.getMainHandItem();
        this.offhandItem = ogre.getOffhandItem();
        this.entityRenderTranslations.set(poseStack.last().pose());
        float scale = ogre.isBaby() ? 0.5F : 1.0F;
        int tick = 40 - ogre.getEvolving();
        if (ogre.getEvolving() > 0 && tick > 0) {
            scale *= 1.0F + 0.5F * ((float)tick / 40.0F);
        }

        this.scaleModelForRender(this.scaleWidth * scale, this.scaleHeight * scale, poseStack, ogre, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
