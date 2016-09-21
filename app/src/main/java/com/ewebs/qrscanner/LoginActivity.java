package com.ewebs.qrscanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ewebs.qrscanner.model.AppData;
import com.ewebs.qrscanner.model.Config;
import com.ewebs.qrscanner.model.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    String token,data;
    public static AppData AppData;

    @BindView(R.id.phone_number)    AutoCompleteTextView  phoneView;
    @BindView(R.id.password)        EditText              passwordView;
    @BindView(R.id.login_form)      ScrollView            mLoginFormView;
    @BindView(R.id.login_progress)  ProgressBar           mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.email_sign_in_button)
    public void onClick() {
        data = String.format("{\"user\":\"%s\",\"password\":\"%s\"}",
                phoneView.getText(),passwordView.getText());

        StringRequest postRequest = new StringRequest(Request.Method.POST, Config.getConfigValue(this, "login_url"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            token = jobj.getJSONObject("data").getString("login_token");

                            if(jobj.optInt("result")==0) {
                                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                AppData = new AppData(token, phoneView.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            showProgress(false);
                            passwordView.setError(getString(R.string.error_incorrect_password));
                            passwordView.requestFocus();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);
                        phoneView.setError(getString(R.string.error_network));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("service", "LOGIN");
                params.put("apikey", Config.getConfigValue(LoginActivity.this, "api_key"));
                params.put("data", data);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }
}
