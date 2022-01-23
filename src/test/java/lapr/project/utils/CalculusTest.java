package lapr.project.utils;

import lapr.project.model.Vessel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculusTest {
    Calculus calculus= new Calculus();
    Vessel vessel =new Vessel("Bulk Carrier",0,275,160000,40,calculus.areaCalc(213.0,11.0,false),
            calculus.areaCalc(17.13,11,false), calculus.areaCalc(29,29,false));

    @BeforeEach
    public void setUp(){
        vessel.setDraft(15.5);
        vessel.setDeeph(29.85);
        vessel.setBeam(48.2);
    }

    @Test
    void areaCalc() {
        assertEquals(calculus.areaCalc(1,2,true),1);
    }
    @Test
    void areaCalcNotTria() {
        assertEquals(calculus.areaCalc(1,2,false),2);
    }

    @Test
    void centerOfMass() {
        double [] xCords = new double[]{213, 11, 9};
        double [] yCords = new double[]{11, 17.13, 15};
        Vessel resultVessel= calculus.centerOfMass(vessel,xCords,yCords);
        Vessel expectedVessel= calculus.centerOfMass(vessel,xCords,yCords);
        expectedVessel.setxCm(75.42050242703334);
        expectedVessel.setyCm(6.170002920742611);
        assertEquals(resultVessel,expectedVessel);
    }

    @Test
    void positionContainers() {

        assertEquals(calculus.positionContainers(100,vessel),"0 at the front and 100at the back");
    }

    @Test
    void volumeOfaShip() {
        vessel.setVolume(calculus.volumeOfaShip(vessel,vessel.getBeam()));
        assertEquals(vessel.getVolume(),197830.875);
    }

    @Test
    void massOfaShip() {
        double submergedValue=calculus.submergedWidth(vessel);
        double submergedVolume = calculus.volumeOfaShip(vessel,submergedValue);
        double mass = calculus.massOfaShip(submergedVolume);
        vessel.setMass(mass);
        assertEquals(mass,1.058080375E8);
    }

    @Test
    void submergedVolume() {

        assertEquals(calculus.submergedVolume(vessel),0.0);
    }

    @Test
    void containerWeight() {
        assertEquals(calculus.containerWeight(2),1000);
    }

    @Test
    void submergedHeigth() {
        assertEquals(calculus.submergedHeigth(vessel,calculus.containerWeight(2)+vessel.getMass()),0.06612697971431049);
    }

    @Test
    void submergedWidth() {
        assertEquals(calculus.submergedWidth(vessel),25.028475711892796);
    }
}