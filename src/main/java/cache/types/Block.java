package cache.types;

public abstract class Block {

    protected int life;

    private boolean alive;
    private int timeAlive;

    public Block(int life) {
        this.life = life;
    }

    public void update() {
        alive = ((timeAlive / 60) < life);
        if (alive) {
            timeAlive++;
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
