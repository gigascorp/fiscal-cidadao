package br.com.gigascorp.ficalcidadao;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import br.com.gigascorp.ficalcidadao.fragment.ListaDenunciasFragment;
import br.com.gigascorp.ficalcidadao.fragment.MapaConveniosFragment;
import br.com.gigascorp.ficalcidadao.fragment.PerfilFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MapaConveniosFragment fragment = new MapaConveniosFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.telaHome, fragment)
                .addToBackStack(null)
                .commit();


        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                if(menuItemId == R.id.bottomBarMapa){

                    setTitle("Fiscal Cidadão");

                    MapaConveniosFragment fragment = new MapaConveniosFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .addToBackStack(null)
                            .commit();

                    return;
                }

                if(menuItemId == R.id.bottomBarDenuncias){

                    setTitle("Suas Denúncias");

                    ListaDenunciasFragment fragment = new ListaDenunciasFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .addToBackStack(null)
                            .commit();

                    return;
                }

                if(menuItemId == R.id.bottomBarPerfil){

                    setTitle("Perfil");
        
                    PerfilFragment fragment = new PerfilFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .addToBackStack(null)
                            .commit();

                    return;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                /*if (resId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }*/
            }
        });

    }

}
