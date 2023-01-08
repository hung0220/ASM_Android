package com.example.asm_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    ImageView btnMore;
    BottomNavigationView menu_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        LinearLayout btnqlnv = findViewById(R.id.btnQlNV);

        btnqlnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),QLNhanVien.class);
                startActivity(intent);

            }
        });
        LinearLayout btnqlhh = findViewById(R.id.btnQLHH);

        btnqlhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QLHangHoa.class);
                startActivity(intent);

            }
        });

        LinearLayout btnGhiChu = findViewById(R.id.btnGhichu);
        btnGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QLGhiChu.class);
                startActivity(intent);
            }
        });




//        menu_bottom =  findViewById(R.id.bottomNavigationView);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameview, new BlankFragment1()).commit();
//
//        menu_bottom.setSelectedItemId(R.id.first_fragment);
//        menu_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                Fragment fragment = null;
//                switch (item.getItemId()) {
//                    case R.id.second_fragment:
////                        fragment = new BlankFragment1();
//                        break;
//                    case R.id.first_fragment:
////                        fragment = new homeFragment();
//                        break;
//                    case R.id.third_fragment:
////                        fragment = new thuvienFragment();
//                        break;
//                    case R.id.fourth_fragment:
////                        fragment = new BlankFragment1();
//                        break;
//                }
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameview, fragment).commit();
//
//
//                return true;
//            }
//        });
//        BottomNavigationView menu_bottom;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void showMenu() {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, btnMore);
        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case  R.id.dangXuat:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
        popupMenu.show();
    }

}