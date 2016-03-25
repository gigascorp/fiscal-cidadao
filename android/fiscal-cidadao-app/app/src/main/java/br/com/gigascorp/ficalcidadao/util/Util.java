package br.com.gigascorp.ficalcidadao.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Environment;

import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.gigascorp.ficalcidadao.R;
import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;

public class Util {

    public static Marker getConvenioNaMesmaLocalizacao(Map<Marker, List<Convenio>> lista, Convenio convenio){

        for (Map.Entry<Marker, List<Convenio>> entry : lista.entrySet()){
            System.out.println(entry.getKey() + "/" + entry.getValue());

            List<Convenio> convenios = entry.getValue();

            for(Convenio c : convenios){
                if(c.getCoordenada().equals(convenio.getCoordenada())){
                    return entry.getKey();
                }
            }

        }
        return null;
    }

    public static int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static FotoHolderWrap criarBotaoAdicionarFoto(Context context){
        Bitmap icam = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add_a_photo_black_48dp);
        return new FotoHolderWrap(icam, true);
    }

    public static ArrayList<FotoHolderWrap> adicionarFoto(Context context, ArrayList<FotoHolderWrap> lista, FotoHolderWrap nova){

        if(lista == null){
            lista = new ArrayList<>();
        }

        if(lista.size() == 0){
            lista.add(nova);
            lista.add(criarBotaoAdicionarFoto(context));
            return lista;
        }

        FotoHolderWrap cam = lista.get(lista.size()-1);

        if(cam.isBotao()){
            lista.add(lista.size()-1, nova);
            return lista;
        }

        //Se chegar até aqui, a lista não possui nenhum botão.
        lista.add(nova);
        lista.add(criarBotaoAdicionarFoto(context));

        return lista;
    }

    public static int rotacaoNecessaria(File ff) {
        try {

            ExifInterface exif = new ExifInterface(ff.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            { return 270; }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            { return 180; }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            { return 90; }
            return 0;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Bitmap redimensionar(Bitmap realImage) {
        float maxImageSize = 800;
        boolean filter = true;

        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);

        realImage.recycle();

        return newBitmap;
    }

}
