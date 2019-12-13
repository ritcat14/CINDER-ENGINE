package core.objects;

import core.loading.Resource;
import core.CinderEngine.RenderType;

public interface Object extends Resource {

    void update(RenderType renderType);

    void render(RenderType renderType);

}
