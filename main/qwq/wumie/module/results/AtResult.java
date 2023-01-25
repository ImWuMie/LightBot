package qwq.wumie.module.results;

import qwq.wumie.module.Resul;
import qwq.wumie.utils.GsonUtils;

public class AtResult extends Resul {
    @Override
    public String toJSON() {
        return GsonUtils.beanToJson(get());
    }

    @Override
    public Resul get() {
        return this;
    }
}
