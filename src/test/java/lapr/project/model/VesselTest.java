package lapr.project.model;

import lapr.project.data.Calculus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VesselTest {
    Vessel vessel;
    @BeforeEach
    void setUp() {
        Calculus calculus = new Calculus();
        vessel =new Vessel("Bulk Carrier",0,275,160000,40,calculus.areaCalc(213.0,11.0,false),
                calculus.areaCalc(17.13,11,false), calculus.areaCalc(29,29,false));
        vessel.setDraft(15.5);
        vessel.setDeeph(29.85);
        vessel.setBeam(48.2);
        vessel.setVolume(calculus.volumeOfaShip(vessel,vessel.getBeam()));

        double submergedValue=calculus.submergedWidth(vessel);
        double submergedVolume = calculus.volumeOfaShip(vessel,submergedValue);
        double mass = calculus.massOfaShip(submergedVolume);
        vessel.setMass(mass);
    }
    @Test
    void getCapacity() {
        assertEquals(vessel.getCapacity(),0);
    }

    @Test
    void setCapacity() {
        vessel.setCapacity(1);
        assertEquals(vessel.getCapacity(),1);
    }

    @Test
    void getLength() {
        assertEquals(vessel.getLength(),275);
    }

    @Test
    void setLength() {
        vessel.setLength(276);
        assertEquals(vessel.getLength(),276);
    }

    @Test
    void getTonnage() {
        assertEquals(vessel.getTonnage(),160000);
    }

    @Test
    void setTonnage() {
        vessel.setTonnage(12);
        assertEquals(vessel.getTonnage(),12);
    }

    @Test
    void getHeight() {
        assertEquals(vessel.getHeight(),40);
    }

    @Test
    void setHeight() {
        vessel.setHeight(39);
        assertEquals(vessel.getHeight(),39.0);

    }

    @Test
    void getBeam() {
        assertEquals(vessel.getBeam(),48.2);
    }

    @Test
    void setBeam() {
        vessel.setBeam(47.2);
        assertEquals(vessel.getBeam(),47.2);
    }

    @Test
    void getDraft() {
        assertEquals(vessel.getDraft(),15.5);
    }

    @Test
    void setDraft() {
        vessel.setDraft(47.2);
        assertEquals(vessel.getDraft(),47.2);
    }

    @Test
    void getName() {
        assertEquals(vessel.getName(),"Bulk Carrier");
    }

    @Test
    void setName() {
        vessel.setName("Maersk");
        assertEquals(vessel.getName(),"Maersk");
    }

    @Test
    void getAreaOne() {
        assertEquals(vessel.getAreaOne(),2343.0);
    }

    @Test
    void setAreaOne() {
        vessel.setAreaOne(3);
        assertEquals(vessel.getAreaOne(),3);
    }

    @Test
    void getAreaTwo() {
        assertEquals(vessel.getAreaTwo(),188.42999999999998);
    }

    @Test
    void setAreaTwo() {
        vessel.setAreaTwo(3);
        assertEquals(vessel.getAreaTwo(),3);
    }

    @Test
    void getAreaThree() {

        assertEquals(vessel.getAreaThree(),841.0);
    }

    @Test
    void setAreaThree() {
        vessel.setAreaThree(3);
        assertEquals(vessel.getAreaThree(),3);
    }

    @Test
    void getTotalArea() {
        assertEquals(vessel.getTotalArea(),3372.43);
    }

    @Test
    void setTotalArea() {
        vessel.setTotalArea(3);
        assertEquals(vessel.getTotalArea(),3);
    }

    @Test
    void getxCm() {
        assertEquals(0.0,vessel.getxCm());
    }

    @Test
    void setxCm() {
        vessel.setxCm(4.0);
        assertEquals(4.0,vessel.getxCm());
    }

    @Test
    void getyCm() {
        assertEquals(0.0,vessel.getyCm());
    }

    @Test
    void setyCm() {
        vessel.setyCm(2);
        assertEquals(2.0,vessel.getyCm());
    }

    @Test
    void getMass() {
        assertEquals(1.058080375E8,vessel.getMass());
    }

    @Test
    void setMass() {
        vessel.setMass(123.0);
        assertEquals(123.0,vessel.getMass());
    }

    @Test
    void getVolume() {
        assertEquals(197830.875,vessel.getVolume());
    }

    @Test
    void setVolume() {
        vessel.setVolume(2);
        assertEquals(2.0,vessel.getVolume());
    }

    @Test
    void getDeeph() {
        assertEquals(29.85,vessel.getDeeph());
    }

    @Test
    void setDeeph() {
        vessel.setDeeph(2);
        assertEquals(2.0,vessel.getDeeph());
    }


}