package com.example.chamiaapp.ui.product;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chamiaapp.Converters;
import com.example.chamiaapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditProduct extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.chamiaapp.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.chamiaapp.EXTRA_NAME";
    public static final String EXTRA_WEIGHT =
            "com.example.chamiaapp.EXTRA_WEIGHT";
    public static final String EXTRA_CADENCE =
            "com.example.chamiaapp.EXTRA_CADENCE";
    public static final String EXTRA_DATE_OF_MANUFACTURE =
            "com.example.chamiaapp.EXTRA_DATE_OF_MANUFACTURE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.chamiaapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_IMAGE =
            "com.example.chamiaapp.EXTRA_IMAGE";

    private TextInputLayout editTextName;
    private TextInputLayout editTextWeight;
    private TextInputLayout editTextCadence;
    private TextInputLayout editTextDateOfManufacture;
    private TextInputLayout editTextDescription;
    private ImageView imageView;
    private Button add_image ;
    Converters converters ;
    int SELECT_IMAGE_CODE = 1;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        editTextName = findViewById(R.id.p_name);
        editTextWeight = findViewById(R.id.p_weight);
        editTextCadence = findViewById(R.id.p_cadence);
        editTextDateOfManufacture = findViewById(R.id.p_dateManufacture);
        editTextDescription = findViewById(R.id.p_description);
        imageView = findViewById(R.id.p_image);
        add_image =findViewById(R.id.p_addImage);

        converters = new Converters();
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar1);

        // make the tool bar and put it into actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);




        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Product");
            editTextName.getEditText().setText(intent.getStringExtra(EXTRA_NAME));
            editTextWeight.getEditText().setText(String.valueOf(intent.getIntExtra(EXTRA_WEIGHT, 1)));
            editTextCadence.getEditText().setText(String.valueOf(intent.getIntExtra(EXTRA_CADENCE, 1)));
            editTextDateOfManufacture.getEditText().setText(intent.getStringExtra(EXTRA_DATE_OF_MANUFACTURE));
            editTextDescription.getEditText().setText(intent.getStringExtra(EXTRA_DESCRIPTION));

            Bitmap bitmap = converters.byteArrayToBitmap(intent.getByteArrayExtra(EXTRA_IMAGE));
            imageView.setImageBitmap(bitmap);

        } else {
            setTitle("Add Product");
        }
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);

            }
        });

    } // fin on create


    //validate product name
    private Boolean validateProductname() {
        String val = editTextName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            editTextName.setError("Field cannot be empty");
            return false;
        } else {
            editTextName.setError(null);
            editTextName.setErrorEnabled(false);
            return true;
        }
    }


    //validate weight
    private Boolean validateWeight() {
        String val = editTextWeight.getEditText().getText().toString();
        String digitPattern = "^[0-9]+$";

        if (val.isEmpty()) {
            editTextWeight.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(digitPattern)) {
            editTextWeight.setError("Field does contain only digits");
            return false;
        }
        else
        {
            editTextWeight.setError(null);
            editTextWeight.setErrorEnabled(false);
            return true;
        }
    }


    // validate cadence
    private Boolean validateCadence() {
        String val = editTextCadence.getEditText().getText().toString();
        String digitPattern = "^[0-9]+$";

        if (val.isEmpty()) {
            editTextCadence.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(digitPattern)) {
            editTextCadence.setError("Field does contain only digits");
            return false;
        }
        else
        {
            editTextCadence.setError(null);
            editTextCadence.setErrorEnabled(false);
            return true;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveProduct() {
        String name = editTextName.getEditText().getText().toString();
        int weight = Integer.parseInt(editTextWeight.getEditText().getText().toString());
        int cadence = Integer.parseInt(editTextCadence.getEditText().getText().toString());
        String date_of_manufacture = editTextDateOfManufacture.getEditText().getText().toString();
        String description = editTextDescription.getEditText().getText().toString();
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        byte[] image = converters.bitmapToByteArray(bitmap);


        if (name.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_WEIGHT, weight);
        data.putExtra(EXTRA_CADENCE, cadence);
        data.putExtra(EXTRA_DATE_OF_MANUFACTURE, date_of_manufacture);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_IMAGE, image);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            if (!validateWeight() | !validateCadence() | !validateProductname()){
                return false ;
            }
            else {
                saveProduct();
                return true;
            }
        }else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }
    }
}