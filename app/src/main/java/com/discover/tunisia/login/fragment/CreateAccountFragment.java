package com.discover.tunisia.login.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.MainActivity;
import com.discover.tunisia.login.entities.User;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {


    View view;
    @BindView(R.id.et_fullname)
    EditText etFullname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_sigin)
    TextView tvSigin;
    @BindView(R.id.tv_view_terms)
    TextView tvViewTerms;
    @BindView(R.id.tv_back)
    TextView tvBack;
    Unbinder unbinder;
    @BindView(R.id.all_layout)
    LinearLayout allLayout;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return  new CreateAccountFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            tvSigin.setOnClickListener(v -> verifyInput());

        }catch (Exception ignored)
        {

        }
        try {
            tvBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());
        }catch (Exception ignored)
        {

        }
        return view;
    }

    private void verifyInput() {
        if(etFullname.getText()==null|| etFullname.getText().toString().isEmpty())
        {
            try {
                Snackbar.make(allLayout, "Enter your name", Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(etEmail.getText()==null|| etEmail.getText().toString().isEmpty())
        {
            try {
                Snackbar.make(allLayout, "Enter your email", Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(etPassword.getText()==null|| etPassword.getText().toString().isEmpty())
        {
            try {
                Snackbar.make(allLayout, "Enter your password", Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            sendData();
        }
    }

    private void sendData() {
        ProgressDialog pd = new ProgressDialog(getContext());
        try {
            pd.setMessage("Loading");
            pd.show();
        }catch (Exception ignored)
        {

        }
        String name = etFullname.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("email", password);
        jsonObject.addProperty("password", email);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().createAccount(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        JSONArray object = new JSONArray(response.body().string());
                        Gson gson = Utils.getGsonInstance();
                        User user = gson.fromJson(object.get(0).toString(), User.class);
                        Preference.saveInPreferences(getContext(), user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                    try {
                        pd.dismiss();
                    }catch (Exception ignored)
                    {

                    }
                    Preference.saveInPreferences(getContext(),user);
                } else {
                    try {
                        pd.dismiss();
                        Snackbar.make(allLayout, "Oops an error has occurred", Snackbar.LENGTH_LONG).show();
                    }catch (Exception ignored)
                    {

                    }


                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                try {
                    pd.dismiss();
                    Snackbar.make(allLayout, "Oops an error has occurred", Snackbar.LENGTH_LONG).show();
                }catch (Exception ignored)
                {

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
