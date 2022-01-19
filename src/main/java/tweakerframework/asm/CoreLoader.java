package tweakerframework.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import tweakerframework.asm.helper.ObfHelper;

@IFMLLoadingPlugin.Name("Tweaker Framework ASM")
@IFMLLoadingPlugin.SortingIndex(1002)
@IFMLLoadingPlugin.TransformerExclusions({ "tweakerframework.asm", "tweakerframework.asm." })

public class CoreLoader implements IFMLLoadingPlugin
{
	//
	// IFMLLoadingPlugin
	// 
	
	@Override
	public String[] getASMTransformerClass()
	{
		ASMConfig.setup();
		return new String[] { "tweakerframework.asm.TweakerASM" };
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		ObfHelper.setObfuscated((Boolean) data.get("runtimeDeobfuscationEnabled"));
		ObfHelper.setRunsAfterDeobfRemapper(true);
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
