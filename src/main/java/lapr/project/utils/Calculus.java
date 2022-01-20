package lapr.project.utils;

import lapr.project.model.Vessel;

public class Calculus {
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
        double rearDistribution= 1-frontDistribution;
        int frontContainers = (int)Math.ceil(numContainers*frontDistribution);
        int rearContainers=numContainers-frontContainers;

        return frontContainers+" at the front and "+rearContainers + "at the back";
    }
}
