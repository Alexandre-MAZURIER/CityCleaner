package etu.ihm.citycleaner.ui.map.createtrash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import etu.ihm.citycleaner.R;

public class CreateTrashFragment extends Fragment implements View.OnClickListener{
    private CreateTrashViewModel trashViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trashViewModel =
                ViewModelProviders.of(this).get(CreateTrashViewModel.class);
        View root = inflater.inflate(R.layout.fragment_createtrash, container, false);
        final TextView textView = root.findViewById(R.id.text_createtrash);
        trashViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.send_button:
                break;

            case R.id.cancel_button:
                break;

            case R.id.add_picture:
//                fragment = new PhoneBookFragment();
//                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, someFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }




}
