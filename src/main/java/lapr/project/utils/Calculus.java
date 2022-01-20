package lapr.project.utils;

import lapr.project.model.Vessel;

public class Calculus {

    final double WATERDENSITY=1030;
    final double CONTAINERWEIGTH=500;
    public double areaCalc(double a, double b, boolean triangle){
        if (triangle)
        return (a*b)/2;
        else
            return a*b;
    }

    public Vessel centerOfMass(Vessel vessel,double[] xCords, double[] yCords){
        double xCm=0.0;
        double yCm=0.0;
       xCm=xCm + ((vessel.getAreaOne()/vessel.getTotalArea())*xCords[0]/2);
       xCm=xCm + ((vessel.getAreaTwo()/vessel.getTotalArea())*xCords[1]/2);
       xCm=xCm + ((vessel.getAreaThree()/vessel.getTotalArea())*xCords[2]/2);

       yCm=yCm + ((vessel.getAreaOne()/vessel.getTotalArea())*yCords[0]/2);
       yCm=yCm + ((vessel.getAreaTwo()/vessel.getTotalArea())*yCords[1]/2);
       yCm=yCm + ((vessel.getAreaThree()/vessel.getTotalArea())*yCords[2]/2);
       vessel.setxCm(xCm);
       vessel.setyCm(yCm);
       return vessel;
    }
    public String positionContainers(int numContainers, Vessel vessel){
        double frontDistribution=(vessel.getxCm()/vessel.getLength());
        int frontContainers = (int)Math.ceil(numContainers*frontDistribution);
        int rearContainers=numContainers-frontContainers;

        return frontContainers+" at the front and "+rearContainers + "at the back";
    }

    public double volumeOfaShip(Vessel vessel,double width){
        return (1.0/2.0)*vessel.getLength()*vessel.getDeeph()*width;
    }

    public double massOfaShip(double volume){

        return volume*WATERDENSITY;
    }
    public double submergedVolume(Vessel vessel){
        return vessel.getMass()*WATERDENSITY;
    }


    public double containerWeight(int nrContainers){
        return (double)CONTAINERWEIGTH*nrContainers;
    }
    public double submergedHeigth(Vessel vessel, double mass){
        double massTotal=mass+vessel.getMass();
        double volume=massTotal/WATERDENSITY;
        double upFraction=(volume*2* vessel.getDeeph());
        double downFraction=(vessel.getLength()* vessel.getBeam());
        return Math.sqrt(upFraction/downFraction);
    }
    public double submergedWidth(Vessel vessel){
        return (vessel.getDraft()/vessel.getDeeph())*vessel.getBeam();
    }
}
