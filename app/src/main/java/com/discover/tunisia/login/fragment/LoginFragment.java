package com.discover.tunisia.login.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.LocaleHelper;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.MainActivity;
import com.discover.tunisia.discover.Ui.CustomViewPager;
import com.discover.tunisia.drawers.adapters.LangueAdapter;
import com.discover.tunisia.drawers.entities.Langue;
import com.discover.tunisia.login.adapters.PagerTutoAdapter;
import com.discover.tunisia.login.entities.User;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
public class LoginFragment extends Fragment {


    View view;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.tv_sigin)
    TextView tvSigin;
    @BindView(R.id.layout_facebook)
    LinearLayout layoutFacebook;
    @BindView(R.id.layout_select_language)
    LinearLayout layoutSelectLanguage;
    @BindView(R.id.tv_create_account)
    TextView tvCreateAccount;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.layout_login)
    LinearLayout layoutLogin;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.tabDots)
    TabLayout tabDots;
    Unbinder unbinder;
    @BindView(R.id.all_layout)
    RelativeLayout allLayout;
    @BindView(R.id.tv_langue)
    TextView tvLangue;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    List<Langue> langues = new ArrayList<>();
    User user = new User();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        if (FacebookSdk.isInitialized()) {
            LoginManager.getInstance().logOut();
        }
        callbackManager = CallbackManager.Factory.create();

    }

    private void initLangue() {
        Locale[] locales = new Locale[]{
                Locale.forLanguageTag("fr"),
                Locale.forLanguageTag("ar"),
                Locale.forLanguageTag("it"),
                Locale.forLanguageTag("en"),
                Locale.forLanguageTag("nl"),
                Locale.forLanguageTag("zh"),
                Locale.forLanguageTag("ru"),
                Locale.forLanguageTag("cs"),
                Locale.forLanguageTag("pl"),
                Locale.forLanguageTag("es")

        };
        for (Locale locale : locales) {
            String lang = locale.getDisplayLanguage(locale).substring(0, 1).toUpperCase() + locale.getDisplayLanguage(locale).substring(1);
            langues.add(new Langue(lang, false,locale));
        }
        try {
            LocaleHelper.onAttach(getContext());
        } catch (Exception ignored) {

        }

        try {
            if (LocaleHelper.getLanguage(getContext()) != null) {
                for (int i = 0; i < langues.size(); i++) {
                    if (langues.get(i).getLocale().getLanguage().equals(LocaleHelper.getLanguage(getContext()))) {
                        langues.get(i).setSelected(true);
                        tvLangue.setText(langues.get(i).getName());
                        LocaleHelper.setLocale(getContext(), langues.get(i).getLocale().getLanguage());
                    }
                }
            }
        } catch (Exception ignored) {

        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        FacebookSdk.sdkInitialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        loginButton = view.findViewById(R.id.login_button);


        if (FacebookSdk.isInitialized()) {
            LoginManager.getInstance().logOut();
        }
        try {
            initLangue();
        } catch (Exception ignored) {

        }

        if (Preference.getCurrentCompte(getContext()) != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        } else {
            if (!Preference.hasViewTuto(getContext())) {
                setupViewPager();
            } else {
                pager.setVisibility(View.GONE);
                tabDots.setVisibility(View.GONE);
                layoutLogin.setVisibility(View.VISIBLE);
            }
            //setupViewPager();
            initLogin();
            initializeLoginFacebook(loginButton, this, callbackManager);
        }
        try {
            layoutSelectLanguage.setOnClickListener(v -> showSelectLangue());
        } catch (Exception ignored) {

        }

        try {
            tvCreateAccount.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), TransitionActivity.class);
                intent.putExtra(Constante.ACTION, Constante.CREATE_ACCOUNT_FRAGMENT);
                startActivity(intent);
            });
        } catch (Exception ignored) {

        }
        try {
            layoutFacebook.setOnClickListener(v ->
                    loginButton.performClick());
        } catch (Exception ignored) {

        }
        return view;
    }

    private void showSelectLangue() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") final View viewAlerte = inflater.inflate(R.layout.layout_langue, null);
        RecyclerView rvLangue = viewAlerte.findViewById(R.id.rv_langue);
        LangueAdapter adapter = new LangueAdapter(getContext(), langues);
        rvLangue.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvLangue.setAdapter(adapter);
        dialogBuilder.setView(viewAlerte);
        dialogBuilder.create();
        final AlertDialog alertDialog = dialogBuilder.show();
        LangueAdapter.setOnLangueChangedListener(langue -> {
                    alertDialog.dismiss();
                    for (int j = 0; j < langues.size(); j++) {
                        if (langues.get(j).getName().equals(langue.getName())) {
                            langues.get(j).setSelected(true);
                        }
                    }
                    tvLangue.setText(langue.getName());
                    LocaleHelper.setLocale(getContext(), langue.getLocale().getLanguage());
                    getActivity().recreate();
                }
        );

    }


    private void initLogin() {
        tvSkip.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        });
        tvSigin.setOnClickListener(v -> {
            if (etEmail.getText() == null || etEmail.getText().toString().isEmpty()) {
                try {
                    Snackbar.make(allLayout, "Enter your email", Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (etPassword.getText() == null || etPassword.getText().toString().isEmpty()) {
                try {
                    Snackbar.make(allLayout, "Enter your password", Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sendData();
            }
        });
    }

    private void sendData() {
        try {
            Utils.hideKeyboard((AppCompatActivity) getActivity());
        } catch (Exception ignored) {

        }
        ProgressDialog pd = new ProgressDialog(getContext());
        try {
            pd.setMessage("Loading");
            pd.show();
        } catch (Exception ignored) {

        }
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().login(jsonObject);
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
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        Objects.requireNonNull(getActivity()).finish();
                        try {
                            pd.dismiss();
                        } catch (Exception ignored) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            pd.dismiss();
                            Snackbar.make(allLayout, "Wrong mail or password", Snackbar.LENGTH_LONG).show();
                        } catch (Exception ignored) {

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            pd.dismiss();
                            Snackbar.make(allLayout, "Wrong mail or password", Snackbar.LENGTH_LONG).show();
                        } catch (Exception ignored) {

                        }
                    }


                } else {
                    try {
                        pd.dismiss();
                        Snackbar.make(allLayout, "Oops an error has occurred", Snackbar.LENGTH_LONG).show();
                    } catch (Exception ignored) {

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                try {
                    pd.dismiss();
                    Snackbar.make(allLayout, "Oops an error has occurred", Snackbar.LENGTH_LONG).show();
                } catch (Exception ignored) {

                }
            }
        });
    }

    private void setupViewPager() {
        tabDots.setupWithViewPager(pager, true);
        pager.setPagingEnabled(false);
        PagerTutoAdapter pagerTutoAdapter = new PagerTutoAdapter(Objects.requireNonNull(getContext()));
        pager.setAdapter(pagerTutoAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        PagerTutoAdapter.setOnNextListener(position -> {
            if (position == 0) {
                pager.setCurrentItem(1);
            }
            if (position == 1) {
                pager.setCurrentItem(2);
            }
            if (position == 2) {
                pager.setVisibility(View.GONE);
                tabDots.setVisibility(View.GONE);
                layoutLogin.setVisibility(View.VISIBLE);
                Preference.saveViewTuto(getContext(), true);
            }
        });
    }

    public void initializeLoginFacebook(LoginButton loginButton, LoginFragment fragment, CallbackManager callbackManager) {

        loginButton.setFragment(fragment);
        loginButton.setReadPermissions(Collections.singletonList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me",
                        null,
                        HttpMethod.GET,
                        response -> {
                            final JsonObject params = new JsonObject();
                            final JSONObject jsonObject = response.getJSONObject();
                            if (jsonObject != null) {
                                try {
                                    params.addProperty("email", jsonObject.optString("email"));
                                    user.setEmail(jsonObject.optString("email"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    params.addProperty("fullName", jsonObject.optString("first_name") + " " + jsonObject.optString("last_name"));
                                    user.setName(jsonObject.optString("first_name") + " " + jsonObject.optString("last_name"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    String picture = jsonObject.optJSONObject("picture").optJSONObject("data").optString("url");
                                    params.addProperty("avatar", picture);
                                    user.setAvatar(picture);
                                } catch (Exception e) {
                                    params.addProperty("image", "");
                                }
                            }


                            GraphRequest requestFriends =
                                    new GraphRequest(
                                            AccessToken.getCurrentAccessToken(),
                                            "/me/friends",
                                            null,
                                            HttpMethod.GET,
                                            response1 -> loginFacebook(params)
                                    );
                            Bundle param = new Bundle();
                            param.putString("fields", "name");
                            requestFriends.setParameters(param);
                            requestFriends.executeAsync();


                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,first_name,last_name,picture{url}");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                if (FacebookSdk.isInitialized()) {
                    LoginManager.getInstance().logOut();
                }

            }

            @Override
            public void onError(FacebookException error) {
                if (FacebookSdk.isInitialized()) {
                    LoginManager.getInstance().logOut();
                }
            }
        });


    }

    private void loginFacebook(JsonObject params) {
        ProgressDialog pd = new ProgressDialog(getContext());
        try {
            pd.setMessage("Loading");
            pd.show();
        } catch (Exception ignored) {

        }
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().loginFacebook(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {

                        assert response.body() != null;
                        JSONArray object = new JSONArray(response.body().string());
                        try {
                            response.body().string();
                            if (response.body().string().isEmpty()) {

                            } else {
                                Gson gson = Utils.getGsonInstance();
                                user = gson.fromJson(object.get(0).toString(), User.class);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (user != null)
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
                    } catch (Exception ignored) {

                    }

                } else {
                    try {
                        pd.dismiss();
                        Snackbar.make(allLayout, "Oops an error has occurred", Snackbar.LENGTH_LONG).show();
                    } catch (Exception ignored) {

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                try {
                    pd.dismiss();
                    Snackbar.make(allLayout, "Oops an error has occurred", Snackbar.LENGTH_LONG).show();
                } catch (Exception ignored) {

                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
