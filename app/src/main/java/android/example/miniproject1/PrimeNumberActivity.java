package android.example.miniproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import static java.lang.Math.*;
import static java.math.BigInteger.valueOf;

import java.util.Random;

public class PrimeNumberActivity extends AppCompatActivity {

    TextView pulledPrime;
    Button pullPrimeButton;
    EditText editPrime;
    Button submitPrime;

    FirebaseDatabase database;
    DatabaseReference reference;

    Random rand = new Random();

    int numberCounter = 0;

    long primeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_number);

        Intent intent = getIntent();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        pulledPrime = findViewById(R.id.pulledPrime);
        pullPrimeButton = findViewById(R.id.pullPrimeButton);
        editPrime = findViewById(R.id.editPrime);
        submitPrime = findViewById(R.id.submitPrime);
    }

    public void Pull(View view) {

        final int randNum = rand.nextInt(1000000);

        DatabaseReference primeNum = reference.child("/"+randNum);

        primeNum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                if(data.exists()) {
                    Long Prime = data.getValue(Long.TYPE);
                    if(Prime > 0)
                    {
                        primeNumber = Prime;
                        pulledPrime.setText(String.valueOf(primeNumber));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Prime didn't load", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Prime " + randNum + " doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
                Log.d("primeNumberActivity", "onCancelled: Didn't load the prime");
            }
        });
    }

    public void submit(View view) {
        Long userPrime = Long.parseLong(editPrime.getText().toString());

        int tempCounter = 1000000 + numberCounter;

        DatabaseReference primeToDB = reference.child("/"+tempCounter);

        BigInteger bigInt = valueOf(userPrime);
        int isPrime = 1;

        if(bigInt.isProbablePrime(isPrime))
        {
            if(bigInt.intValue() > 15485863) //15485863 is the 1000000th prime number
            {
                Toast.makeText(getApplicationContext(), bigInt + " is prime! Pushing the prime.", Toast.LENGTH_SHORT).show();
                primeToDB.setValue(userPrime);
                numberCounter++;
            }
            else
            {
                Toast.makeText(getApplicationContext(), bigInt + " is prime... but the database already has that one.", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), bigInt+" is NOT prime...", Toast.LENGTH_SHORT).show();
        }
    }

    public void backclick(View view) {
        Intent sendBack = new Intent();
        sendBack.putExtra("Correct", true);

        setResult(RESULT_OK, sendBack);
        finish();
    }
}
