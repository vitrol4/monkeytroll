package br.edu.ifspsaocarlos.sdm.monkeytroll;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.monkeytroll.adapter.DrawerListAdapter;
import br.edu.ifspsaocarlos.sdm.monkeytroll.fragment.ContatosFragment;
import br.edu.ifspsaocarlos.sdm.monkeytroll.model.NavItem;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.ContatoSessionManager;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private static List<NavItem> mNavItems;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ContatoSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new ContatoSessionManager(getBaseContext());
        if (sessionManager.checkLogin()) {
            finish();
        }

        ((TextView) findViewById(R.id.userName)).setText(sessionManager.getUserDetails().get(ContatoSessionManager.KEY_APELIDO));
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

        mNavItems = new ArrayList<>();
        mNavItems.add(new NavItem("Contatos", "todos", R.drawable.contacts1));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkgreen)));
        }

        Bundle args = getIntent().getBundleExtra("args");
        Fragment fragment = new ContatosFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(fragment.getClass().getName())
                .replace(R.id.mainContent, fragment)
                .commit();
    }

    private void selectItemFromDrawer(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ContatosFragment();
                fragmentManager.beginTransaction()
                        .addToBackStack(fragment.getClass().getName())
                        .replace(R.id.mainContent, fragment)
                        .commit();
                break;
        }

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).getmTitle());

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mDrawerLayout.isDrawerOpen(mDrawerPane)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            if (mDrawerLayout.isDrawerOpen(mDrawerPane)) {
                mDrawerLayout.closeDrawer(mDrawerPane);
            }
            getFragmentManager().popBackStack();
        }
    }
}
