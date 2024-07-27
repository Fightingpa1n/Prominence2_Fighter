package net.fightingpainter.providence.main.client.screens;

import org.jetbrains.annotations.NotNull;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.ui.core.Surface;
import io.wispforest.owo.ui.core.VerticalAlignment;

import net.minecraft.text.Text;

public class SkillTree extends BaseOwoScreen<FlowLayout> {
        
        //parts
        ButtonComponent closeButton = (ButtonComponent) Components.button(Text.literal("X"), close_button -> close()).sizing(Sizing.fixed(20), Sizing.fixed(20));

        public SkillTree() {
            super(Text.of("Skill Tree"));
        }

        
        @Override
        protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
            return OwoUIAdapter.create(this, Containers::verticalFlow);
        }
        
        @Override public boolean shouldPause() {return false;} //don't pause pls

        
        @Override
        protected void build(FlowLayout rootComponent) {
            
            LabelComponent title = Components.label(Text.of("HELLO IM A SKILL TREE"));
        
            ParentComponent top_title = Containers.horizontalFlow(Sizing.fixed(100), Sizing.content())
            .child(title)
            .verticalAlignment(VerticalAlignment.CENTER)
            .horizontalAlignment(HorizontalAlignment.CENTER);
            
            ParentComponent top_close = Containers.horizontalFlow(Sizing.fixed(100), Sizing.content())
            .child(closeButton)
                .verticalAlignment(VerticalAlignment.CENTER)
                .horizontalAlignment(HorizontalAlignment.RIGHT);
    
            ParentComponent top = Containers.stack(Sizing.fixed(100), Sizing.content())
                .child(top_title)
                .child(top_close)
                .verticalAlignment(VerticalAlignment.CENTER);

            rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);
    
            rootComponent.child(
                    Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(top)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
            );
        }
    
}