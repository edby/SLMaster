package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohe
 * @since 2019-09-02
 */
@Data
public class News implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;

    @Override
    public String toString() {
        return "News{" +
        ", id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", type=" + type +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        "}";
    }
}
