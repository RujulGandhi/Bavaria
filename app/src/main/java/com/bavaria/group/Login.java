package com.bavaria.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by archirayan on 2/24/2016.
 */
public class Login extends FragmentActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    GoogleSignInOptions gso;
    TextView tvFbLogin, tvGoogleLogin, tvLinkedInLogin;
    public static final String PACKAGE = "com.bavaria.group";
    public static final String LINKEDIN_CONSUMER_KEY = "75wqzglcc6u78m"; // your KEY
    public static final String LINKEDIN_CONSUMER_SECRET = "pojG0awu89mJj9r5"; // your SECRET

    LoginButton loginButton;
    private CallbackManager callbackManager;
    Button submit, register;
    EditText userNameEdt, passwordEdt;
    String email, password;
    ImageView backImage;
    TextView tvLogout, tvUname, tvUemail;
    LinearLayout llLogout, llLogin, llBottom;
    private ProgressDialog progressDialog;
    String fbId, fbEmail, fbFname, fbLname;
    // google login
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 100;

    // linkedin login
    private static final String host = "api.linkedin.com";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        generateHashkey();
        final Activity thisActivity = this;
        init();
// Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        if (Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN).equalsIgnoreCase("1")) {
            llLogin.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
            llLogout.setVisibility(View.VISIBLE);
            tvLogout.setVisibility(View.VISIBLE);
            tvUname.setText(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME));
            tvUemail.setText(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_EMAIL));
        } else {
            llLogin.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            llLogout.setVisibility(View.GONE);
            tvLogout.setVisibility(View.GONE);

        }


        // FB LOGIN CODE START HERE
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                social = 1;
                Toast.makeText(Login.this, "SUCESS FB", Toast.LENGTH_SHORT).show();
//                Log.e("onSuccess", "onSuccess");
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Loading");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
//                Log.e("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        Log.e("LoginActivity", response.toString());
                        // Get facebook data from login
                        try {
                            Bundle bFacebookData = getFacebookData(object);

                            new getSignUp().execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
//                Log.e("RESULT", "Login attempt canceled.");
                Toast.makeText(Login.this, "Login attempt canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
//                Log.e("RESULT", "Login attempt failed." + e.toString());
                Toast.makeText(Login.this, "Login attempt failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void generateHashkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

//                ((TextView) findViewById(R.id.package_name)).setText(info.packageName);
                Log.e("HASHKEY", "" + Base64.encodeToString(md.digest(),
                        Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("EXCEPTION", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("EXCEPTION", e.getMessage(), e);
        }
    }

    public void init() {
        submit = (Button) findViewById(R.id.activity_login_submit);
        register = (Button) findViewById(R.id.activity_login_register);
        backImage = (ImageView) findViewById(R.id.activity_login_back);
        userNameEdt = (EditText) findViewById(R.id.activity_login_username);
        passwordEdt = (EditText) findViewById(R.id.activity_login_password);
        tvLogout = (TextView) findViewById(R.id.tvLogout_LoginActivity);
        llLogin = (LinearLayout) findViewById(R.id.llLogin_LoginActivity);
        llLogout = (LinearLayout) findViewById(R.id.llLogout_MainActivity);
        tvUname = (TextView) findViewById(R.id.tvUname_MainActivity);
        tvUemail = (TextView) findViewById(R.id.tvEmail_MainActivity);
        llBottom = (LinearLayout) findViewById(R.id.llBottom_LoginActivity);

        tvFbLogin = (TextView) findViewById(R.id.tvFB_LoginActivity);
        tvGoogleLogin = (TextView) findViewById(R.id.tvGoogle_LoginActivity);
        tvLinkedInLogin = (TextView) findViewById(R.id.tvLinkedin_LoginActivity);
        // FB LOGIN CODE
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");

        // google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton) findViewById(R.id.SignInGoogle);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        backImage.setOnClickListener(this);
        submit.setOnClickListener(this);
        register.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvFbLogin.setOnClickListener(this);
        tvLinkedInLogin.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        tvGoogleLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent in = null;
        switch (v.getId()) {
            case R.id.activity_login_submit:
                email = userNameEdt.getText().toString();
                password = passwordEdt.getText().toString();

                if (Utils.isOnline(getApplicationContext())) {
                    if (email.length() > 3 && password.length() > 2) {
                        new getLogin().execute();
                    } else {
                        Toast.makeText(Login.this, "Please Insert Valid Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "No internet connection found, Please check the internet connectivity", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.activity_login_register:
                in = new Intent(Login.this, Register.class);
                startActivity(in);
                break;

            case R.id.activity_login_back:
                in = new Intent(Login.this, MainActivity.class);
                startActivity(in);
                break;

            case R.id.tvLogout_LoginActivity:
                showLogOutDialog(Login.this, "Logout", "logout from the app?");
                break;

            case R.id.tvFB_LoginActivity:
                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_SOCIAL_LOGIN, "1");
                loginButton.performClick();
                break;

            case R.id.tvGoogle_LoginActivity:
//                signInButton.performClick();
//                Toast.makeText(Login.this, "G CLick", Toast.LENGTH_SHORT).show();
                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_SOCIAL_LOGIN, "2");
                signIn();
                break;

            case R.id.tvLinkedin_LoginActivity:
                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_SOCIAL_LOGIN, "3");
                login_linkedin();
                break;


            case R.id.SignInGoogle:
//                signIn();
                break;
        }
    }


    public void showLogOutDialog(Context ctx, String title, String message) {

        try {
            final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN, "0");
                            Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME, "");
                            Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN, "0");
                            Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_EMAIL, "");
                            llLogin.setVisibility(View.VISIBLE);
                            llLogout.setVisibility(View.GONE);
                            tvLogout.setVisibility(View.GONE);
                            llBottom.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();

                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
            alertDialog.show();

        } catch (Exception e) {
            Log.e("Exception", "" + e);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public class getLogin extends AsyncTask<String, String, String> {
        Dialog dialog;
        String name, emailId, id, phone;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(Login.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("email", email);
            data.put("password", password);
//            Log.e("URL", "" + Constant.BASE_URL + "login11.php?" + data);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "login11.php?", data);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.e("Response", "" + s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
                    JSONArray arry = object.getJSONArray("data");
//                    Log.e("arry", "" + arry);
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
//                        Log.e("???????", "" + obj.getString("name"));
//                        Log.e("???????", "" + obj.getString("email"));
                        name = obj.getString("name");
                        emailId = obj.getString("email");
                        id = obj.getString("id");

                    }
                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME, name);
                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_EMAIL, emailId);
                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_ID, id);
