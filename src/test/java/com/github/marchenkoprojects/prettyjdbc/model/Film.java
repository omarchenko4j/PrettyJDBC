package com.github.marchenkoprojects.prettyjdbc.model;

/**
 * @author Oleg Marchenko
 */

public class Film {

    private int id;
    private String originalName;
    private short year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }
}
