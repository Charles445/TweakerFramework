package tweakerframework.asm.patch;

import tweakerframework.asm.TweakerASM;
import tweakerframework.asm.util.ASMLogger;

public abstract class PatchManager implements IPatchManager
{
	private String name;
	
	public PatchManager()
	{
		this.name = "PatchManager";
	}
	
	public PatchManager(String name)
	{
		ASMLogger.info("PatchManager: "+name);
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	public void add(Patch patch)
	{
		TweakerASM.addPatch(patch);
	}
}
