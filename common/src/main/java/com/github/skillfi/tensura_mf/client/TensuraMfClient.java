package com.github.skillfi.tensura_mf.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.github.skillfi.tensura_mf.client.entity.monster.OgreRenderer;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfClient {

    public static void init() {
        registerLivingEntityRenderer();
    }

    public static void registerRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> entityRenderers,
                                         BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> blockEntityRenderers) {
                                            entityRenderers.accept(MonsterEntityTypes.OGRE.get(), OgreRenderer::new);
                                         }

    public static void registerLivingEntityRenderer() {
        EntityRendererRegistry.register(MonsterEntityTypes.OGRE, OgreRenderer::new);
    }

}
