package com.allen508.fretflex.ui.tuner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.allen508.fretflex.R;
import com.allen508.fretflex.data.NoteRepository;
import com.allen508.fretflex.databinding.FragmentTuningsBinding;


public class TuningsFragment extends Fragment {

    private FragmentTuningsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentTuningsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        view.setBackgroundColor(Color.rgb(0, 0, 0));

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NoteRepository repository = new NoteRepository();
        String[] tuningNames = repository.getTuningNames();


        View.OnClickListener listener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuningName = String.valueOf(((Button)v).getText());

                NavDirections action = TuningsFragmentDirections.actionTuningsFragmentToTunerFragment();

                Bundle bundle = new Bundle();
                bundle.putString("tuningName", tuningName);

                NavOptions.Builder builder = new NavOptions.Builder();
                builder.setPopUpTo(R.id.tunerFragment, true);
                //builder.setEnterAnim(R.anim.slide_left);
                //builder.setEnterAnim(R.anim.slide_right);
                //builder.setPopEnterAnim(R.anim.slide_left);
                //builder.setPopExitAnim(R.anim.slide_right);

                Navigation.findNavController(view).navigate(R.id.tunerFragment, bundle, builder.build());

            }
        };


        for (String tuning: tuningNames) {
            Button button = new Button(this.getContext());
            button.setText(tuning);
            button.setOnClickListener(listener);
            binding.tuningList.addView(button);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}