package org.example;

public class Hexagon {
    private int q;
    private int r;
    public Hexagon(){

    }

    public Hexagon(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Hexagon getNeighbor(int direction) {
        switch (direction) {
            case 30: return new Hexagon(this.q + 1, this.r - 1 );
            case 90: return new Hexagon(this.q + 1, this.r);
            case 150: return new Hexagon(this.q, this.r + 1);
            case 210: return new Hexagon(this.q - 1, this.r+1);
            case 270: return new Hexagon(this.q - 1, this.r);
            case 330: return new Hexagon(this.q, this.r - 1);
            default: return this; // Stay in place for undefined directions
        }
    }



    @Override
    public String toString() {
        return "Hexagon{" +
                "q=" + q +
                ", r=" + r +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + q;
        result = prime * result + r;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; //Checks if the two Hexagons are equal
        if (obj == null) return false; //Check for null
        if (getClass() != obj.getClass()) return false;//Check if obj has the same class ass Hexagon
        Hexagon other = (Hexagon) obj;//Instantiate obj
        if (q != other.q) return false; //Then check for coordinates
        if (r != other.r) return false;
        return true;
    }


}
