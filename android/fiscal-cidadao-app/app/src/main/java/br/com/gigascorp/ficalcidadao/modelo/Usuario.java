package br.com.gigascorp.ficalcidadao.modelo;

/**
 * Created by wallashss on 29/03/16.
 */
public class Usuario
{
    private String name;
    private String id;
    private String token;
    private String imageUrl;

    public Usuario(String name, String id, String token, String imageUrl)
    {
        this.name = name;
        this.id = id;
        this.token = token;
        this.imageUrl = imageUrl;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getToken()
    {
        return token;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }


}
