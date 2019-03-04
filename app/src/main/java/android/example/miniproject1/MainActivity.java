package android.example.miniproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String[] questionTopics = {"Fast Power", "BabyStep GiantStep", "Brute Force"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, questionTopics);

        ListView topicsListView = (ListView) findViewById(R.id.questionTopics);
        topicsListView.setAdapter(listAdapter);

        final Intent intent = new Intent(this, CalculationActivity.class);

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
}
