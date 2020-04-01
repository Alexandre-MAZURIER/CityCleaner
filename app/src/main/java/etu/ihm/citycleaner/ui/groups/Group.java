package etu.ihm.citycleaner.ui.groups;

import java.util.ArrayList;

public class Group {
    private int id;
    private String name;
    private ArrayList<Integer> trashs;

    public Group(int id, String name, ArrayList<Integer> thrashs) {
        this.id = id;
        this.name = name;
        this.trashs = thrashs;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public ArrayList<Integer> getThrashs() { return this.trashs; }
}
