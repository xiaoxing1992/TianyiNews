package tianyinews.tianyi.com.tianyinews.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import tianyinews.tianyi.com.tianyinews.R;
import tianyinews.tianyi.com.tianyinews.util.SharedPreferencesUtil;

public class CallphoneActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText callphone_number_ed;
    private EditText callphone_pwd_ed;
    private TextView bianhua_tv;
    private Handler handler = new Handler();
    private TextView btn_get;
    private String userPhoneNumber;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_callphone);

        initView();

        gestureDetector = new GestureDetector(CallphoneActivity.this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

                if (Math.abs(v1) < 100) {
                    // System.out.println("手指移动的太慢了");
                    return true;
                }

                // 手势向下 down
                if ((motionEvent1.getRawY() - motionEvent.getRawY()) > 200) {
                    finish();//在此处控制关闭
                    return true;
                }
                // 手势向上 up
                if ((motionEvent.getRawY() - motionEvent1.getRawY()) > 200) {
                    return true;
                }


                return false;
            }
        });
    }

    private void initView() {
        ImageView regist_top_close_img = (ImageView) findViewById(R.id.regist_top_close_img);
        Button callphone_login_button = (Button) findViewById(R.id.callphone_login_button);
        callphone_pwd_ed = (EditText) findViewById(R.id.callphone_pwd_ed);
        callphone_number_ed = (EditText) findViewById(R.id.callphone_number_ed);
        bianhua_tv = (TextView) findViewById(R.id.bianhua_tv);
        btn_get = (TextView) findViewById(R.id.btn_get);
        TextView qiehuan_login_style = (TextView) findViewById(R.id.qiehuan_login_style);
        ImageView bottom_qq_login = (ImageView) findViewById(R.id.bottom_qq_login);
        regist_top_close_img.setOnClickListener(this);
        callphone_login_button.setOnClickListener(this);
        btn_get.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //关闭页面
            case R.id.regist_top_close_img:
                finish();
                break;
            //获取验证码
            case R.id.btn_get:
                //  regist();
                //获取手机号码
                String phoneNumber = callphone_number_ed.getText().toString().trim();
                //发送短信，传入国家号和电话---使用SMSSDK核心类之前一定要在MyApplication中初始化，否侧不能使用

                if (TextUtils.isEmpty(phoneNumber)) {
                    bianhua_tv.setText("手机号不能为空");
                    bianhua_tv.setTextColor(Color.parseColor("#ff0000"));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bianhua_tv.setText("未注册手机验证后自动注册");
                            bianhua_tv.setTextColor(Color.GRAY);
                        }
                    }, 2000);
                } else {
                    SMSSDK.getVerificationCode("+86", phoneNumber);
                    timer.start();
                    if (bianhua_tv.getText().toString().trim().equals("发送验证码") || bianhua_tv.getText().toString().trim().equals("重新发送")) {
                        bianhua_tv.setText("发送成功");
                        //   SMSSDK.getVerificationCode("+86", phoneNumber);
                        bianhua_tv.setTextColor(Color.parseColor("#ff0000"));
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bianhua_tv.setText("未注册手机验证后自动注册");
                                bianhua_tv.setTextColor(Color.GRAY);
                            }
                        }, 2000);
                    }
                }


                break;

            //登录头条
            case R.id.callphone_login_button:
                regist();
                //获取用户输入的手机号码
                userPhoneNumber = callphone_number_ed.getText().toString().trim();
                //获取用户输入的手机号码
                String userPhonePwd = callphone_pwd_ed.getText().toString().trim();
                if (!TextUtils.isEmpty(userPhoneNumber)) {
                    if (!TextUtils.isEmpty(userPhonePwd)) {
                        SMSSDK.submitVerificationCode("+86", userPhoneNumber, userPhonePwd);
                    } else {
                        bianhua_tv.setText("验证码不能为空");
                        bianhua_tv.setTextColor(Color.parseColor("#ff0000"));
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bianhua_tv.setText("未注册手机验证后自动注册");
                                bianhua_tv.setTextColor(Color.GRAY);
                            }
                        }, 2000);
                    }
                } else {
                    bianhua_tv.setText("手机号不能为空");
                    bianhua_tv.setTextColor(Color.parseColor("#ff0000"));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bianhua_tv.setText("未注册手机验证后自动注册");
                            bianhua_tv.setTextColor(Color.GRAY);
                        }
                    }, 2000);
                }

                break;
        }
    }

    private void regist() {

        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                Intent intent = new Intent(CallphoneActivity.this, MainActivity.class);
                                intent.putExtra("telephone", userPhoneNumber);
                                //   startActivity(intent);
                                setResult(RESULT_OK, intent);
                                finish();
                                //   Toast.makeText(CallphoneActivity.this, "您已验证成功，请您订餐", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CallphoneActivity.this, "已发送验证码，请查收", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CallphoneActivity.this, "操作失败，请重新获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btn_get.setText("重新发送" + "(" + (millisUntilFinished / 1000) + "s)");
            btn_get.setTextColor(Color.GRAY);
            btn_get.setFocusable(false);
        }

        @Override
        public void onFinish() {
            btn_get.setText("重新发送");
            btn_get.setTextColor(Color.BLACK);
            btn_get.setFocusable(true);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //要在activity销毁时反注册，否侧会造成内存泄漏问题
        SMSSDK.unregisterAllEventHandler();
    }
}
