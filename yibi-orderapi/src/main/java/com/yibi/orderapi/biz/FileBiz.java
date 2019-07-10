package com.yibi.orderapi.biz;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:16
 */
public interface FileBiz {
    void IoReadImage(String imgUrl, HttpServletResponse response) throws IOException;
}
