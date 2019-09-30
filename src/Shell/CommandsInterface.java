package Shell;

import java.io.Serializable;

public abstract class CommandsInterface implements Serializable {

    public abstract String run(Object[] o);
}