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

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import br.com.gigascorp.ficalcidadao.fragment.ListaDenunciasFragment;
import br.com.gigascorp.ficalcidadao.fragment.MapaConveniosFragment;
import br.com.gigascorp.ficalcidadao.fragment.PerfilFragment;
import br.com.gigascorp.ficalcidadao.fragment.RankingFragment;
import br.com.gigascorp.ficalcidadao.modelo.Ranking;

public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        mostrarBotaoVoltar();

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.ignoreNightMode();
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                if (menuItemId == R.id.bottomBarMapa) {

                    setTitle("Fiscal Cidadão");

                    MapaConveniosFragment fragment = new MapaConveniosFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .commit();

                    return;
                }

                if (menuItemId == R.id.bottomBarDenuncias) {

                    setTitle("Suas Denúncias");

                    ListaDenunciasFragment fragment = new ListaDenunciasFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .commit();

                    return;
                }

                if (menuItemId == R.id.bottomBarPerfil) {

                    setTitle("Perfil");

                    PerfilFragment fragment = new PerfilFragment();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.telaHome, fragment)
                            .commit();

                    return;
                }

                if (menuItemId == R.id.bottomBarRanking) {

                    setTitle("Pontuação dos Amigos");

                    RankingFragment fragment = new RankingFragment();

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

        /*mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.white));
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.white));
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.white));
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.white));*/

        if(!ImageLoader.getInstance().isInited()){
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
            ImageLoader.getInstance().init(config);
        }
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
            setTitle("Pontuação dos Amigos");
            return;
        }

        if(pos == 3){
            setTitle("Perfil");
            return;
        }

    }

}
