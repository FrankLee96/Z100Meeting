package com.gzz100.Z100_HuiYi.meetingPrepare.selectMeeting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.gzz100.Z100_HuiYi.BaseActivity;
import com.gzz100.Z100_HuiYi.R;
import com.gzz100.Z100_HuiYi.data.MeetingBean;
import com.gzz100.Z100_HuiYi.meeting.MainActivity;
import com.gzz100.Z100_HuiYi.meetingPrepare.ItemSpaceDecoration;
import com.gzz100.Z100_HuiYi.meetingPrepare.signIn.SignInActivity;
import com.gzz100.Z100_HuiYi.utils.ActivityStackManager;
import com.gzz100.Z100_HuiYi.utils.Constant;
import com.gzz100.Z100_HuiYi.utils.MPhone;
import com.gzz100.Z100_HuiYi.utils.SharedPreferencesUtil;

import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.ErrorManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMeetingActivity extends BaseActivity implements SelectMeetingContract.View, OnMeetingClickListener {
    public static void toSelectMeetingActivity(Context context) {
        Intent intent = new Intent(context, SelectMeetingActivity.class);
        context.startActivity(intent);

    }

    @BindView(R.id.id_rcv_select_meeting)
    RecyclerView mRcvSelectMeeting;
    @BindView(R.id.id_tv_no_meeting)
    TextView mTvNoMeeting;
    private MeetingAdapter mAdapter;
    private List<MeetingBean> mMeetings;

    private SelectMeetingContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meeting);
        ButterKnife.bind(this);
        mPresenter = new SelectMeetingPresenter(SelectMeetingActivity.this, this);
        saveIMEI();
    }

    private void saveIMEI() {
        String deviceIMEI = MPhone.getDeviceIMEI(this.getApplicationContext());
        mPresenter.saveIMEI(this.getApplicationContext(), deviceIMEI);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    /**
     * 重新获取会议列表
     */
    @OnClick(R.id.id_btn_select_meeting)
    void getMeetingList() {
        String deviceIMEI = SharedPreferencesUtil.getInstance(this.getApplicationContext()).getString(Constant.DEVICE_IMEI, "");
        mPresenter.fetchMeetingList(true, deviceIMEI);
    }

    @Override
    public void showMeetingList(List<MeetingBean> meetings) {
        if (mMeetings != null)
            mMeetings.clear();
        mMeetings = meetings;
        mAdapter = new MeetingAdapter(this, meetings);
        mRcvSelectMeeting.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvSelectMeeting.setAdapter(mAdapter);
//        mRcvSelectMeeting.addItemDecoration(new ItemSpaceDecoration(ItemSpaceDecoration.VERTICAL,
//                (int) getResources().getDimension(R.dimen.distance_twenty_dp)));
        mAdapter.setOnMeetingClickListener(this);

    }

    @Override
    public void showNoMeetingList(String errorMsg) {
        if (TextUtils.isEmpty(errorMsg)){
            mTvNoMeeting.setVisibility(View.VISIBLE);
        }else {
            mTvNoMeeting.setText(errorMsg);
            mTvNoMeeting.setVisibility(View.VISIBLE);
        }
        mRcvSelectMeeting.setVisibility(View.GONE);
    }

    @Override
    public void showSignIn(String IMEI, int meetingID) {
        SignInActivity.toSignInActivity(this, IMEI, meetingID);
    }

    @Override
    public void setPresenter(SelectMeetingContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onMeetingClick(int position) {
        int meetingID = mMeetings.get(position).getMeetingID();
        String deviceIMEI = MPhone.getDeviceIMEI(this.getApplicationContext());
        mPresenter.selectMeeting(deviceIMEI, meetingID);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            ActivityStackManager.pop();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
