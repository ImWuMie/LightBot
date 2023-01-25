package qwq.wumie.module.results;

import qwq.wumie.module.Result;
import qwq.wumie.utils.GsonUtils;

public class AtResult extends Result {
    @Override
    public String toJSON() {
        return GsonUtils.beanToJson(get());
    }

    @Override
    public Result get() {
        return this;
    }
}
