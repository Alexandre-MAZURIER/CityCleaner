package etu.ihm.citycleaner.ui.groups.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import etu.ihm.citycleaner.R;

public class AddGroupDialog extends AppCompatDialogFragment {

    private EditText editText;

    private DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_group_dialog, null);

        //we invert add and cancel in the code to have create before cancel when reading
        builder.setView(view)
                .setTitle("Ajouter un groupe")
                .setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Créer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String groupName = editText.getText().toString();
                        listener.applyText(groupName);
                        Toast feedback = Toast.makeText(getContext(), groupName + " créé", Toast.LENGTH_LONG);
                        feedback.show();
                    }
                });

        this.editText = view.findViewById(R.id.group_name_editText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (DialogListener) getContext();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddGroupDialogListener");
        }
    }

    public interface DialogListener {
        void applyText(String groupName);
    }
}
