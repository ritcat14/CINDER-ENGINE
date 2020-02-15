package core.loading;

public abstract class Resource {

    private boolean initialised = false;

    public void init() {
        initialised = true;
    }

    public void deinitialise() {
        initialised = false;
    }

    public boolean isInitialised() {
        return initialised;
    }
}
