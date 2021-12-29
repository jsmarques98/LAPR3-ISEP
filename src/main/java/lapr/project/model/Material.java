package lapr.project.model;

import java.util.Objects;

public class Material {
    private final String name;
    private final double thermalConductivity;
    private final String wall;

    public Material(String name, double thermalConductivity, String wall) {
        this.name = name;
        this.thermalConductivity = thermalConductivity;
        this.wall = wall;
    }

    public String getName() {
        return name;
    }

    public double getThermalConductivity() {
        return thermalConductivity;
    }

    public String getWall() {
        return wall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Double.compare(material.thermalConductivity, thermalConductivity) == 0 && Objects.equals(name, material.name) && Objects.equals(wall, material.wall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, thermalConductivity, wall);
    }

    @Override
    public String toString() {
        return String.format("%s With Thermal Conductivity %.3f W/mk, good for %s layer", name , thermalConductivity,wall);
    }
}
