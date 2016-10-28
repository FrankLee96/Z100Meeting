package com.gzz100.Z100_HuiYi.meeting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.gzz100.Z100_HuiYi.BaseActivity;
import com.gzz100.Z100_HuiYi.MyAPP;
import com.gzz100.Z100_HuiYi.R;
import com.gzz100.Z100_HuiYi.meeting.about.AboutFragment;
import com.gzz100.Z100_HuiYi.meeting.agenda.AgendaFragment;
import com.gzz100.Z100_HuiYi.meeting.agenda.AgendaPresenter;
import com.gzz100.Z100_HuiYi.meeting.delegate.DelegateFragment;
import com.gzz100.Z100_HuiYi.meeting.delegate.DelegatePresenter;
import com.gzz100.Z100_HuiYi.meeting.file.FileFragment;
import com.gzz100.Z100_HuiYi.meeting.file.FilePresenter;
import com.gzz100.Z100_HuiYi.meeting.file.fileDetail.FileDetailActivity;
import com.gzz100.Z100_HuiYi.meeting.meetingScenario.MeetingFragment;
import com.gzz100.Z100_HuiYi.meeting.meetingScenario.MeetingPresenter;
import com.gzz100.Z100_HuiYi.meeting.vote.VoteFragment;
import com.gzz100.Z100_HuiYi.data.RepositoryUtil;
import com.gzz100.Z100_HuiYi.meeting.vote.VotePresenter;
import com.gzz100.Z100_HuiYi.tcpController.ControllerInfoBean;
import com.gzz100.Z100_HuiYi.multicast.ReceivedMulticastService;
import com.gzz100.Z100_HuiYi.tcpController.Client;
import com.gzz100.Z100_HuiYi.tcpController.ControllerUtil;
import com.gzz100.Z100_HuiYi.tcpController.Server;
import com.gzz100.Z100_HuiYi.utils.ActivityStackManager;
import com.gzz100.Z100_HuiYi.utils.AppUtil;
import com.gzz100.Z100_HuiYi.utils.Constant;
import com.gzz100.Z100_HuiYi.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ICommunicate, ControllerView.IOnControllerListener {

    private ControllerView mControllerView;
    private FrameLayout.LayoutParams mFl;
    private int mMeetingState;
    private ControllerInfoBean mControllerInfoBean;
    private Gson mGson;

    public static void toMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static final Long TRIGGER_OF_REMOVE_CONTROLLERVIEW = 1L;

    @BindView(R.id.id_main_fl_root)
    FrameLayout mRootView;
    @BindView(R.id.id_main_tbv)
    NavBarView mNavBarView;
    @BindView(R.id.id_main_ViewPager)
    ViewPager mViewPager;

    @BindView(R.id.id_main_rdg)
    RadioGroup mTabGroup;
    @BindView(R.id.id_main_meetingTab)
    RadioButton mMeetingTab;
    @BindView(R.id.id_main_delegateTab)
    RadioButton mDelegateTab;
    @BindView(R.id.id_main_agendaTab)
    RadioButton mAgendaTab;
    @BindView(R.id.id_main_fileTab)
    RadioButton mFileTab;
    @BindView(R.id.id_main_aboutTab)
    RadioButton mAboutTab;
    @BindView(R.id.id_main_voteTab)
    RadioButton mVoteTab;

    private MainFragmentAdapter mMainFragmentAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private MeetingFragment mMeetingFragment;
    private DelegateFragment mDelegateFragment;
    private AgendaFragment mAgendaFragment;
    private FileFragment mFileFragment;
    private VoteFragment mVoteFragment;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_FIVE = 4;
    public static final int PAGE_SIX = 5;
    private AboutFragment mAboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityStackManager.clearExceptOne(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();

//        Configuration config = getResources().getConfiguration();
//        int  smallestScreenWidth = config.smallestScreenWidthDp;
//        int screenHeightDp = config.screenHeightDp;
//        int screenWidthDp = config.screenWidthDp;
//        Log.e("screenHeightDp=","====       "+screenHeightDp+"\n    screenWidthDp=====     "+screenWidthDp);
    }

    private void initMulticastService() {
        Intent intent = new Intent(MainActivity.this, ReceivedMulticastService.class);
        startService(intent);
    }

    private void init() {
        mMeetingFragment = MeetingFragment.newInstance();
        mDelegateFragment = DelegateFragment.newInstance();
        mAgendaFragment = AgendaFragment.newInstance();
        mFileFragment = FileFragment.newInstance();
        mAboutFragment = AboutFragment.newInstance();
        mVoteFragment = VoteFragment.newInstance();
        mFragments.add(mMeetingFragment);
        mFragments.add(mDelegateFragment);
        mFragments.add(mAgendaFragment);
        mFragments.add(mFileFragment);
        mFragments.add(mAboutFragment);
        //测试
        if (MyAPP.getInstance().getUserRole() == 1){//主持人
            mFragments.add(mVoteFragment);
            mVoteTab.setVisibility(View.VISIBLE);
            //控制条显示与事件监听
//            this.setIOnControllerListener(this);
//            this.setControllerVisibility(true);
            mControllerView = ControllerView.getInstance(this);
            mFl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mFl.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            mRootView.addView(mControllerView, mFl);
            mControllerView.setIOnControllerListener(this);
            //发送消息的实体
            mControllerInfoBean = new ControllerInfoBean();
            mGson = new Gson();

        }else {//不是主持人
            mVoteTab.setVisibility(View.GONE);
        }
        //默认选择哪个
        defaultSelected();

        initEvent();
        initPresenter();
        //接收组播，已换成tcp
//        initMulticastService();

        timeCounting();
    }

    private void defaultSelected(){
        mMainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mMainFragmentAdapter);
        mViewPager.setCurrentItem(PAGE_ONE);
        mNavBarView.mTvTitle.setText(mMeetingTab.getText());
        mMeetingTab.setChecked(true);
    }

    private void timeCounting() {

        mHandler.post(mRunnable);

    }

    private int hour = 0;
    private int min = 0;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 60000);
            mNavBarView.setTimeMin(getMin());
            min++;
            mNavBarView.setTimeHour(getHour());
        }
    };

    public String getHour() {
        String newHour = hour >= 10 ? "" + hour : "0" + hour;
        return newHour;
    }

    public String getMin() {
        String newMin = min >= 10 ? "" + min : "0" + min;
        if (newMin.equals("60")) {
            hour++;
            min = 0;
            return "00";
        }
        return newMin;
    }

    private void initPresenter() {
        new FilePresenter(RepositoryUtil.getFileRepository(this),
                mFileFragment);
        new AgendaPresenter(RepositoryUtil.getFileRepository(this),
                mAgendaFragment);
        new DelegatePresenter(RepositoryUtil.getDelegateRepository(this.getApplicationContext()),
                mDelegateFragment);
        new VotePresenter(RepositoryUtil.getVoteRepository(this),
                mVoteFragment);
        new MeetingPresenter(RepositoryUtil.getMeetingRepository(this),
                mMeetingFragment);
    }

    private void initEvent() {
        mViewPager.setOnPageChangeListener(this);
        mTabGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.id_main_meetingTab:
                mViewPager.setCurrentItem(PAGE_ONE);
                mNavBarView.mTvTitle.setText(mMeetingTab.getText());
                break;
            case R.id.id_main_delegateTab:
                mViewPager.setCurrentItem(PAGE_TWO);
                mNavBarView.mTvTitle.setText(mDelegateTab.getText());
                break;
            case R.id.id_main_agendaTab:
                mViewPager.setCurrentItem(PAGE_THREE);
                mNavBarView.mTvTitle.setText(mAgendaTab.getText());
                break;
            case R.id.id_main_fileTab:
                mViewPager.setCurrentItem(PAGE_FOUR);
                mNavBarView.mTvTitle.setText(mFileTab.getText());
                break;
            case R.id.id_main_aboutTab:
                mViewPager.setCurrentItem(PAGE_FIVE);
                mNavBarView.mTvTitle.setText(mAboutTab.getText());
                break;
            case R.id.id_main_voteTab:
                mViewPager.setCurrentItem(PAGE_SIX);
                mNavBarView.mTvTitle.setText(mVoteTab.getText());
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (mViewPager.getCurrentItem()) {
                case PAGE_ONE:
                    mMeetingTab.setChecked(true);
                    break;
                case PAGE_TWO:
                    mDelegateTab.setChecked(true);
                    break;
                case PAGE_THREE:
                    mAgendaTab.setChecked(true);
                    break;
                case PAGE_FOUR:
                    mFileTab.setChecked(true);
                    break;
                case PAGE_FIVE:
                    mAboutTab.setChecked(true);
                    break;
                case PAGE_SIX:
                    mVoteTab.setChecked(true);
                    break;

            }
        }

    }

    @Override
    public String getCurrentTitle() {
        return mNavBarView.mTvTitle.getText().toString();
    }

    @Override
    public void removeControllerView() {
        mRootView.removeView(mControllerView);
    }

    /**
     * 重新添加控制条
     * @param reAdd   值为1l
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reAddControllerView(Long reAdd){
        if (reAdd == TRIGGER_OF_REMOVE_CONTROLLERVIEW){//从文件详情界面发送，
            mRootView.addView(mControllerView, mFl);
            mControllerView.setIOnControllerListener(this);
        }
    }

    /**
     * 跳转到人员界面的  其他参会人员
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDelegate(Boolean showDelegate) {
        if (showDelegate)
            mDelegateTab.setChecked(showDelegate);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
//        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    //接收组播
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMultiCast(ControllerInfoBean data) {
        if (MyAPP.getInstance().getUserRole() != 1) {
            int agendaIndex = data.getAgendaIndex();
            int documentIndex = data.getDocumentIndex();
            String upLevelTitle = data.getUpLevelTitle();
            //开始
            if (data.getMeetingState() == Constant.MEETING_STATE_BEGIN ) {
                FileDetailActivity.start(this, agendaIndex, documentIndex, upLevelTitle,true,
                        false,false,"","");
            }
            //继续
            else if (data.getMeetingState() == Constant.MEETING_STATE_CONTINUE){
                FileDetailActivity.start(this, agendaIndex, documentIndex, upLevelTitle,true,false,
                        true,data.getCountdingMin(),data.getCountdingSec());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showVoteFragment(Integer votePage){
        Log.e("MainActivity ==","===============   "+votePage);
        if (PAGE_SIX == votePage){
            if (MyAPP.getInstance().getUserRole() != 1){
                //不是主持人
                mFragments.add(mVoteFragment);
                mVoteTab.setVisibility(View.VISIBLE);
                defaultSelected();
            }else {
                //主持人
                mVoteTab.setChecked(true);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("退出系统？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (AppUtil.isServiceRun(MainActivity.this, "com.gzz100.Z100_HuiYi.tcpController.Server")) {
                                stopService(new Intent(MainActivity.this, Server.class));
                            }
                            if (AppUtil.isServiceRun(MainActivity.this, "com.gzz100.Z100_HuiYi.tcpController.Client")) {
                                stopService(new Intent(MainActivity.this, Client.class));
                            }
                            ActivityStackManager.exit();
                        }
                    })
                    .setNegativeButton("否", null)
                    .create();
            dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void startMeeting(View view) {
        if ("开始".equals(((Button)view).getText().toString())) {
            mMeetingState = Constant.MEETING_STATE_BEGIN;
            try {
                ControllerInfoBean controllerInfoBean = mControllerInfoBean.clone();
                controllerInfoBean.setMeetingState(mMeetingState);
                controllerInfoBean.setAgendaIndex(1);
                controllerInfoBean.setDocumentIndex(0);
                controllerInfoBean.setUpLevelTitle("文件");

                String json = mGson.toJson(controllerInfoBean);

                mRootView.removeView(mControllerView);
                FileDetailActivity.start(this, controllerInfoBean.getAgendaIndex(),
                        controllerInfoBean.getDocumentIndex(), "文件",true,true,false,"","");

                ControllerUtil.getInstance().sendMessage(json);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            mControllerView.setBeginAndEndText("结束");
        } else {//结束
            mMeetingState = Constant.MEETING_STATE_ENDING;

            try {
                ControllerInfoBean controllerInfoBean = mControllerInfoBean.clone();
                controllerInfoBean.setMeetingState(mMeetingState);

                String json = mGson.toJson(controllerInfoBean);

                ControllerUtil.getInstance().sendMessage(json);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void pauseMeeting(View view) {
        if ("暂停".equals(((Button)view).getText().toString())) {
            mMeetingState = Constant.MEETING_STATE_PAUSE;
            try {
                ControllerInfoBean controllerInfoBean = mControllerInfoBean.clone();
                controllerInfoBean.setMeetingState(mMeetingState);

                String json = mGson.toJson(controllerInfoBean);
                ControllerUtil.getInstance().sendMessage(json);
                getMultiCast(controllerInfoBean);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }else {//继续

        }



    }

    @Override
    public void startVote(View view) {
        mControllerView.setVoteAndEndVoteText("结束投票");
    }

    @Override
    public void voteResult(View view) {

    }
}
