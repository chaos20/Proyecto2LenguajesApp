package com.example.proyecto2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class NewRecipe extends AppCompatActivity {

    private static final String AWS_KEY = "";
    private static final String AWS_SECRET = "";
    private static final String AWS_BUCKET = "jose-tec-lenguajes";

    private EditText name;
    private EditText type;
    private EditText ingri;
    private EditText step;
    private ArrayList<String> ingridients = new ArrayList<>();
    private String steps;
    private ArrayList<String> imags = new ArrayList<>();
    private Button addIngri;
    private Button addStep;
    private Button addImag;

    Button selectButton;
    ImageView mImage;

    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent i = getIntent();
        token =i.getExtras().getString("token");


        mImage = findViewById(R.id.imageViewtest);

        name = findViewById(R.id.nameAddEdit);
        type = findViewById(R.id.typeAddEdit);

        selectButton = findViewById(R.id.addRecipeFinalbutton);

        ingri = findViewById(R.id.ingriAddEdit);
        addIngri = findViewById(R.id.addButtonIngriTxt);
        addIngri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getIngi = ingri.getText().toString();
                if(ingridients.contains(getIngi)){
                    Toast.makeText(getBaseContext(),"Item already in the list", Toast.LENGTH_LONG).show();
                }
                else{
                    ingridients.add(getIngi);
                    Toast.makeText(getBaseContext(),"Item added to the list", Toast.LENGTH_LONG).show();
                }
            }
        });

        step = findViewById(R.id.stepsAddEdit);
        addStep = findViewById(R.id.addButtonStepsTxt);

        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steps = step.getText().toString();

            }
        });

        addImag = findViewById(R.id.addButtonImage);
        addImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });


        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });


    }
    //guardar la receta usando la api
    public void saveRecipe(){
        String pname = name.getText().toString().replace(" ","_");
        String ptype = type.getText().toString().replace(" ","_");


        //parsear las listas para que no haya problemas con el envio

        try{
            String api = "https://cryptic-mesa-87439.herokuapp.com/";


            URL url = new URL(api +"new-recipe?name='"+ pname +"'&"+"tipo='"+ptype+"'&"+"ingredients='" + turnArrToS(ingridients)+"'&"+"steps='"+steps+"'&"+"images='"+turnArrToS(imags)+"'");
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization",token);
            urlConnection.connect();


            if(urlConnection.getErrorStream() == null){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder b = new StringBuilder();
                String input;
                while((input = br.readLine()) != null){
                    b.append(input);
                }
                Log.d("INput",b.toString());

                br.close();
            }
            else{
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                StringBuilder b = new StringBuilder();
                String input;
                while((input = br.readLine()) != null){
                    b.append(input);
                }

                Log.d("error",b.toString());
            }


            urlConnection.disconnect();
        } catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private void SelectImage() {
        final CharSequence[] options = {"Capture image", "Select from gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NewRecipe.this);
        builder.setTitle("Select");
        builder.setCancelable(true);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Capture image")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "newTest.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Select from gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }

            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File newTest : f.listFiles()) {
                    if (newTest.getName().equals("newTest.jpg")) {
                        f = newTest;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    mImage.setImageBitmap(bitmap);

                    File path = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            ;
                    f.delete();
                    OutputStream outFile = null;
                    final File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final  String pat= getRealPathFromURI(Uri.fromFile(file).toString());
                    Log.e("PRINTPATH", pat);


                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                uploadImageToAWS(pat
                                );

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                final String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                mImage.setImageBitmap(thumbnail);

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {

                            uploadImageToAWS(picturePath);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }
    }

    private void uploadImageToAWS(String selectedImagePath) {
        String tempImagName;

        if (selectedImagePath == null) {
            Toast.makeText(this, "Could not find the filepath of the selected file", Toast.LENGTH_LONG).show();

// to make sure that file is not emapty or null
            return;
        }

        File file = new File(selectedImagePath);

        AmazonS3 s3Client = null;

        if (s3Client == null) {

            ClientConfiguration clientConfig = new ClientConfiguration();

            clientConfig.setProtocol(Protocol.HTTP);

            clientConfig.setMaxErrorRetry(0);

            clientConfig.setSocketTimeout(60000);

            BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_KEY, AWS_SECRET);

            s3Client = new AmazonS3Client(credentials, clientConfig);

            s3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
        }

        FileInputStream stream = null;

        try {

            stream = new FileInputStream(file);

            ObjectMetadata objectMetadata = new ObjectMetadata();

            Log.d("messge", "converting to bytes");

            objectMetadata.setContentLength(file.length());

            String[] s = selectedImagePath.split("\\.");

            String extenstion = s[s.length - 1];

            Log.d("messge", "set content length : " + file.length() + "sss" + extenstion);

            String fileName = UUID.randomUUID().toString();

            Log.d("Image Name <-----", fileName);

            tempImagName = fileName+"."+extenstion;

            imags.add(tempImagName);

            PutObjectRequest putObjectRequest = new PutObjectRequest(AWS_BUCKET, "new/" + fileName + "." + extenstion, stream, objectMetadata)

                    .withCannedAcl(CannedAccessControlList.PublicRead);

// above line is  making the request to the aws  server for the specific place to upload the image were aws_bucket is the main folder  name and inside that is the profiles folder and there the file will be get uploaded

            PutObjectResult result = s3Client.putObject(putObjectRequest);

// this will  add the image to the specified path in the aws bucket.


            runOnUiThread(new Runnable() {

                public void run() {

                }

            });


            if (result == null) {

                Log.e("RESULT", "NULL");

            } else {

                Log.e("RESULT", result.toString());

            }

        } catch (Exception e) {

            Log.d("ERRORR", " " + e.getMessage());

            e.printStackTrace();

//            Log.e("ERROR",e.getMessage());

        }

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    private String turnArrToS(ArrayList<String> l){
        String result = "";

        String fin = l.get(l.size()-1);

        for(String value : l){
            if(!value.equals(fin)){
                result = result  +value+",";
            }
            else{
                result = result  +value;
            }
        }
        return result;
    }
}
