
package com.example.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;

    UsersAdapter usersAdapter;

    int cacheSize = 10 * 1024 * 1024;
    
    
    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        usersAdapter = new UsersAdapter(this::ClickedUser);

        getAllUsers();




    }

    public void getAllUsers(){

        Call<List<Todos>> userlist = ApiClient.getUserService().getAllUsers();

        userlist.enqueue(new Callback<List<Todos>>() {
            @Override
            public void onResponse(Call<List<Todos>> call, Response<List<Todos>> response) {

                if(response.isSuccessful()){
                 List<Todos> userResponses = response.body();
                 usersAdapter.setData(userResponses);
                 recyclerView.setAdapter(usersAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<Todos>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void ClickedUser(UserResponse userResponse) {

        startActivity(new Intent(this,UserDetailsActivity.class).putExtra("data",Todos));

    }
}
            @Override
            public void onResponse(Call<List<Todos>> call, Response<List<Todos>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Todos> todos = response.body();
                for (Todos todo : todos) {
                    String content = "";
                    content += "ID: " + todos.getId() + "\n";
                    content += "User ID: " + todos.getUserId() + "\n";
                    content += "Title: " + todos.getTitle() + "\n";
                    content += "Completed: " + todos.getCompleted() + "\n\n";
                    textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Todos>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}

private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
private void loadJSON(){

Cache cache = new Cache(getCacheDir(), cacheSize);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain)
                                throws IOException {
                            Request request = chain.request();
                            if (!isNetworkAvailable()) {
                                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                                request = request
                                        .newBuilder()
                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                        .build();
                            }
                            return chain.proceed(request);
                        }
                    })
                    .build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/todos")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            Service apiService = retrofit.create(Service.class);

    
    recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),Todos);
        recyclerView.setAdapter(recyclerAdapter);

        JsonPlaceHolderAPi apiService = ApiClient.getClient().create(JsonPlaceHolderAPi.class);
        Call<List<Todos>> call = apiService.getTodos();

        call.enqueue(new Callback<ListTodos>>() {
            @Override
            public void onResponse(Call<List<Todos>> call, Response<List<Todos>> response) {
                Todos = response.body();
                Log.d("ID","Response = "+Todos);
                recyclerAdapter.setTodos(Todos);
            }
