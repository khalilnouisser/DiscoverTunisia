package com.discover.tunisia.photos;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.photos.entities.Photo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneImageFragment extends Fragment {


    View view;
    @BindView(R.id.iv_photo)
    RoundedImageView ivPhoto;
    Unbinder unbinder;

    public OneImageFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Photo photo) {
        Bundle args = new Bundle();
        args.putSerializable(Constante.PHOTO, photo);
        OneImageFragment fragment = new OneImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        assert this.getArguments() != null;
        Photo photo = (Photo) this.getArguments().getSerializable(Constante.PHOTO);
        if (photo != null) {
            Utils.setRoundedImageUri(photo.getImage(),getContext(),ivPhoto);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
