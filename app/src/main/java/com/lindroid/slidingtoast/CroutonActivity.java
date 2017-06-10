package com.lindroid.slidingtoast;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CroutonActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Button btnRoot, btnChild, btnCfg, btnCustom;
    private TranslateAnimation inAnimation;
    private TranslateAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crouton);
        context = CroutonActivity.this;
        initUI();
        initClickListener();
        initAnimation();
    }

    private void initUI() {
        btnRoot = (Button) findViewById(R.id.btn_root);
        btnChild = (Button) findViewById(R.id.btn_child);
        btnCfg = (Button) findViewById(R.id.btn_cfg);
        btnCustom = (Button) findViewById(R.id.btn_custom);
    }

    private void initAnimation() {
        //滑入的动画
        inAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        inAnimation.setDuration(500);
        inAnimation.setFillAfter(true);
        //滑出的动画
        outAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        outAnimation.setDuration(500);
        outAnimation.setFillAfter(true);
    }

    private void initClickListener() {
        btnRoot.setOnClickListener(this);
        btnChild.setOnClickListener(this);
        btnCfg.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_root:
                Crouton.makeText(this,
                        "根布局的Crouton",
                        Style.INFO,
                        R.id.rl_root).show();
                break;
            case R.id.btn_child:
                Crouton.makeText(this, "子布局的Crouton", Style.ALERT, R.id.rl_child).show();
                break;
            case R.id.btn_custom:
                View view = View.inflate(context, R.layout.custom_crouton, null);
                Crouton crouton = Crouton.make(this, view, R.id.rl_root);
                crouton.show();
                break;
            case R.id.btn_cfg:
                Configuration.Builder cfg = new Configuration.Builder();
                cfg.setInAnimation(R.anim.crouton_in)
                        .setOutAnimation(R.anim.crouton_out)
                        .setDuration(1500);
                Crouton.showText(this, "修改配置后的Crouton", Style.CONFIRM, R.id.rl_root, cfg.build());
                break;
        }
    }
}
