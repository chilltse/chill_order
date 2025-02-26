package com.example.menu.data.model;

public class DishItem {
    private String name;
    private String description;

    // 构造函数
    public DishItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter 方法
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setter 方法（如果需要修改项的属性）
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}