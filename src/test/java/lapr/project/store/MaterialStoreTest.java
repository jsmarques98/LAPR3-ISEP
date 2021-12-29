package lapr.project.store;

import lapr.project.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialStoreTest {

    private MaterialStore ms;
    private Material m;
    private Material m2;


    @BeforeEach
    public void setUp() {
        ms = new MaterialStore();
        m = new Material("Aco", 52.00, "Outside");
        m2 = new Material("Aco", 52.00, "Outside");
    }

    @Test
    public void testGetMaterialList() {
        ms.addMaterial(m2);
        assertEquals(1,ms.getMaterialList().size());
        ms.addMaterial(m);
        assertEquals(2,ms.getMaterialList().size());
    }

    @Test
    public void testAddMaterial() {
        assertTrue(ms.addMaterial(m));
    }

}