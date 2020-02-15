package cache.types;

public class StringArrayBlock extends Block {

    private String[] data;

    public StringArrayBlock(String[] data) {
        super();
        this.data = data;
    }

    public StringArrayBlock(String[] data, int life) {
        super(life);
        this.data = data;
    }

    public String[] getData() {
        return data;
    }
}
