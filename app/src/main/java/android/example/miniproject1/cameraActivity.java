package android.example.miniproject1;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static java.math.BigInteger.valueOf;

public class cameraActivity extends AppCompatActivity {

    private Camera mCamera;
    private cameraPreview mPreview;
    Bitmap bitmap;
    String pathToFile;
    android.widget.ImageView ImageView;
    Button TakePic;
    FrameLayout preview;
    Button submit;
    Button back;
    TextView instructions;
    RecyclerView recycler;
    private ArrayList<String> mData = new ArrayList<>();
    private int currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    int pictureNum = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCamera = safeCameraOpen();

        mPreview = new cameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.cameraArea);
        ImageView = findViewById(R.id.imageView);
        TakePic = findViewById(R.id.takePic);
        submit = findViewById(R.id.submit);
        instructions = findViewById(R.id.Instructions);
        back = findViewById(R.id.back);

        preview.addView(mPreview);

        TakePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCamera.takePicture(null, null, mPicture);
                setNewView();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTextRecognition();
            }
        });
    }

    public void setNewView(){
        preview.setVisibility(View.INVISIBLE);
        ImageView.setVisibility(View.VISIBLE);
        TakePic.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.VISIBLE);
        instructions.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
    }

    public void setOldView(){
        preview.setVisibility(View.VISIBLE);
        ImageView.setVisibility(View.INVISIBLE);
        TakePic.setVisibility(View.VISIBLE);
        submit.setVisibility(View.INVISIBLE);
        instructions.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
    }

    public Camera safeCameraOpen(){
        Camera opened = null;
        try{
            opened = Camera.open(currentCameraId);
        }
        catch (Exception e){
            Log.e(TAG,"Failed to open camera");
            e.printStackTrace();
        }
        return opened;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                pathToFile = pictureFile.getAbsolutePath();
                Log.d("        My App", "file path: " + pathToFile);

                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
            bitmap = BitmapFactory.decodeFile(pathToFile);
            ImageView.setImageBitmap(bitmap);

        }
    };

    private File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Path");

        if (Environment.getExternalStorageState() == null) {
            Log.d("MyCameraApp", "failed to create directory");
        }

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile = null;
        try {
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File.createTempFile(timeStamp, ".jpg", mediaStorageDir);
            } else {
                return null;
            }
        }
        catch(IOException e){
            Log.d("couldn't create file", "Excep: " + e.toString());
        }

        return mediaFile;
    }

    public int getCurrentCameraId() {
        return currentCameraId;
    }

    private void runTextRecognition() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        submit.setEnabled(false);
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                submit.setEnabled(true);
                                processTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                submit.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    }
    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
        String newText = texts.getText();

        newText = newText.replaceAll("[^?0-9]+", " ");
        //showToast(newText);
        String[] numbersArray = newText.trim().split("\\W+");
        newText = "";

        for(int i = 0; i < numbersArray.length; i++)
        {
            Long tempLong = Long.parseLong(numbersArray[i]);
            BigInteger bigInt = valueOf(tempLong);
            int isPrime = 1;

            if(bigInt.isProbablePrime(isPrime))
            {
                newText = numbersArray[i]+" is Prime\n" + newText;
            }
            else
            {
                newText = numbersArray[i]+" is not Prime\n" + newText;
            }
        }

        mData.add(newText);
        initRecyclerView();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView");
        recycler = findViewById(R.id.recyclerView);
        recyclerViewAdapter adapter = new recyclerViewAdapter(mData, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void Back(View view) {
        Intent sendBack = new Intent();
        sendBack.putExtra("Correct", true);
        setResult(RESULT_OK, sendBack);
        finish();
    }
}

