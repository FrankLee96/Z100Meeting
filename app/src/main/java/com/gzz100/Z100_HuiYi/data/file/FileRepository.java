package com.gzz100.Z100_HuiYi.data.file;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gzz100.Z100_HuiYi.data.Agenda;
import com.gzz100.Z100_HuiYi.data.Document;
import com.gzz100.Z100_HuiYi.data.db.DBHelper;
import com.gzz100.Z100_HuiYi.utils.Constant;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
/**
* 文件仓库，将获取数据的请求分发给本地或远程
* @author XieQXiong
* create at 2016/9/7 16:21
*/

public class FileRepository implements FileDataSource {
    private static FileRepository INSTANCE = null;

    private final FileDataSource mFileRemoteDataSource;

    private final FileDataSource mFileLocalDataSource;

    private Context mContext;

    private FileRepository(@NonNull FileDataSource fileRemoteDataSource,
                           @NonNull FileDataSource fileLocalDataSource,
                           Context context) {
        mFileRemoteDataSource = checkNotNull(fileRemoteDataSource);
        mFileLocalDataSource = checkNotNull(fileLocalDataSource);
        this.mContext = context;
    }

    public static FileRepository getInstance(@NonNull FileDataSource fileRemoteDataSource,
                                             @NonNull FileDataSource fileLocalDataSource,
                                             Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FileRepository(fileRemoteDataSource, fileLocalDataSource,context);
        }
        return INSTANCE;
    }
    @Override
    public void getFileList(int agendaPos, @NonNull LoadFileListCallback callback) {
        checkNotNull(callback);
        //TODO  此处查询了两次数据库，可进一步优化
        List<Document> documents = FileOperate.getInstance(mContext).queryFileList(agendaPos);
        //查询数据库中是否有该议程序号对应的文件列表
        if (documents != null && documents.size() > 0){
            mFileLocalDataSource.getFileList(agendaPos,callback);
        }else{
            mFileRemoteDataSource.getFileList(agendaPos,callback);
        }
    }

    @Override
    public void getAgendaList(String IMEI, String userId, @NonNull LoadAgendaListCallback callback) {
        checkNotNull(callback);
        List<Agenda> agendas = FileOperate.getInstance(mContext).queryAgendaList(Constant.COLUMNS_AGENDAS);
        //查询数据库中是否有议程列表
        if (agendas != null && agendas.size() > 0){
            mFileLocalDataSource.getAgendaList(IMEI,userId,callback);
        }else {
            mFileRemoteDataSource.getAgendaList(IMEI,userId,callback);
        }
    }

    @Override
    public void getSearchResult(String fileOrName, @NonNull LoadFileListCallback callback) {
        checkNotNull(callback);
    }
}
