package br.com.gigascorp.ficalcidadao;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import br.com.gigascorp.ficalcidadao.fragment.ListaDenunciasFragment;
import br.com.gigascorp.ficalcidadao.fragment.MapaConveniosFragment;
import br.com.gigascorp.ficalcidadao.fragment.PerfilFragment;

public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        mostrarBotaoVoltar();

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                if(menuItemId == R.id.bottomBarMapa){

                    setTitle("Fiscal Cidadão");

                    MapaConveniosFragment fragment = new MapaConveniosFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .commit();

                    return;
                }

                if(menuItemId == R.id.bottomBarDenuncias){

                    setTitle("Suas Denúncias");

                    ListaDenunciasFragment fragment = new ListaDenunciasFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .commit();

                    return;
                }

                if(menuItemId == R.id.bottomBarPerfil){

                    setTitle("Perfil");

                    PerfilFragment fragment = new PerfilFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
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

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
        mostrarBotaoVoltar();
    }

    public void mostrarBotaoVoltar(){
        boolean mostrar = getSupportFragmentManager().getBackStackEntryCount()>0;

        if(!mostrar){
            setarTitulo();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(mostrar);
    }

    public void selecionarAba(int pos){
        mBottomBar.selectTabAtPosition(pos, true);
    }

    private void setarTitulo(){

        if(mBottomBar == null)
            return;

        if(mBottomBar.getCurrentTabPosition() < 0)
            return;

        int pos = mBottomBar.getCurrentTabPosition();

        if(pos == 0){
            setTitle("Fiscal Cidadão");
            return;
        }

        if(pos == 1){
            setTitle("Suas Denúncias");
            return;
        }

        if(pos == 2){
            setTitle("Perfil");
            return;
        }
    }

}
