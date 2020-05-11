package etu.ihm.citycleaner.ui.groups;

import java.util.ArrayList;

import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class Group {

    public static int userId = 150;
    public static int actualGroupId = -1;

    private int id;
    private String name;
    private ArrayList<Trash> trashs;
    private  int isMyGroup;

    public Group(int id, String name, ArrayList<Trash> thrashs, int isMyGroup) {
        this.id = id;
        this.name = name;
        this.trashs = thrashs;
        this.isMyGroup = isMyGroup;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public ArrayList<Trash> getThrashs() { return this.trashs; }

    public void setIsMyGroup(int isMyGroup) { this.isMyGroup = isMyGroup; }

    public int isMyGroup() { return this.isMyGroup; }
}
