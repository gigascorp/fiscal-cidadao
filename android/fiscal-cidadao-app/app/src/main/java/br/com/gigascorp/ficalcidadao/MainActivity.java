/*
Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guilherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

   This file is part of Fiscal Cidadão.

   Fiscal Cidadão is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Fiscal Cidadão is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>.
*/

package br.com.gigascorp.ficalcidadao;

import android.Manifest;
import android.content.Intent;

/*import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.gigascorp.ficalcidadao.modelo.FacebookLogin;
import br.com.gigascorp.ficalcidadao.modelo.Usuario;
import br.com.gigascorp.ficalcidadao.modelo.LoginResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private int PERMISSIONS_REQUEST_RETURN = 1;

    //CallbackManager callbackManager;
    private GoogleApiClient client;

    public void login(){
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        FiscalCidadaoApp app = (FiscalCidadaoApp) this.getApplication();

        Usuario usuario = new Usuario(androidId);
        Log.d(FiscalCidadaoApp.TAG, "Android_Id: " + usuario.getId());
        app.setCurrentUsuario(usuario);

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            if (Build.VERSION.SDK_INT >= 23) {

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        PERMISSIONS_REQUEST_RETURN);
            } else {
                login();
            }

        } else {
            login();
        }


        /*FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

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
                        doLogin(loginResult.getAccessToken().getToken(), loginResult.getAccessToken().getUserId());
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

        if (AccessToken.getCurrentAccessToken() != null & Profile.getCurrentProfile() != null) {
            if (!AccessToken.getCurrentAccessToken().isExpired() && Profile.getCurrentProfile().getId() != null) {
//                goToMapActivity();
                doLogin(AccessToken.getCurrentAccessToken().getToken(), Profile.getCurrentProfile().getId());
            }
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
    }

    /*private void doLogin(final String accessToken, final String fbUserId) {

        if (accessToken != null && fbUserId != null)
        {
            FacebookLogin fbLogin = new FacebookLogin();
            fbLogin.setId(fbUserId);
            fbLogin.setAccessToken(accessToken);

            final FiscalCidadaoApp app = (FiscalCidadaoApp) this.getApplication();

            Call<LoginResponse> call = app.getFiscalCidadaoApi().login(fbLogin);

            showLoadingView();

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Response<LoginResponse> response, Retrofit retrofit)
                {
                    Log.d(FiscalCidadaoApp.TAG, "Worked");
                    LoginResponse user = response.body();
                    Log.d(FiscalCidadaoApp.TAG, "Name: " + user.getName());
                    Log.d(FiscalCidadaoApp.TAG, "Profile: " + user.getProfilePicture());
                    Log.d(FiscalCidadaoApp.TAG, "Statuds: " + user.getHttpStatus());

                    if(user.getHttpStatus() == 200) // Any better way to check this?
                    {
                        Usuario usuario = new Usuario(user.getName(), fbUserId, accessToken, user.getProfilePicture());
                        app.setCurrentUsuario(usuario);

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Erro ao fazer login no servidor (Error:" + user.getHttpStatus() + ")", Toast.LENGTH_LONG);
                    }
                    dissmissLoadingView();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(FiscalCidadaoApp.TAG, "Erro ao rodar");
                    Toast.makeText(MainActivity.this, "Erro ao fazer login no servidor ", Toast.LENGTH_LONG);
                    t.printStackTrace();
                    dissmissLoadingView();

                }
            });



        }
        else
        {
            Toast.makeText(this, "Erro ao obter dados do login do Facebook", Toast.LENGTH_LONG);
        }
    }

    private void showLoadingView()
    {
        // TODO
    }

    private void dissmissLoadingView()
    {
        // TODO
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

    public void onClickBtnPassarDireto(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://br.com.gigascorp.ficalcidadao/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://br.com.gigascorp.ficalcidadao/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_RETURN) {

            if (grantResults.length > 0) {

                for(int i : grantResults){
                    if( i != PackageManager.PERMISSION_GRANTED ){
                        Toast.makeText(this, "Você precisa garantir as permissões de acesso necessárias para que o aplicativo funcione bem.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                login();

            } else {
                Toast.makeText(this, "Você precisa garantir as permissões de acesso necessárias para que o aplicativo funcione bem.", Toast.LENGTH_LONG).show();
                return;
            }

        }
    }
}
