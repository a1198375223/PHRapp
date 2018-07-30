package com.starstudio.loser.phrapp.item.modify;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ModifyActivity";
    private ImageView head_back,head_img,home_icon;
    private TextView change_head,name,note;
    public static final int CHOOSE_PHOTO=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_modify);
        initView();
        Log.d(TAG, "onCreate: 查询邮箱"+AVUser.getCurrentUser().getEmail());
//        AVOSCloud.initialize(this,"NUjpdRi6jqP1S2iAfQCs7YNU-gzGzoHsz","27zlhvjRBd155W8iAWSoNJiO");
//        AVOSCloud.setDebugLogEnabled(true);
//        AVQuery<AVObject> query = new AVQuery<>("_User");
//        query.whereEqualTo("mobilePhoneNumber","18246443492");
//        AVUser.getCurrentUser().setEmail("397655952@qq.com");
//        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                AVUser.getCurrentUser().setEmail("18246443492@163.com");
//                AVUser.getCurrentUser().saveInBackground();
//                Log.d(TAG, "onCreate: 查询邮箱"+AVUser.getCurrentUser().getEmail());
//            }
//        });
    }
    private void initView() {
        //Bundle bundle = getIntent().getExtras();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        AVUser avUser = AVUser.getCurrentUser();
        AVFile avFile = avUser.getAVFile("head");
        name = (TextView) findViewById(R.id.phr_modify_name);
        name.setText(avUser.getUsername());
        note = (TextView) findViewById(R.id.phr_modify_note);
        note.setText(avUser.getEmail());
        head_back = (ImageView) findViewById(R.id.phr_modify_back);
        head_img = (ImageView) findViewById(R.id.phr_modify_head);
        home_icon = (ImageView) findViewById(R.id.phr_modify_homeicon);
        home_icon.bringToFront();
        home_icon.setOnClickListener(this);
        change_head = (TextView) findViewById(R.id.phr_modify_change_head);
        change_head.bringToFront();
        change_head.setOnClickListener(this);

        Glide.with(this).load(avFile.getUrl())
                .apply(options.bitmapTransform(new BlurTransformation(25, 3)))
                .into(head_back);
        Glide.with(this)
                .load(avFile.getUrl())
                .apply(options)
                .into(head_img);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phr_modify_homeicon:
                finish();
                break;
            case R.id.phr_modify_change_head:
                Toast.makeText(ModifyActivity.this,"改变头像",Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(ModifyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ModifyActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast toast = Toast.makeText(ModifyActivity.this, "您拒绝了访问权限！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                handleImage(data);
                break;
            default:
                break;
        }
    }

    @TargetApi(19)//4.4以上才能调用，由于4.4以下的机器很少所以没有考虑进来
    private void handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Glide.with(this)
                .load(uri)
                .into(head_img);
        Glide.with(this).load(uri)
                .apply(bitmapTransform(new BlurTransformation(25, 3)))
                .into(head_back);

        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        //得到图片本地路径后，先删除原有头像，再上传
        try {
            AVUser avUser = AVUser.getCurrentUser();
            AVFile avFile = avUser.getAVFile("head");
            Log.d(TAG, "handleImage: &&&&&&&***********++++++++"+avFile.getOriginalName());
            if (!avFile.getOriginalName().equals("default_head.png")) {
                avFile.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Log.d(TAG, "删除原有头像成功！");
                        }
                    }
                });
            }
            AVFile file = AVFile.withAbsoluteLocalPath(avUser.getMobilePhoneNumber() + ".png", imagePath);
            avUser.put("head",file);
            avUser.saveInBackground();
            Log.d(TAG, "handleImage: 图片已经存到后台");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}
