package com.qlp.commons.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2014/8/7.
 */
public class TreeNode {

    private String id;
    private String name;

    private List<TreeNode> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public TreeNode() {
    }

    public TreeNode(String id, String name, List<TreeNode> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }
}
