package com.coms309.peddler.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Namable implements Serializable {
    private String name;
    private String subtitle;

    public Namable(String name, String subtitle) {
        this.name = name;
        this.subtitle = subtitle;
    }

    public String getName() { return name; }
    public String getSubtitle() { return subtitle; }
}
