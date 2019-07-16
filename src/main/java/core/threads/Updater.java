package core.threads;

/*
 *  Class responsible for rendering the current game state
 */

public class Updater extends Loop {

    private final double width, height;

    public Updater(ThreadManager threadManager, double MAX_TPS, double width, double height) {
        super(threadManager, MAX_TPS, "UPDATER");
        this.width = width;
        this.height = height;
        threadManager.setUpdater(this);
    }

    @Override
    public void init() {
        threadManager.init(width, height);
    }

    @Override
    protected void loop() {
        threadManager.intermediateCode();
        threadManager.update();
    }
}
