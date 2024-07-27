package net.fightingpainter.providence.main.entities;

import net.fightingpainter.providence.main.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class OwlEntity extends PathAwareEntity implements GeoEntity {
    public OwlEntity(EntityType<? extends PathAwareEntity> type, World world) { //constructor
        super(type, world);
    }
    
    //================================================================== ENTITY ==================================================================\\
    public enum State {IDLE, FLYING} //setting different states
    private State current_state = State.IDLE; //init current state
    
    @Override
    protected void initGoals() { //init goals
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F)); //add goal
    }
    
    
    public static DefaultAttributeContainer.Builder setEntityAtributes() { //set attributes
        return PathAwareEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }
    
    
    //================================================================== PLAYER INTERACTIONS ==================================================================\\        
    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) { //toggle flying on right click
        if (player.isSneaking()) { //(debug) sneaking while interacting will iterate through the states
            if (this.current_state == State.IDLE) {this.current_state = State.FLYING;}
            else {this.current_state = State.IDLE;}
        } else {
            // Example of adding a sneeze animation
            playShortAnimation(SNEEZE);
        }
        return ActionResult.SUCCESS; //return success   
    }
    
    //================================================================== ANIMATION STUFF ==================================================================\\
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this); //animation cache
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;} //get animation cache
    
    private RawAnimation currentAnimation = null; //current short animation
    
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private final RawAnimation FLY = RawAnimation.begin().thenLoop("fly");
    private final String SNEEZE = "sneeze";
    
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) { //register animation controllers
        controllers.add(new AnimationController<>(this, "statePlayer", 0, this::statePlayer));
        controllers.add(new AnimationController<>(this, "animPlayer", 0, this::AnimPlayer));
        
    }
    
    private <E extends GeoEntity> PlayState statePlayer(AnimationState<E> event) { //state player controller
        if (currentAnimation == null) {
            if (current_state == State.IDLE) {
                event.getController().setAnimation(IDLE); //idle animation
            } else if (current_state == State.FLYING) {
                event.getController().setAnimation(FLY); //flying animation
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    
    private <E extends GeoEntity> PlayState AnimPlayer(AnimationState<E> event) { //animation player controller
        if (currentAnimation != null) {
            event.getController().setAnimation(currentAnimation);
            if(event.getController().hasAnimationFinished()) {
                Main.LOGGER.info("animation finished");
                event.getController().forceAnimationReset();
                currentAnimation = null;
                return PlayState.STOP;
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    
    
    public void playShortAnimation(String animation) {
        Main.LOGGER.info("starting animation");
        currentAnimation = RawAnimation.begin().thenPlay(animation);
    }
    
    //================================================================== MISC ==================================================================\\


    //TODO: My idea to disable it looking at the player while playing an animation didn't work so for now I'll just have to live with it
}
