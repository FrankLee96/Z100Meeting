package com.gzz100.Z100_HuiYi.utils;
/**
* 常量类
* @author XieQXiong
* create at 2016/9/6 10:42
*/

public class Constant {
    public static final String MULTI_IP = "239.0.0.1";//组播地址
    public static final int MULTI_PORT = 30001;//组播端口
    public static final int TCP_PORT = 9898;//tcp端口

    /**
     * 参会代表界面中，角色标签的类型
     * DEFAULT_SPEAKER   主讲人            2
     * OTHER_DELEGATE    普通参会人员      1
     * HOSTS             主持人
     */
    public static final int DEFAULT_SPEAKER=2;
    public static final int HOSTS=1;
    public static final int OTHER_DELEGATE=0;

    //测试控制
    public static final boolean DEBUG = true;
    /**
     * 议程列表存储数据库的对应字段值，用于查询数据库中是否有议程列表
     */
    public static final int COLUMNS_AGENDAS = 4;
    /**
     * 用户列表存储数据库的对应字段值，用于查询数据库中是否有用户列表
     */
    public static final int COLUMNS_USER = 5;
    /**
     * 会议概况存储数据库的对应字段值，用于查询数据库中是否有会议概况
     */
    public static final int COLUMNS_MEETING_INFO = 6;
    /**
     * 投票列表存储数据库的对应字段值，用于查询数据库中是否有投票列表
     */
    public static final int COLUMNS_VOTE_INDEX = 7;
    /**
     * 存储输入的IP地址  对应的  key
     */
    public static final String IP_HISTORY = "ipHistory";
    /**
     * 存储确认的IP地址  对应的  key
     */
    public static final String CURRENT_IP = "currentIP";
    /**
     * 存储设备标识码  对应的  key
     */
    public static final String DEVICE_IMEI = "deviceIMEI";
    /**
     * 存储会议id  对应的  key
     */
    public static final String MEETING_ID = "meetingID";
    /**
     * 存储用户角色  对应的  key
     */
    public static final String USER_ROLE = "userRole";
    /**
     * 存储用户名字  对应的  key
     */
    public static final String USER_NAME = "userName";
    /**
     * 存储投票的id  对应的key，在voteFragment中需要根据这个id来取得投票的详细信息
     */
    public static final String BEGIN_VOTE_ID = "begin_vote_id";
    /**
     * 存储正在投票，并且处于开会状态下的  key，
     * 在VotePresenter中根据该值是否为真来显示VoteFragment显示的内容
     */
    public static final String IS_VOTE_BEGIN = "isVoteBegin";
    /**
     * 存储已经将投票结果上传的，正处于等待主持人结束投票的状态的  Key
     */
    public static final String IS_VOTE_COMMIT = "isVoteCommit";
    /**
     * 会议是否已结束 标识的   Key
     */
    public static final String IS_MEETING_END = "isMeetingEnd";
    /**
     * 存储会议结束后的时间-->  时
     */
    public static final String ENDING_HOUR = "endHour";
    /**
     * 存储会议结束后的时间-->  分
     */
    public static final String ENDING_MIN = "endMin";
    /**
     * 存储会议结束的时的系统时间
     */
    public static final String ENDING_CURRENT_TIME = "endCurrentTime";
    /**
     * 存储当前会议已经进行的时间，时
     * 该时间只有主持人端需要存储，在有临时人员进行会议后，再把该值一起发送给刚进入会议的客户端平板，
     * 用于显示会议已经进行的时间
     */
    public static final String MEETING_BEGIN_TIME_HOUR = "meetingBeginTimeHour";
    /**
     * 存储当前会议已经进行的时间，分
     * 该时间只有主持人端需要存储，在有临时人员进行会议后，再把该值一起发送给刚进入会议的客户端平板，
     * 用于显示会议已经进行的时间
     */
    public static final String MEETING_BEGIN_TIME_MIN = "meetingBeginTimeMin";

    /**
     * 会议的状态值
     * MEETING_STATE_NOT_BEGIN  会议未开始  1
     * MEETING_STATE_BEGIN      会议已开始  2
     * MEETING_STATE_PAUSE      会议暂停中  4
     * MEETING_STATE_ENDING     会议已结束  8
     * MEETING_STATE_CONTINUE   会议继续    9
     *
     */
    public static final int MEETING_STATE_NOT_BEGIN = 1;
    public static final int MEETING_STATE_BEGIN = 2;
    public static final int MEETING_STATE_PAUSE = 4;
    public static final int MEETING_STATE_ENDING = 8;
    public static final int MEETING_STATE_CONTINUE = 9;
    //作为TCP服务器端的平板的ip
    public static final String TCP_SERVER_IP = "tcp_server_ip";

    //下面的设置，为主持人端点击暂停后，需要保存在本地的各种信息的key
    /**
     * 议程在倒计时，按下暂停后用于保存当前议程倒计时时间的 分 的key
     */
    public static final String COUNTING_MIN = "counting_min";
    /**
     * 议程在倒计时，按下暂停后用于保存当前议程倒计时时间的 秒 的key
     */
    public static final String COUNTING_SEC = "counting_sec";
    /**
     * 按下暂停后用于保存当前的议程序号
     */
    public static final String PAUSE_AGENDA_INDEX = "pause_agenda_index";
    /**
     * 按下暂停后用于保存当前的议程文件序号
     */
    public static final String PAUSE_DOCUMENT_INDEX = "pause_document_index";
    /**
     * 会议状态
     */
    public static final String MEETING_STATE = "meetingState";

}
