package com.example.koorosh.shop5.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.koorosh.shop5.API.APIClient;
import com.example.koorosh.shop5.API.APIInterface;
import com.example.koorosh.shop5.Classes.Product;
import com.example.koorosh.shop5.Classes.ProductModel;
import com.example.koorosh.shop5.Classes.RetrofitModel;
import com.example.koorosh.shop5.Classes.Temp;
import com.example.koorosh.shop5.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.koorosh.shop5.R.menu.products;

public class ProductsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyRecyclerViewAdapter.ItemClickListener, MyRecyclerViewAdapterForSpecialOffers.ItemClickListener2, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    MyRecyclerViewAdapter adapter;
    MyRecyclerViewAdapterForSpecialOffers adapter2;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SHOP5");
        setSupportActionBar(toolbar);

        Typeface typeface = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/BYEKAN.TTF");
//if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item_home = new PrimaryDrawerItem().withIdentifier(1).withTypeface(typeface).withName("خانه").withIcon(getResources().getDrawable(R.mipmap.ic_home)).withSelectedTextColor(Color.parseColor("#0077c6")).withTextColor(Color.parseColor("#0077c6")).withSelectedColor(Color.parseColor("#DDDDDD"));
        PrimaryDrawerItem item_login = new PrimaryDrawerItem().withIdentifier(2).withTypeface(typeface).withName("ورود/ثبت نام").withIcon(getResources().getDrawable(R.mipmap.profile)).withSelectedTextColor(Color.parseColor("#333333")).withTextColor(Color.parseColor("#333333")).withSelectedColor(Color.parseColor("#DDDDDD"));
        PrimaryDrawerItem item_cat = new PrimaryDrawerItem().withIdentifier(3).withTypeface(typeface).withName("دسته بندی ها").withIcon(getResources().getDrawable(R.mipmap.sort)).withSelectedTextColor(Color.parseColor("#333333")).withTextColor(Color.parseColor("#333333")).withSelectedColor(Color.parseColor("#DDDDDD"));

        PrimaryDrawerItem item_special = new PrimaryDrawerItem().withIdentifier(4).withTypeface(typeface).withName("پیشنهادهای ویژه").withSelectedTextColor(Color.parseColor("#555555")).withTextColor(Color.parseColor("#555555")).withSelectedColor(Color.parseColor("#DDDDDD"));
        PrimaryDrawerItem item_latest = new PrimaryDrawerItem().withIdentifier(5).withTypeface(typeface).withName("جدیدترین ها").withSelectedTextColor(Color.parseColor("#555555")).withTextColor(Color.parseColor("#555555")).withSelectedColor(Color.parseColor("#DDDDDD"));
        PrimaryDrawerItem item_bestsellers = new PrimaryDrawerItem().withIdentifier(6).withTypeface(typeface).withName("پرفروش\u200Cترین ها").withSelectedTextColor(Color.parseColor("#555555")).withTextColor(Color.parseColor("#555555")).withSelectedColor(Color.parseColor("#DDDDDD"));
        PrimaryDrawerItem item_mostviewed = new PrimaryDrawerItem().withIdentifier(7).withTypeface(typeface).withName("پربازدید\u200Cترین ها").withSelectedTextColor(Color.parseColor("#555555")).withTextColor(Color.parseColor("#555555")).withSelectedColor(Color.parseColor("#DDDDDD"));

        final ProfileDrawerItem item_siteprofile = new ProfileDrawerItem().withName("SHOP5").withEmail("در حال همگام سازی...").withIcon(getResources().getDrawable(R.drawable.header_back));

