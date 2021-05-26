package com.example.niposttracker.ui.gallery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.niposttracker.R;
import com.example.niposttracker.model.LoginResponse;
import com.example.niposttracker.model.UserResponse;
import com.example.niposttracker.service.NIPostApi;
import com.example.niposttracker.service.NIPostClient;
import com.example.niposttracker.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class GalleryFragment extends Fragment {
    SharedPreferences sharedPreferences;

    private GalleryViewModel galleryViewModel;
    TextView txtFname, txtLname, txtEmail, txtPhone;
    CircleImageView imgProfile;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        sharedPreferences = getActivity().getSharedPreferences("My Preference", MODE_PRIVATE);
//        Toast.makeText(this, sharedPreferences.getString("token",null), Toast.LENGTH_SHORT).show();
        token = sharedPreferences.getString("token", null);
        txtFname = root.findViewById(R.id.txtFname);
        txtLname = root.findViewById(R.id.txtLname);
        txtEmail = root.findViewById(R.id.txtEmail);
        txtPhone = root.findViewById(R.id.txtPhone);
        imgProfile = root.findViewById(R.id.imgProfile);

        NIPostApi postApi = NIPostClient.getInstance().create(NIPostApi.class);
        Call<LoginResponse> niPostClient = postApi.getUserResponse("Bearer 20|WB17CR5GjzBs5O1z4cf3oOaze4w9GEQFUjNL7d");
        niPostClient.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.i(TAG, "onCall: " + response.raw());
                Log.i(TAG, "onToken: " + Constants.TOKEN);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        Log.i(TAG, "onSUCCESS" + response.body().getMessage());
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onError: " + response.body().toString());
                    }
                } else {
                    Log.i(TAG, "Failed: " + response.message());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.i(TAG, "onFailure: " + t.getMessage());

            }
        });

        return root;
    }
}