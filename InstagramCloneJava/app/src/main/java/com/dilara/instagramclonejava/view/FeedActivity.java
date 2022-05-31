package com.dilara.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dilara.instagramclonejava.R;
import com.dilara.instagramclonejava.adapter.PostAdapter;
import com.dilara.instagramclonejava.databinding.ActivityFeedBinding;
import com.dilara.instagramclonejava.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding binding;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFeedBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        auth=FirebaseAuth.getInstance();
        getData();
        firebaseFirestore=FirebaseFirestore.getInstance();
        postArrayList=new ArrayList<>();

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter=new PostAdapter(postArrayList);
        binding.RecyclerView.setAdapter(postAdapter);

    }
    private void getData(){

        //DocumentReference documentReference=firebaseFirestore.collection("Posts").document("sdjfs");
        //CollectionReference documentReference=firebaseFirestore.collection("Posts");
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {

                if(error!=null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if(value!=null){
                    for(DocumentSnapshot snapshot:value.getDocuments()){//bu dizi içinden tek tek dökümanları alıp snapshota kaydeder

                        Map<String,Object> data=snapshot.getData();

                        String userEmail=(String) data.get("useremail");
                        String comment=(String) data.get("comment");
                        String downloadUrl=(String) data.get("downloadurl");

                        Post post=new Post(userEmail,comment,downloadUrl);
                        postArrayList.add(post);



                    }
                    postAdapter.notifyDataSetChanged();//recycler viewa yeni veri gelince haber ver
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//menüyü bağlama işlemleri
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);//menüyü bağlamamızı sağlar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//seçince ne olacağı

        if(item.getItemId() == R.id.add_post){
            Intent intentToUpload=new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intentToUpload);

        }else if(item.getItemId() == R.id.signout){

            auth.signOut();//firebase a çıkış işleminibildirmek

            Intent intentToMain=new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();


        }
        return super.onOptionsItemSelected(item);
    }
}