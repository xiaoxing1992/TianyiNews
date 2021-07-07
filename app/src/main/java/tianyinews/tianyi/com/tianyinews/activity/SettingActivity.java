package tianyinews.tianyi.com.tianyinews.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import coil.Coil;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_SETTRINGS_CODE = 300;
    public static final int RESULT_SETTRINGS_CODE_PHONE = 301;
    private boolean flag = false;
    private ImageView tuisong_off_on;
    private LinearLayout exit_account_ll;
    private LinearLayout clear_ll;

    private TextView huancunsize_tv;
    private ImageView settings_back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        tuisong_off_on = (ImageView) findViewById(R.id.tuisong_off_on);
        exit_account_ll = (LinearLayout) findViewById(R.id.exit_account_ll);
        clear_ll = (LinearLayout) findViewById(R.id.clear_ll);
        huancunsize_tv = (TextView) findViewById(R.id.huancunsize_tv);
        settings_back_img = (ImageView) findViewById(R.id.settings_back_img);

        clear_ll.setOnClickListener(this);
        tuisong_off_on.setOnClickListener(this);
        exit_account_ll.setOnClickListener(this);
        settings_back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tuisong_off_on:
                if (flag) {
                    flag = false;
                    tuisong_off_on.setSelected(flag);
                } else {
                    flag = true;
                    tuisong_off_on.setSelected(true);
                }

                break;
            case R.id.clear_ll:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定要删除所有缓存吗？问答草稿、离线内容及图片均会被清除。");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Coil.imageLoader(getBaseContext()).getMemoryCache().clear();
                        huancunsize_tv.setText("0M");

                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.exit_account_ll:
                boolean config = SharedPreferencesUtil.getSharedConfig(SettingActivity.this);
                if (config) {
                    int sharedFlag = SharedPreferencesUtil.getSharedFlag(SettingActivity.this);
                    switch (sharedFlag) {
                        case 1:

                            AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
                            builder2.setTitle("退出确认");
                            builder2.setMessage("退出当前账号，将不能同步到QQ收藏中，发布评论和云端分享等");
                            builder2.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                                    setResult(RESULT_SETTRINGS_CODE, intent);
                                    finish();
                                }
                            });

                            builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder2.show();
                            break;
                        case 2:
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(SettingActivity.this);
                            builder3.setTitle("退出确认");
                            builder3.setMessage("退出当前账号，将不能同步到手机收藏夹中，发布评论和云端分享等");
                            builder3.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                                    setResult(RESULT_SETTRINGS_CODE_PHONE, intent);
                                    finish();
                                }
                            });

                            builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder3.show();
                            break;
                    }
                } else {
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(SettingActivity.this);
                    builder4.setTitle("提示");
                    builder4.setMessage("小主，您还未登录，要去登录吗？");
                    builder4.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            SettingActivity.this.finish();
                        }
                    });

                    builder4.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder4.show();
                }
                break;
            case R.id.settings_back_img:
                finish();

                break;
        }


    }


}
