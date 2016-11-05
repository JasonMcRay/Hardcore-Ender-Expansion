package chylex.hee.game.save.types.player;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import chylex.hee.game.save.types.PlayerFile;
import chylex.hee.mechanics.compendium.content.LoreTexts;
import chylex.hee.system.abstractions.nbt.NBTCompound;
import chylex.hee.system.util.RandUtil;

public class LoreFile extends PlayerFile{
	private final EnumMap<LoreTexts, byte[]> readTexts = new EnumMap<>(LoreTexts.class);
	
	public LoreFile(String filename){
		super("lore", filename);
	}
	
	public int getRandomTextIndex(LoreTexts category, Random rand){
		byte[] read = readTexts.getOrDefault(category, ArrayUtils.EMPTY_BYTE_ARRAY);
		
		if (read.length < category.getTextCount()){
			int[] unread = IntStream.range(0, category.getTextCount()).filter(ind -> !ArrayUtils.contains(read, (byte)ind)).toArray();
			int selected = RandUtil.anyOf(rand, unread, 0); // never returns default
			
			readTexts.put(category, ArrayUtils.add(read, (byte)selected));
			setModified();
			
			return selected;
		}
		else return rand.nextInt(category.getTextCount());
	}
	
	public void markAsRead(LoreTexts category, int index){
		byte[] read = readTexts.getOrDefault(category, ArrayUtils.EMPTY_BYTE_ARRAY);
		
		if (ArrayUtils.contains(read, (byte)index)){
			readTexts.put(category, ArrayUtils.add(read, (byte)index));
			setModified();
		}
	}

	@Override
	protected void onSave(NBTCompound nbt){
		for(Entry<LoreTexts, byte[]> entry:readTexts.entrySet()){
			nbt.setByteArray(entry.getKey().getTitle(), entry.getValue());
		}
	}

	@Override
	protected void onLoad(NBTCompound nbt){
		for(String key:nbt.keySet()){
			readTexts.put(LoreTexts.fromTitle(key), nbt.getByteArray(key));
		}
	}
}
