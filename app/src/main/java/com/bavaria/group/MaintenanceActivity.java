package com.bavaria.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
 * Created by archirayan1 on 3/1/2016.
 */
public class MaintenanceActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner spProjectSelect, spBuildingSelect, spFlatSelect, spFloorSelect;
    String ProjectSelect, BuildingSelect, FloorSelect, FlatSelect;
    SpinnerAdapter adapter_project, adapter_building, adapter_floor, adapter_flat;
    private ImageView ivBack;
    private EditText etName, etEmail, etDescription, etPhone;
    private String name, email, phone, description;
    private Button btSubmit;
    ArrayList<String> projectList, builingList, floorList, flatList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        inIt();
        setStartupData();

        if (Utils.isOnline(getApplicationContext())) {
            new getProject().execute();
        } else {
            Toast.makeText(MaintenanceActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

//        adapter_project = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Project));
//        spProjectSelect.setPrompt("Make your selection... ");
//        spProjectSelect.setAdapter(adapter_project);
//
//        adapter_building = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Building));
//        spBuildingSelect.setPrompt("Make your selection... ");
//        spBuildingSelect.setAdapter(adapter_building);
//
//        adapter_floor = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Floor));
//        spFloorSelect.setPrompt("Make your selection... ");
//        spFloorSelect.setAdapter(adapter_floor);
//
//        adapter_flat = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Flat));
//        spFlatSelect.setPrompt("Make your selection... ");
//        spFlatSelect.setAdapter(adapter_flat);
    }

    private void setStartupData() {
        builingList = new ArrayList<String>();
        builingList.clear();
        builingList.add("Make your selection...");
        adapter_building = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, builingList);
        spBuildingSelect.setAdapter(adapter_building);
        adapter_building.notifyDataSetChanged();

        floorList = new ArrayList<String>();
        floorList.clear();
        floorList.add("Make your selection...");
        adapter_floor = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, floorList);
        spFloorSelect.setAdapter(adapter_floor);
        adapter_floor.notifyDataSetChanged();


        flatList = new ArrayList<String>();
        flatList.clear();
        flatList.add("Make your selection...");
        adapter_flat = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, flatList);
        spFlatSelect.setAdapter(adapter_flat);
        adapter_flat.notifyDataSetChanged();

    }

    private void inIt() {
        spProjectSelect = (Spinner) findViewById(R.id.spSelectProject_Maintenance);
        spBuildingSelect = (Spinner) findViewById(R.id.spSelectBuilding_Maintenance);
        spFloorSelect = (Spinner) findViewById(R.id.spSelectFloor_Maintenance);
        spFlatSelect = (Spinner) findViewById(R.id.spSelectFlat_Maintenance);

        etName = (EditText) findViewById(R.id.etName_Maintainance);
        etEmail = (EditText) findViewById(R.id.etEmail_Maintainance);
        etPhone = (EditText) findViewById(R.id.etPhone_Maintainance);
        etDescription = (EditText) findViewById(R.id.etDescription_Maintainance);

        btSubmit = (Button) findViewById(R.id.btSubmit_Maintainance);
        ivBack = (ImageView) findViewById(R.id.ivBack_Maintenance);

        spProjectSelect.setOnItemSelectedListener(this);
        spBuildingSelect.setOnItemSelectedListener(this);
        spFloorSelect.setOnItemSelectedListener(this);
        spFlatSelect.setOnItemSelectedListener(this);
        btSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spSelectProject_Maintenance:

                if (Utils.isOnline(getApplicationContext())) {
                    if (position != 0) {
                        ProjectSelect = parent.getSelectedItem().toString();
                        new getBuilding().execute();
                    } else {
                        Toast.makeText(MaintenanceActivity.this, "Please select Project from the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MaintenanceActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.spSelectBuilding_Maintenance:
                if (Utils.isOnline(getApplicationContext())) {
                    if (position != 0) {
                        BuildingSelect = parent.getSelectedItem().toString();
                        new getBlockList().execute();
                    } else {
                        Toast.makeText(MaintenanceActivity.this, "Please select Building from the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MaintenanceActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(MaintenanceActivity.this, "" + BuildingSelect, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spSelectFloor_Maintenance:

                if (Utils.isOnline(getApplicationContext())) {
                    if (position != 0) {
                        FloorSelect = parent.getSelectedItem().toString();
                        new getFlatList().execute();
                    } else {
                        Toast.makeText(MaintenanceActivity.this, "Please select Block from the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MaintenanceActivity.this, "No iternet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(MaintenanceActivity.this, "" + FloorSelect, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spSelectFlat_Maintenance:
                if (position != 0) {
                    FlatSelect = parent.getSelectedItem().toString();
                } else {
                    Toast.makeText(MaintenanceActivity.this, "Please select Block from the list", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(MaintenanceActivity.this, "" + FlatSelect, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubmit_Maintainance:
                if (Utils.isOnline(getApplicationContext())) {
                    if (!etName.getText().toString().equalsIgnoreCase("")) {
                        if (!etPhone.getText().toString().equalsIgnoreCase("")) {
                            if (!etEmail.getText().toString().equalsIgnoreCase("")) {
                                if (!etDescription.getText().toString().equalsIgnoreCase("")) {
                                    name = etName.getText().toString();
                                    phone = etPhone.getText().toString();
                                    email = etEmail.getText().toString();
                                    description = etDescription.getText().toString();
                                    new submitData().execute();
                                } else {
                                    Toast.makeText(MaintenanceActivity.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MaintenanceActivity.this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MaintenanceActivity.this, "Please Enter phone number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MaintenanceActivity.this, "Please Enter name", Toast.LENGTH_SHORT).show();
                    }
                    new submitData().execute();
                } else {
                    Toast.makeText(MaintenanceActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.ivBack_Maintenance:
                Intent intent = new Intent(MaintenanceActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
                break;

        }

    }

    private class getProject extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MaintenanceActivity.this);
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
            adapter_project = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, projectList);
            spProjectSelect.setAdapter(adapter_project);

        }
    }


    private class getBuilding extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MaintenanceActivity.this);
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
            adapter_building = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, builingList);

            spBuildingSelect.setAdapter(adapter_building);

        }
    }

    private class getBlockList extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MaintenanceActivity.this);
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
            adapter_floor = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, floorList);

            spFloorSelect.setAdapter(adapter_floor);

        }
    }


    private class getFlatList extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MaintenanceActivity.this);
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
            adapter_flat = new SpinnerAdapter(MaintenanceActivity.this, android.R.layout.simple_spinner_item, flatList);
            spFlatSelect.setAdapter(adapter_flat);

        }
    }

//    https://www.bavariagroup.net/api/maintenance2.php?
// project=aliya&building=Building%20A&floor=1&flat=A&name=archi&
// phone=90075698&email=archirayan18@gmail.com&description=test123

    private class submitData extends AsyncTask<String, String, String> {
        Dialog dialog;
        String response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MaintenanceActivity.this);
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
            data.put("project", ProjectSelect);
            data.put("building", BuildingSelect);
            data.put("floor", FloorSelect);
            data.put("flat", FlatSelect);
            data.put("name", name);
            data.put("phone", phone);
            data.put("email", email);
            data.put("description", description);
            Log.e("project", "" + spProjectSelect);
            Log.e("BuildingSelect", "" + BuildingSelect);
            Log.e("FloorSelect", "" + FloorSelect);
            Log.e("FlatSelect", "" + FlatSelect);
            Log.e("name", "" + name);
            Log.e("phone", "" + phone);
            Log.e("email", "" + email);
            Log.e("descr", "" + description);

//            https://www.bavariagroup.net/api/get_floor_flat.php?floor_name=1
            try {
                response = new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "maint1.php?", data);

            } catch (Exception ex) {
                Log.e("Exception", "" + ex);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("RESPONSE", ">>>>>>>" + s);
            dialog.dismiss();
            String msg = "Record Successfully Saved.";
            try {

            } catch (Exception e) {
                // WindowManager$BadTokenException will be caught and the app would not display
                // the 'Force Close' message
            }
//            projectList.clear();
//            adapter_project.notifyDataSetChanged();
            builingList.clear();
            adapter_building.notifyDataSetChanged();
            floorList.clear();
            adapter_floor.notifyDataSetChanged();
            flatList.clear();
            adapter_flat.notifyDataSetChanged();
            etName.setText("");
            etEmail.setText("");
            etPhone.setText("");
            etDescription.setText("");
            setStartupData();
            promptForUpgrade(MaintenanceActivity.this, msg);


        }
    }


    private void promptForUpgrade(Context context, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert")
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
