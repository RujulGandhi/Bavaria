package com.bavaria.group;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bavaria.group.Adapter.SpinnerAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/2/2016.
 */
public class PayOnlineActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String CLIENT_ID = "AS0FsC3Y17Z8Do7t1q4StGproaHNrl4FrerGD2f7wcFZTTVXPLdZ6Z2mx1hzI7Zs48PeUBGZ2znxpQKj";
    private ImageView ivBack;
    private EditText etName, etPhone, etEmail, etCivilId, etAmountPay;
    SpinnerAdapter adapter_project, adapter_building, adapter_floor, adapter_flat;
    private Spinner spProjectName, spBuildingName, spFloorName, spFlatName;
    String ProjectSelect, BuildingSelect, FloorSelect, FlatSelect;
    private String nameStr, phoneStr, emailStr, civilIdStr, projectNameStr, buildingNameStr, floorNameStr, flatNameStr, amountPayStr;
    private Button btPay;
    ArrayList<String> projectList, builingList, floorList, flatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payonline);
        inIt();
        setStartupData();
        if (Utils.isOnline(getApplicationContext())) {
            new getProject().execute();
        } else {
            Toast.makeText(PayOnlineActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }


    private void setStartupData() {
        builingList = new ArrayList<String>();
        builingList.clear();
        builingList.add("Make your selection...");
        adapter_building = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, builingList);
        spBuildingName.setAdapter(adapter_building);
        adapter_building.notifyDataSetChanged();

        floorList = new ArrayList<String>();
        floorList.clear();
        floorList.add("Make your selection...");
        adapter_floor = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, floorList);
        spFloorName.setAdapter(adapter_floor);
        adapter_floor.notifyDataSetChanged();


        flatList = new ArrayList<String>();
        flatList.clear();
        flatList.add("Make your selection...");
        adapter_flat = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, flatList);
        spFlatName.setAdapter(adapter_flat);
        adapter_flat.notifyDataSetChanged();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void inIt() {
        ivBack = (ImageView) findViewById(R.id.ivBack_PayOnlineActivity);
        btPay = (Button) findViewById(R.id.btPay_Payonline_Actiivity);

        etName = (EditText) findViewById(R.id.etName_PayOnlineActivity);
        etPhone = (EditText) findViewById(R.id.etPhoneNo_PayOnlineActivity);
        etEmail = (EditText) findViewById(R.id.etEmail_PayOnlineActivity);
        etCivilId = (EditText) findViewById(R.id.etCivilId_PayOnlineActivity);
        etAmountPay = (EditText) findViewById(R.id.etAmountPay_PayOnlineActivity);

        spProjectName = (Spinner) findViewById(R.id.spProjectName_PayOnlineActivity);
        spBuildingName = (Spinner) findViewById(R.id.spBuildingName_PayOnlineActivity);
        spFloorName = (Spinner) findViewById(R.id.spFloorName_PayOnlineActivity);
        spFlatName = (Spinner) findViewById(R.id.spFlatName_PayOnlineActivity);


        ivBack.setOnClickListener(this);
        btPay.setOnClickListener(this);
        spProjectName.setOnItemSelectedListener(this);
        spBuildingName.setOnItemSelectedListener(this);
        spFloorName.setOnItemSelectedListener(this);
        spFlatName.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack_PayOnlineActivity:
                Intent intent = new Intent(PayOnlineActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
                break;
            case R.id.btPay_Payonline_Actiivity:
                if (Utils.isOnline(getApplicationContext())) {
                    if (!etName.getText().toString().equalsIgnoreCase("")) {

                        if (!etEmail.getText().toString().equalsIgnoreCase("")) {

                            if (!etCivilId.getText().toString().equalsIgnoreCase("")) {

                                if (!ProjectSelect.equalsIgnoreCase("")) {
                                    projectNameStr = ProjectSelect;
                                    if (!BuildingSelect.equalsIgnoreCase("")) {
                                        buildingNameStr = BuildingSelect;
                                        if (!FloorSelect.equalsIgnoreCase("")) {
                                            floorNameStr = FloorSelect;
                                            if (!FlatSelect.equalsIgnoreCase("")) {
                                                flatNameStr = FlatSelect;
                                                if (!etAmountPay.getText().toString().equalsIgnoreCase("")) {
                                                    nameStr = etName.getText().toString();
                                                    phoneStr = etPhone.getText().toString();
                                                    emailStr = etEmail.getText().toString();
                                                    amountPayStr = etAmountPay.getText().toString();
                                                    civilIdStr = etCivilId.getText().toString();
//                                                                    onBuyPressed(v);
//                                                    new paymentDetailSave().execute();
                                                    makePayment();

                                                } else {
                                                    Toast.makeText(PayOnlineActivity.this, "Please enter Amount To Pay", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(PayOnlineActivity.this, "Please Select Flat Name", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(PayOnlineActivity.this, "Please Select Floor Name", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(PayOnlineActivity.this, "Please Select Building Name", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(PayOnlineActivity.this, "Please Select Project Name", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(PayOnlineActivity.this, "Please Enter Civil ID", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(PayOnlineActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PayOnlineActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(PayOnlineActivity.this, "Plesae check your internet connectivity", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void makePayment() {
//        Log.e("****", "******************************");
//        Log.e("name", "" + nameStr);
//        Log.e("phone", "" + phoneStr);
//        Log.e("email", "" + emailStr);
//        Log.e("civil", "" + civilIdStr);
//        Log.e("project", "" + projectNameStr);
//        Log.e("build", "" + buildingNameStr);
//        Log.e("floor", "" + floorNameStr);
//        Log.e("flat", "" + flatNameStr);
//        Log.e("AMT", "" + amountPayStr);
//        Log.e("****", "******************************");
        String url = Constant.BASE_URL + "working_insert_paymentdetails1.php?knetpayment_name=" + nameStr + "&phone_no=" + phoneStr + "&email=" + emailStr + "&civil_id=" + civilIdStr + "&project_name=" + projectNameStr + "&building_name=" + buildingNameStr + "&floor_name=" + floorNameStr + "&flat_name=" + flatNameStr + "&amount_pay=" + amountPayStr;
        url = url.replaceAll(" ", "%20");
//        Log.e("URL",""+url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spProjectName_PayOnlineActivity:
                if (Utils.isOnline(getApplicationContext())) {
                    if (position != 0) {
                        ProjectSelect = parent.getSelectedItem().toString();
                        new getBuilding().execute();
                    } else {
                        ProjectSelect = "";
//                        Toast.makeText(PayOnlineActivity.this, "Please select Project from the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PayOnlineActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.spBuildingName_PayOnlineActivity:
                if (Utils.isOnline(getApplicationContext())) {
                    if (position != 0) {
                        BuildingSelect = parent.getSelectedItem().toString();
                        Toast.makeText(PayOnlineActivity.this, "SELECTED " + BuildingSelect, Toast.LENGTH_SHORT).show();
                        new getBlockList().execute();
                    } else {
                        BuildingSelect = "";
//                        Toast.makeText(PayOnlineActivity.this, "Please select Building from the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PayOnlineActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(PayOnlineActivity.this, "" + BuildingSelect, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spFloorName_PayOnlineActivity:
                if (Utils.isOnline(getApplicationContext())) {
                    if (position != 0) {
                        FloorSelect = parent.getSelectedItem().toString();
                        new getFlatList().execute();
                    } else {
                        FloorSelect = "";
//                        Toast.makeText(PayOnlineActivity.this, "Please select Block from the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PayOnlineActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(PayOnlineActivity.this, "" + FloorSelect, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spFlatName_PayOnlineActivity:
                if (position != 0) {
                    FlatSelect = parent.getSelectedItem().toString();
                } else {
                    FlatSelect = "";
//                    Toast.makeText(PayOnlineActivity.this, "Please select Block from the list", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(PayOnlineActivity.this, "" + FlatSelect, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public class paymentDetailSave extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(PayOnlineActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            projectList = new ArrayList<String>();
            projectList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
//            https://www.bavariagroup.net/api/working_insert_paymentdetails1.php?
// knetpayment_name=eerf&phone_no=445&email=fgr&civil_id=4345&project_name=Aliya&building_name=Building%20A
// &floor_name=A&flat_name=1&amount_pay=565645
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("knetpayment_name", nameStr);
            data.put("phone_no", phoneStr);
            data.put("email", emailStr);
            data.put("civil_id", civilIdStr);
            data.put("project_name", projectNameStr);
            data.put("building_name", buildingNameStr);
            data.put("floor_name", floorNameStr);
            data.put("flat_name", flatNameStr);
            data.put("amount_pay", amountPayStr);
//            Log.e("****", "******************************");
//            Log.e("name", "" + nameStr);
//            Log.e("phone", "" + phoneStr);
//            Log.e("email", "" + emailStr);
//            Log.e("civil", "" + civilIdStr);
//            Log.e("project", "" + projectNameStr);
//            Log.e("build", "" + buildingNameStr);
//            Log.e("floor", "" + floorNameStr);
//            Log.e("flat", "" + flatNameStr);
//            Log.e("AMT", "" + amountPayStr);
//            Log.e("****", "******************************");
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "working_insert_paymentdetails1.php", data);

        }

        @Override
        protected void onPostExecute(String s) {
//            Log.d("Response$$$$$$$$$$", "" + s);
            dialog.dismiss();
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
//                    Toast.makeText(PayOnlineActivity.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PayOnlineActivity.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(PayOnlineActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private class getProject extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PayOnlineActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            projectList = new ArrayList<String>();
            projectList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "n1_get_category.php");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.e("RESPONSE", s.toString());
            projectList = new ArrayList<>();

            try {
                projectList.add(getResources().getString(R.string.prompt));
                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    projectList.add(object.getString("category_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Log.e("SIZE PROJECT", "" + projectList.size());
            adapter_project = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, projectList);
            spProjectName.setAdapter(adapter_project);

        }
    }


    private class getBuilding extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PayOnlineActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            builingList = new ArrayList<String>();
            builingList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
//            https://www.bavariagroup.net/api/get_building_project.php?project_name=Aliya
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("project_name", ProjectSelect);
//            Log.e(" projectName", ">>>>> " + ProjectSelect);

            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "get_building_project.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.d("BUILDING", s.toString());


            try {
                builingList.add(getResources().getString(R.string.prompt));
                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    builingList.add(object.getString("building_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.e("SIZE BUILDING", "" + builingList.size());
            adapter_building = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, builingList);
            spBuildingName.setAdapter(adapter_building);

        }
    }

    private class getBlockList extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PayOnlineActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            floorList = new ArrayList<String>();
            floorList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
//            https://www.bavariagroup.net/api/get_building_project.php?project_name=Aliya
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("project_name", ProjectSelect);
            data.put("building_name", BuildingSelect);
//            Log.e(" PRO", ">>>>> " + ProjectSelect);
//            Log.e(" BUIL", ">>>>> " + BuildingSelect);
//            https://www.bavariagroup.net/api/get_floor_bid.php?project_name=Aliya&building_name=Building%20A
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "get_floor_bid.php??", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.d("FLOOR", s.toString());


            try {

                floorList.add(getResources().getString(R.string.prompt));

                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
//                    hashMap = new HashMap<String,String>();
//                    hashMap.put("project_name", object.getString("project_name"));
                    floorList.add(object.getString("floor_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.e("SIZE FLOOR", "" + floorList.size());
            adapter_floor = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, floorList);
            spFloorName.setAdapter(adapter_floor);

        }
    }


    private class getFlatList extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PayOnlineActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            flatList = new ArrayList<String>();
            flatList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("floor_name", FloorSelect);
//            Log.e(" FloorSelect", ">>>>> " + FloorSelect);

//            https://www.bavariagroup.net/api/get_floor_flat.php?floor_name=1
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "get_floor_flat.php?", data);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.e("FLAT", s.toString());


            try {
                flatList.add(getResources().getString(R.string.prompt));

                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
//                    hashMap = new HashMap<String,String>();
//                    hashMap.put("project_name", object.getString("project_name"));
                    flatList.add(object.getString("flat_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.e("SIZE BUILDING", "" + flatList.size());
            adapter_flat = new SpinnerAdapter(PayOnlineActivity.this, android.R.layout.simple_spinner_item, flatList);
            spFlatName.setAdapter(adapter_flat);

        }
    }


}
