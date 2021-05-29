package cl.inacap.kevinguerrero.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionMenu;

import cl.inacap.kevinguerrero.afinal.Local.AgLocal;
import cl.inacap.kevinguerrero.afinal.Local.ListLocal;
import cl.inacap.kevinguerrero.afinal.Remoto.AgRem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionMenu mp=(FloatingActionMenu)findViewById(R.id.mp);
        mp.setClosedOnTouchOutside(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fr= getSupportFragmentManager();

        if (id == R.id.nav_mapa) {

            fr.beginTransaction().replace(R.id.contenedor,new Ubicacion()).commit();

        } else if (id == R.id.nav_contactos) {

            fr.beginTransaction().replace(R.id.contenedor,new ListarPhpFragment()).commit();

        } else if (id == R.id.nav_camara) {

            Intent inte = new Intent(MainActivity.this, Camara2.class);
            startActivity(inte);

        }else if (id == R.id.AgLocal) {

              fr.beginTransaction().replace(R.id.contenedor,new AgLocal()).commit();

        } else if (id == R.id.ListLocal) {

            fr.beginTransaction().replace(R.id.contenedor,new ListLocal()).commit();

        }  else if (id == R.id.nav_share) {

            fr.beginTransaction().replace(R.id.contenedor,new AgRem()).commit();

        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clicubi( View view){

        FragmentManager fr= getSupportFragmentManager();

        fr.beginTransaction().replace(R.id.contenedor,new Ubicacion()).commit();

    }

    public void cliccam( View view){

        Intent inte = new Intent(MainActivity.this, Camara2.class);
        startActivity(inte);

    }
}
