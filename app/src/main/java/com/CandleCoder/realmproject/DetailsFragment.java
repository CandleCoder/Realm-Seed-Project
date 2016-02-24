package com.CandleCoder.realmproject;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CandleCoder.RealmDomain.StudentData;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by manikant.upadhyay on 2/23/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailsFragment extends Fragment implements View.OnClickListener{

    private EditText userName;
    private EditText userAge;
    private EditText userEmail;
    /*private Spinner Sex;
    private Button Save;
    private Button ShowDetails;*/
    private Button saveButton;
    private Button showDetailsButton;
    Realm myRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        this.myRealm = Realm.getInstance(getActivity());

        /*Realm myOtherRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(getActivity())
                                .name("myOtherRealm.realm")
                                .build()
                );*/

        userName = (EditText) view.findViewById(R.id.Name);
        userAge = (EditText) view.findViewById(R.id.Age);
        userEmail = (EditText) view.findViewById(R.id.email);

        userName.addTextChangedListener(textWatcher);
        userAge.addTextChangedListener(textWatcher);
        userEmail.addTextChangedListener(textWatcher);
      //  Sex = (Spinner) getActivity().findViewById(R.id.sexSelection);
        saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener(this);

        showDetailsButton = (Button) view.findViewById(R.id.showDetailsButton);
        showDetailsButton.setOnClickListener(this);

        //onShowDetailsButtonClick();

        Button deleteAllRecords = (Button) view.findViewById(R.id.deleteAllRecords);
        deleteAllRecords.setOnClickListener(this);

        return view;
    }

    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.saveButton:
              onSaveButtonClick();
              break;


          case R.id.showDetailsButton:

              onShowDetailsButtonClick();
              break;

          case R.id.deleteAllRecords:
              deleteAllEntries();
              break;
      }

    }


    private void onShowDetailsButtonClick(){
        RealmResults<StudentData> data =
                myRealm.where(StudentData.class).findAll();
      if(data != null) {
          for (StudentData c : data) {
              Log.d("Name", c.getName());
              Log.d("Email", c.getEmail());
              Log.d("Age", String.valueOf(c.getAge()));


              Toast.makeText(getActivity(), "Details has been shown in Console",
                      Toast.LENGTH_SHORT).show();
          }
      }
          else{
           Log.d("No records","@@");
              Toast.makeText(getActivity(), "No records found",
                      Toast.LENGTH_SHORT).show();
          }
    }

    private void onSaveButtonClick(){
        System.out.println(" saveButton clicked ");

            try {
                myRealm.beginTransaction();
                StudentData studentData = myRealm.createObject(StudentData.class);

                // Set its fields

                String uname = userName.getText().toString();
                String email = userEmail.getText().toString();

                studentData.setName(uname);
                studentData.setEmail(email);
                studentData.setAge(Integer.parseInt(userAge.getText().toString()));
                saveButton.setEnabled(true);
                Toast.makeText(getActivity(), "Data has been saved",
                        Toast.LENGTH_SHORT).show();
            } catch (RealmPrimaryKeyConstraintException e) {
                Toast.makeText(getActivity(), "Entered Email Id already exists.Try again!",
                        Toast.LENGTH_SHORT).show();
                System.out.println(" Duplicate entry " + e.getLocalizedMessage());
            }

        userName.setText("");
        userEmail.setText("");
        userAge.setText("");
        Toast.makeText(getActivity(), "Enter the data in all fields",
                Toast.LENGTH_SHORT).show();
        myRealm.commitTransaction();
}



    private void deleteAllEntries(){

        myRealm.beginTransaction();
        myRealm.clear(StudentData.class);
        myRealm.commitTransaction();
        Toast.makeText(getActivity(), "All records Deleted",
                Toast.LENGTH_SHORT).show();
    }

    private  void checkFieldsForEmptyValues(){
        String userName = this.userName.getText().toString();
        String userAge = this.userAge.getText().toString();
        String userEmail = this.userEmail.getText().toString();

        if(userName.equals("") && userAge.equals("") && userEmail.equals(""))
        {
            saveButton.setEnabled(false);
        }

        else if(!userName.equals("")&& userAge.equals("")&& userEmail.equals("")){
            saveButton.setEnabled(false);
        }

        else if(!userAge.equals("")&& userName.equals("")&& userEmail.equals(""))
        {
            saveButton.setEnabled(false);
        }

        else if (!userEmail.equals("")&& userName.equals("")&&userAge.equals(""))
        {
            saveButton.setEnabled(false);
        }

        else if (!userAge.equals("") && !userEmail.equals("") && userName.equals("")){
            saveButton.setEnabled(false);
        }

        else if (!userAge.equals("") && !userName.equals("") && userEmail.equals("")){
            saveButton.setEnabled(false);
        }

        else if (!userName.equals("") && !userEmail.equals("") && userAge.equals("")){
            saveButton.setEnabled(false);
        }

        else
        {
            saveButton.setEnabled(true);
        }
    }
}
