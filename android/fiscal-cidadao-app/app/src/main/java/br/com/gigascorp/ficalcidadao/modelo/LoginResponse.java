package br.com.gigascorp.ficalcidadao.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wallashss on 29/03/16.
 */
public class LoginResponse
{

    @SerializedName("Nome")
    private String name;

    @SerializedName("FotoPerfil")
    private String profilePicture;

    @SerializedName("HttpStatus")
    private int httpStatus;

    public  String getName()
    {
        return name;
    }

    public String getProfilePicture()
    {
        return profilePicture;
    }

    public int getHttpStatus()
    {
        return httpStatus;
    }
}
