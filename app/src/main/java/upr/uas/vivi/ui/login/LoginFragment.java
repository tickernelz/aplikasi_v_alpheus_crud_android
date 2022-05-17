package upr.uas.vivi.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import upr.uas.vivi.R;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.User;

public class LoginFragment extends Fragment implements View.OnClickListener {
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_login, container, false);
    Button button_submit = mView.findViewById(R.id.login_button);
    button_submit.setOnClickListener(this);
    return mView;
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Inflate the layout for this fragment
    DBHandler db = new DBHandler(requireContext());
    User user = new User();
    user.setIsLogin(1);
    if (db.checkIsLogin(user)) {
      EditText editText_username = requireView().findViewById(R.id.login_username_et);
      EditText editText_password = requireView().findViewById(R.id.login_password_et);
      Button button_submit = requireView().findViewById(R.id.login_button);
      editText_username.setText(user.getUsername(false));
      editText_password.setText(user.getPassword(false));
      editText_username.setEnabled(false);
      editText_password.setEnabled(false);
      button_submit.setText(R.string.logout);
    }
  }

  public void onClick(View v) {
    if (v.getId() == R.id.login_button) {
      EditText editText_username = requireView().findViewById(R.id.login_username_et);
      EditText editText_password = requireView().findViewById(R.id.login_password_et);
      Button button_submit = requireView().findViewById(R.id.login_button);
      String username = editText_username.getText().toString();
      String password = editText_password.getText().toString();
      TextView nav_header_subtitle_tv =
          Objects.requireNonNull(requireActivity()).findViewById(R.id.nav_header_subtitle_tv);
      NavigationView navigationView =
          Objects.requireNonNull(requireActivity().findViewById(R.id.nav_view));
      Menu menu = navigationView.getMenu();
      MenuItem nav_brand = menu.findItem(R.id.nav_brand);
      MenuItem nav_produk = menu.findItem(R.id.nav_produk);
      MenuItem nav_transaksi = menu.findItem(R.id.nav_transaksi);
      DBHandler db = new DBHandler(requireContext());
      User user = new User();
      user.setUsername(username);
      user.setPassword(password);
      user.setIsLogin(1);
      if (db.checkIsLogin(user)) {
        nav_brand.setEnabled(false);
        nav_produk.setEnabled(false);
        nav_transaksi.setEnabled(false);
        user.setIsLogin(0);
        db.updateIsLogin(user);
        editText_username.setEnabled(true);
        editText_password.setEnabled(true);
        button_submit.setText(R.string.submit);
        nav_header_subtitle_tv.setText(R.string.login_please);
        Snackbar.make(requireView(), "Logout berhasil", Snackbar.LENGTH_SHORT).show();
      } else {
        if (db.checkUser(user)) {
          nav_brand.setEnabled(true);
          nav_produk.setEnabled(true);
          nav_transaksi.setEnabled(true);
          editText_username.setEnabled(false);
          editText_password.setEnabled(false);
          button_submit.setText(R.string.logout);
          nav_header_subtitle_tv.setText("Welcome " + db.getName(user));
          // Set isLogin
          user.setIsLogin(1);
          db.updateIsLogin(user);
          Snackbar.make(requireView(), "Login Success", Snackbar.LENGTH_SHORT).show();
        } else {
          Snackbar.make(requireView(), "Username atau password salah", Snackbar.LENGTH_LONG).show();
        }
      }
    }
  }
}
