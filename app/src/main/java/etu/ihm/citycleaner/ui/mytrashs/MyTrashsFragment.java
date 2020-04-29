package etu.ihm.citycleaner.ui.mytrashs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;

public class MyTrashsFragment extends Fragment {

    private MyTrashsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MyTrashsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_trashs, container, false);

        ListView listView = root.findViewById(R.id.contenu_my_trashs);
        ArrayList<Trash> listTrash = myTrashTemporaire();
        TrashAdapter trashAdapter = new TrashAdapter(getActivity(),R.layout.fragment_trash, listTrash);
        listView.setAdapter(trashAdapter);

        return root;
    }

    //temporaire tant qu'on a pas de BDD ou autre
    public ArrayList<Trash> myTrashTemporaire(){
        ArrayList<Trash> listTrash = new ArrayList<>();
        listTrash.add(new Trash(0,0,0,0,0,"02/02/02","oui"));
        listTrash.add(new Trash(1,1,0,0,0,"03/02/02","oui"));
        listTrash.add(new Trash(2,2,0,0,0,"04/02/02","oui"));
        return listTrash;
    }
}