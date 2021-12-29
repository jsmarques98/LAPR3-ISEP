package lapr.project.store;

import lapr.project.model.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialStore {
    private final List<Material> materialList;

    public MaterialStore( ) {
        this.materialList = new ArrayList<>();
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public boolean addMaterial(Material material){
        return this.materialList.add(material);
    }

}
