package com.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 3/11/2017.
 */

public class DisjointUnionSets {
    private List<Integer>  parent;

    // Constructor
    public DisjointUnionSets()
    {
        parent = new ArrayList<>();
        parent.add(0,0);
    }

    public void addNewElement(int i) {
        parent.add(i,i);
    }

    public int find(int i)
    {
        // If i is the parent of itself
        if (parent.get(i) == i)
        {
            // Then i is the representative of this set
            return i;
        }
        else
        {
            // Else if i is not the parent of
            // itself, then i is not the
            // representative of his set. So we
            // recursively call Find on its parent
            return find(parent.get(i));
        }
    }

    // Unites the set that includes i and the set that includes j
    public void union(int i, int j)
    {
        // Find the representatives (or the root nodes) for the set
        // that includes i

        int irep = this.find(i);

        // And do the same for the set
        // that includes j
        int jrep = this.find(j);

        // Make the parent of i’s representative
        // be j’s  representative effectively
        // moving all of i’s set into j’s set)
        this.parent.set(irep, jrep);
    }

}