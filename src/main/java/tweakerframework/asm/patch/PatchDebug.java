package tweakerframework.asm.patch;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class PatchDebug extends PatchManager
{	
	public PatchDebug()
	{
		super("Debug");
		
		add(new Patch(this, "net.minecraft.classname.goes.here", ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES)
		{
			@Override
			public void patch(ClassNode clazzNode)
			{
				//
			}	
		});
	}
}
