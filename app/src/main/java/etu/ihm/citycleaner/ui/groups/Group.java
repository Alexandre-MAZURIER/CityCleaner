package etu.ihm.citycleaner.ui.groups;

import java.util.ArrayList;

import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class Group {
    private int id;
    private String name;
    private ArrayList<Trash> trashes;

    public Group(int id, String name, ArrayList<Trash> thrashs) {
        this.id = id;
        this.name = name;
        this.trashes = thrashs;
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<Trash> getThrashes() { return this.trashes; }

    public void setTrashes(ArrayList<Trash> trashes) { this.trashes = trashes; }
}
