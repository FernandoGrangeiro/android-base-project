package br.com.baseproject.baseproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaopiz.kprogresshud.KProgressHUD;

import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private TextView signUp;
    private LinearLayout container;

    //Firebase
    private FirebaseAuth mAuth;
    private KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressHUD = KProgressHUD.create(this).setDimAmount(0.5F).setCancellable(false);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        getLayoutIds();
        setButtonActions();
    }

    private void getLayoutIds() {

        emailEditText = findViewById(R.id.activity_login_field_email);
        passwordEditText = findViewById(R.id.activity_login_field_password);
        signUp = findViewById(R.id.login_sign_up);
        loginButton = findViewById(R.id.activity_login_button_login);
        container = findViewById(R.id.container_login);
    }

    private void setButtonActions() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                doLogin(email, password);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(view, LoginActivity.this);
            }
        });
    }

    public void doLogin(String email, String password) {
        progressHUD.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressHUD.dismiss();
                    FirebaseUser user = task.getResult().getUser();
                    //TODO: login successful
                    Intent intent = new Intent(LoginActivity.this, TabsMainActivity.class);
                    startActivity(intent);
                } else {
                    progressHUD.dismiss();
                    Toast.makeText(LoginActivity.this, "Ocorreu um erro com o login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
