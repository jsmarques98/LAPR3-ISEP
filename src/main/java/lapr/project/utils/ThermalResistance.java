package lapr.project.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ThermalResistance {
    private Double resistanceOuterLayer;
    private Double resistanceMiddleLayer;
    private Double resistanceInteriorLayer;

    private Double resistanceLateralWalls;
    private Double resistanceBaseWalls;
    private Double resistanceDoorWalls;


    public Double calculateResistancerLayer(Double k, Double layerThickness, Double x, Double y){
        return (layerThickness/(k* x * y));
    }

    public Double calculateResistanceContainer(List<Double> kMaterials, Double layerThickness, List<Double> dimensions){
        if (dimensions.contains(0.0))
            return 0.0;
        else{

            resistanceOuterLayer = calculateResistancerLayer(kMaterials.get(0),layerThickness,dimensions.get(0), dimensions.get(2));
            resistanceMiddleLayer = calculateResistancerLayer(kMaterials.get(1),layerThickness,dimensions.get(0), dimensions.get(2));
            resistanceInteriorLayer = calculateResistancerLayer(kMaterials.get(2),layerThickness,dimensions.get(0), dimensions.get(2));

            resistanceLateralWalls = 2 * (resistanceOuterLayer+resistanceMiddleLayer+resistanceInteriorLayer);

            resistanceOuterLayer = calculateResistancerLayer(kMaterials.get(0),layerThickness,dimensions.get(0), dimensions.get(1));
            resistanceMiddleLayer = calculateResistancerLayer(kMaterials.get(1),layerThickness,dimensions.get(0), dimensions.get(1));
            resistanceInteriorLayer = calculateResistancerLayer(kMaterials.get(2),layerThickness,dimensions.get(0), dimensions.get(1));

            resistanceBaseWalls =  2 * (resistanceOuterLayer+resistanceMiddleLayer+resistanceInteriorLayer);

            resistanceOuterLayer = calculateResistancerLayer(kMaterials.get(0),layerThickness,dimensions.get(2), dimensions.get(1));
            resistanceMiddleLayer = calculateResistancerLayer(kMaterials.get(1),layerThickness,dimensions.get(2), dimensions.get(1));
            resistanceInteriorLayer = calculateResistancerLayer(kMaterials.get(2),layerThickness,dimensions.get(2), dimensions.get(1));

            resistanceDoorWalls =  2 * (resistanceOuterLayer+resistanceMiddleLayer+resistanceInteriorLayer);
        }

        BigDecimal bd = BigDecimal.valueOf(resistanceBaseWalls + resistanceDoorWalls + resistanceLateralWalls).setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }
}
