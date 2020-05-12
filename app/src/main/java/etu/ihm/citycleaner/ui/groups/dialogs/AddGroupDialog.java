package etu.ihm.citycleaner.ui.groups.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;

import etu.ihm.citycleaner.CreateTrashActivity;
import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.Group;
import etu.ihm.citycleaner.ui.groups.GroupsFragment;

public class AddGroupDialog extends AppCompatDialogFragment {

    private GroupManager databaseManager;

    private EditText editText;
    private DialogListener listener;

    private GroupsFragment parentFragment;

    public AddGroupDialog(GroupsFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.databaseManager = new GroupManager(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_group_dialog, null);
        this.editText = view.findViewById(R.id.group_name_editText);

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

                        //we add the group in the database
                        databaseManager.open();
                        databaseManager.addGroup(groupName);
                        databaseManager.close();

                        //we update the list
                        parentFragment.updateGroupsList();
                        parentFragment.updateMyGroupsList();
                        sendNotification();
                    }
                });

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

    public void sendNotification() {
        NotificationManager notificationManager =  (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder((getActivity().getApplicationContext()), "notify_002")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Groupe crée")
                .setContentText("Le groupe " + editText.getText().toString() + " a bien été crée")
                .setPriority(NotificationCompat.PRIORITY_MAX);


        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "123";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        notificationManager.notify(1, builder.build());
    }
}
