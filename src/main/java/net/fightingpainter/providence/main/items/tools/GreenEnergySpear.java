package net.fightingpainter.providence.main.items.tools;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fightingpainter.providence.main.client.items.GreenEnergySpearRenderer;
import net.fightingpainter.providence.main.items.tools.materials.GreenEnergyMaterial;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GreenEnergySpear extends ToolItem implements GeoItem {
    public GreenEnergySpear() {
        super(new GreenEnergyMaterial(1), new FabricItemSettings().maxCount(1));
	}
    
    //=================================== GECKOLIB ===================================//
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this); //the animation cache
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this); //the renderer supplier
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;} //get the animation cache
    @Override public Supplier<Object> getRenderProvider() {return this.renderProvider;} //get the renderer supplier
    
	@Override
	public void createRenderer(Consumer<Object> consumer) { //this is where we register the item renderer (items need their renderer registered inside their item class)
		consumer.accept(new RenderProvider() {
			private GreenEnergySpearRenderer renderer;
            @Override public BuiltinModelItemRenderer getCustomRenderer() {
				if (this.renderer == null) this.renderer = new GreenEnergySpearRenderer();
				return this.renderer;
			}
		});
	}

	@Override 
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) { //add controllers
        controllers.add(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    private <E extends SingletonGeoAnimatable> PlayState predicate(AnimationState<E> state) {
        state.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }


    //=================================== FUNCTIONALITY ===================================//

    //okay so this being a Spear it should have be throwable, so first of we need to make it so it can be charged. like a bow or crossbow or trident


    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStack);
    }


}