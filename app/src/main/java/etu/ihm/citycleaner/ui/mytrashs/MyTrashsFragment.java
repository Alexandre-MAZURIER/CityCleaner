package etu.ihm.citycleaner.ui.mytrashs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.TrashManager;

public class MyTrashsFragment extends Fragment {

    private MyTrashsViewModel notificationsViewModel;
    private TrashManager trashManager;
    private TrashAdapter trashAdapter;
    private boolean isSimplified = false;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MyTrashsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_my_trashs, container, false);

        trashManager = new TrashManager(this.getContext());
        trashManager.open();

        final ListView listView = root.findViewById(R.id.contenu_my_trashs);

        final ArrayList<Trash> listTrash = trashManager.getTrashs();
        if(isSimplified)
            listView.setAdapter(new TrashAdapter(getActivity(),R.layout.fragment_simplified_trash, listTrash));
        else
            listView.setAdapter(new TrashAdapter(getActivity(),R.layout.fragment_trash, listTrash));

        Button simplifiedViewOn = root.findViewById(R.id.buttonSimplify);
        View.OnClickListener handler = new View.OnClickListener() {

            public void onClick(View v) {
                if(isSimplified){
                    trashAdapter = new TrashAdapter(getActivity(),R.layout.fragment_trash, listTrash);
                    listView.setAdapter(trashAdapter);
                    trashAdapter.notifyDataSetChanged();
                    isSimplified = false;
                }
                else {
                    trashAdapter = new TrashAdapter(getActivity(), R.layout.fragment_simplified_trash, listTrash);
                    listView.setAdapter(trashAdapter);
                    trashAdapter.notifyDataSetChanged();
                    isSimplified = true;
                }


            }
        };
        simplifiedViewOn.setOnClickListener(handler);

        return root;
    }

    private TrashAdapter trashAdapterr(int layout, ArrayList<Trash> listTrash){
        return new TrashAdapter(getActivity(),layout, listTrash);
    }
}