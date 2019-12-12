package com.discover.tunisia.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.discover.tunisia.R;
import com.discover.tunisia.login.fragment.LoginFragment;

import java.security.MessageDigest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.layout_container)
    ConstraintLayout layoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        printHashKey(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new LoginFragment(), "LoginFragment").commit();
        }
    }



    private void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                //Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
                System.out.println("printHashKey() Hash Key: " + hashKey);
            }
        } catch (Exception e) {

        }
    }

}
