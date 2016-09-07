package com.gzz100.Z100_HuiYi.data.delegate;

import android.support.annotation.NonNull;


import com.gzz100.Z100_HuiYi.data.delegate.local.DelegateLocalDataSource;
import com.gzz100.Z100_HuiYi.data.delegate.remote.DelegateRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by DELL on 2016/8/31.
 */
public class DelegateRepository implements DelegateDataSource {
    private static DelegateRepository INSTANCE;

    private boolean mDelegateListIsDirty=true;
    private boolean mRoleListIsDirty=true;
    private boolean mDelegateDetailListIsDirty=true;

    private final DelegateRemoteDataSource mDelegateRemoteDataSource;
    private final DelegateLocalDataSource mDelegateLocalDataSource;

    public  DelegateRepository (@NonNull DelegateRemoteDataSource delegateRemoteDataSource, @NonNull DelegateLocalDataSource delegateLocalDataSource){
        this.mDelegateRemoteDataSource=checkNotNull(delegateRemoteDataSource);
        this.mDelegateLocalDataSource=checkNotNull(delegateLocalDataSource);

    }

   public static DelegateRepository getInstance(@NonNull DelegateRemoteDataSource delegateRemoteDataSource, @NonNull DelegateLocalDataSource delegateLocalDataSource)
   {
       if(INSTANCE==null)
           return new DelegateRepository(delegateRemoteDataSource,delegateLocalDataSource);
       return INSTANCE;
   }

    @Override
    public void getRoleList(final LoadRoleListCallback callback) {
        if(mRoleListIsDirty)
             mDelegateLocalDataSource.getRoleList(callback);
        else
            mDelegateRemoteDataSource.getRoleList(callback);
    }

    @Override
    public void getDelegateList(int rolePos,@NonNull LoadDelegateListCallback callback) {
        checkNotNull(callback);
        if(mDelegateListIsDirty)
            mDelegateLocalDataSource.getDelegateList(rolePos,callback);
        else
            mDelegateRemoteDataSource.getDelegateList(rolePos,callback);
    }

    @Override
    public void getDelegateBean(int delegateDetailPos, LoadDelegateDetailCallback callback) {
        if(mDelegateDetailListIsDirty)
          mDelegateLocalDataSource.getDelegateBean(delegateDetailPos,callback);
        else
            mDelegateRemoteDataSource.getDelegateBean(delegateDetailPos,callback);
    }


}