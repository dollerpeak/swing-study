/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.starccm.macro;

import star.base.neo.NeoObjectVector;
import star.base.neo.StringVector;
import star.base.query.NameOperator;
import star.base.query.NamePredicate;
import star.base.query.Query;
import star.common.CompositePart;
import static star.common.FieldFunctionUnits.FieldFunctionUnitsKey.Units;
import star.common.IntegerValue;
import star.common.ScalarPhysicalQuantity;
import star.common.Simulation;
import star.common.SimulationPartManager;
import star.common.StarMacro;
import star.common.Units;
import star.meshing.AutoMeshOperation;
import star.meshing.BaseSize;
import star.meshing.CadPart;
import star.meshing.MaximumCellSize;
import star.meshing.MeshOperationManager;
import star.meshing.MeshPart;
import star.meshing.MesherParallelModeOption;
import star.meshing.PartsMinimumSurfaceSize;
import star.meshing.PartsResurfacerSurfaceProximity;
import star.meshing.RelativeOrAbsoluteOption;
import star.meshing.SurfaceCurvature;
import star.prismmesher.NumPrismLayers;
import star.prismmesher.PrismThickness;

/**
 *
 * @author cadit
 */
@SuppressWarnings("unchecked")
public class MakeAutoMesh2 extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {

        Simulation simulation_0 = getActiveSimulation();
        
