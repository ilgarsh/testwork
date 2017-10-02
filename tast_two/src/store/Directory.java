package store;

import java.util.List;

public class Directory {
    private int id;
    private String name;
    private int parent;
    private boolean showChild;
    private List<Directory> childs;

    Directory() {}

    public Directory(int id, String name, int parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.showChild = false;
    }

    public String getName() {
        return name;
    }

    public int getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }

    public void setShowChild(boolean showChild) {
        this.showChild = showChild;
    }

    public boolean isShowChild() {
        return showChild;
    }

    public List<Directory> getChilds() {
        return childs;
    }

    public void setChilds(List<Directory> childs) {
        this.childs = childs;
    }
}
