package com.example.koorosh.shop5.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.mikepenz.materialdrawer.holder.BadgeStyle;
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

import static com.example.koorosh.shop5.R.menu.one_product;
import static com.example.koorosh.shop5.R.menu.products;

public class OneProductActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    private SliderLayout mDemoSlider;
    private boolean more;
    private long product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_product);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        product_id = getIntent().getLongExtra("ProductId", 0);


        findViewById(R.id.layout1).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout2).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout3).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout4).setVisibility(View.INVISIBLE);
        findViewById(R.id.layout5).setVisibility(View.INVISIBLE);
        findViewById(R.id.comment_cardview).setVisibility(View.INVISIBLE);
        findViewById(R.id.card_view).setVisibility(View.INVISIBLE);

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


        mehdi.sakout.fancybuttons.FancyButton btnBackToTop = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.go_top);

        final ScrollView mainScrollView = (ScrollView) findViewById(R.id.one_product_container);

        btnBackToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        //---------------LOAD FONT-----------
        Typeface typeface = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/BKOODB.TTF");

        final TextView txtLatinName = (TextView) findViewById(R.id.LatinName);
        final TextView txtPersianName = (TextView) findViewById(R.id.PersianName);
        final TextView txtOrginalPrice = (TextView) findViewById(R.id.OriginalPrice);
        final TextView txtLastPrice = (TextView) findViewById(R.id.LastPrice);
        final mehdi.sakout.fancybuttons.FancyButton btnAddToCart = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btn_addtocart);
        final TextView txt5 = (TextView) findViewById(R.id.txtComment_title);
        final TextView txtComment = (TextView) findViewById(R.id.description);
        final mehdi.sakout.fancybuttons.FancyButton btnShowmore = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.show_more);
        final mehdi.sakout.fancybuttons.FancyButton btnProperties = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btn_properties);
        final mehdi.sakout.fancybuttons.FancyButton btnComments = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btn_comment);
        final mehdi.sakout.fancybuttons.FancyButton btnOpinions = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btn_opinions);
        final mehdi.sakout.fancybuttons.FancyButton btnReview = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btn_review);
//        txt.setTypeface(typeface);
        txtPersianName.setTypeface(typeface);
        txtOrginalPrice.setTypeface(typeface);
        txtOrginalPrice.setPaintFlags(txtOrginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtLastPrice.setTypeface(typeface);
        btnAddToCart.setCustomTextFont("fonts/BKOODB.TTF");
        txt5.setTypeface(typeface);
        txtComment.setTypeface(typeface);
        btnShowmore.setCustomTextFont("fonts/BKOODB.TTF");
        btnProperties.setCustomTextFont("fonts/BKOODB.TTF");
        btnComments.setCustomTextFont("fonts/BKOODB.TTF");
        btnOpinions.setCustomTextFont("fonts/BKOODB.TTF");
        btnReview.setCustomTextFont("fonts/BKOODB.TTF");
        //------------------END----------------


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SHOP5");
        toolbar.bringToFront();
        setSupportActionBar(toolbar);

        android.support.design.widget.AppBarLayout abl = (android.support.design.widget.AppBarLayout) findViewById(R.id.action_bar);
        abl.bringToFront();

        typeface = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/BYEKAN.TTF");
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
                .withSelectedItem(-1)
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
                    Toast.makeText(OneProductActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call4.clone().enqueue(this);
            }
        });


        more = false;

        int lineCount = txtComment.getLineCount();

        if(lineCount < 7) {

            btnShowmore.setVisibility(View.INVISIBLE);

        }
        btnShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (more == false) {
                    txtComment.setMaxLines(Integer.MAX_VALUE);
                    txtComment.setEllipsize(null);
                    btnShowmore.setText("کمتر");
                    more = true;
                } else {
                    txtComment.setMaxLines(7);
                    txtComment.setEllipsize(TextUtils.TruncateAt.END);
                    btnShowmore.setText("بیشتر");
                    more = false;
                }
            }
        });

        //----------SLIDER----------------


        final Call<RetrofitModel> call = apiInterface.getProductImages(product_id);
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
                        Log.d("TAG", "http://shop5.ir/" + t.property1);
                        url_maps.put(t.property2, "http://shop5.ir/" + t.property1);
                    }
                } catch (Exception e) {
//                    Toast.makeText(OneProductActivity.this, "اشکال", Toast.LENGTH_SHORT).show();
                }
                for (String name : url_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(OneProductActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterInside)
                            .setOnSliderClickListener(OneProductActivity.this);

                    mDemoSlider.addSlider(textSliderView);
                }
                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new SliderCommentAnimation());
                mDemoSlider.setDuration(10000);
                mDemoSlider.addOnPageChangeListener(OneProductActivity.this);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RetrofitModel> call, Throwable t) {
                if (retryCount++ < TOTAL_RETRIES) {
                    Log.v("TAG", "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                } else {
                    Toast.makeText(OneProductActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call.clone().enqueue(this);
            }
        });

        //-----------Slider ends

        final Call<ProductModel> call2 = apiInterface.getOneProduct(product_id);

        call2.enqueue(new Callback<ProductModel>() {

            private static final int TOTAL_RETRIES = 3;
            private int retryCount = 0;

            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                findViewById(R.id.layout1).setVisibility(View.VISIBLE);
                findViewById(R.id.layout2).setVisibility(View.VISIBLE);
                findViewById(R.id.layout3).setVisibility(View.VISIBLE);
                findViewById(R.id.layout4).setVisibility(View.VISIBLE);
                findViewById(R.id.card_view).setVisibility(View.VISIBLE);
                findViewById(R.id.layout5).setVisibility(View.VISIBLE);
                findViewById(R.id.comment_cardview).setVisibility(View.VISIBLE);
                ArrayList<Product> products = new ArrayList<>();
                try {
                    products = response.body().products;

                    for (Product p : products) {
                        txtPersianName.setText(p.name);
                        txtLatinName.setText(p.latinname);
                        txtComment.setText(p.comment);
                        txtLastPrice.setText(p.price);
                        txtOrginalPrice.setText(p.discount);
                    }
                } catch (Exception e) {
                    Toast.makeText(OneProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                if (retryCount++ < TOTAL_RETRIES) {
                    Log.v("TAG", "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                } else {
                    Toast.makeText(OneProductActivity.this, "لطفا اتصال خود به اینترنت را بررسی نمایید.", Toast.LENGTH_LONG);
                }
            }

            private void retry() {
                call2.clone().enqueue(this);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent = new Intent(OneProductActivity.this , SearchActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_back) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
