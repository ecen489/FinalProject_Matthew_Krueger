package android.example.miniproject1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] questionTopics = {"Fast Power", "BabyStep GiantStep", "Brute Force", "Prime Numbers", "Take a Picture", "Special Service"};

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth authorize;
    FirebaseUser user = null;

    Button loginButton;
    Button newButton;
    Button gobutton;
    EditText passwordView;
    EditText usernameView;

    ArrayAdapter<String> listAdapter;
    ListView topicsListView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, questionTopics);
        topicsListView = (ListView) findViewById(R.id.questionTopics);
        //topicsListView.setAdapter(listAdapter);

        arrayList = new ArrayList<String>();

        arrayList.add("Fast Power");
        arrayList.add("BabyStep GiantStep");
        arrayList.add("Brute Force");
        arrayList.add("Take a Picture");
        arrayList.add("Special Service");

        listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrayList);
        topicsListView.setAdapter(listAdapter);

        final Intent intent = new Intent(this, CalculationActivity.class);
        final Intent intent2 = new Intent(this, PrimeNumberActivity.class);
        final Intent intent3 = new Intent(this, cameraActivity.class);
        final Intent intent4 = new Intent(this, SpecialService.class);

        FirebaseApp.initializeApp(this);
        authorize = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login);
        newButton = findViewById(R.id.create);
        passwordView = findViewById(R.id.password);
        usernameView = findViewById(R.id.username);

        topicsListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedTopic = String.valueOf(parent.getItemAtPosition(position));
                    switch(selectedTopic) {
                        case "Fast Power":
                            intent.putExtra("Topic", questionTopics[0]);
                            //Toast.makeText(MainActivity.this, questionTopics[0], Toast.LENGTH_LONG).show();
                            startActivityForResult(intent, 1);
                            break;
                        case "BabyStep GiantStep":
                            intent.putExtra("Topic", questionTopics[1]);
                            startActivityForResult(intent, 1);
                            break;
                        case "Brute Force":
                            intent.putExtra("Topic", questionTopics[2]);
                            startActivityForResult(intent, 1);
                            break;
                        case "Prime Numbers":
                            intent2.putExtra("Topics", questionTopics[3]);
                            startActivityForResult(intent2, 1);
                            break;
                        case "Take a Picture":
                            intent3.putExtra("Topics", questionTopics[4]);
                            startActivityForResult(intent3, 1);
                            break;
                        case "Special Service":
                            startService(intent4);
                            break;
                        default:
                            break;
                    }
                }
            }
        );
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1)
        {
            boolean correct = intent.getBooleanExtra("Correct", true);
            if (correct) {
                //Toast.makeText(this, "Correct! +1", Toast.LENGTH_SHORT).show();
                //readWrite.writeToFile(String.valueOf(readWrite.returnScore()));
            } else {
                //Toast.makeText(this, "Incorrect... -999", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createClick(View view){

        String password = passwordView.getText().toString();
        String username = usernameView.getText().toString();

        authorize.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Created Firebase account", Toast.LENGTH_SHORT).show();
                            user = authorize.getCurrentUser();

                            arrayList.add("Prime Numbers");
                            listAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Couldn't make a new account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void loginClick (View view){

        String password = passwordView.getText().toString();
        String username = usernameView.getText().toString();

        authorize.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Logged in to Firebase", Toast.LENGTH_SHORT).show();
                            user = authorize.getCurrentUser();

                            arrayList.add("Prime Numbers");
                            listAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Couldn't login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
