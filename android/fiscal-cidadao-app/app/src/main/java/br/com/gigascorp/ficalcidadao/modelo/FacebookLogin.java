package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;


/**
 * Created by wallashss on 29/03/16.
 */
public class FacebookLogin {

    @SerializedName("Id")
    private String id;

    @SerializedName("AccessToken")
    private String accessToken;

    public  String getId()
    {
        return id;
    }

    public  void setId(String id)
    {
        this.id = id;
    }

    public  String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }


}