        AutoMeshOperation autoMeshOperation_0 = simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.trimmer.TrimmerAutoMesher", "star.resurfacer.ResurfacerAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[]{}));
        autoMeshOperation_0.setPresentationName("Automated Mesh1");
        autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
        autoMeshOperation_0.getInputGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "core"), Query.STANDARD_MODIFIERS));

        Units units_0 = simulation_0.getUnitsManager().getObject("mm");
        autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValueAndUnits(4.0, units_0);

        PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);
        partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
        partsMinimumSurfaceSize_0.getAbsoluteSizeValue().setValueAndUnits(0.7, units_0);

        SurfaceCurvature surfaceCurvature_0 = autoMeshOperation_0.getDefaultValues().get(SurfaceCurvature.class);
        surfaceCurvature_0.setNumPointsAroundCircle(72.0);

        PartsResurfacerSurfaceProximity partsResurfacerSurfaceProximity_0 = autoMeshOperation_0.getDefaultValues().get(PartsResurfacerSurfaceProximity.class);
        partsResurfacerSurfaceProximity_0.setNumPointsInGap(3.0);

        NumPrismLayers numPrismLayers_0 = autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);

        IntegerValue integerValue_0 = numPrismLayers_0.getNumLayersValue();
        integerValue_0.getQuantity().setValue(5.0);

        PrismThickness prismThickness_0 = autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);

        Units units_1 = simulation_0.getUnitsManager().getObject("");

        prismThickness_0.getRelativeSizeScalar().setValueAndUnits(50.0, units_1);

        MaximumCellSize maximumCellSize_0 = autoMeshOperation_0.getDefaultValues().get(MaximumCellSize.class);

        maximumCellSize_0.getRelativeSizeScalar().setValueAndUnits(100.0, units_1);

        ////////////////////////////////////////////////////////////
        AutoMeshOperation autoMeshOperation_1 = simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.dualmesher.DualAutoMesher", "star.resurfacer.ResurfacerAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[]{}));
        autoMeshOperation_1.setPresentationName("Automated Mesh2");

        autoMeshOperation_1.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);

        autoMeshOperation_1.getInputGeometryObjects().setQuery(null);

        CompositePart compositePart_0 = ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Part3"));

        CadPart cadPart_12 = ((CadPart) compositePart_0.getChildParts().getPart("hvac-mode-vent"));

        CadPart cadPart_11 = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-cool"));

        CompositePart compositePart_1 = ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Part3 2"));

        CadPart cadPart_34 = ((CadPart) compositePart_1.getChildParts().getPart("vent-duct-ctr"));

        CadPart cadPart_33 = ((CadPart) compositePart_1.getChildParts().getPart("vent-duct-lh"));

        CadPart cadPart_35 = ((CadPart) compositePart_1.getChildParts().getPart("vent-duct-rh"));

        MeshPart meshPart_0 = ((MeshPart) simulation_0.get(SimulationPartManager.class).getPart("SW_P1_Vent_Grille"));

        autoMeshOperation_1.getInputGeometryObjects().setObjects(cadPart_12, cadPart_11, cadPart_34, cadPart_33, cadPart_35, meshPart_0);

        autoMeshOperation_1.getDefaultValues().get(BaseSize.class).setValueAndUnits(4.0, units_0);

        PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = autoMeshOperation_1.getDefaultValues().get(PartsMinimumSurfaceSize.class);

        partsMinimumSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

        partsMinimumSurfaceSize_1.getAbsoluteSizeValue().setValueAndUnits(0.7, units_0);

        SurfaceCurvature surfaceCurvature_1 = autoMeshOperation_1.getDefaultValues().get(SurfaceCurvature.class);

        surfaceCurvature_1.setNumPointsAroundCircle(72.0);

        PartsResurfacerSurfaceProximity partsResurfacerSurfaceProximity_1 = autoMeshOperation_1.getDefaultValues().get(PartsResurfacerSurfaceProximity.class);

        partsResurfacerSurfaceProximity_1.setNumPointsInGap(3.0);

        NumPrismLayers numPrismLayers_1 = autoMeshOperation_1.getDefaultValues().get(NumPrismLayers.class);

        IntegerValue integerValue_1 = numPrismLayers_1.getNumLayersValue();

        integerValue_1.getQuantity().setValue(5.0);

        PrismThickness prismThickness_1 = autoMeshOperation_1.getDefaultValues().get(PrismThickness.class);

        prismThickness_1.getRelativeSizeScalar().setValueAndUnits(50.0, units_1);

        MaximumCellSize maximumCellSize_1 = autoMeshOperation_1.getDefaultValues().get(MaximumCellSize.class);

        maximumCellSize_1.getRelativeSizeScalar().setValueAndUnits(100.0, units_1);

        ////////////////////////////////////////////////////////////
        AutoMeshOperation autoMeshOperation_2 = simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher", "star.resurfacer.ResurfacerAutoMesher"}), new NeoObjectVector(new Object[]{}));
        autoMeshOperation_2.setPresentationName("Automated Mesh3");

        autoMeshOperation_2.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);

        autoMeshOperation_2.getInputGeometryObjects().setQuery(null);

        CadPart cadPart_10 = ((CadPart) compositePart_0.getChildParts().getPart("hvac-fr"));

        CadPart cadPart_3 = ((CadPart) compositePart_0.getChildParts().getPart("intake-lower"));

        CadPart cadPart_1 = ((CadPart) compositePart_0.getChildParts().getPart("intake-upper-rec"));

        CadPart cadPart_0 = ((CadPart) compositePart_0.getChildParts().getPart("inzone-rec"));

        CadPart cadPart_4 = ((CadPart) compositePart_0.getChildParts().getPart("rot-zone"));

        CadPart cadPart_5 = ((CadPart) compositePart_0.getChildParts().getPart("scroll1"));

        CadPart cadPart_6 = ((CadPart) compositePart_0.getChildParts().getPart("scroll2"));

        autoMeshOperation_2.getInputGeometryObjects().setObjects(cadPart_10, cadPart_3, cadPart_1, cadPart_0, cadPart_4, cadPart_5, cadPart_6);

        autoMeshOperation_2.getDefaultValues().get(BaseSize.class).setValueAndUnits(4.0, units_0);

        PartsMinimumSurfaceSize partsMinimumSurfaceSize_2 = autoMeshOperation_2.getDefaultValues().get(PartsMinimumSurfaceSize.class);

        partsMinimumSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

        partsMinimumSurfaceSize_2.getAbsoluteSizeValue().setValueAndUnits(0.7, units_0);

        SurfaceCurvature surfaceCurvature_2 = autoMeshOperation_2.getDefaultValues().get(SurfaceCurvature.class);

        surfaceCurvature_2.setNumPointsAroundCircle(72.0);

        PartsResurfacerSurfaceProximity partsResurfacerSurfaceProximity_2 = autoMeshOperation_2.getDefaultValues().get(PartsResurfacerSurfaceProximity.class);

        partsResurfacerSurfaceProximity_2.setNumPointsInGap(3.0);

        NumPrismLayers numPrismLayers_2 = autoMeshOperation_2.getDefaultValues().get(NumPrismLayers.class);

        IntegerValue integerValue_2 = numPrismLayers_2.getNumLayersValue();

        integerValue_2.getQuantity().setValue(5.0);

        PrismThickness prismThickness_2 = autoMeshOperation_2.getDefaultValues().get(PrismThickness.class);

        prismThickness_2.getRelativeSizeScalar().setValueAndUnits(50.0, units_1);

        MaximumCellSize maximumCellSize_2 = autoMeshOperation_2.getDefaultValues().get(MaximumCellSize.class);

        maximumCellSize_2.getRelativeSizeScalar().setValueAndUnits(100.0, units_1);
    }
}
