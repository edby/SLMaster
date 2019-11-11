package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:16
 */
public interface TeamBiz {
    String init(User user, PageModel pageModel);

    String list(User user);

    String directList(User user, PageModel pageModel);
}
