package com.manikant.realmproject;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manikant.RealmDomain.StudentData;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by manikant.upadhyay on 2/23/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private EditText userName;
    private EditText userAge;
    private EditText userEmail;
    /*private Spinner Sex;
    private Button Save;
    private Button ShowDetails;*/
    //private Button saveButton;
    //private Button showDetailsButton;
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
      //  Sex = (Spinner) getActivity().findViewById(R.id.sexSelection);
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        Button showDetailsButton = (Button) view.findViewById(R.id.showDetailsButton);
        showDetailsButton.setOnClickListener(this);

        onShowDetailsButtonClick();

        Button deleteAllRecords = (Button) view.findViewById(R.id.deleteAllRecords);
        deleteAllRecords.setOnClickListener(this);

        return view;
    }

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
        RealmResults<StudentData> results1 =
                myRealm.where(StudentData.class).findAll();

        for(StudentData c:results1) {
            Log.d("Name", c.getName());
            Log.d("Email",c.getEmail());
            Log.d("Age", String.valueOf(c.getAge()));
            if(c==null){
               System.out.println("No record found");
                Toast.makeText(getActivity(), "No record found",
                        Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(getActivity(), "List has been shown in Console :)",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onSaveButtonClick(){
        System.out.println(" saveButton clicked ");
        myRealm.beginTransaction();
        try {
            // Create an object
            StudentData studentData = myRealm.createObject(StudentData.class);

            // Set its fields

            String uname = userName.getText().toString();
            String email = userEmail.getText().toString();
            System.out.println(" >>>> email " + email);

            studentData.setName(uname);
            studentData.setEmail(email);
            studentData.setAge(Integer.parseInt(userAge.getText().toString()));
            Toast.makeText(getActivity(), "Data has been saved",
                    Toast.LENGTH_SHORT).show();
        }  catch (RealmPrimaryKeyConstraintException e){
            Toast.makeText(getActivity(), "Entered Email Id already exists.Try again!",
                    Toast.LENGTH_SHORT).show();
            userName.setText("");
            userEmail.setText("");
            userAge.setText("");
            System.out.println(" Duplicate entry "+e.getLocalizedMessage());

        }
        userName.setText("");
        userEmail.setText("");
        userAge.setText("");
        myRealm.commitTransaction();
    }

    private void deleteAllEntries(){

        myRealm.beginTransaction();
        myRealm.clear(StudentData.class);
        Toast.makeText(getActivity(), "All records Deleted",
                Toast.LENGTH_SHORT).show();
        myRealm.commitTransaction();
    }
}
