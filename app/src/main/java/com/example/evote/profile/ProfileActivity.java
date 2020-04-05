package com.example.evote.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evote.R;
import com.example.evote.detail.DetailActivity;
import com.example.evote.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    String TAG;
    private FirebaseFirestore db;
    private Button btnProfil;
    private TextView profilNama, profilNim, profilEmail, profilProdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilNama = findViewById(R.id.profil_nama);
        profilEmail = findViewById(R.id.profil_email);
        profilNim = findViewById(R.id.profil_nim);
        profilProdi = findViewById(R.id.profil_prodi);
        btnProfil = findViewById(R.id.profil_btn);


        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance( );
        DocumentReference docRef = db.collection("users").document(Objects.requireNonNull(userId).getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    profilNama.setText(document.getString("nama"));
                    profilEmail.setText(document.getString("email"));
                    profilNim.setText(document.getString("nim"));
                    profilProdi.setText(document.getString("prodi"));//Print the name
                } else {
                    Log.d(TAG, "Dokumen kosong");
                }
            } else {
                Log.d(TAG, "Koneksi Bermasalah ", task.getException());
            }
        });

        btnProfil.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });
    }
}
