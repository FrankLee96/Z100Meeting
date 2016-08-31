package com.gzz100.Z100_HuiYi.meeting.file.fileDetailManage;

import android.view.View;

import com.gzz100.Z100_HuiYi.BasePresenter;
import com.gzz100.Z100_HuiYi.BaseView;
/**
* 文件详情的契约接口
* @author XieQXiong
* create at 2016/8/30 10:43
*/

public interface FileDetailContract {
    interface DetailView extends BaseView<Presenter>{
        void fallback();
        void slideLeft(int distanceX);
        void slideRight(int distanceX);
        void setContent(String content);
    }

    interface Presenter extends BasePresenter{
        /**
         * 返回上一页
         */
        void fallback();

        /**
         * 隐藏侧滑菜单
         * @param v  侧滑菜单
         */
        void slideLeft(View v);

        void slideRight(View v);
        void setContent(String content);
    }
}