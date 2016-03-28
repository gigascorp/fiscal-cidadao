package br.com.gigascorp.ficalcidadao;

import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("public_profile", "user_friends", "email");


//        LoginManager.getInstance().registerCallback(callbackManager,
            loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        Log.i(getClass().toString(), "Acces tokken " + loginResult.getAccessToken().toString());
                        Log.i(getClass().toString(), "Granted Permissions");
                        for (String str : loginResult.getRecentlyGrantedPermissions()) {
                            Log.i(getClass().toString(), str);
                        }
                        // App code

                        goToMapActivity();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i(getClass().toString(), "User canceled ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e(getClass().toString(), "Exception on login!");
                        exception.printStackTrace();
                    }
                });

//        boolean isLoggedOn = false;
        if(AccessToken.getCurrentAccessToken() != null)
        {
            if (!AccessToken.getCurrentAccessToken().isExpired())
            {
                goToMapActivity();

                if(Profile.getCurrentProfile() != null)
                {
                    Profile profile = Profile.getCurrentProfile();
                    Log.i(getClass().toString(), "Name " + profile.getFirstName());
                    Log.i(getClass().toString(), "Access Token " + AccessToken.getCurrentAccessToken().getToken());
                }
            }
        }

//        if(!isLoggedOn)
//        {
//            loginViaFacebook();
//        }
    }


    private void goToMapActivity()
    {
        Intent intent = new Intent(MainActivity.this, MapaConveniosActivity.class);
        startActivity(intent);



//        Profile userProfile = Profile.getCurrentProfile();
//        Log.i(getClass().toString(), userProfile.getFirstName());
//        Log.i(getClass().toString(), userProfile.getLastName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
