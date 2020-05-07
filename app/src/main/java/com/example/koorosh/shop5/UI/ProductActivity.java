package com.example.koorosh.shop5.UI;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.koorosh.shop5.API.APIClient;
import com.example.koorosh.shop5.API.APIInterface;
import com.example.koorosh.shop5.Classes.Result;
import com.example.koorosh.shop5.Classes.RetrofitModel;
import com.example.koorosh.shop5.Classes.Temp;
import com.example.koorosh.shop5.Globals;
import com.example.koorosh.shop5.R;
import com.example.koorosh.shop5.UploadImageInterface;
import com.example.koorosh.shop5.UploadObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    private static final String TAG = ProductActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://shop5.ir/";
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Button selectUploadButton = (Button) findViewById(R.id.select_image);
        selectUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

                Log.d("test2", "1");
            }
        });

        final int UserId = getIntent().getIntExtra("UserId", 0);
        final int ProductId = getIntent().getIntExtra("ProductId", 0);

        Log.d(Globals.LOG_TAG, "Pro " + ProductId);

        final Spinner s = (Spinner) findViewById(R.id.brand);
        s.setEnabled(false);

        Call<RetrofitModel> call = apiInterface.getBrands();

        final ArrayList<String> mylist = new ArrayList<String>();
        mylist.add("برند محصول را انتخاب کنید...");

        call.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                s.setEnabled(true);
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
                        mylist.add(t.property1 + " | " + t.property2);
                    }
                } catch (Exception e) {
                    Log.d(Globals.LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                Log.d(Globals.LOG_TAG, t.getMessage());
            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mylist);
        s.setAdapter(adapter);


        final Spinner s2 = (Spinner) findViewById(R.id.group);
        s2.setEnabled(false);

        Call<RetrofitModel> call2 = apiInterface.getGroups(UserId);

        final ArrayList<String> mylist2 = new ArrayList<String>();
        mylist2.add("مجموعه محصول را انتخاب کنید...");

        call2.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                s2.setEnabled(true);
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
                        mylist2.add(t.property1 + " | " + t.property2);
                    }
                } catch (Exception e) {
                    Log.d(Globals.LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                Log.d(Globals.LOG_TAG, t.getMessage());
            }
        });
        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mylist2);
        s2.setAdapter(adapter2);


        final Spinner s3 = (Spinner) findViewById(R.id.subgroup);
        s3.setEnabled(false);

        Call<RetrofitModel> call3 = apiInterface.getSubGroups(UserId);

        final ArrayList<String> mylist3 = new ArrayList<String>();
        mylist3.add("زیرمجموعه محصول را انتخاب کنید...");

        call3.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                s3.setEnabled(true);
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
                        mylist3.add(t.property1 + " | " + t.property2);
                    }
                } catch (Exception e) {
                    Log.d(Globals.LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                Log.d(Globals.LOG_TAG, t.getMessage());
            }
        });
        ArrayAdapter adapter3 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mylist3);
        s3.setAdapter(adapter3);


        final Spinner s4 = (Spinner) findViewById(R.id.suppergroup);
        s4.setEnabled(false);

        Call<RetrofitModel> call4 = apiInterface.getSupperGroups(UserId);

        final ArrayList<String> mylist4 = new ArrayList<String>();
        mylist4.add("زیر زیرمجموعه محصول را انتخاب کنید...");

        call4.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                s4.setEnabled(true);
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
                        mylist4.add(t.property1 + " | " + t.property2);
                    }
                } catch (Exception e) {
                    Log.d(Globals.LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                Log.d(Globals.LOG_TAG, t.getMessage());
            }
        });
        ArrayAdapter adapter4 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mylist4);
        s4.setAdapter(adapter4);


        Button btn = (Button) findViewById(R.id.btnAddColor);
        Call<RetrofitModel> call5 = apiInterface.getColors();

        final ArrayList<String> myclist = new ArrayList<String>();
        myclist.add("رنگ را انتخاب کنید...");

        call5.enqueue(new Callback<RetrofitModel>() {
            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
                        myclist.add(t.property1);
                    }
                } catch (Exception e) {
                    Log.d(Globals.LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                Log.d(Globals.LOG_TAG, t.getMessage());
            }
        });

        // add button listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(ProductActivity.this);
                dialog.setContentView(R.layout.activity_color_dialog);
                dialog.setTitle("رنگ و تعداد");

                // set the custom dialog components - text, image and button
                final Spinner spn = (Spinner) dialog.findViewById(R.id.spnColors);

                ArrayAdapter cadapter = new ArrayAdapter(ProductActivity.this, android.R.layout.simple_spinner_item, myclist);
                spn.setAdapter(cadapter);


                final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.txtQuantity);
                np.setMinValue(0);
                np.setMaxValue(100);

                Button dialogButton = (Button) dialog.findViewById(R.id.btnAdd);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final ProgressDialog progress2 = new ProgressDialog(ProductActivity.this);
                        progress2.setTitle("در حال افزودن");
                        progress2.setMessage("لطفا کمی صبر کنید...");
                        progress2.setCancelable(false);
                        progress2.show();

                        String colorname = spn.getSelectedItem().toString().trim();
                        int quantity = np.getValue();

                        Call<Result> call = apiInterface.insertColor(ProductId, colorname, quantity);

                        call.enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                if (!response.body().getError()) {
                                    progress2.dismiss();
                                    Toast.makeText(ProductActivity.this, "رنگ و تعداد با موفقیت افزوده شدند", Toast.LENGTH_SHORT).show();
                                } else {
                                    progress2.dismiss();
                                    Toast.makeText(getApplicationContext(), "مشکلی به وجود آمد، لطفا دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {
                                progress2.dismiss();
                                Toast.makeText(getApplicationContext(), "مشکلی به وجود آمد، لطفا دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });

                dialog.show();
            }
        });


        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                EditText txtName = (EditText) findViewById(R.id.name);
                EditText txtLatinName = (EditText) findViewById(R.id.latinname);
                EditText txtPrice = (EditText) findViewById(R.id.price);

                String name = txtName.getText().toString().trim();
                String latinname = txtLatinName.getText().toString().trim();
                int price;
                if (txtPrice.getText().toString().equals("")) {
                    price = 0;
                } else {
                    price = Integer.parseInt(txtPrice.getText().toString());
                }

                String brand = s.getSelectedItem().toString().trim();
                String group = s2.getSelectedItem().toString().trim();
                String subgroup = s3.getSelectedItem().toString().trim();
                String suppergroup = s4.getSelectedItem().toString().trim();

                if (name.equals("") || latinname.equals("") || price == 0 || brand.equals("برند محصول را انتخاب کنید...") || group.equals("مجموعه محصول را انتخاب کنید...") || subgroup.equals("زیرمجموعه محصول را انتخاب کنید...") || suppergroup.equals("زیر زیرمجموعه محصول را انتخاب کنید...")) {
                    Toast.makeText(ProductActivity.this, "لطفا تمام فیلد ها را پر کنید!", Toast.LENGTH_SHORT).show();
                } else {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    final ProgressDialog progress2 = new ProgressDialog(ProductActivity.this);
                                    progress2.setTitle("در حال افزودن");
                                    progress2.setMessage("لطفا کمی صبر کنید...");
                                    progress2.setCancelable(false);
                                    progress2.show();


                                    EditText txtName = (EditText) findViewById(R.id.name);
                                    EditText txtLatinName = (EditText) findViewById(R.id.latinname);
                                    EditText txtPrice = (EditText) findViewById(R.id.price);

                                    String name = txtName.getText().toString().trim();
                                    String latinname = txtLatinName.getText().toString().trim();
                                    int price = Integer.parseInt(txtPrice.getText().toString());
                                    String brand = s.getSelectedItem().toString().trim();
                                    String group = s2.getSelectedItem().toString().trim();
                                    String subgroup = s3.getSelectedItem().toString().trim();
                                    String suppergroup = s4.getSelectedItem().toString().trim();

                                    Call<Result> call = apiInterface.insertProduct(ProductId, name, latinname, price, brand, group, subgroup, suppergroup, UserId);

                                    call.enqueue(new Callback<Result>() {
                                        @Override
                                        public void onResponse(Call<Result> call, Response<Result> response) {
                                            if (!response.body().getError()) {
                                                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("UserId", String.valueOf(UserId));

                                                Toast.makeText(ProductActivity.this, "محصول با موفقیت افزوده شد", Toast.LENGTH_LONG).show();
//                                            Toast.makeText(ProductActivity.this , response.body().getMessage() , Toast.LENGTH_LONG).show();
                                                startActivity(intent);
                                                ProductActivity.this.finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "مشکلی در افزودن محصول به وجود آمد، لطفا دوباره امتحان کنید.", Toast.LENGTH_LONG).show();
                                                progress2.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Result> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            progress2.dismiss();
                                        }
                                    });


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                    builder.setMessage("آیا میخواهید محصول را اضافه کنید؟").setPositiveButton("بله", dialogClickListener)
                            .setNegativeButton("خیر", dialogClickListener).show();

                }


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ProductActivity.this);
        Log.d("test2", "2");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final ProgressDialog progress = new ProgressDialog(ProductActivity.this);
        progress.setTitle("در حال آپلود");
        progress.setMessage("لطفا کمی صبر کنید...");
        progress.setCancelable(false);
        progress.show();
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, ProductActivity.this);
//                File file = new File(filePath);

                File file = null;
                try {
                    file = new Compressor(ProductActivity.this).compressToFile(new File(filePath));
                } catch (IOException e) {
                    file = new File(filePath);
                }

                Log.d(TAG, "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                RequestBody productId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getIntent().getIntExtra("ProductId", 0)));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_PATH)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UploadImageInterface uploadImage = retrofit.create(UploadImageInterface.class);
                Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename, productId);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        Toast.makeText(ProductActivity.this, response.body().getSuccess(), Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        Toast.makeText(ProductActivity.this, "مشکلی در آپلود تصویر به وجود آمد, لطفا دوباره امتحان کنید!", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Error " + t.getMessage());
                        progress.dismiss();
                    }
                });
            } else {
                EasyPermissions.requestPermissions(this, "این نرم افزار اجازه دسترسی به حافظه دستگاه شما را دارد تا تصاویر را بارگذاری کند!.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                progress.dismiss();
            }
        }
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                progress.dismiss();
            }

        }.start();
        Log.d("test2", "3");
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {

        ProgressDialog progress = new ProgressDialog(ProductActivity.this);
        progress.setTitle("در حال آپلود");
        progress.setMessage("لطفا کمی صبر کنید...");
        progress.setCancelable(false);
        progress.show();
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            progress.dismiss();
            Log.d("test2", "4");
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            progress.dismiss();
            Log.d("test2", "4");
            return cursor.getString(idx);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        final ProgressDialog progress = new ProgressDialog(ProductActivity.this);
        progress.setTitle("در حال آپلود");
        progress.setMessage("لطفا کمی صبر کنید...");
        progress.setCancelable(false);
        progress.show();
        if (uri != null) {
            String filePath = getRealPathFromURIPath(uri, ProductActivity.this);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody productId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getIntent().getIntExtra("ProductId", 0)));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UploadImageInterface uploadImage = retrofit.create(UploadImageInterface.class);
            Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename, productId);
            fileUpload.enqueue(new Callback<UploadObject>() {
                @Override
                public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                    Toast.makeText(ProductActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(ProductActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }

                @Override
                public void onFailure(Call<UploadObject> call, Throwable t) {
                    Log.d(TAG, "Error " + t.getMessage());
                    progress.dismiss();
                }
            });
        }
        Log.d("test2", "5");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
        Log.d("test2", "6");
    }
}
