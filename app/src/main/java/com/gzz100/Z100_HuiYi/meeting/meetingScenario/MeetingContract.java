package com.gzz100.Z100_HuiYi.meeting.meetingScenario;

import com.gzz100.Z100_HuiYi.BasePresenter;
import com.gzz100.Z100_HuiYi.BaseView;
import com.gzz100.Z100_HuiYi.data.UserBean;

import java.util.List;

/**
 * Created by XieQXiong on 2016/9/5.
 */
public interface MeetingContract {
    interface View extends BaseView<Presenter>{
        /**
         * 显示会议概况。该方法中应该有参数，该参数为会议概况的实体类
         */
        void showMeetingInfo();

        /**
         * 跳转到人员界面中的其他参会人员标签
         */
        void showOthers();

        /**
         * 跳转到用户详情界面
         */
        void showUserInfo();

        /**
         * 显示会议桌
         * @param users 用户列表
         */
        void showMeetingRoom(List<UserBean> users);

        /**
         * 设置其他人员个数
         * @param isShow       是否显示其他人员标签
         * @param othersNum    显示个数
         */
        void setOthersNum(boolean isShow,int othersNum);
        /**
         * 显示人员界面中的其他参会人员
         * @param isShowDelegate  是否显示人员界面
         */
        void showDelegate(Boolean isShowDelegate);
    }

    interface Presenter extends BasePresenter{
        /**
         * 获取会议概况，封装成一个会议概况实体类，再调用 MeetingContract.View 的 showMeetingInfo
         */
        void getMeetingInfo();

        /**
         * 调用 MeetingContract.View 的 showOthers()
         */
        void showOthers();

        /**
         * 根据用户id获取用户信息，再调用 MeetingContract.View 的 showUserInfo
         * @param userId 用户id
         */
        void fetchUserInfo(int userId);

        /**
         * 获取显示在会议桌的参会人员列表，再调用 MeetingContract.View 的 showMeetingRoom
         */
        void fetchMainUsers();

        /**
         * 重新设置为第一次加载，避免界面销毁后再回来时，数据无法重新获取到
         */
        void resetFirstLoad();

        /**
         * 调用 MeetingContract.View 的 showDelegate
         */
        void showDelegate();

        /**
         * 取得其他参会人数后调用 MeetingContract.View 的  setOthersNum
         * 人数为0 的话，设置setOtherNum中的第一个参数为false
         * @param otherNum
         */
        void setOtherNum(int otherNum);

    }
}