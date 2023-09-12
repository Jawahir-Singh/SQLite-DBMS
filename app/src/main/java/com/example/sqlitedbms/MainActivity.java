package com.example.sqlitedbms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private DBHandler dbHandler;
    CustomAdapter adapter;
    List<Model> models;
    ArrayList<String> employeeNames;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(MainActivity.this);
        Log.d("Insert: ", "Inserting ..----2");
        dbHandler = new DBHandler(this);

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        models = dbHandler.getAllDetails();
        employeeNames = new ArrayList<>();

        for (Model cn : models) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                    cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
            employeeNames.add(log);
        }

        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(this, models);
        recyclerView.setAdapter(adapter);

//        // below line is to add on click listener for our add course button.
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // below line is to get data from all edit text fields.
//                String name = personName.getText().toString();
//                String number = mNumber.getText().toString();
//
//
//                // validating if the text fields are empty or not.
//                if (name.isEmpty() && number.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // on below line we are calling a method to add new
//                // course to sqlite data and pass all our values to it.
//                dbHandler.addNewCourse(name, number);
//
//                // after adding the data we are displaying a toast message.
//                Toast.makeText(MainActivity.this, "Details has been added.", Toast.LENGTH_SHORT).show();
//                personName.setText("");
//                mNumber.setText("");
//               ;
//            }
//        });
//
    }
    public void Save_no(View view){

        EditText name =(EditText)findViewById(R.id.EditText1);
        EditText phone =(EditText)findViewById(R.id.EditText2);

        DBHandler dbHandler = new DBHandler(this);
        if(name.getText().toString().trim().equals("")) {
            // Inserting Contacts
            Log.d("Enter Name", "---------");
            Toast.makeText(getApplication(), "Enter Name", Toast.LENGTH_SHORT).show();

        }else {
            if (phone.getText().toString().trim().equals("")) {
                // Inserting Contacts
                Log.d("Enter Phone Number", "---------");
                Toast.makeText(getApplication(), "Enter phone  Number", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(name.getText().toString().trim(), phone.getText().toString().trim());
                dbHandler.addNewEmployee(new Model(name.getText().toString().trim(), phone.getText().toString().trim()));

                models.clear();
                employeeNames.clear();
                models = dbHandler.getAllDetails();
                employeeNames = new ArrayList<>();

                for (Model cn : models) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                            cn.getPhoneNumber();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    employeeNames.add(log);
                }


                adapter = new CustomAdapter(this, models);
                recyclerView.setAdapter(adapter);
                name.setText("");
                phone.setText("");

            }
        }

    }
    public void edit_pop(final Context context, final String id, String name2, String phone2){
        LayoutInflater factory = LayoutInflater.from(this);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.recyclerview_update);

        TextView text = (TextView) dialog.findViewById(R.id.tit);
        text.setText("Update");

        final Button cancel_Button = (Button) dialog.findViewById(R.id.cancel);
        FloatingActionButton update = (FloatingActionButton) dialog.findViewById(R.id.update);

        final EditText name1 = (EditText) dialog.findViewById(R.id.name);
        final EditText phone1 = (EditText) dialog.findViewById(R.id.phone);

        name1.setText(name2);
        phone1.setText(phone2);

        final DBHandler db = new DBHandler(this);


        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Perfome Action
                if(name1.getText().toString().trim().equals("")) {
                    // Inserting Contacts
                    Log.d("Enter Name", "---------");
                    Toast.makeText(getApplication(), "Enter Name", Toast.LENGTH_SHORT).show();

                }else {
                    if (phone1.getText().toString().trim().equals("")) {
                        // Inserting Contacts
                        Log.d("Enter Phone Number", "---------");
                        Toast.makeText(getApplication(), "Enter phone number", Toast.LENGTH_SHORT).show();
                    } else {

                        int check = db.updateDetails(id, name1.getText().toString().trim(),phone1.getText().toString().trim());

                        if(check>0){

                            dialog.dismiss();

// Reload..................................................
                            models.clear();
                            employeeNames.clear();
                            models = db.getAllDetails();
                            employeeNames = new ArrayList<>();
                            for (Model cn : models) {
                                String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                                        cn.getPhoneNumber();
                                // Writing Contacts to log
                                Log.d("Name: ", log);
                                employeeNames.add(log);
                            }
                            adapter = new CustomAdapter(context, models);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                }


            }
        });
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void Delete_pop(final String id, String name2, String phone2){

        final DBHandler db = new DBHandler(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm ");
        builder.setMessage("Are you sure you want to delete");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db.deleteDetails(id);


// Reload..................................................
                models.clear();
                employeeNames.clear();
                models = db.getAllDetails();
                employeeNames = new ArrayList<>();
                for (Model cn : models) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                            cn.getPhoneNumber();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    employeeNames.add(log);
                }
                adapter = new CustomAdapter(MainActivity.this, models);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

}