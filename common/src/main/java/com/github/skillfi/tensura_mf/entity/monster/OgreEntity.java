package com.github.skillfi.tensura_mf.entity.monster;

import com.github.skillfi.tensura_mf.api.data.IResourceEntity;
import com.github.skillfi.tensura_mf.entity.ai.behaviour.TensuraMfBehaviourHelper;
import com.github.skillfi.tensura_mf.entity.variant.OgreVariant;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import com.github.skillfi.tensura_mf.registry.entity.TensuraMfEntityTypes;
import io.github.manasmods.tensura.entity.ai.behaviour.ProfessionBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.HumanoidConsumeItem;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.InteractWithEntity;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.VillagerLikeBreed;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.ai.sensor.NearbyTreeSensor;
import io.github.manasmods.tensura.entity.ai.sensor.NearbyWantedItemSensor;
import io.github.manasmods.tensura.entity.ai.sensor.SleepSensor;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.IGender;
import io.github.manasmods.tensura.entity.template.subclass.INameEvolution;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import io.github.manasmods.tensura.world.TensuraGameRules;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.InteractWithDoor;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

/**
 * Base common entity for an ogre-like monster.
 *
 * <p>Platform-specific registration and client rendering belong in the
 * corresponding platform modules.</p>
 */
public class OgreEntity extends PlayerLikeEntity implements SmartBrainOwner<OgreEntity>, GeoEntity, INameEvolution, IGender, IResourceEntity<OgreEntity> {
    /** Synchronized appearance and evolution state shared with the client. */
    private static final EntityDataAccessor<Integer> EVOLUTION_STATE;
    private static final EntityDataAccessor<Integer> GENDER;
    private static final EntityDataAccessor<Integer> HORNS;
    private static final EntityDataAccessor<Integer> SKIN;
    private static final EntityDataAccessor<Integer> FACE;
    private static final EntityDataAccessor<Integer> HAIR;
    private static final EntityDataAccessor<Integer> HAIR_COLOR;
    private static final EntityDataAccessor<Integer> TOP;
    private static final EntityDataAccessor<Integer> TOP_COLOR;
    private static final EntityDataAccessor<Integer> BOTTOM;
    private static final EntityDataAccessor<Integer> BOTTOM_COLOR;
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT;
    private static final EntityDataAccessor<Integer> EVOLVING;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private SmartBrainSchedule schedule;

    /** Creates an ogre and configures its navigation for door use and water avoidance. */
    public OgreEntity(EntityType<? extends OgreEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
    }

    /** Returns the default combat, movement and interaction attributes for ogres. */
    public static AttributeSupplier.Builder setAttributes() {
        return TensuraTamableEntity.setAttributes()
                .add(Attributes.ATTACK_DAMAGE, (double)1.5F)
                .add(Attributes.MAX_HEALTH, (double)28.0F)
                .add(Attributes.MOVEMENT_SPEED, (double)0.2F)
                .add(Attributes.KNOCKBACK_RESISTANCE, (double)0.5F)
                .add(Attributes.ENTITY_INTERACTION_RANGE, (double)2.0F)
                .add(Attributes.STEP_HEIGHT, (double)1.0F);
    }

