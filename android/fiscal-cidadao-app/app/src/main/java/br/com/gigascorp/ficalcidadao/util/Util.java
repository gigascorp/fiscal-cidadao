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

package br.com.gigascorp.ficalcidadao.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.ExifInterface;

import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;
import br.com.gigascorp.ficalcidadao.modelo.wrapper.FotoHolderWrap;

public class Util {

    public static Marker getConvenioNaMesmaLocalizacao(Map<Marker, List<Convenio>> lista, Convenio convenio){

        for (Map.Entry<Marker, List<Convenio>> entry : lista.entrySet()){

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

    public static ArrayList<FotoHolderWrap> adicionarFoto(ArrayList<FotoHolderWrap> lista, FotoHolderWrap nova){

        if(lista == null){
            lista = new ArrayList<>();
        }

        lista.add(nova);

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
