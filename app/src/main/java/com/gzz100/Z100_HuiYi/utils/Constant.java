package com.gzz100.Z100_HuiYi.utils;
/**
* 常量类
* @author XieQXiong
* create at 2016/9/6 10:42
*/

public class Constant {
    public static final String MULTI_IP = "239.0.0.1";
    public static final int MULTI_PORT = 30001;

    public static final int TCP_PORT = 9898;

    //参会代表界面中，角色标签的类型
    public static final int DEFAULT_SPEAKER=0;
    public static final int HOSTS=1;
    public static final int OTHER_DELEGATE=2;


    //主场景会议桌中显示的各个参会人员的类型
    public static final int NORMAL_DELEGATE = 1;
    public static final int KEYNOTE_SPEAKER = 2;
    public static final int HOST = 3;
    //测试控制
    public static final boolean DEBUG = true;
    //议程列表存储数据库的对应字段值，用于查询数据库中是否有议程列表
    public static final int COLUMNS_AGENDAS = 4;
    //用户列表存储数据库的对应字段值，用于查询数据库中是否有用户列表
    public static final int COLUMNS_USER = 5;
    //会议概况存储数据库的对应字段值，用于查询数据库中是否有会议概况
    public static final int COLUMNS_MEETING_INFO = 6;
    //存储输入的IP地址  对应的  key
    public static final String IP_HISTORY = "ipHistory";
    //存储确认的IP地址  对应的  key
    public static final String CURRENT_IP = "currentIP";
    //存储设备标识码  对应的  key
    public static final String DEVICE_IMEI = "deviceIMEI";
    //存储会议id  对应的  key
    public static final String MEETING_ID = "meetingID";
    //存储用户角色  对应的  key
    public static final String USER_ROLE = "userRole";

    public static final int MEETING_STATE_NOT_BEGIN = 1;
    public static final int MEETING_STATE_BEGIN = 2;
    public static final int MEETING_STATE_PAUSE = 4;
    public static final int MEETING_STATE_ENDING = 8;
    public static final int MEETING_STATE_CONTINUE = 9;
    //作为TCP服务器端的平板的ip
    public static final String TCP_SERVER_IP = "tcp_server_ip";
}
