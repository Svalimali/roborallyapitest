package org.example;
import java.util.HashSet;
import java.util.Set;

public class HexGrid implements Grid{
    private Set<Hexagon> hexagons;


    public HexGrid() {
        this.hexagons = new HashSet<>();
    }
    @Override
    public void createGrid(int radius) {
        for (int q = -radius; q <= radius; q++) {
            int r1 = Math.max(-radius, -q - radius);
            int r2 = Math.min(radius, -q + radius);
            for (int r = r1; r <= r2; r++) {
                hexagons.add(new Hexagon(q, r));
            }
        }
    }

    public Hexagon neighborOf(Hexagon hex, int degrees) {
        int q = hex.getQ();
        int r = hex.getR();
        switch (degrees) {
            case 30:  return new Hexagon(q + 1, r - 1);//NOrth east
            case 90:  return new Hexagon(q + 1, r); //East
            case 150: return new Hexagon(q, r + 1); //South east
            case 210: return new Hexagon(q - 1, r + 1);//South west
            case 270: return new Hexagon(q - 1, r);//West
            case 330: return new Hexagon(q, r - 1);//North west
            default:  return hex; // Returns the original hexagon if direction is invalid
        }
    }

    @Override
    public boolean hasHex(Hexagon hex) {
        return hexagons.contains(hex);
    }
}