    /** Defines entity data synchronized between the server and the client. */
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(EVOLUTION_STATE, 0);
        builder.define(GENDER, 0);
        builder.define(HORNS, 0);
        builder.define(SKIN, 0);
        builder.define(FACE, 0);
        builder.define(HAIR, 0);
        builder.define(HAIR_COLOR, 0);
        builder.define(TOP, 0);
        builder.define(TOP_COLOR, -1);
        builder.define(BOTTOM, 0);
        builder.define(BOTTOM_COLOR, -1);
        builder.define(DATA_ID_TYPE_VARIANT, 0);
        builder.define(EVOLVING, 0);
    }

    /** Saves evolution, appearance, gender and home-memory data to the entity tag. */
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("EvoState", this.getCurrentEvolutionState());
        super.addAdditionalSaveData(compound);
        compound.putInt("Evolving", this.getEvolving());
        compound.putInt("Gender", (Integer)this.entityData.get(GENDER));
        compound.putInt("Horns", (Integer)this.entityData.get(HORNS));
        TensuraBehaviourHelper.saveGlobalPos(this, compound, MemoryModuleType.HOME, "Home");
        compound.putInt("Skin", (Integer)this.entityData.get(SKIN));
        compound.putInt("Face", (Integer)this.entityData.get(FACE));
        compound.putInt("Hair", (Integer)this.entityData.get(HAIR));
        compound.putInt("HairColor", (Integer)this.entityData.get(HAIR_COLOR));
        compound.putInt("Top", (Integer)this.entityData.get(TOP));
        compound.putInt("TopColor", (Integer)this.entityData.get(TOP_COLOR));
        compound.putInt("Bottom", (Integer)this.entityData.get(BOTTOM));
        compound.putInt("BottomColor", (Integer)this.entityData.get(BOTTOM_COLOR));
    }

    /** Restores evolution, appearance, gender and home-memory data from the entity tag. */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.setCurrentEvolutionState(compound.getInt("EvoState"));
        super.readAdditionalSaveData(compound);
        this.setEvolving(compound.getInt("Evolving"));
        TensuraBehaviourHelper.readGlobalPos(this, compound, MemoryModuleType.HOME, "Home");
        this.entityData.set(GENDER, compound.getInt("Gender"));
        this.entityData.set(HORNS, compound.getInt("Horns"));
        this.entityData.set(SKIN, compound.getInt("Skin"));
        this.entityData.set(FACE, compound.getInt("Face"));
        this.entityData.set(HAIR, compound.getInt("Hair"));
        this.entityData.set(HAIR_COLOR, compound.getInt("HairColor"));
        this.entityData.set(TOP, compound.getInt("Top"));
        this.entityData.set(TOP_COLOR, compound.getInt("TopColor"));
        this.entityData.set(BOTTOM, compound.getInt("Bottom"));
        this.entityData.set(BOTTOM_COLOR, compound.getInt("BottomColor"));
    }

    public OgreVariant.Gender getGender() {
        return OgreVariant.Gender.byId((Integer)this.entityData.get(GENDER));
    }

    public void setGender(int gender) {
        this.entityData.set(GENDER, gender);
    }

    public boolean isMale() {
        return this.getGender() == OgreVariant.Gender.MALE;
    }

    public boolean isFemale() {
        return this.getGender() == OgreVariant.Gender.FEMALE;
    }

    public OgreVariant.Skin getSkin() {
        return OgreVariant.Skin.byId((Integer)this.entityData.get(SKIN));
    }

    public void setSkin(int skin) {
        this.entityData.set(SKIN, skin);
    }

    public void setHorns(int horns) {
        this.entityData.set(HORNS, horns);
    }

    public OgreVariant.Horns getHorns() {
        return OgreVariant.Horns.byId((Integer)this.entityData.get(HORNS));
    }

    public OgreVariant.Face getFace() {
        return OgreVariant.Face.byId((Integer)this.entityData.get(FACE));
    }

    public void setFace(int face) {
        this.entityData.set(FACE, face);
    }

    public int getEvolving() {
        return (Integer)this.entityData.get(EVOLVING);
    }

    public void setEvolving(int tick) {
        this.entityData.set(EVOLVING, tick);
    }

    public OgreVariant.Hair getHair() {
        return OgreVariant.Hair.byId(this.entityData.get(HAIR));
    }

    public void setHair(int hair) {
        this.entityData.set(HAIR, hair);
    }

    public int getHairColor() {
        return this.entityData.get(HAIR_COLOR);
    }

    public void setHairColor(int i) {
        this.entityData.set(HAIR_COLOR, i);
    }

    public OgreVariant.Top getTop() {
        return OgreVariant.Top.byId(this.entityData.get(TOP));
    }

    public void setTop(int top) {
        this.entityData.set(TOP, top);
    }

    public int getTopColor() {
        return (Integer)this.entityData.get(TOP_COLOR);
    }

    public void setTopColor(int i) {
        this.entityData.set(TOP_COLOR, i);
    }

    public OgreVariant.Bottom getBottom() {
        return OgreVariant.Bottom.byId((Integer)this.entityData.get(BOTTOM));
    }

    public void setBottom(int bottom) {
        this.entityData.set(BOTTOM, bottom);
    }

    public int getBottomColor() {
        return (Integer)this.entityData.get(BOTTOM_COLOR);
    }

    public void setBottomColor(int i) {
        this.entityData.set(BOTTOM_COLOR, i);
    }

    /** Registers the fallback vanilla goals used by the ogre. */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    /** Supplies sensors used by the SmartBrain AI, including sleep and profession work. */
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.VILLAGER_HURT;
    }

    /** Registers GeckoLib animation controllers for this entity. */
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 1.2F;
    }

    @Override
    public List<ExtendedSensor<OgreEntity>> getSensors() {
        return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor(), new SleepSensor(), (new NearbyTreeSensor()).setRadius((double)10.0F, (double)2.0F).shouldScan(entity -> ((OgreEntity)entity).shouldDoLumberjack() && ((OgreEntity)entity).level().getGameRules().getBoolean(TensuraGameRules.NPC_WORKING)), (new NearbyWantedItemSensor()).shouldScan((entity) -> ((OgreEntity)entity).shouldAlwaysPickUpItem() && ((OgreEntity)entity).level().getGameRules().getBoolean(TensuraGameRules.NPC_WORKING)).setPredicate(NearbyWantedItemSensor.getProfessionItemPredicate())});
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "loopController", 10, this::loopController), (new AnimationController(this, "miscController", 3, (event) -> {
            this.swinging = false;
            return PlayState.STOP;
        })).triggerableAnim("attack", RawAnimation.begin().then("animation.ogre.attack", Animation.LoopType.PLAY_ONCE)).triggerableAnim("shield", RawAnimation.begin().then("animation.ogre.shield", Animation.LoopType.PLAY_ONCE)).triggerableAnim("crossbow", RawAnimation.begin().then("animation.ogre.crossbow", Animation.LoopType.PLAY_ONCE)).triggerableAnim("spear", RawAnimation.begin().then("animation.ogre.spear", Animation.LoopType.PLAY_ONCE)));
    }

    /** Builds idle activities such as breeding, following, work and random wandering. */
    @Override
    public BrainActivityGroup<OgreEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour(
                        new FirstApplicableBehaviour(
                                (new InteractWithEntity(this.getType(), MemoryModuleType.BREED_TARGET))
                                        .selfPredicate((entity) -> ((OgreEntity) entity).canBreed())
                                        .targetPredicate((entity) -> ((OgreEntity) entity).canBreed())
                                        .bothPredicate((entity, target) -> ((Animal) entity).canMate((Animal) target))
                                        .interactTime((entity) -> 300),
                                new VillagerLikeBreed()
                        ).startCondition((entity) -> !((OgreEntity) entity).isTamedByNonPlayer()),
                        (new FollowParent()).startCondition((entity) -> !((OgreEntity) entity).isTame() || ((OgreEntity) entity).isWandering()),
                        TensuraBehaviourHelper.getPreyTargeting(this, (entity) -> false),
                        new SubordinateFollowOwner(),
                        ProfessionBehaviourHelper.getBasicJobBehaviours(this),
                        new SetPlayerLookTarget(),
                        new SetRandomLookTarget()
                ),
                new InteractWithDoor(),
                (new HumanoidConsumeItem())
                        .startCondition((entity) -> ((OgreEntity) entity).shouldHeal())
                        .stopIf((entity) -> !((OgreEntity) entity).shouldHeal()),
                (new OneRandomBehaviour(
                        new SetRandomWalkTarget(),
                        (new Idle()).runFor((entity) -> ((OgreEntity) entity).getRandom().nextInt(30, 60))
                )).startCondition((entity) -> !((OgreEntity) entity).isOrderedToSit())
        );
    }

    /** Builds combat activities for melee and ranged weapons. */
    @Override
    public BrainActivityGroup<OgreEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new Behavior[]{(new InvalidateNeutralAttackTarget()).invalidateIf((entity, target) -> this.shouldStopTarget((Mob) entity, (LivingEntity) target)), (new StrafeTarget()).stopStrafingWhen((entity) -> !((OgreEntity) entity).usingRangedWeapon()).startCondition(entity -> ((OgreEntity) entity).usingRangedWeapon()), (new SetWalkTargetToAttackTarget()).speedMod((owner, target) -> 2.0F).startCondition((entity) -> !((OgreEntity) entity).usingRangedWeapon()), new FirstApplicableBehaviour(PlayerLikeEntity.getSpearAttack(20).attackInterval((entity) -> 5).attackRadius(25.0F).whenStarting((entity) -> this.triggerAnim("miscController", "spear")), PlayerLikeEntity.getCrossbowAttack().attackInterval((entity) -> 10).attackRadius(30.0F).whenStarting((entity) -> this.triggerAnim("miscController", "crossbow")), PlayerLikeEntity.getBowAttack().attackInterval((entity) -> 10).attackRadius(25.0F).whenStarting((entity) -> this.triggerAnim("miscController", "crossbow")), (new AnimatableMeleeAttack(1)).attackInterval((entity) -> 5).whenStarting((entity) -> ((OgreEntity)entity).swing(InteractionHand.MAIN_HAND, true)).startCondition((entity) -> !((PlayerLikeEntity) entity).usingRangedWeapon()))});
    }

    /** Returns the daily schedule used by the SmartBrain activities. */
    @Override
    public SmartBrainSchedule getSchedule() {
        if (this.schedule == null) {
            this.schedule = (new SmartBrainSchedule()).activityAt(10, Activity.IDLE).activityAt(13000, Activity.REST);
        }

        return this.schedule;
    }

    /** Starts sleeping and records the time while clearing stale movement targets. */
    @Override
    public void startSleeping(BlockPos blockPos) {
        super.startSleeping(blockPos);
        BrainUtils.setMemory(this, MemoryModuleType.LAST_SLEPT, this.level().getGameTime());
        BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
        BrainUtils.clearMemory(this, MemoryModuleType.LOOK_TARGET);
        BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    /** Stops sleeping and records the time the ogre woke up. */
    @Override
    public void stopSleeping() {
        super.stopSleeping();
        BrainUtils.setMemory(this, MemoryModuleType.LAST_WOKEN, this.level().getGameTime());
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        if (!(pRandom.nextFloat() >= 0.5F)) {
            int i = pRandom.nextInt(3);
            ItemStack stack = new ItemStack(TensuraToolItems.IRON_KATANA.get());
            if (i == 0) {
                stack = new ItemStack(TensuraToolItems.WOODEN_ODACHI.get());
            }

            this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), stack);
            this.updateContainerEquipment();
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        if (this.canRandomizeSpawnData(pReason)) {
            this.populateDefaultEquipmentSlots(this.random, pDifficulty);
            this.randomTexture();
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        if (!super.canMate(pOtherAnimal)) {
            return false;
        } else {
            return ((OgreEntity)pOtherAnimal).getGender() != this.getGender();
        }
    }

    private void randomTexture() {
        this.setGender(this.random.nextBoolean() ? 0 : 1);
        this.setSkin(OgreVariant.Skin.getRandom(this));
        this.setFace(OgreVariant.Face.getRandom(this.getGender(), this));
        List<Integer> hairColors = TensuraMfBehaviourHelper.CONFIG.Ogre.ogreHairColors;
        this.setHair(OgreVariant.Hair.getRandom(this));
        this.setHairColor(hairColors.get(this.random.nextInt(hairColors.size())));
        List<Integer> colors = TensuraMfBehaviourHelper.CONFIG.Ogre.ogreClothingColors;
        this.setTop(OgreVariant.Top.getRandom(this));
        this.setHorns(OgreVariant.Horns.getRandom(this));
//        this.setTopColor(colors.get(this.random.nextInt(colors.size())));
        List<Integer> bottomColors = TensuraMfBehaviourHelper.CONFIG.Ogre.ogreBottomClothesColors;
        this.setBottom(OgreVariant.Bottom.getRandom(this));
//        this.setBottomColor(bottomColors.get(this.random.nextInt(bottomColors.size())));
    }

    @Override
    public void die(DamageSource damageSource) {
        TensuraBehaviourHelper.releaseHome(this);
        super.die(damageSource);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.isTame();
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
        return TensuraEntityTypes.rollSpawn(TensuraMfEntityTypes.CONFIG.SpawnChance.ogre, pLevel, pSpawnReason) && super.checkSpawnRules(pLevel, pSpawnReason);
    }

    protected PlayState loopController(AnimationState<OgreEntity> state) {
        String name;
        if (this.isSleeping()) {
            name = "animation.ogre.idle";
        } else if (this.isInSittingPose()) {
            name = "animation.ogre.sit";
        } else if (this.shouldSwim()) {
            name = "animation.ogre.swim";
        } else if (state.isMoving()) {
            if (!this.isAngry() && !this.isSprinting() && (this.getControllingPassenger() == null || !this.getControllingPassenger().isSprinting())) {
                name = "animation.ogre.walk";
            } else {
                name = "animation.ogre.run";
            }
        } else {
            name = "animation.ogre.idle";
        }

        return state.setAndContinue(RawAnimation.begin().thenLoop(name));
    }

    /** Returns the GeckoLib animation cache for this entity. */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    static {
        EVOLUTION_STATE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        GENDER = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        HORNS = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        SKIN = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        FACE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        HAIR = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        HAIR_COLOR = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        TOP = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        TOP_COLOR = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        BOTTOM = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        BOTTOM_COLOR = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        EVOLVING = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    }

    @Override
    public String getClassName() {
        return MonsterEntityTypes.OGRE.toString();
    }

    @Override
    public ResourceLocation getResource() {
        return MonsterEntityTypes.OGRE.get().arch$registryName();
    }
}
