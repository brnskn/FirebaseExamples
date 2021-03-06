package com.gelecegiyazanlar.firebaseauthproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText userEmail,userPassword;
    private Button newRegisterButton,userLoginButton;
    private FirebaseAuth auth;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth=FirebaseAuth.getInstance();

        userEmail =(EditText)findViewById(R.id.uyeEmail);
        userPassword =(EditText)findViewById(R.id.uyeParola);
        newRegisterButton =(Button)findViewById(R.id.yeniUyeButton);
        userLoginButton =(Button)findViewById(R.id.uyeGirisButton);

        newRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registerFunction();

            }
        });


        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void registerFunction() {

        String email = userEmail.getText().toString();
        String parola = userPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Lütfen emailinizi giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(parola)){
            Toast.makeText(getApplicationContext(),"Lütfen parolanızı giriniz",Toast.LENGTH_SHORT).show();
        }
        if (parola.length()<6){
            Toast.makeText(getApplicationContext(),"Parola en az 6 haneli olmalıdır", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(email,parola)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        //İşlem başarısız olursa kullanıcıya bir Toast mesajıyla bildiriyoruz.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Yetkilendirme Hatası",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                        else {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });



    }
}
