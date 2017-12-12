package com.nomad.audit5s.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nomad.audit5s.Model.Usuario;
import com.nomad.audit5s.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity  {


    private ImageButton loginBtn;

    private ProgressBar progressBar;

    private ImageButton fakeFbLogin;
    private String idFacebook, nombreFacebook, nombreMedioFacebook, apellidoFacebook, sexoFacebook, imagenFacebook, nombreCompletoFacebook, emailFacebook;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private EditText editUsuario;
    private EditText editPass;
    private EditText editRepePass;

    private TextInputLayout til1;
    private TextInputLayout til2;
    private TextInputLayout til3;

    private Button botonLogin;
    private Button botonRegister;
    private Button botonOk;

    private String mail;
    private String contraseña;

    private TextView titulo;

    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar=(ProgressBar) findViewById(R.id.progreso);

        mAuth = FirebaseAuth.getInstance();
        Realm.init(getApplicationContext());
        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                mDatabase= FirebaseDatabase.getInstance().getReference();


                if (user!=null){
                    DatabaseReference reference = mDatabase.child("usuarios").child(user.getUid()).child("datos");
                    String mail;
                    String foto;
                    if (user.getEmail() == null||user.getEmail().isEmpty()){
                        mail=user.getDisplayName();
                        reference.child("nombre").setValue(user.getDisplayName());
                    }
                    else{
                        mail=user.getEmail();
                        reference.child("email").setValue(user.getEmail());
                        reference.child("nombre").setValue(user.getDisplayName());

                    }
                    if (user.getPhotoUrl()==null){
                        foto="sinFoto";
                        reference.child("foto").setValue("sinFoto");

                    }
                    else{
                        foto=user.getPhotoUrl().toString();
                        reference.child("foto").setValue(user.getPhotoUrl().toString());
                    }


                    irALanding();



                }
                else{

                }
            }
        };

        TextView unTextview = (TextView) findViewById(R.id.textViewLogin);
        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        unTextview.setTypeface(roboto);

         til1 = (TextInputLayout) findViewById(R.id.inputLayout1);
         til2 = (TextInputLayout) findViewById(R.id.inputLayout2);
         til3 = (TextInputLayout) findViewById(R.id.inputLayout3);


        botonLogin = (Button) findViewById(R.id.buttonLogin);
        botonRegister = (Button) findViewById(R.id.buttonRegister);
        botonOk=(Button)findViewById(R.id.okRegister);
        
        editUsuario= (EditText) findViewById(R.id.editTextUsuario);
        editPass = (EditText) findViewById(R.id.editTextPassword);
        editRepePass = (EditText) findViewById(R.id.editTextRepePassword);
        editUsuario.setTypeface(roboto);

        //COMPROBAR SI COINCIDE USUARIO Y CONTRASEÑA
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                til1.setError(null);
                til2.setError(null);
                Integer validar =0;
                if (editUsuario.getText().toString().isEmpty()) {
                    til1.setError(getResources().getString(R.string.ingreseUsuarioValido));
                    validar = validar + 1;
                }

                if (editPass.getText().toString().toLowerCase().isEmpty()) {
                    til2.setError(getResources().getString(R.string.ingreseContraseniaValida));
                    validar = validar + 1;
                }

                if (validar > 0) {
                    return;
                }
                
                 mail = editUsuario.getText().toString().toLowerCase();
                 contraseña = editPass.getText().toString();
                
                    loguearFirebaseManual(mail, contraseña);
                
                   editPass.setText(null);
                   editUsuario.setText(null);
                
            }
        });
        //BOTON REGISTER
        
        botonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til3.setVisibility(View.VISIBLE);
                editRepePass.setVisibility(View.VISIBLE);
                botonOk.setVisibility(View.VISIBLE);
                botonRegister.setVisibility(View.GONE);
                botonLogin.setVisibility(View.GONE);
            }
        });
        
        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer control = 0;
                til1.setError(null);
                til2.setError(null);
                til3.setError(null);

                if (editUsuario.getText().toString().isEmpty()) {
                    til1.setError(getResources().getString(R.string.ingreseUsuarioValido));
                    control = control + 1;
                }

                if (editPass.getText().toString().isEmpty()) {
                    til3.setError(getResources().getString(R.string.ingreseContraseniaValida));
                    control = control + 1;
                }
                if (editPass.getText().toString().length() < 6) {
                    til3.setError(getResources().getString(R.string.caracteresContrasenia));
                    control = control + 1;
                }

                if (control > 0) {
                    return;
                }
                if (editPass.getText().toString().equals(editRepePass.getText().toString())) {

                    final Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setMail(editUsuario.getText().toString().toLowerCase());
                    nuevoUsuario.setPass(editPass.getText().toString());
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Usuario> mResults = realm.where(Usuario.class)
                            .equalTo("mail",nuevoUsuario.getMail())
                            .findAll();
                    if (mResults.size()<1){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Usuario mUser=realm.copyToRealm(nuevoUsuario);
                            }
                        });
                        crearCuentaFirebase(nuevoUsuario.getMail(), nuevoUsuario.getPass());
                    }

                }
                else {
                    til3.setError(getResources().getString(R.string.contraseniasNoCoinciden));
                    editRepePass.setText("");
                }
            }

            ;

        });

    }

    public void irALanding(){
        progressBar.setVisibility(View.GONE);
        Intent unIntent = new Intent(this, ActivityLanding.class);
        startActivity(unIntent);
        Toast.makeText(this, getResources().getString(R.string.bienvenido)+mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        LoginActivity.this.finish();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        irALanding();
    }

    public void loguearFirebaseManual(String usuario, String pass){
        mAuth.signInWithEmailAndPassword(usuario, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.autenticacionFallida), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });
    }

    public void crearCuentaFirebase(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            botonOk.setVisibility(View.GONE);
                            botonLogin.setVisibility(View.VISIBLE);
                            botonRegister.setVisibility(View.VISIBLE);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            //creo usuario en mi base de datos
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.sinConexionIntenteMasTarde), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



}