// Create the AccountHeader
        final AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeightDp(70)
                .withHeaderBackground(R.drawable.header_back)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        item_siteprofile
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item_home,
                        item_login,
                        item_cat,
                        new DividerDrawerItem(),
                        item_special,
                        item_latest,
                        item_bestsellers,
                        item_mostviewed
                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return true;
                    }
                })
                .build();


        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


        final Call<RetrofitModel> call4 = apiInterface.getSiteInfo();


        call4.enqueue(new Callback<RetrofitModel>() {


            private static final int TOTAL_RETRIES = 3;
            private int retryCount = 0;

            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
//                        TextView txt = (TextView) findViewById(R.id.txtWebsiteName);
//                        txt.setText(t.property2);

                        item_siteprofile.withName("SHOP5").withEmail(t.property1).withIcon(getResources().getDrawable(R.drawable.header_back));
                        headerResult.updateProfile(item_siteprofile);
//                        Picasso.with(ProductsActivity.this).load("http://shop5.ir/" + t.property1).into((ImageView) findViewById(R.id.Logo));

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                if (retryCount++ < TOTAL_RETRIES) {
                    Log.v("TAG", "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                } else {
                    Toast.makeText(ProductsActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call4.clone().enqueue(this);
            }
        });

        //----------SLIDER----------------

        final Call<RetrofitModel> call = apiInterface.getSlides();
        final HashMap<String, String> url_maps = new HashMap<String, String>();

        final ProgressBar spinner;
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        call.enqueue(new Callback<RetrofitModel>() {

            private static final int TOTAL_RETRIES = 3;
            private int retryCount = 0;


            @Override
            public void onResponse(Call<RetrofitModel> call, Response<RetrofitModel> response) {
                try {
                    ArrayList<Temp> temps = response.body().temps;
                    for (Temp t : temps) {
                        url_maps.put(t.property2 + " " + System.currentTimeMillis(), "http://shop5.ir/" + t.property1);
                    }
                } catch (Exception e) {
//                    Toast.makeText(ProductsActivity.this, "اشکال", Toast.LENGTH_SHORT).show();
                }
                for (String name : url_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(ProductsActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(ProductsActivity.this);

                    mDemoSlider.addSlider(textSliderView);
                }
                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new SliderCommentAnimation());
                mDemoSlider.setDuration(10000);
                mDemoSlider.addOnPageChangeListener(ProductsActivity.this);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                if (retryCount++ < TOTAL_RETRIES) {
                    Log.v("TAG", "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                } else {
                    Toast.makeText(ProductsActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call.clone().enqueue(this);
            }
        });

        //----------SLIDER----------------


        // data to populate the RecyclerView with


        final Call<ProductModel> call2 = apiInterface.getSpecialOfferProducts();

        final ProgressBar spinner2;
        spinner2 = (ProgressBar) findViewById(R.id.progressBar2);

        call2.enqueue(new Callback<ProductModel>() {

            private static final int TOTAL_RETRIES = 3;
            private int retryCount = 0;

            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                ArrayList<Product> products3 = new ArrayList<>();
                try {
                    products3 = response.body().products;

                    for (Product p : products3) {
                        Log.d("RET_LOG", String.valueOf(p.productId));
                    }
                    TextView txtTitle1 = (TextView) findViewById(R.id.txtTitle1);
                    Button btn = (Button) findViewById(R.id.btnSpecialOffers);
                    if (products3 != null) {
                        txtTitle1.setText("پیشنهادهای ویژه");
                        btn.setText("ادامه لیست");
                        btn.setBackground(getResources().getDrawable(R.drawable.more_list));
                    } else {
                        txtTitle1.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(ProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                spinner2.setVisibility(View.GONE);
                ArrayList<Product> data = products3;
                // set up the RecyclerView
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
                int numberOfColumns = 1;
                recyclerView.setLayoutManager(new GridLayoutManager(ProductsActivity.this, numberOfColumns));

                adapter2 = new MyRecyclerViewAdapterForSpecialOffers(ProductsActivity.this, data);
                adapter2.setClickListener((MyRecyclerViewAdapterForSpecialOffers.ItemClickListener2) ProductsActivity.this);
                recyclerView.setAdapter(adapter2);
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                if (retryCount++ < TOTAL_RETRIES) {
                    Log.v("TAG", "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                } else {
                    Toast.makeText(ProductsActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call2.clone().enqueue(this);
            }
        });


        final Call<ProductModel> call3 = apiInterface.getLatestProducts();

        final ProgressBar spinner3;
        spinner3 = (ProgressBar) findViewById(R.id.progressBar3);

        call3.enqueue(new Callback<ProductModel>() {

            private static final int TOTAL_RETRIES = 3;
            private int retryCount = 0;

            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                ArrayList<Product> products3 = new ArrayList<>();
                try {
                    products3 = response.body().products;

                    for (Product p : products3) {
                        Log.d("RET_LOG", String.valueOf(p.productId));
                    }
                    TextView txtTitle1 = (TextView) findViewById(R.id.txtTitle2);
                    Button btn = (Button) findViewById(R.id.btnLatest);
                    if (products3 != null) {
                        txtTitle1.setText("جدیدترین محصولات");
                        btn.setText("ادامه لیست");
                        btn.setBackground(getResources().getDrawable(R.drawable.more_list));
                    } else {
                        txtTitle1.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(ProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                spinner3.setVisibility(View.GONE);
                ArrayList<Product> data = products3;
                // set up the RecyclerView
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvProducts);
                int numberOfColumns = 1;
                recyclerView.setLayoutManager(new GridLayoutManager(ProductsActivity.this, numberOfColumns));

                adapter = new MyRecyclerViewAdapter(ProductsActivity.this, data);
                adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener) ProductsActivity.this);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                if (retryCount++ < TOTAL_RETRIES) {
                    Log.v("TAG", "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                } else {
                    Toast.makeText(ProductsActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call3.clone().enqueue(this);
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "محصول با موفقیت به سبد خرید افزوده شد", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//            }
//        });

        final CounterFab counterFab = (CounterFab) findViewById(R.id.counter_fab);
        counterFab.setCount(0); // Set the count value to show on badge
        counterFab.bringToFront();
        counterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "محصول با موفقیت به سبد خرید افزوده شد", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();

//                counterFab.increase();
            }
        });


//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

        @Override
        public void onItemClick (View view,int position){
            Log.i("TAG", "You clicked number " + adapter.getItem(position));

            Intent intent = new Intent(ProductsActivity.this, OneProductActivity.class);
            intent.putExtra("ProductId", adapter.getItem(position));
            startActivity(intent);
        }

        @Override
        public void onItemClick2 (View view,int position){
            Log.i("TAG", "You clicked number " + adapter2.getItem(position));

            Intent intent = new Intent(ProductsActivity.this, OneProductActivity.class);
            intent.putExtra("ProductId", adapter2.getItem(position));
            startActivity(intent);
        }

        @Override
        public void onBackPressed () {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(products, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_search) {
                Intent intent = new Intent(ProductsActivity.this, SearchActivity.class);
                startActivity(intent);
            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            return true;
        }


        @Override
        public void onSliderClick (BaseSliderView slider){

        }

        @Override
        public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels){

        }

        @Override
        public void onPageSelected ( int position){

        }

        @Override
        public void onPageScrollStateChanged ( int state){

        }
    }
