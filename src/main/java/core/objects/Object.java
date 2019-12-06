package core.objects;

import core.loading.Resource;
import core.CinderEngine.RenderType;

public interface Object extends Resource {
    
    private RenderType renderType;
    
    public Object(RenderType renderType) {
        this.renderType = renderType;
    }

    void update();

    void render(RenderType renderType);

}
