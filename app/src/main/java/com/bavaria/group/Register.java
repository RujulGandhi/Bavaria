package com.bavaria.group;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by archirayan on 2/24/2016.
 */
public class Register extends Activity implements View.OnClickListener {
    EditText usernameEdt, emailEdt, passwordEdt, phonenumberEdt;
    String username, email, password, phonenumber;
    getSignUp object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameEdt = (EditText) findViewById(R.id.activity_register_username);
        emailEdt = (EditText) findViewById(R.id.activity_register_email);
        passwordEdt = (EditText) findViewById(R.id.activity_register_password);
        phonenumberEdt = (EditText) findViewById(R.id.activity_register_phno);

        submit = (Button) findViewById(R.id.activity_register_submit);
        submit.setOnClickListener(this);

    }

    Button submit;

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.activity_register_submit) {

            username = usernameEdt.getText().toString();
            email = emailEdt.getText().toString();
            password = passwordEdt.getText().toString();
            phonenumber = phonenumberEdt.getText().toString();
            if (Utils.isOnline(getApplicationContext())) {
                if (username.length() > 3 && password.length() > 2) {
                    object = new getSignUp();
                    object.execute();
                } else {
                    Toast.makeText(Register.this, "Please Insert Valid Data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Register.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (object != null) {
            object.cancel(true);
        }
    }

    public class getSignUp extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(Register.this);
            pd.setMessage("Loading");
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("name", username);
//            data.put("username",username);
            data.put("password", password);
            data.put("email", email);

            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "registration1.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
//            Log.e("RESPONSE",""+s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
                    Toast.makeText(Register.this, "Sucessfully Register", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Register.this, object.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Register.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
