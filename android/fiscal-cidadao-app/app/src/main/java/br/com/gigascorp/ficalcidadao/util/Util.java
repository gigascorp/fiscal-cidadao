package br.com.gigascorp.ficalcidadao.util;

import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;

import br.com.gigascorp.ficalcidadao.modelo.Convenio;

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
}
