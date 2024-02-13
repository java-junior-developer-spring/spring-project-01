package com.itmo.springproject01.info.queries;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_boxes")
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int height;
    private int width;
    private int length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", height=" + height +
                ", width=" + width +
                ", length=" + length +
                '}';
    }
}
