package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram.R;
import com.example.parstagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Bundle bundle;

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTimestamp;
    private ImageButton btnLike;
    private TextView tvLikes;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle = this.getArguments();
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivImage = view.findViewById(R.id.ivImage);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvTimestamp = view.findViewById(R.id.tvTimestamp);
        btnLike = view.findViewById(R.id.btnLike);
        tvLikes = view.findViewById(R.id.tvLikes);

        final Post post = (Post) bundle.getParcelable("post");
        tvUsername.setText(post.getUser().getUsername());
        if (post.getImage() != null) {
            Glide.with(getContext()).load(post.getImage().getUrl()).into(ivImage);
        }
        tvDescription.setText(post.getDescription());
        tvTimestamp.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

        if (post.getLikes() != null) {
            tvLikes.setText(post.getLikes().size() + " likes");
            if (has(post.getLikes(), ParseUser.getCurrentUser())) {
                btnLike.setImageResource(R.drawable.ufi_heart_active);
            }
        }

        else {
            tvLikes.setText("0 likes");
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.addLike(ParseUser.getCurrentUser());
                post.saveInBackground();
                tvLikes.setText(post.getLikes().size() + " likes");
                btnLike.setImageResource(R.drawable.ufi_heart_active);
            }
        });
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        if (rawJsonDate  == null) {
            return "NULL";
        }
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static boolean has(ArrayList<ParseObject> list, ParseUser parseUser) {
        for (ParseObject user: list) {
            if ( ((ParseUser) user).getUsername().equals(parseUser.getUsername())) {
                return true;
            }
        }
        return false;
    }
}