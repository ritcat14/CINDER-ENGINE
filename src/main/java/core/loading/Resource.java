package core.loading;

public abstract class Resource {

    private boolean initialised = false;

    public void init() {
        initialised = true;
    }

    public boolean isInitialised() {
        return initialised;
    }
}
