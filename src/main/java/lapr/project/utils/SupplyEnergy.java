package lapr.project.utils;

import java.util.Arrays;
import java.util.List;

public class SupplyEnergy {

    private final static  double CONSTANT_STEFAN_BOLZTMANN = 5.67504*Math.pow(10.0,-8.0);
    private final static  double EMISSIVITY = 1;
    private final static  double MAX_POWER_GE = 75000;


    public Double calcPowerEmittedSun(double area, double temp){
        return EMISSIVITY * CONSTANT_STEFAN_BOLZTMANN * area * Math.pow(temp+273.15,4);
    }

    public Double calcEnergy(Double resistivity, Double ambientTemp, Double tempCont, Double travelTime){
        if(resistivity != 0.0){
            return ((Math.abs(ambientTemp-tempCont)/resistivity) * travelTime*3600);
        }
        return 0.0;
    }

    public Double calcTripEnergy(List<Double> temp, List<Double> time, Double resistivity, Double tempCont){
        double totalEnergy = 0.0;
        for(int i = 0; i < temp.size(); i++) {
            for(int j = 0; j < time.size(); j++){
                if (i==j){
                    totalEnergy += calcEnergy(resistivity,temp.get(i),tempCont,time.get(j));
                }
            }
        }
        return totalEnergy;
    }

    public Double calcContainersTripEnergy(List<Double> temp, List<Double> time, Double resistivity, Double tempCont, Integer numContainer){
        double totalEnergy = 0.0;
        for(int i = 0; i < temp.size(); i++) {
            for(int j = 0; j < time.size(); j++){
                if (i==j){
                    totalEnergy += calcEnergy(resistivity,temp.get(i),tempCont,time.get(j))*numContainer;
                }
            }
        }
        return totalEnergy;
    }

    public Integer numOfGen(List<Double> temp, List<Double> time, double [][] areas, Integer numRows) {
        int containerC = 15;
        int containerS = 25;
        double energy;
        double energyC = 0.0;
        double energyS = 0.0;
        double energySun = 0.0;
        double areaT = (numRows-1)*areaRow(areas)+areaFirstRow(areas);
        double totalTravelTime = journetTime(time)*3600;

        for (int i = 0 ; i < time.size() ; i++){
            energyC += (calcEnergy(1.38,temp.get(i),-5.0,time.get(i)));
        }

        energyC += energyC*containerC;

        for (int i = 0 ; i < time.size() ; i++){
            energyS += (calcEnergy(0.96,temp.get(i),7.0,time.get(i))) ;
        }

        for (int i = 0 ; i < time.size() ; i++) {
            energySun += calcPowerEmittedSun(areaT,temp.get(i))*(time.get(i)*3600);
        }

        energyS += energyS*containerS;

        energy = energyC+energyS+energySun;

        double powerTotal = MAX_POWER_GE * totalTravelTime;

        return (int) Math.ceil(energy/powerTotal);
    }

    public Double calcTotalEnergy(Integer numRows, double [][] areas, List<Double> temp,
                                  List<Double> time, Double resistivity, Double tempCont){
        double energyAllContainers = 0.0;
        int numContainers = areas.length*areas[0].length*numRows;
        double areaT = (numRows-1)*areaRow(areas)+areaFirstRow(areas);

        for(int i = 0; i < time.size(); i++){
            energyAllContainers += (calcEnergy(resistivity,temp.get(i),tempCont,time.get(i))*numContainers) + calcPowerEmittedSun(areaT,temp.get(i))*(time.get(i)*3600);
        }

        return energyAllContainers;
    }

    private Double areaFirstRow(double [][] areas){
        double areaT = 0.0;
        for (int i = 0; i < areas.length; i++) {
            for (int j = 0; j < areas[0].length; j++) {
                areaT += areas[i][j];
            }
        }
        return areaT;
    }

    private Double areaRow(double [][] areas){
        double areaT = 0.0;
        for (int i = 0; i < areas.length; i++) {
            for (int j = 0; j < areas[0].length; j++) {
                areaT += areas[i][j]-10.0;
            }
        }
        return areaT;
    }

    public Double journetTime(List<Double> time){
        double journetTime = 0.0;
        for (Double t:time) {
            journetTime += t;
        }
        return journetTime;
    }

}
