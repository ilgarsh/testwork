package main.store;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@code Directory}.
 */
public class Directory {
    private int id;
    private String name;
    private Integer parent;
    private List<Directory> childs;

    Directory() {}

    public Directory(int id, String name, Integer parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        childs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Integer getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public List<Directory> getChilds() {
        return childs;
    }

    public void setChilds(List<Directory> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", parent=" + parent +
                ", childs: " + childs;
    }
}
