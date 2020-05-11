package etu.ihm.citycleaner.ui.mytrashs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Date;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.TrashManager;

public class MyTrashsFragment extends Fragment {

    private MyTrashsViewModel notificationsViewModel;
    private TrashManager trashManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MyTrashsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_trashs, container, false);

        trashManager = new TrashManager(this.getContext());
        trashManager.open();

        ListView listView = root.findViewById(R.id.contenu_my_trashs);
        ArrayList<Trash> listTrash = trashManager.getTrashs();
        TrashAdapter trashAdapter = new TrashAdapter(getActivity(),R.layout.fragment_trash, listTrash);
        listView.setAdapter(trashAdapter);


        return root;
    }
}