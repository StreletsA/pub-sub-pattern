package com.saber3d.task.commons;

public class ExampleItem {

    private int id;
    private String name;
    private String hash;

    public ExampleItem(String name, int id){
        this.id = id;
        this.name = name;
        this.hash = String.valueOf(this.hashCode());
    }

    public ExampleItem(String name){
        this(name, -1);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "ExampleItem{id=" + id + ", name=" + name + ", hash=" + hash + "}";
    }
}
