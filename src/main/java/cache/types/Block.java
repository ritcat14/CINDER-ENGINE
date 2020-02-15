package cache.types;

public abstract class Block {

    private static int BLOCK_LIFE = 60;
    protected int life;

    private boolean alive;
    private int timeAlive;

    public Block(int life) {
        this.life = life;
    }

    public Block() {
        this.life = BLOCK_LIFE;
    }

    public void update() {
        alive = ((timeAlive / 60) < life);
        if (alive) {
            timeAlive++;
        }
    }

    public void kill() {
        timeAlive = life * 60 + 10;
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
