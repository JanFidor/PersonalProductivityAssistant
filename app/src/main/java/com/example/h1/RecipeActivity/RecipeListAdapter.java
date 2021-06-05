package com.example.h1.RecipeActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.h1.PopupFactory;
import com.example.h1.R;
import com.example.h1.RecipeActivity.RecipeDB.Recipe;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private final String MAIN = "https://customsearch.googleapis.com/customsearch/v1";
    private final String KEY = "?key=" + "AIzaSyCtSp-CkpXr1KYxSi-5icAYTUDBKWYOQXY";
    private final String ID = "&cx=" + "e420f61a6d9ea04fa";
    private final String PHOTO = " photo";

    public static class RecipeViewHolder extends RecyclerView.ViewHolder{



        RelativeLayout containerView;
        TextView recipeText, recipeTime;
        ImageView thumbnail;
        Button verifyDelete;

        public RecipeViewHolder(View view){
            super(view);

            containerView = view.findViewById(R.id.recipe_row);
            //recipeText = view.findViewById(R.id.recipe_name);
            recipeTime = view.findViewById(R.id.recipe_time_to_make);
            thumbnail = view.findViewById(R.id.thumbnail);



            //option to delete recipe from database
            containerView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Recipes Delete
                    Recipe recipe = (Recipe) containerView.getTag();
                    PopupFactory popupFactory = new PopupFactory();
                    popupFactory.getPopup("RecipesDelete", v, recipe.id);

                    return true;
                }
            });

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recipe recipe = (Recipe) containerView.getTag();

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.url));
                    v.getContext().startActivity(browserIntent);
                }
            });

        }
    }

    private List<Recipe> recipes= new ArrayList<>();
    private RequestQueue requestQueue;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO recipes_row_recipe layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_row_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        Recipe current = recipes.get(position);

        requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
        String url = MAIN + KEY + ID + "&q=" + current.name + PHOTO;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("items");
                    JSONObject data = results.getJSONObject(0);
                    JSONObject image_url = data.getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0);
                    String image = image_url.getString("src");
                    Picasso.get().load(image).into(holder.thumbnail);
                    Log.e("worked", "w");
                } catch (JSONException e) {
                    Log.e("CS50", "Json error", e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CS50", "pokemon list error");
            }
        });
        requestQueue.add(request);

        //holder.recipeText.setText(current.name);
        holder.recipeTime.setText(Values.timeString(current.time, holder.itemView.getContext()));



        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void reload(int taste, int type){
        recipes = RecipeList.database.recipeDAO().getAll();
        recipes = Filter.filter(recipes, taste, type);

        notifyDataSetChanged();
        notifyItemRangeChanged(0, recipes.size());
    }

}
