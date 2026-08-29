package com.github.skillfi.tensura_mf.entity.monster;

import com.github.skillfi.tensura_mf.entity.ai.behaviour.TensuraMfBehaviourHelper;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomHeldAttack;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomRangeAttack;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.OrbitAttack;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.monster.ArchDaemonEntity;
import io.github.manasmods.tensura.entity.monster.GreaterDaemonEntity;
import io.github.manasmods.tensura.entity.monster.LesserDaemonEntity;
import io.github.manasmods.tensura.entity.projectile.magic.*;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.subclass.IDaemon;
import io.github.manasmods.tensura.entity.template.subclass.IFlying;
import io.github.manasmods.tensura.entity.template.subclass.INameEvolution;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import com.github.skillfi.tensura_mf.entity.variant.PrimordialVariant;
import io.github.manasmods.tensura.entity.variant.OrcVariant;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import lombok.Generated;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Predicate;

/** Shared base for the primordial daemon variants. */
public class PrimordialDaemonEntity extends PlayerLikeEntity implements GeoEntity, SmartBrainOwner<PrimordialDaemonEntity>, IDaemon, IFlying, INameEvolution, VariantHolder<PrimordialVariant> {
    protected static final EntityDataAccessor<Boolean> FLYING;
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT;
    private static final EntityDataAccessor<Integer> EVOLUTION_STATE;
    private static final EntityDataAccessor<Integer> GENDER;
    private static final EntityDataAccessor<Integer> SKIN;
    private static final EntityDataAccessor<Integer> FACE;
    private static final EntityDataAccessor<Integer> TOP;
    private static final EntityDataAccessor<Integer> TOP_COLOR;
    private static final EntityDataAccessor<Integer> BOTTOM;
    private static final EntityDataAccessor<Integer> BOTTOM_COLOR;
    protected int flyingTick;
    protected boolean wasFlying;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public PrimordialDaemonEntity(EntityType<? extends PrimordialDaemonEntity> entityType, Level level) {
        super(entityType, level);
    }

//    public PrimordialDaemonEntity(EntityType<? extends PrimordialDaemonEntity> entityType, Level level, PrimordialVariant variant) {
//        this(entityType, level);
//        this.setVariant(variant);
//    }

