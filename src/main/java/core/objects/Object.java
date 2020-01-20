package core.objects;

import core.loading.Resource;

public interface Object extends Resource {

    @Override
    void init();

    void update();

    void render();

}
