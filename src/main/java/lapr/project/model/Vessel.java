package lapr.project.model;

public class Vessel {
    String name;
    int capacity;
    double length;
    double tonnage;
    double height;
    double beam;
    double draft;
    double areaOne;
    double areaTwo;
    double areaThree;
    double totalArea;
    double xCm;
    double yCm;
    double mass;
    double volume;
    double deeph;
    public Vessel(String name,int capacity, double length, double tonnage, double height, double areaOne, double areaTwo, double areaThree) {
        this.name=name;
        this.capacity = capacity;
        this.length = length;
        this.tonnage = tonnage;
        this.height = height;
        this.beam = 0;
        this.draft = 0;
        this.areaOne=areaOne;
        this.areaTwo = areaTwo;
        this.areaThree = areaThree;
        this.totalArea= areaOne+areaTwo+areaThree;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getTonnage() {
        return tonnage;
    }

    public void setTonnage(double tonnage) {
        this.tonnage = tonnage;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBeam() {
        return beam;
    }

    public void setBeam(double beam) {
        this.beam = beam;
    }

    public double getDraft() {
        return draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAreaOne() {
        return areaOne;
    }

    public void setAreaOne(double areaOne) {
        this.areaOne = areaOne;
    }

    public double getAreaTwo() {
        return areaTwo;
    }

    public void setAreaTwo(double areaTwo) {
        this.areaTwo = areaTwo;
    }

    public double getAreaThree() {
        return areaThree;
    }

    public void setAreaThree(double areaThree) {
        this.areaThree = areaThree;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getxCm() {
        return xCm;
    }

    public void setxCm(double xCm) {
        this.xCm = xCm;
    }

    public double getyCm() {
        return yCm;
    }

    public void setyCm(double yCm) {
        this.yCm = yCm;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getDeeph() {
        return deeph;
    }

    public void setDeeph(double deeph) {
        this.deeph = deeph;
    }
}
