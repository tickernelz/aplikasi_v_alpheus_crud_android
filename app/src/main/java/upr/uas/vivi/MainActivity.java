package upr.uas.vivi;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import upr.uas.vivi.databinding.ActivityMainBinding;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.User;

public class MainActivity extends AppCompatActivity {

  private AppBarConfiguration mAppBarConfiguration;
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Create User to DB
    DBHandler db = new DBHandler(this);
    User user = new User();
    user.setName("Vivi");
    user.setUsername("admin");
    user.setPassword("admin");
    db.InsertUser(user);

    setSupportActionBar(binding.appBarMain.toolbar);
    DrawerLayout drawer = binding.drawerLayout;
    NavigationView navigationView = binding.navView;
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration =
        new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_login)
            .setOpenableLayout(drawer)
            .build();
    NavController navController =
        Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);
  }

  @Override
  protected void onResume() {
    DBHandler db = new DBHandler(this);
    User user = new User();
    user.setIsLogin(1);
    super.onResume();
    new Handler()
        .postDelayed(
            () -> {
              LinearLayout linearLayout = findViewById(R.id.nav_header_main);
              TextView nav_header_subtitle_tv = findViewById(R.id.nav_header_subtitle_tv);
              NavigationView navigationView = findViewById(R.id.nav_view);
              Menu menu = navigationView.getMenu();
              //                      MenuItem nav_bus = menu.findItem(R.id.nav_bus);
              //                      MenuItem nav_penumpang = menu.findItem(R.id.nav_penumpang);
              if (db.checkIsLogin(user)) {
                //                        nav_bus.setEnabled(true);
                //                        nav_penumpang.setEnabled(true);
                nav_header_subtitle_tv.setText("Welcome " + db.getName(user));
              } else {
                nav_header_subtitle_tv.setText(R.string.login_please);
              }
              linearLayout.setVisibility(LinearLayout.VISIBLE);
            },
            1000);
  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController =
        Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }
}
