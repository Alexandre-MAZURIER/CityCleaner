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

public class MyTrashsFragment extends Fragment {

    private MyTrashsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MyTrashsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_trashs, container, false);
        final TextView textView = root.findViewById(R.id.text_my_trashs);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ListView listView = root.findViewById(R.id.contenu_my_trashs);
        ArrayList<Trash> listTrash = myTrashTemporaire();
        TrashAdapter trashAdapter = new TrashAdapter(getContext(), listTrash);
        listView.setAdapter(trashAdapter);


        return root;
    }

    //temporaire tant qu'on a pas de BDD ou autre
    public ArrayList<Trash> myTrashTemporaire(){
        ArrayList<Trash> listTrash = new ArrayList<>();
        /*listTrash.add(new Trash(0, "6 rue de l'aire", "Plan de Cuques", 13380, "France", new Date()));
        listTrash.add(new Trash(1, "124 rue Viktor", "Putier-sur-Marnes", 56000, "France", new Date()));
        listTrash.add(new Trash(2, "13 avenue Damso", "Choux sur Bruxelle", 68452, "France", new Date()));*/
        return listTrash;
    }
}