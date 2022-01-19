package tweakerframework.util;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<A, B, C>
{
	void accept(A a, B b, C c);
}
