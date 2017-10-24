package anhnht.Component;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhnh.sliderdemo.MainActivity;
import com.example.anhnh.sliderdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_In extends AppCompatActivity {

    private EditText txtEmail, txtPass;
    private Button btnLogin;
    private TextView txtRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);
        init();
    }

    public void onClickAction(View view){
        if(view == btnLogin){
            String email = txtEmail.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();
            if(email.isEmpty()){
                setErrorText(txtEmail);
                return;
            }else if(pass.isEmpty()){
                setErrorText(txtPass);
                return;
            }
            loginUser(email, pass);
        }else if(view == txtRegister){
            Intent regist = new Intent(this, Register.class);
            startActivity(regist);
        }
    }

    private void loginUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Log_In.this, " log in successful", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(Log_In.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);

                    finish();
                }else {
                    Toast.makeText(Log_In.this, "not successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init(){
        txtEmail = (EditText) findViewById(R.id.txtLogEmail);
        txtPass = (EditText) findViewById(R.id.txtLogPass);
        txtRegister = (TextView) findViewById(R.id.txtRegist);
        btnLogin = (Button) findViewById(R.id.btnLogIn);
        txtEmail.requestFocus();
    }

    private void setErrorText(EditText text){
        text.setError("This field can't be empty");
        text.requestFocus();
    }
}
