package me.srgantmoomoo.postmanplusplus.modules;

import org.lwjgl.input.Keyboard;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import me.srgantmoomoo.postman.api.event.events.DamageBlockEvent;
import me.srgantmoomoo.postman.api.event.events.PacketEvent;
import me.srgantmoomoo.postman.api.event.events.RenderEvent;
import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.api.util.render.JTessellator;
import me.srgantmoomoo.postman.api.util.world.GeometryMasks;
import me.srgantmoomoo.postman.api.util.world.JTimer;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import net.minecraft.util.text.TextFormatting;

/*
 * this isnt at all mine, a i got it from some guy on github, will rewrite for packet and shit soon.
 */
public class InstantMine extends Module {
	public BooleanSetting autoBreak = new BooleanSetting("autoBreak", this, true);
	public NumberSetting delay = new NumberSetting("delay", this, 20, 0, 500, 1);
	public BooleanSetting picOnly = new BooleanSetting("picOnly", this, true);
	
	public InstantMine() {
		super("" + TextFormatting.RESET + TextFormatting.ITALIC + "instantMine" + TextFormatting.OBFUSCATED + "++", "this is completey skidded module, check modinfo.", Keyboard.KEY_NONE, Category.EXPLOITS);
		this.addSettings(autoBreak, delay, picOnly);
		// TODO actually add it to modinfo lol
	}
	
	private BlockPos renderBlock;
	private BlockPos lastBlock;
	private boolean packetCancel = false;
	private JTimer breaktimer = new JTimer();
	private EnumFacing direction;

	public static InstantMine INSTANCE;

	@Override
	protected void onEnable() {
		INSTANCE = this;
	}

	public static InstantMine getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new InstantMine();
		}
		return INSTANCE;
	}

	@Override
	public void onWorldRender(RenderEvent event) {
		if (renderBlock != null) {
			drawBlock(renderBlock, 255, 0, 255, true);
		}
	}

	private void drawBlock(BlockPos blockPos, int r, int g, int b, boolean bounding) {
		JTessellator.prepare();
		JTessellator.drawBox(blockPos, 1, new JColor(r,g,b,40), GeometryMasks.Quad.ALL);
		JTessellator.release();
	}

	@Override
	public void onUpdate() {
		if(renderBlock != null) {
			if(autoBreak.isEnabled() && breaktimer.hasReached((long)delay.getValue())) {
				if(picOnly.isEnabled() &&!(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE)) return;
				mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
						renderBlock, direction));
				breaktimer.reset();
			}

		}
		
		try {
			mc.playerController.blockHitDelay = 0;

		} catch (Exception e) {
		}
	}

	@EventHandler
	private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
		Packet packet = event.getPacket();
		if (packet instanceof CPacketPlayerDigging) {
			CPacketPlayerDigging digPacket = (CPacketPlayerDigging) packet;
			if(((CPacketPlayerDigging) packet).getAction()== CPacketPlayerDigging.Action.START_DESTROY_BLOCK && packetCancel) event.cancel();
		}
	});

	@EventHandler
	private Listener<DamageBlockEvent> OnDamageBlock = new Listener<>(p_Event -> {
		if (canBreak(p_Event.getBlockPos())) {

			if(lastBlock==null||p_Event.getBlockPos().x!=lastBlock.x || p_Event.getBlockPos().y!=lastBlock.y || p_Event.getBlockPos().z!=lastBlock.z) {
				//Command.sendChatMessage("New Block");
				packetCancel = false;
				//Command.sendChatMessage(p_Event.getPos()+" : "+lastBlock);
				mc.player.swingArm(EnumHand.MAIN_HAND);
				mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
						p_Event.getBlockPos(), p_Event.getEnumFacing()));
				packetCancel = true;
			}else{
				packetCancel = true;
			}
			//Command.sendChatMessage("Breaking");
			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
					p_Event.getBlockPos(), p_Event.getEnumFacing()));

			renderBlock = p_Event.getBlockPos();
			lastBlock = p_Event.getBlockPos();
			direction = p_Event.getEnumFacing();

			p_Event.cancel();

		}
	});

	private boolean canBreak(BlockPos pos) {
		final IBlockState blockState = mc.world.getBlockState(pos);
		final Block block = blockState.getBlock();

		return block.getBlockHardness(blockState, mc.world, pos) != -1;
	}

	public BlockPos getTarget(){
		return renderBlock;
	}

	public void setTarget(BlockPos pos){
		renderBlock = pos;
		packetCancel = false;
		mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
				pos, EnumFacing.DOWN));
		packetCancel = true;
		mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
				pos, EnumFacing.DOWN));
		direction = EnumFacing.DOWN;
		lastBlock = pos;
	}

}
