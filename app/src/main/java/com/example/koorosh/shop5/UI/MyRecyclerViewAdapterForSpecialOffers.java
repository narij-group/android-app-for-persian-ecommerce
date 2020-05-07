package com.example.koorosh.shop5.UI;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koorosh.shop5.Classes.Product;
import com.example.koorosh.shop5.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Koorosh on 29/09/2017.
 */

public class MyRecyclerViewAdapterForSpecialOffers extends  RecyclerView.Adapter<MyRecyclerViewAdapterForSpecialOffers.ViewHolder>  {
    private ArrayList<Product> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener2 mClickListener;
    Context context;

    // data is passed into the constructor
    MyRecyclerViewAdapterForSpecialOffers(Context context, ArrayList<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context.getApplicationContext();
    }





    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = mData.get(position);
        holder.ProductId = p.productId;
        holder.PersianName.setText(p.name);
        holder.LatinName.setText(p.latinname);
        holder.OriginalPrice.setText(p.discount);
        holder.OriginalPrice.setPaintFlags(holder.OriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.LastPrice.setText(p.price);
        setImage(holder,p.image);
    }

    public void setImage(ViewHolder holder , String url) {
//        // get input stream
//        InputStream ims = null;
//        try {
//            ims = context.getAssets().open("images/ProductImagePlacejolder.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // load image as Drawable
//        Drawable d = Drawable.createFromStream(ims, null);
        Picasso.with(context).load("http://shop5.ir/"+url).into(holder.ProductImage);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        long ProductId;
        TextView PersianName;
        TextView LatinName;
        TextView OriginalPrice;
        TextView LastPrice;
        ImageView ProductImage;



        ViewHolder(View itemView) {
            super(itemView);
            ProductId = 0;
            Typeface typeface= Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
            PersianName = (TextView) itemView.findViewById(R.id.PersianName);
            LatinName = (TextView) itemView.findViewById(R.id.LatinName);
            OriginalPrice = (TextView) itemView.findViewById(R.id.OriginalPrice);
            LastPrice = (TextView) itemView.findViewById(R.id.LastPrice);
            ProductImage = (ImageView) itemView.findViewById(R.id.ProductImage);

            PersianName.setTypeface(typeface);
//            LatinName.setTypeface(typeface);
            OriginalPrice.setTypeface(typeface);
            LastPrice.setTypeface(typeface);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick2(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    long getItem(int id) {
        return mData.get(id).productId;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener2 itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener2 {
        void onItemClick2(View view, int position);
    }

}
