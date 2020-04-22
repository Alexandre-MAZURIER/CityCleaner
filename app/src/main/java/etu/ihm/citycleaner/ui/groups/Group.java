package etu.ihm.citycleaner.ui.groups;

import java.util.ArrayList;

import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class Group {
    private int id;
    private String name;
    private ArrayList<Trash> trashs;

    public Group(int id, String name, ArrayList<Trash> thrashs) {
        this.id = id;
        this.name = name;
        this.trashs = thrashs;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public ArrayList<Trash> getThrashs() { return this.trashs; }
}
