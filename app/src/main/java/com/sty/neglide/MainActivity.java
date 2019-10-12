package com.sty.neglide;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sty.neglide.neglide.NeGlide;
import com.sty.neglide.neglide.RequestListener;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private LinearLayout scrollLine;
    private Button btnSingle;
    private Button btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);
        initView();
        addListeners();
    }

    private void initView() {
        scrollLine = findViewById(R.id.ll_scroll_line);
        btnSingle = findViewById(R.id.btn_single);
        btnMore = findViewById(R.id.btn_more);
    }

    private static void verifyStoragePermissions(Activity activity) {
        try{
            //检查是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE" );
            if(permission != PackageManager.PERMISSION_GRANTED) {
                //没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addListeners() {
        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnSingleClicked();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnMoreClicked();
            }
        });
    }

    private void onBtnSingleClicked() {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        scrollLine.addView(imageView);
        //设置占位图片
        NeGlide.with(this)
                .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3950876957,1467991853&fm=26&gp=0.jpg")
                .loading(R.mipmap.ic_launcher)
                .listener(new RequestListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        Toast.makeText(MainActivity.this, "coming", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                })
                .into(imageView);
    }

    private void onBtnMoreClicked() {
        for (int i = 1; i <= 10; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            scrollLine.addView(imageView);
            String url = null;
            switch (i) {
                case 1:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558535575115&di=61c229bcc65dec568822f4ff28202264&imgtype=0&src=http%3A%2F%2Fimg.ef43.com.cn%2FnewsImages%2F2017%2F5%2F06092015807.jpg";
                    break;
                case 2:
                    url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=934222697,3928396438&fm=11&gp=0.jpg";
                    break;
                case 3:
                    url = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3967114459,4266717778&fm=26&gp=0.jpg";
                    break;
                case 4:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558535826986&di=02e806de33a58a1457f815c5b7581cc7&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201601%2F15%2F20160115151155_FeuKM.jpeg";
                    break;
                case 5:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558535853515&di=2adf0ca953b66515c32a650f386acbc5&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201605%2F02%2F20160502190153_RLTwW.jpeg";
                    break;
                case 6:
                    url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1449634080,114330435&fm=27&gp=0.jpg";
                    break;
                case 7:
                    url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2742398684,1626866478&fm=26&gp=0.jpg";
                    break;
                case 8:
                    url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3251808624,792931401&fm=26&gp=0.jpg";
                    break;
                case 9:
                    url = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1432937052,3680173361&fm=26&gp=0.jpg";
                    break;
                case 10:
                    url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3436777124,1783395554&fm=26&gp=0.jpg";
                    break;
            }
            //设置占位图片
            NeGlide.with(this)
                    .load(url)
                    .loading(R.mipmap.ic_launcher)
                    .into(imageView);
        }
    }
}
