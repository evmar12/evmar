package com.agendaapp.agendapediatrica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.agendaapp.agendapediatrica.Hijos_pack.HijosActivity;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * Actividad de la pantalla principal. Se muestra el botón de iniciar sesión con cuanta
 * Google. Con el login exitoso se muestra el nombre completo y la dirección de correo.
 * Se utiliza esta misma actividad para solicitar iniciar seesión, inicio exitoso y sesión cerrada.
 */
public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    public static final String EXTRA_MESSAGE = "Esto es una prueba";
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    // URL BASE del WebService
    private static String URL_BASE = "http://localhost:8084/AgendaWS/webresources";

    private GoogleApiClient mGoogleApiClient;
    private TextView mTitleText, mStatusTextView, mMailAddrTextView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        mTitleText = (TextView) findViewById(R.id.title_text);
        mStatusTextView = (TextView) findViewById(R.id.status);
        mMailAddrTextView = (TextView) findViewById(R.id.mail_address);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            // Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

            mMailAddrTextView.setText(getString(R.string.mail_address, acct.getEmail()));

            // todo llamado al service
            String correo = acct.getEmail();
            validarUsuario(correo);

            // updateUI(true);      // TODO deprecar
            //Intent intent = new Intent(this, HijosActivity.class);
            //startActivity(intent);

        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }


    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void updateUI(boolean signedIn) {

        if (!signedIn) {
            // se depreca actualización de UI en el mismo activity.
            // En cambio se llamará a la pantalla que lista hijos.
            mTitleText.setText(getString(R.string.logged_out));
            mStatusTextView.setText(R.string.signed_out);
            mMailAddrTextView.clearComposingText();
            mMailAddrTextView.setVisibility(View.GONE);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;

        }
    }

    public MainActivity getOuter() {
        return MainActivity.this;
    }

    public void validarUsuario(String correo) {
        // TODO tener en cuenta que esta URL es local solamente
        String urlValidar = "%s/usuarios/validarUsuario/%s";
        urlValidar = String.format(urlValidar, URL_BASE, correo);
        new ValidarUsuarioTask().execute(urlValidar);
    }

    private class ValidarUsuarioTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                return HttpRequest.get(urls[0]).accept("application/json")
                        .body();
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        protected void onPostExecute(String response) {

            // evaluar respuesta booleana del WEbService
            if ("true".equalsIgnoreCase(response.toString())) {
                Intent intent = new Intent(getOuter(), HijosActivity.class);

                Log.i(TAG, response);
                startActivity(intent);

            } else {
                updateUI(false);
            }

        }
    }
}