//                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_PHONE, phone);
//                    sp.edit().putString(Constant.UserPhNO, object.getString("Phone")).commit();
                    Toast.makeText(Login.this, "" + object.getString("error_msg"), Toast.LENGTH_SHORT).show();
                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN, "1");
                    tvUname.setText(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME));
                    tvUemail.setText(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_EMAIL));
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                } else {
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private Bundle getFacebookData(JSONObject object) throws JSONException {

        Bundle bundle = new Bundle();
        String id = object.getString("id");

        try {
            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
//            Log.e("profile_pic", profile_pic + "");
            bundle.putString("profile_pic", profile_pic.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        bundle.putString("idFacebook", id);
//        Log.e("idFacebook", "" + object.getString("id"));
//        Log.e("idFacebook", "" + object.getString("email"));
        fbEmail = object.getString("email");
        fbId = object.getString("id");
        fbFname = object.getString("first_name");
        if (object.has("first_name"))
            bundle.putString("first_name", object.getString("first_name"));
        if (object.has("last_name"))
            bundle.putString("last_name", object.getString("last_name"));
        if (object.has("email"))
            bundle.putString("email", object.getString("email"));
        if (object.has("gender"))
            bundle.putString("gender", object.getString("gender"));
        if (object.has("birthday"))
            bundle.putString("birthday", object.getString("birthday"));
        if (object.has("location"))
            bundle.putString("location", object.getJSONObject("location").getString("name"));

        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String socialStatus = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_SOCIAL_LOGIN);
        switch (socialStatus) {
            case "1":
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;

            case "2":
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                }
                break;

            case "3":
                LISessionManager.getInstance(Login.this).onActivityResult(this,
                        requestCode, resultCode, data);
                break;
        }


//        twitLogin.onActivityResult(requestCode, resultCode, data);
//        Log.e("requestCode", "" + requestCode);
//        Log.e("resultCode", "" + resultCode);
//        Log.e("data", "" + data);
    }


    public class getSignUp extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(Login.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("name", fbFname);
//            data.put("username",username);
            data.put("password", "");
            data.put("email", fbEmail);
            Log.e("URL", "" + Constant.BASE_URL + "registration1.php?" + data);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "registration1.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE", "" + s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
                    Toast.makeText(Login.this, "Sucessfully Login", Toast.LENGTH_SHORT).show();
                    saveDataAndGoMainScreen();
                } else {
                    Toast.makeText(Login.this, "Sucessfully Login", Toast.LENGTH_SHORT).show();
                    saveDataAndGoMainScreen();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Login.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDataAndGoMainScreen() {
        Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN, "1");
        Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME, fbFname);
        Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_EMAIL, fbEmail);
        llLogin.setVisibility(View.GONE);
        llBottom.setVisibility(View.GONE);
        llLogout.setVisibility(View.VISIBLE);
        tvLogout.setVisibility(View.VISIBLE);
        Intent in = new Intent(Login.this, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();
    }


    /// LINKED IN LOGIN START
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    public void login_linkedin() {
        LISessionManager.getInstance(Login.this).init(this,
                buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        Toast.makeText(Login.this, "sucess", Toast.LENGTH_SHORT).show();
                        getUserData();

//                        Toast.makeText(getApplicationContext(), "success" +
//                                LISessionManager.getInstance(getApplicationContext().getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {

                        Toast.makeText(getApplicationContext(), "Timeout error Please try again",
                                Toast.LENGTH_LONG).show();
                    }
                }, true);
    }

    public void getUserData() {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(Login.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {
                    JSONObject response = result.getResponseDataAsJson();
//                    Log.e("LINKEDIN", "" + response.get("emailAddress").toString());
//                    Log.e("LINKEDIN", "" + response.get("formattedName").toString());
//                    Log.e("LINKEDIN", "" + response.get("pictureUrl").toString());
                    fbEmail = response.get("emailAddress").toString();
                    fbFname = response.get("formattedName").toString();
                    if (Utils.isOnline(getApplicationContext())) {
                        new getSignUp().execute();
                    } else {
                        Toast.makeText(Login.this, "No internet connection found, Please check your internet connectivity.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError error) {
                // ((TextView) findViewById(R.id.error)).setText(error.toString());
                Toast.makeText(Login.this, "Error found in login, Please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }


    // google login code
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        Toast.makeText(Login.this, "G Call", Toast.LENGTH_SHORT).show();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(Login.this, "" + acct.getDisplayName() + " " + acct.getEmail(), Toast.LENGTH_SHORT).show();
            fbEmail = acct.getEmail();
            Log.e("*******", "***************");
            Log.e("EMAIL", "" + fbEmail);
            fbFname = acct.getDisplayName();
            Log.e("NAME", "" + fbFname);
            new getSignUp().execute();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }
}


