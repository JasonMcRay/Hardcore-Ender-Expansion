package chylex.hee.world.feature.blobs.populators;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import chylex.hee.system.abstractions.Pos;
import chylex.hee.system.abstractions.facing.Facing4;
import chylex.hee.world.feature.blobs.StructureWorldBlob;
import chylex.hee.world.util.IRangeGenerator;
import chylex.hee.world.util.IRangeGenerator.RangeGenerator;
import chylex.hee.world.util.RandomAmount;

public class BlobPopulatorLiquidStream extends BlobPopulator{
	private Block block = Blocks.air;
	private IRangeGenerator amount;
	private boolean canHaveAirAbove, strictFlowCheck;
	
	public BlobPopulatorLiquidStream(int weight){
		super(weight);
	}
	
	public BlobPopulatorLiquidStream setBlock(Block block){
		this.block = block;
		return this;
	}
	
	public BlobPopulatorLiquidStream setAmount(int min, int max){
		this.amount = new RangeGenerator(min, max, RandomAmount.linear);
		return this;
	}
	
	public BlobPopulatorLiquidStream setAmount(IRangeGenerator amount){
		this.amount = amount;
		return this;
	}
	
	public BlobPopulatorLiquidStream setCanHaveAirAbove(){
		this.canHaveAirAbove = true;
		return this;
	}
	
	public BlobPopulatorLiquidStream setStrictFlowCheck(){
		this.strictFlowCheck = true;
		return this;
	}

	@Override
	public void populate(StructureWorldBlob world, Random rand){
		int targetAmount = amount.next(rand);
		
		List<Pos> endStoneBlocks = world.getEndStoneBlocks();
		int attempts = strictFlowCheck ? endStoneBlocks.size() : endStoneBlocks.size()/2;
		
		for(int attempt = 0; attempt < attempts; attempt++){
			Pos pos = endStoneBlocks.remove(rand.nextInt(endStoneBlocks.size())); // size never gets to 0
			
			Facing4 facing = findStreamStart(world, pos);
			if (facing == null)continue;
			
			if (strictFlowCheck){
				pos = pos.offset(facing);
				int y = pos.getY();
				
				while(--y > 0 && !isContained(world, pos.getX(), y, pos.getZ()));
				if (y == 0)continue;
				
				y = pos.getY();
				
				while(--y > 0 && !isContained(world, pos.getX(), y, pos.getZ()))world.setAir(pos.getX(), y, pos.getZ());
				world.setAir(pos.getX(), y, pos.getZ());
				
				pos = pos.offset(facing.opposite());
				world.setBlock(pos, block);
			}
			else{
				world.setBlock(pos, block);
				
				pos = pos.offset(facing);
				int y = pos.getY();
				
				while(--y > 0 && !isContained(world, pos.getX(), y, pos.getZ()))world.setAir(pos.getX(), y, pos.getZ());
				world.setAir(pos.getX(), y, pos.getZ());
			}
			
			if (--targetAmount <= 0)return;
		}
	}
	
	private @Nullable Facing4 findStreamStart(StructureWorldBlob world, Pos pos){
		if (!(canHaveAirAbove || world.getBlock(pos.getX(), pos.getY()+1, pos.getZ()) == Blocks.end_stone))return null;
		if (world.isAir(pos.getX(), pos.getY()-1, pos.getZ()))return null;
		
		Facing4 suitable = null;
		
		for(Facing4 facing:Facing4.list){
			if (world.isAir(pos.offset(facing))){
				if (suitable == null)suitable = facing;
				else return null; // only one side
			}
		}
		
		return suitable;
	}
	
	private boolean isContained(StructureWorldBlob world, int x, int y, int z){
		for(Facing4 facing:Facing4.list){
			if (world.getBlock(x+facing.getX(), y, z+facing.getZ()) != Blocks.end_stone)return false;
		}
		
		return true;
	}
}
