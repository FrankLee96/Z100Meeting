package com.gzz100.Z100_HuiYi.meeting;
/**
* 该接口让MainActivity实现，为了让Fragment获取每个标签的标题
* @author XieQXiong
* create at 2016/8/26 14:59
*/

public interface ICommunicate {
    /**
     * 获取当前界面的标题
     * @return
     */
    String getCurrentTitle();

    /**
     * 移除控制条
     */
    void removeControllerView();
}
