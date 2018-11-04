package com.pearlmaknun.uts.uts_prkmobpro_b_lulual_maknun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pearlmaknun.uts.uts_prkmobpro_b_lulual_maknun.adapter.MovieAdapter;
import com.pearlmaknun.uts.uts_prkmobpro_b_lulual_maknun.api.ApiClient;
import com.pearlmaknun.uts.uts_prkmobpro_b_lulual_maknun.api.ApiService;
import com.pearlmaknun.uts.uts_prkmobpro_b_lulual_maknun.model.Movie;
import com.pearlmaknun.uts.uts_prkmobpro_b_lulual_maknun.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();
    }

    private void showList() {
        progressDialog = ProgressDialog.show(this, null, "Mengambil....", true, false);

        final RecyclerView recyclerView = findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        Call<MovieResponse> call = apiService.getNowPlaying(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                final List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_movie, getApplicationContext()));

                recyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Tidak ada koneksi.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(i);
            return true;
        }
        else if (id == R.id.profile){
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