    /** Uses the stronger Greater Daemon baseline while retaining Lesser Daemon behaviour. */
    public static AttributeSupplier.Builder setAttributes() {
        return io.github.manasmods.tensura.entity.template.TensuraTamableEntity.setAttributes()
                .add(Attributes.ATTACK_DAMAGE, 30.0D)
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4D)
                .add(Attributes.FLYING_SPEED, 2.0D)
                .add(Attributes.STEP_HEIGHT, 1.0D)
                .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 10.0D)
                .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 3.0D);
    }

    @Override
    public void swing(InteractionHand hand, boolean updateSelf) {
        super.swing(hand, updateSelf);
        if (hand == InteractionHand.MAIN_HAND) {
            this.triggerAnim("attackController", "attack");
        }
    }

    private PlayState loopController(AnimationState<PrimordialDaemonEntity> state) {
        String animation = this.isFlying()
                ? "animation.primordial_daemon.fly"
                : (state.isMoving() ? (this.isSprinting() ? "animation.primordial_daemon.run" : "animation.primordial_daemon.walk") : "animation.primordial_daemon.idle");
        return state.setAndContinue(RawAnimation.begin().thenLoop(animation));
    }

    public void switchMoveControl(MoveControl control) {
        this.moveControl = control;
    }

    public void switchNavigation(PathNavigation navigation) {
        this.navigation = navigation;
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.tickBrain(this);
    }

    @Override
    public List<ExtendedSensor<PrimordialDaemonEntity>> getSensors() {
        return List.of(new NearbyLivingEntitySensor<>(), new HurtBySensor<>());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLYING, false);
        builder.define(DATA_ID_TYPE_VARIANT, 0);
        builder.define(EVOLUTION_STATE, 0);
        builder.define(GENDER, 0);
        builder.define(SKIN, 0);
        builder.define(FACE, 0);
        builder.define(TOP, 0);
        builder.define(TOP_COLOR, -1);
        builder.define(BOTTOM, 0);
        builder.define(BOTTOM_COLOR, -1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        TensuraBehaviourHelper.saveGlobalPos(this, compound, MemoryModuleType.HOME, "Home");
        compound.putBoolean("Flying", this.isFlying());
        compound.putInt("Variant", this.getTypeVariant());
        compound.putInt("EvoState", this.getCurrentEvolutionState());
        compound.putInt("Gender", this.entityData.get(GENDER));
        compound.putInt("Skin", this.entityData.get(SKIN));
        compound.putInt("Face", this.entityData.get(FACE));
        compound.putInt("Top", this.entityData.get(TOP));
        compound.putInt("TopColor", (Integer)this.entityData.get(TOP_COLOR));
        compound.putInt("Bottom", this.entityData.get(BOTTOM));
        compound.putInt("BottomColor", (Integer)this.entityData.get(BOTTOM_COLOR));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        TensuraBehaviourHelper.readGlobalPos(this, compound, MemoryModuleType.HOME, "Home");
        this.setFlying(compound.getBoolean("Flying"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, compound.getInt("Variant"));
        this.setCurrentEvolutionState(compound.getInt("EvoState"));
        this.setGender(compound.getInt("Gender"));
        this.setSkin(compound.getInt("Skin"));
        this.setFace(compound.getInt("Face"));
        this.setTop(compound.getInt("Top"));
        this.entityData.set(TOP_COLOR, compound.getInt("TopColor"));
        this.setBottom(compound.getInt("Bottom"));
        this.entityData.set(BOTTOM_COLOR, compound.getInt("BottomColor"));
        if (this.getCurrentEvolutionState() > 0) {
            this.setTop(PrimordialVariant.Top.BLANC.getId());
            this.setBottom(PrimordialVariant.Bottom.BLANC.getId());
        }
    }

    @Override
    public @NotNull PrimordialVariant getVariant() {
        return PrimordialVariant.byId(this.entityData.get(DATA_ID_TYPE_VARIANT));
    }

    @Override
    public void setVariant(PrimordialVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return (Integer)this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public PrimordialVariant.Gender getGender() {
        return PrimordialVariant.Gender.byId(this.entityData.get(GENDER));
    }

    public void setGender(int gender) {
        this.entityData.set(GENDER, gender);
    }

    public PrimordialVariant.Skin getSkin() {
        return PrimordialVariant.Skin.byId(this.entityData.get(SKIN));
    }

    public void setSkin(int skin) {
        this.entityData.set(SKIN, skin);
    }

    public PrimordialVariant.Face getFace() {
        return PrimordialVariant.Face.byId(this.entityData.get(FACE));
    }

    public void setFace(int face) {
        this.entityData.set(FACE, face);
    }

    public PrimordialVariant.Top getTop() {
        return PrimordialVariant.Top.byId(this.entityData.get(TOP));
    }

    public int getTopColor() {
        return (Integer)this.entityData.get(TOP_COLOR);
    }

    public void setTopColor(int i) {
        this.entityData.set(TOP_COLOR, i);
    }

    public void setTop(int top) {
        this.entityData.set(TOP, top);
    }

    public PrimordialVariant.Bottom getBottom() {
        return PrimordialVariant.Bottom.byId(this.entityData.get(BOTTOM));
    }

    public int getBottomColor() {
        return (Integer)this.entityData.get(BOTTOM_COLOR);
    }

    public void setBottomColor(int i) {
        this.entityData.set(BOTTOM_COLOR, i);
    }

    public void setBottom(int bottom) {
        this.entityData.set(BOTTOM, bottom);
    }

    @Override
    public int getMaxEvolutionState() {
        return 1;
    }

    @Override
    public int getCurrentEvolutionState() {
        return this.entityData.get(EVOLUTION_STATE);
    }

    @Override
    public void setCurrentEvolutionState(int state) {
        this.entityData.set(EVOLUTION_STATE, Math.clamp(state, 0, getMaxEvolutionState()));
        if (state > 0) {
            this.setTop(PrimordialVariant.Top.BLANC.getId());
            this.setBottom(PrimordialVariant.Bottom.BLANC.getId());
        }
    }

    @Override
    public void evolve() {
        int before = this.getCurrentEvolutionState();
        INameEvolution.super.evolve();
        if (this.getCurrentEvolutionState() != before) {
            this.setTop(PrimordialVariant.Top.BLANC.getId());
            this.setBottom(PrimordialVariant.Bottom.BLANC.getId());
        }
    }

    public boolean isFlying() {
        return (Boolean)this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    public boolean wasFlying() {
        return this.wasFlying;
    }

    public boolean shouldStopFlying(Mob entity) {
        return IFlying.super.shouldStopFlying(entity) || this.isOrderedToSit() || this.isInLove();
    }

    public EntityDimensions getSleepingDimensions(Pose pPose) {
        return this.getType().getDimensions().scale(this.getAgeScale());
    }

    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else if (entity instanceof LesserDaemonEntity) {
            LesserDaemonEntity daemon = (LesserDaemonEntity)entity;
            return daemon.isTame() == this.isTame();
        } else if (entity instanceof GreaterDaemonEntity) {
            GreaterDaemonEntity daemon = (GreaterDaemonEntity)entity;
            return daemon.isTame() == this.isTame();
        } else if (entity instanceof ArchDaemonEntity) {
            ArchDaemonEntity daemon = (ArchDaemonEntity)entity;
            return daemon.isTame() == this.isTame();
        } else {
            return false;
        }
    }

    public boolean canAttack(LivingEntity pTarget) {
        return this.isAlliedTo(pTarget) ? false : super.canAttack(pTarget);
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.IN_FIRE) || super.isInvulnerableTo(source);
    }

    protected void actuallyHurt(DamageSource source, float damage) {
        damage *= this.getPhysicalAttackInput(source);
        if (TensuraDamageHelper.isTensuraMagic(source)) {
            damage *= 0.1F;
        }

        super.actuallyHurt(source, damage);
    }

    public boolean canBeNamed(Player player) {
        return !this.isTamedByNonPlayer();
    }

    public void tick() {
        super.tick();
        this.handleFlying(this);
    }

    public void shootFireBolt(@NotNull LivingEntity target, float v) {
        if (this.canCastMagics(this)) {
            ManasSkillInstance instance = this.getMagic(this, AspectualMagics.FIRE.get());
            if (instance == null) {
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            } else {
                this.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
                FireBoltProjectile bolt = new FireBoltProjectile(this.level(), this);
                bolt.setSkill(instance);
                bolt.setSize(1.5F);
                float angle = ((float)Math.PI / 180F) * this.yBodyRot;
                double xOffset = Mth.sin((float)(Math.PI + (double)angle));
                double zOffset = Mth.cos(angle);
                bolt.moveTo(this.getX() + xOffset, this.getEyeY(), this.getZ() + zOffset, this.getYRot(), this.getXRot());
                bolt.setNoGravity(true);
                bolt.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                bolt.setSecondaryDamage((float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)2.0F));
                bolt.setBurnTicks(100);
                bolt.shootToward(target, v, 0.0F);
                this.level().addFreshEntity(bolt);
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }
        }
    }

    public void shootWaterBlade(@NotNull LivingEntity target, float v) {
        if (this.canCastMagics(this)) {
            ManasSkillInstance instance = this.getMagic(this, (Magic)AspectualMagics.WATER_CUTTER.get());
            if (instance == null) {
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            } else {
                this.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
                WaterBladeProjectile blade = new WaterBladeProjectile(this.level(), this);
                blade.setSkill(instance);
                blade.setSize(1.0F);
                float angle = ((float)Math.PI / 180F) * this.yBodyRot;
                double xOffset = (double)Mth.sin((float)(Math.PI + (double)angle));
                double zOffset = (double)Mth.cos(angle);
                blade.moveTo(this.getX() + xOffset, this.getEyeY(), this.getZ() + zOffset, this.getYRot(), this.getXRot());
                blade.setNoGravity(true);
                blade.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                blade.setSecondaryDamage((float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)3.0F));
                blade.setBurnTicks(-1);
                blade.shootToward(target, v, 0.0F);
                this.level().addFreshEntity(blade);
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_WATER.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }
        }
    }

    public void shootWindCutter(@NotNull LivingEntity target, float v) {
        if (this.canCastMagics(this)) {
            ManasSkillInstance instance = this.getMagic(this, (Magic)AspectualMagics.WIND_CUTTER.get());
            if (instance == null) {
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            } else {
                this.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
                WindBladeProjectile sphere = new WindBladeProjectile(this.level(), this);
                sphere.setSkill(instance);
                sphere.setSize(1.0F);
                float angle = ((float)Math.PI / 180F) * this.yBodyRot;
                double xOffset = (double)Mth.sin((float)(Math.PI + (double)angle));
                double zOffset = (double)Mth.cos(angle);
                sphere.moveTo(this.getX() + xOffset, this.getEyeY(), this.getZ() + zOffset, this.getYRot(), this.getXRot());
                sphere.setNoGravity(true);
                sphere.setKnockForce(3.0F);
                sphere.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                sphere.setSecondaryDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 3.0F);
                sphere.setBurnTicks(-1);
                sphere.shootToward(target, v, 0.0F);
                this.level().addFreshEntity(sphere);
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_WIND.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }
        }
    }

    public void burstStoneShot(Predicate<LivingEntity> predicate) {
        if (this.canCastMagics(this)) {
            ManasSkillInstance stoneShot = this.getMagic(this, (Magic)AspectualMagics.STONE_SHOT.get());
            if (stoneShot == null) {
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            } else {
                this.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.EARTH_LOCK), 240, 0, false, false, false));
                double size = this.getAttributeValue(Attributes.SCALE) * (double)4.0F;
                TensuraParticleHelper.spawnServerParticles(this.level(), TensuraParticleUtils.getEarthAura(1.0F, (float)size, -0.3F), this.getX(), this.getEyeY(), this.getZ(), 55, 0.08, 0.08, 0.08, 0.2, true);
                TensuraParticleHelper.spawnServerParticles(this.level(), new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MUD_BRICKS.defaultBlockState()), this.getX(), this.getEyeY(), this.getZ(), 55, 0.08, 0.08, 0.08, 0.2, true);
                TensuraParticleHelper.addServerParticlesAroundSelf(this, TensuraParticleUtils.getEarthAura(1.0F, (float)size, -0.3F), (double)2.0F);
                AABB aabb = this.getBoundingBox().inflate(this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) + (double)10.0F);
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb, predicate);
                if (!list.isEmpty()) {
                    DamageSource source = TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.MAGIC_GENERIC, this);

                    for(LivingEntity target : list) {
                        target.hurt(source, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 3.0F);
                        target.setDeltaMovement((double)0.0F, 0.1, (double)0.0F);
                        SkillHelper.knockBack(this, target, 2.0F);
                    }

                    double angleStep = (Math.PI / 6D);

                    for(int i = 0; i < 12; ++i) {
                        double angle = (double)i * angleStep;
                        double offsetX = Math.cos(angle) * (double)3.0F;
                        double offsetZ = Math.sin(angle) * (double)3.0F;
                        double spawnX = this.getX() + offsetX;
                        double spawnY = this.getY() + (double)(this.getBbHeight() / 2.0F);
                        double spawnZ = this.getZ() + offsetZ;
                        Vec3 direction = (new Vec3(offsetX, (double)0.0F, offsetZ)).normalize();
                        StoneShotProjectile projectile = new StoneShotProjectile(this.level(), this);
                        projectile.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        projectile.setSecondaryDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 3.0F);
                        projectile.setSkill(stoneShot);
                        projectile.setNoGravity(true);
                        projectile.setPiercingEntity(true);
                        projectile.setPos(spawnX, spawnY, spawnZ);
                        projectile.shoot(direction.x, direction.y, direction.z, 1.0F, 0.0F);
                        this.level().addFreshEntity(projectile);
                    }

                }
            }
        }
    }

    protected void flameOrb() {
        if (this.canCastMagics(this)) {
            ManasSkillInstance instance = this.getMagic(this, (Magic)AspectualMagics.FIRE_BALL.get());
            if (instance == null) {
                this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            } else {
                int orbID = instance.getOrCreateTag().getInt("orbID");
                if (orbID == 0) {
                    FlameSphereProjectile orb = new FlameSphereProjectile(this.level(), this);
                    orb.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    orb.setSecondaryDamage((float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)6.0F));
                    orb.setBurnTicks(100);
                    orb.setMpCost((double)1000.0F);
                    orb.setSkill(instance);
                    orb.setExplosionRadius(4.0F);
                    orb.setPos(this.getEyePosition().add((double)0.0F, (double)this.getBbHeight() * (double)1.5F, (double)0.0F));
                    orb.setOwnerOffset(new Vec3((double)0.0F, (double)this.getBbHeight() * (double)1.5F, (double)0.0F));
                    orb.setLookDistance(30.0F);
                    orb.setDelayTick(30);
                    orb.setDelaySizeChange(0.05F);
                    orb.setNoGravity(true);
                    this.level().addFreshEntity(orb);
                    instance.getOrCreateTag().putInt("orbID", orb.getId());
                } else {
                    Entity entity = this.level().getEntity(orbID);
                    if (!(entity instanceof FlameSphereProjectile)) {
                        instance.getOrCreateTag().putInt("orbID", 0);
                        this.flameOrb();
                    }
                }

                this.level().playSound((Player)null, this, (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), TensuraSkill.ABILITY_SOUND, 10.0F, 0.95F + this.random.nextFloat() * 0.1F);
            }
        }
    }

    @Override
    public List<EquipmentSlot> getAvailableSlots() {
        return List.of(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
    }

    @Override
    public int getChestSlots() {
        return 18;
    }

    @Override
    public int getMenuRenderSize() {
        return 15;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(TensuraItemTags.SPIRIT_FOOD);
    }

    @Override
    public InteractionResult handleCommanding(Player player, InteractionHand hand, ItemStack stack) {
        if (this.isTame() && this.isOwnedBy(player)) {
            InteractionResult golemInteraction = this.getGolemInteraction(player, hand, this);
            if (golemInteraction.consumesAction()) {
                return golemInteraction;
            } else {
                InteractionResult interaction = this.getInventoryInteraction(player, hand);
                if (interaction.consumesAction()) {
                    return interaction;
                } else {
                    this.cycleCommands(this, player);
                    return InteractionResult.sidedSuccess(this.level().isClientSide());
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void applyFoodHeal(ItemStack stack, Player player, InteractionHand hand) {
        this.heal(5.0F);
        this.ate();
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
        return TensuraEntityTypes.rollSpawn(TensuraEntityTypes.CONFIG.SpawnChance.lesserDaemon, pLevel, pSpawnReason)
                && super.checkSpawnRules(pLevel, pSpawnReason);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        if (this.canRandomizeSpawnData(reason)) {
            PrimordialVariant variant = null;
            if (variant == null) {
                variant = PrimordialVariant.byId(this.getRandom().nextInt(4));
            }
            this.randomAppearance(variant);
        }
        if (level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof PrimordialDaemonEntity daemon
                        && daemon.getVariant() == this.getVariant()) {
                    this.discard();
                    return spawnData;
                }
            }
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    private void randomAppearance(PrimordialVariant variant) {
        this.setGender(this.getVariant().getGender().getId());
        this.setSkin(this.getVariant().getSkin().getId());
        this.setFace(this.getVariant().getFace().getId());
        this.setTop(this.getVariant().getTop().getId());
        this.setBottom(this.getVariant().getBottom().getId());
        List<Integer> colors = TensuraMfBehaviourHelper.CONFIG.Ogre.ogreClothingColors;
        this.setTopColor(colors.get(this.random.nextInt(colors.size())));
        List<Integer> bottomColors = TensuraMfBehaviourHelper.CONFIG.Ogre.ogreBottomClothesColors;
        this.setBottomColor(bottomColors.get(this.random.nextInt(bottomColors.size())));
    }

    @Override
    public BrainActivityGroup<PrimordialDaemonEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new Behavior[]{(new InvalidateNeutralAttackTarget()).invalidateIf((entity, target) -> this.shouldStopTarget((Mob) entity, (LivingEntity) target)), (new OrbitAttack()).lookAtTargetWhileOrbiting(true).speedMod((entity, target) -> 2.0F).orbitRadius((entity, target) -> (double)20.0F).orbitMinRadius((entity, target) -> (double)10.0F).orbitHeight((entity, target) -> (double)10.0F).orbitAttackInterval((entity) -> !((PrimordialDaemonEntity)entity).canCastMagics((LivingEntity) entity) ? 10 : 200).shouldDoMeleeAttack((entity, target) -> ((PrimordialDaemonEntity)entity).distanceTo((Entity) target) <= 5.0F).onStartOrbitAttack((entity, target) -> ((PrimordialDaemonEntity)entity).level().playSound((Player)null, ((PrimordialDaemonEntity)entity).getX(), ((PrimordialDaemonEntity)entity).getY(), ((PrimordialDaemonEntity)entity).getZ(), SoundEvents.GOAT_PREPARE_RAM, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F)).performOrbitAttack((entity, target) -> {
            ((PrimordialDaemonEntity)entity).doHurtTarget(((LivingEntity)target));
            ((PrimordialDaemonEntity)entity).triggerAnim("attackController", ((PrimordialDaemonEntity)entity).getRandom().nextBoolean() ? "attack_right" : "attack_left");
        }).onTick((entity) -> {
            ((PrimordialDaemonEntity)entity).setFlying(true);
            return true;
        }), (new FirstApplicableBehaviour(new ExtendedBehaviour[]{(new CustomRangeAttack(10)).maxAttackRadius(12.0F).attackInterval((entity) -> 20).performAttack((entity, target) -> {
            ((PrimordialDaemonEntity)entity).burstStoneShot((living) -> ((PrimordialDaemonEntity)entity).shouldAttack(((PrimordialDaemonEntity)entity), living));
            ((PrimordialDaemonEntity)entity).playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), 10.0F, 0.95F + ((PrimordialDaemonEntity)entity).getRandom().nextFloat() * 0.1F);
        }).whenStarting((entity) -> {
            ((PrimordialDaemonEntity)entity).triggerAnim("miscController", "burst");
            ManasSkillInstance instance = ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.STONE_SHOT.get());
            if (instance != null) {
                MagicCircle.castMagicCircle(4.0F, 25, MagicCircleVariant.EARTH, true, (LivingEntity) entity, new CompoundTag(), 0.0F, new Vec3((double)0.0F, (double)(((PrimordialDaemonEntity)entity).getBbHeight() / 2.0F), (double)0.0F), instance, 0, Pair.of((double)0.0F, (double)1000.0F));
            }

        }).startCondition((entity) -> ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.STONE_SHOT.get()) != null && ((PrimordialDaemonEntity)entity).getRandom().nextFloat() < 0.1F), (new CustomRangeAttack(15)).maxAttackRadius(40.0F).attackInterval((entity) -> 20).performAttack((entity, target) -> ((PrimordialDaemonEntity)entity).shootFireBolt(((LivingEntity)target), 1.5F)).whenStarting((entity) -> {
            ((PrimordialDaemonEntity)entity).triggerAnim("miscController", "magic_shoot");
            ManasSkillInstance instance = ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.FIRE.get());
            if (instance != null) {
                MagicCircle.castMagicCircle(2.0F, 25, MagicCircleVariant.FLAME, (LivingEntity) entity, new CompoundTag(), 3.0F, Vec3.ZERO, instance, 0, Pair.of((double)0.0F, (double)100.0F));
            }

        }).startCondition((entity) -> ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.FIRE.get()) != null && ((PrimordialDaemonEntity)entity).getRandom().nextFloat() < 0.3F), (new CustomRangeAttack(15)).maxAttackRadius(40.0F).attackInterval((entity) -> 20).performAttack((entity, target) -> ((PrimordialDaemonEntity)entity).shootWaterBlade((LivingEntity) target, 2.5F)).whenStarting((entity) -> {
            ((PrimordialDaemonEntity)entity).triggerAnim("attackController", ((PrimordialDaemonEntity)entity).getRandom().nextBoolean() ? "swing_right" : "swing_left");
            ManasSkillInstance instance = ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.WATER_CUTTER.get());
            if (instance != null) {
                MagicCircle.castMagicCircle(2.0F, 25, MagicCircleVariant.WATER, (LivingEntity) entity, new CompoundTag(), 3.0F, Vec3.ZERO, instance, 0, Pair.of((double)0.0F, (double)100.0F));
            }

        }).startCondition((entity) -> ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.WATER_CUTTER.get()) != null && ((PrimordialDaemonEntity)entity).getRandom().nextFloat() < 0.3F), (new CustomRangeAttack(15)).maxAttackRadius(40.0F).attackInterval((entity) -> 20).performAttack((entity, target) -> ((PrimordialDaemonEntity)entity).shootWindCutter((LivingEntity) target, 1.5F)).whenStarting((entity) -> {
            ((PrimordialDaemonEntity)entity).triggerAnim("attackController", ((PrimordialDaemonEntity)entity).getRandom().nextBoolean() ? "swing_right" : "swing_left");
            ManasSkillInstance instance = ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.WIND_CUTTER.get());
            if (instance != null) {
                MagicCircle.castMagicCircle(2.0F, 25, MagicCircleVariant.WIND, (LivingEntity) entity, new CompoundTag(), 3.0F, Vec3.ZERO, instance, 0, Pair.of((double)0.0F, (double)100.0F));
            }

        }).startCondition((entity) -> ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.WIND_CUTTER.get()) != null && ((Entity)entity).getRandom().nextFloat() < 0.3F), (new CustomHeldAttack()).minAttackRadius(0.0F).maxAttackRadius(40.0F).attackInterval((entity) -> 40).onTick((entity, target, tick) -> {
            if (((int)tick) >= 15 && ((int)tick) <= 45) {
                ((PrimordialDaemonEntity)entity).flameOrb();
            }

            return ((int)tick) < 60;
        }).whenStarting((entity) -> {
            ((PrimordialDaemonEntity)entity).triggerAnim("miscController", "magic_big");
            ManasSkillInstance instance = ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.FIRE_BALL.get());
            if (instance != null) {
                MagicCircle.castMagicCircle(3.0F, 45, MagicCircleVariant.FLAME, true, (LivingEntity) entity, new CompoundTag(), 0.0F, new Vec3((double)0.0F, (double)-1.0F, (double)0.0F), instance, 0, Pair.of((double)0.0F, (double)1000.0F));
            }

        }).startCondition((entity) -> ((PrimordialDaemonEntity)entity).getMagic((LivingEntity) entity, (Magic)AspectualMagics.FIRE_BALL.get()) != null && ((PrimordialDaemonEntity)entity).getRandom().nextFloat() < 0.2F)})).startCondition((entity) -> ((PrimordialDaemonEntity)entity).canCastMagics((LivingEntity) entity))});
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{(new AnimationController(this, "loopController", 10, this::loopController)).triggerableAnim("conceal", RawAnimation.begin().then("animation.lesser_daemon.conceal", Animation.LoopType.PLAY_ONCE)).triggerableAnim("conceal_off", RawAnimation.begin().then("animation.lesser_daemon.conceal_off", Animation.LoopType.PLAY_ONCE)), (new AnimationController(this, "miscController", 3, (event) -> PlayState.STOP)).triggerableAnim("magic_shoot", RawAnimation.begin().then("animation.lesser_daemon.magic_shoot", Animation.LoopType.PLAY_ONCE)).triggerableAnim("magic_big", RawAnimation.begin().then("animation.lesser_daemon.magic_big", Animation.LoopType.PLAY_ONCE)).triggerableAnim("burst", RawAnimation.begin().then("animation.lesser_daemon.burst", Animation.LoopType.PLAY_ONCE)), (new AnimationController(this, "attackController", 3, (event) -> PlayState.STOP)).setAnimationSpeed((double)2.0F).triggerableAnim("attack_left", RawAnimation.begin().then("animation.lesser_daemon.attack_left", Animation.LoopType.PLAY_ONCE)).triggerableAnim("attack_right", RawAnimation.begin().then("animation.lesser_daemon.attack_right", Animation.LoopType.PLAY_ONCE)).triggerableAnim("swing_left", RawAnimation.begin().then("animation.lesser_daemon.swing_left", Animation.LoopType.PLAY_ONCE)).triggerableAnim("swing_right", RawAnimation.begin().then("animation.lesser_daemon.swing_right", Animation.LoopType.PLAY_ONCE))});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Generated
    public void setFlyingTick(int flyingTick) {
        this.flyingTick = flyingTick;
    }

    @Generated
    public int getFlyingTick() {
        return this.flyingTick;
    }

    @Generated
    public void setWasFlying(boolean wasFlying) {
        this.wasFlying = wasFlying;
    }

    static {
        FLYING = SynchedEntityData.defineId(LesserDaemonEntity.class, EntityDataSerializers.BOOLEAN);
        DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        EVOLUTION_STATE = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        GENDER = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        SKIN = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        FACE = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        TOP = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        TOP_COLOR = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        BOTTOM = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
        BOTTOM_COLOR = SynchedEntityData.defineId(PrimordialDaemonEntity.class, EntityDataSerializers.INT);
    }
}
