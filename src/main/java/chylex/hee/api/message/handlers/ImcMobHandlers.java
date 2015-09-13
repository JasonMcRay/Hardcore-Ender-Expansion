package chylex.hee.api.message.handlers;
import chylex.hee.api.message.MessageHandler;
import chylex.hee.api.message.MessageRunner;
import chylex.hee.api.message.element.SpawnEntryValue;
import chylex.hee.api.message.utils.MessageLogger;
import chylex.hee.api.message.utils.RunEvent;
import chylex.hee.entity.GlobalMobData;

public final class ImcMobHandlers extends ImcHandler{
	private static final MessageHandler enderGooTolerantAdd = new MessageHandler(){
		@Override
		public void call(MessageRunner runner){
			if (GlobalMobData.setEnderGooTolerant(runner.getString("id")))MessageLogger.logOk("Added 1 mob to the list.");
			else MessageLogger.logFail("The mob was already in the list.");
		}
	};
	
	@Override
	public void register(){
		register("HEE:Mobs:SetGooImmune",enderGooTolerantAdd,RunEvent.LOADCOMPLETE)
		.addProp("id",SpawnEntryValue.livingMobString);
	}
}
