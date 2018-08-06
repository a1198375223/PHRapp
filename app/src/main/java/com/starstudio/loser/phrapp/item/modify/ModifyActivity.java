package com.starstudio.loser.phrapp.item.modify;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.item.main.PHRMainActivity;




import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ModifyActivity";
    private ImageView head_back,head_img,home_icon;
    private TextView change_head;
    public static final int CHOOSE_PHOTO=1;
    private TextView tv_name,tv_phone,tv_email,tv_note;
    private RelativeLayout change_name,change_phone,change_email,change_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_modify);
        initView();
    }

    private void initText(AVUser avUser){
        tv_name = (TextView) findViewById(R.id.phr_modify_tv_name);
        tv_name.setText(avUser.getUsername());
        tv_phone = (TextView) findViewById(R.id.phr_modify_tv_phone);
        tv_phone.setText(avUser.getMobilePhoneNumber());
        tv_email = (TextView) findViewById(R.id.phr_modify_tv_email);
        tv_email.setText(avUser.getEmail());
        tv_note = (TextView) findViewById(R.id.phr_modify_tv_note);
        tv_note.setText(avUser.getString("note"));
    }

    private void initView() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.default_head)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereEqualTo("objectId",AVUser.getCurrentUser().getObjectId());
        userQuery.getFirstInBackground(new GetCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                initText(avUser);
            }
        });
        change_name = (RelativeLayout) findViewById(R.id.phr_modify_change_name);
        change_name.setOnClickListener(this);
        change_email = (RelativeLayout) findViewById(R.id.phr_modify_change_email);
        change_email.setOnClickListener(this);
        change_phone = (RelativeLayout) findViewById(R.id.phr_modify_change_phone);
        change_phone.setOnClickListener(this);
        change_note = (RelativeLayout) findViewById(R.id.phr_modify_change_note);
        change_note.setOnClickListener(this);

        home_icon = (ImageView) findViewById(R.id.phr_modify_homeicon);
        home_icon.bringToFront();
        home_icon.setOnClickListener(this);
        change_head = (TextView) findViewById(R.id.phr_modify_change_head);
        change_head.bringToFront();
        change_head.setOnClickListener(this);


        head_back = (ImageView) findViewById(R.id.phr_modify_back);
        head_img = (ImageView) findViewById(R.id.phr_modify_head);

        AVFile avFile = AVUser.getCurrentUser().getAVFile("head_img");
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
            case R.id.phr_modify_change_name:
                modifyItem("用户名","username");
                break;
            case R.id.phr_modify_change_phone:
                modifyItem("手机号","mobilePhoneNumber");
                break;
            case R.id.phr_modify_change_email:
                modifyItem("邮箱号","email");
                break;
            case R.id.phr_modify_change_note:
                modifyItem("个性签名","note");
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
        if (data==null){
            return;//若用户未从相册中选图片则什么都不做，否则上传头像并保存到后台
        }else {
            Uri uri = data.getData();
            Glide.with(this)
                    .load(uri)
                    .into(head_img);
            Glide.with(this).load(uri)
                    .apply(bitmapTransform(new BlurTransformation(25, 3)))
                    .into(head_back);

            if (DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            //得到图片本地路径后，先删除原有头像，再上传
            try {
                AVUser avUser = AVUser.getCurrentUser();
                AVFile avFile = avUser.getAVFile("head_img");
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
                avUser.put("head_img", file);
                avUser.saveInBackground();
                Log.d(TAG, "handleImage: 图片已经存到后台");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void modifyItem(String name,final String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
        builder.setTitle("请输入新的"+name);    //设置对话框标题
        final EditText edit = new EditText(ModifyActivity.this);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edit.getText().toString().equals("")){
                    Toast.makeText(ModifyActivity.this, "请填入要修改的内容", Toast.LENGTH_SHORT).show();
                }else {
                    AVObject avUser = AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId());
                    avUser.put(type, edit.getText());
                    avUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                AVQuery<AVUser> userQuery = new AVQuery<>("_User");
                                userQuery.whereEqualTo("objectId",AVUser.getCurrentUser().getObjectId());
                                userQuery.getFirstInBackground(new GetCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser avUser, AVException e) {
                                        initText(avUser);
                                    }
                                }); //保存成功就更新当前页面的信息
                                Toast.makeText(ModifyActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                if (e.getCode() == 0) {
                                    Toast.makeText(ModifyActivity.this, "无法连接到服务器！", Toast.LENGTH_SHORT).show();
                                }else if (e.getCode() == 127){
                                    Toast.makeText(ModifyActivity.this, "请输入正确格式的手机号！", Toast.LENGTH_SHORT).show();
                                }else if (e.getCode() == 125){
                                    Toast.makeText(ModifyActivity.this, "请输入正确格式的邮箱！", Toast.LENGTH_SHORT).show();
                                }else if (e.getCode() == 202){
                                    Toast.makeText(ModifyActivity.this, "该用户名已经存在！", Toast.LENGTH_SHORT).show();
                                }else if (e.getCode() == 214){
                                    Toast.makeText(ModifyActivity.this, "该手机号已经注册！", Toast.LENGTH_SHORT).show();
                                }else if (e.getCode() == 203){
                                    Toast.makeText(ModifyActivity.this, "该邮箱已经注册！", Toast.LENGTH_SHORT).show();
                                }
                                Log.d(TAG, "done: +++++++++++++++错误代码：" + e.getCode());
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "你点了取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

}
