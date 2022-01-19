package tweakerframework.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.tree.ClassNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import tweakerframework.asm.helper.ASMHelper;
import tweakerframework.asm.patch.IPatch;
import tweakerframework.asm.patch.Patch;
import tweakerframework.asm.util.ASMInfo;
import tweakerframework.asm.util.ASMLogger;

public class TweakerASM implements IClassTransformer
{
	private boolean run = true;
	
	private boolean firstrun = false;
	
	//private boolean debug = true;
	
	protected static Map<String, List<IPatch>> transformMap = new HashMap<>();
	
	public TweakerASM()
	{
		super();
		
		ASMInfo.cacheServerType(this.getClass().getClassLoader());
		
		ASMLogger.info("Server Type: "+ASMInfo.serverType.name());
		
		this.run = ASMConfig.getBoolean("general.patches.ENABLED", true);
		
		if(this.run)
		{
			ASMLogger.info("Patcher is enabled");
		}
		else
		{
			ASMLogger.info("Patcher has been disabled");
			return;
		}
		
		this.createPatches();
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		byte[] result = doTransform(name, transformedName, basicClass);
		return result;
	}
	
	public byte[] doTransform(String name, String transformedName, byte[] basicClass)
	{
		if(!this.run)
			return basicClass;
		
		if(!firstrun)
		{
			firstrun = true;
			
			//Gather up loaded transformers in order to make informed decisions later
			if(this.getClass().getClassLoader() instanceof LaunchClassLoader)
			{
				LaunchClassLoader loader = (LaunchClassLoader)this.getClass().getClassLoader();
				for(IClassTransformer transformer : loader.getTransformers())
				{
					if(transformer != null)
					{
						Patch.loadedTransformers.add(transformer.getClass().getName());
					}
				}
			}
			else
			{
				ASMLogger.warn("WARNING: "+ASMReference.NAME+" transformer firstrun is not loaded by LaunchClassLoader! Some mod and server compatibility will fail!");
			}
		}
		
		//if(this.debug)
		//	PatchDebug.transformAll(basicClass);
		
		//Check for patches
		if(transformMap.containsKey(transformedName))
		{
			ASMLogger.info("Patch exists for "+transformedName);
			int flags = 0;
			int oldFlags = 0;
			
			boolean ranAnyPatch = false;
			
			ClassNode clazzNode = ASMHelper.readClassFromBytes(basicClass);
			
			//TODO backup old classnode state and flags for safer exception handling?
			for(IPatch patch : transformMap.get(transformedName))
			{
				oldFlags = flags;
				flags = flags | patch.getFlags();
				try
				{
					patch.patch(clazzNode);
					if(patch.isCancelled())
					{
						flags = oldFlags;
					}
					else
					{
						ranAnyPatch = true;
					}
				}
				catch(Exception e)
				{
					ASMLogger.warn("Failed at patch "+patch.getPatchManager().getName());
					ASMLogger.warn("Failed to write "+transformedName);
					ASMLogger.error("Failed Patch Trace: ", e);
					return basicClass;
				}
			}
			
			//TODO verbose
			if(ranAnyPatch)
			{
				ASMLogger.info("Writing class "+transformedName+" with flags "+flagsAsString(flags));
				return ASMHelper.writeClassToBytes(clazzNode, flags);
				//return ASMHelper.writeClassToBytes(clazzNode, ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
			}
			else
			{
				ASMLogger.info("All patches for class "+transformedName+" were cancelled, skipping...");
				return basicClass;
			}
			
		}
		
		
		return basicClass;
	}
	
	public static String flagsAsString(int flags)
	{
		switch(flags)
		{
			case 0: return "None";
			case 1: return "MAXS";
			case 2: return "FRAMES";
			case 3: return "MAXS | FRAMES";
			default: return "(unknown "+flags+")";
		}
	}
	
	public static void addPatch(IPatch patch)
	{
		String target = patch.getTargetClazz();
		
		if(!transformMap.containsKey(target))
			transformMap.put(target, new ArrayList<IPatch>());
		
		List<IPatch> patches = transformMap.get(target);
		patches.add(patch);
	}
	
	private void createPatches()
	{
		//Create all the patches
		
		Patches.addPatches();
	}

}
