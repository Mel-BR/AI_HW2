package csp.entities;

public class MyPair
{
    private final String categ;
    private final Integer index;

    public MyPair(String categ, Integer index)
    {
        this.categ = categ;
        this.index = index;
    }

    public String categ()   { return categ; }
    public Integer index() { return index; }
}