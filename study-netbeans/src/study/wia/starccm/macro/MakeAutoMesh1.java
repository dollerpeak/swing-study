/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.starccm.macro;

import star.base.neo.NeoObjectVector;
import star.base.neo.StringVector;
import star.common.CompositePart;
import star.common.Simulation;
import star.common.SimulationPartManager;
import star.common.StarMacro;
import star.meshing.AutoMeshOperation;
import star.meshing.CadPart;
import star.meshing.MeshOperationManager;
import star.meshing.MesherParallelModeOption;

@SuppressWarnings("unchecked")
public class MakeAutoMesh1 extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {

        Simulation simulation_0 = getActiveSimulation();
        
        AutoMeshOperation autoMeshOperation_4 = simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.trimmer.TrimmerAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[]{}));

        autoMeshOperation_4.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);

        simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.meshing.AutoMeshOperation", "Automated Mesh copy");

        AutoMeshOperation autoMeshOperation_5 = ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh copy"));

        autoMeshOperation_5.copyProperties(autoMeshOperation_4);

        simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.meshing.AutoMeshOperation", "Automated Mesh copy copy");

        AutoMeshOperation autoMeshOperation_6 = ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh copy copy"));

        autoMeshOperation_6.copyProperties(autoMeshOperation_5);

        simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.meshing.AutoMeshOperation", "Automated Mesh copy copy copy");

        AutoMeshOperation autoMeshOperation_7 = ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh copy copy copy"));

        autoMeshOperation_7.copyProperties(autoMeshOperation_6);

        autoMeshOperation_4.getInputGeometryObjects().setQuery(null);

        CompositePart compositePart_0
                = ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Part3"));

        CadPart cadPart_0
                = ((CadPart) compositePart_0.getChildParts().getPart("cond-core"));

        CadPart cadPart_1
                = ((CadPart) compositePart_0.getChildParts().getPart("evap-core"));

        CadPart cadPart_2
                = ((CadPart) compositePart_0.getChildParts().getPart("filter-core"));

        CadPart cadPart_3
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-fr"));

        CadPart cadPart_4
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-mode-bi"));

        CadPart cadPart_5
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-mode-defrost"));

        CadPart cadPart_6
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-mode-floor"));

        CadPart cadPart_7
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-mode-vent"));

        CadPart cadPart_8
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-cool"));

        CadPart cadPart_9
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-temp2"));

        CadPart cadPart_10
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-temp3"));

        CadPart cadPart_11
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-temp4"));

        CadPart cadPart_12
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-temp5"));

        CadPart cadPart_13
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-temp6"));

        CadPart cadPart_14
                = ((CadPart) compositePart_0.getChildParts().getPart("hvac-temp-warm"));

        autoMeshOperation_4.getInputGeometryObjects().setObjects(cadPart_0, cadPart_1, cadPart_2, cadPart_3, cadPart_4, cadPart_5, cadPart_6, cadPart_7, cadPart_8, cadPart_9, cadPart_10, cadPart_11, cadPart_12, cadPart_13, cadPart_14);

        autoMeshOperation_4.setPresentationName("Automated Mesh1");

        autoMeshOperation_5.setPresentationName("Automated Mesh2");

        autoMeshOperation_5.getInputGeometryObjects().setQuery(null);

        CadPart cadPart_15
                = ((CadPart) compositePart_0.getChildParts().getPart("inlet-fre"));

        CadPart cadPart_16
                = ((CadPart) compositePart_0.getChildParts().getPart("inlet-rec"));

        CadPart cadPart_17
                = ((CadPart) compositePart_0.getChildParts().getPart("intake-lower"));

        CadPart cadPart_18
                = ((CadPart) compositePart_0.getChildParts().getPart("intake-upper-fre"));

        CadPart cadPart_19
                = ((CadPart) compositePart_0.getChildParts().getPart("intake-upper-rec"));

        CadPart cadPart_20
                = ((CadPart) compositePart_0.getChildParts().getPart("inzone-fre"));

        CadPart cadPart_21
                = ((CadPart) compositePart_0.getChildParts().getPart("inzone-rec"));

        autoMeshOperation_5.getInputGeometryObjects().setObjects(cadPart_14, cadPart_15, cadPart_16, cadPart_17, cadPart_18, cadPart_19, cadPart_20, cadPart_21);

        autoMeshOperation_6.setPresentationName("Automated Mesh3");

        autoMeshOperation_6.getInputGeometryObjects().setQuery(null);

        CadPart cadPart_22
                = ((CadPart) compositePart_0.getChildParts().getPart("outlet-drain"));

        CadPart cadPart_23
                = ((CadPart) compositePart_0.getChildParts().getPart("outlet-shower-lh"));

        CadPart cadPart_24
                = ((CadPart) compositePart_0.getChildParts().getPart("outlet-shower-rh"));

        CadPart cadPart_25
                = ((CadPart) compositePart_0.getChildParts().getPart("ptc-core"));

        CadPart cadPart_26
                = ((CadPart) compositePart_0.getChildParts().getPart("rot-zone"));

        CadPart cadPart_51
                = ((CadPart) compositePart_0.getChildParts().getPart("scroll1"));

        CadPart cadPart_52
                = ((CadPart) compositePart_0.getChildParts().getPart("scroll2"));

        CadPart cadPart_27
                = ((CadPart) compositePart_0.getChildParts().getPart("shower-duct-lh"));

        CadPart cadPart_28
                = ((CadPart) compositePart_0.getChildParts().getPart("shower-duct-rh"));

        autoMeshOperation_6.getInputGeometryObjects().setObjects(cadPart_22, cadPart_23, cadPart_24, cadPart_25, cadPart_26, cadPart_51, cadPart_52, cadPart_27, cadPart_28);

        autoMeshOperation_7.setPresentationName("Automated Mesh4");

        autoMeshOperation_7.getInputGeometryObjects().setQuery(null);

        CadPart cadPart_29
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-cond-in"));

        CadPart cadPart_30
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-cond-out"));

        CadPart cadPart_31
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-cutoff"));

        CadPart cadPart_32
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-defrost"));

        CadPart cadPart_33
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-drain"));

        CadPart cadPart_34
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-evap-in"));

        CadPart cadPart_35
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-evap-out"));

        CadPart cadPart_36
                = ((CadPart) compositePart_0.getChildParts().getPart("vir-filter-in"));

        CadPart cadPart_37 = ((CadPart) compositePart_0.getChildParts().getPart("vir-filter-out"));

        CadPart cadPart_38 = ((CadPart) compositePart_0.getChildParts().getPart("vir-hvac-temp"));

        CadPart cadPart_39 = ((CadPart) compositePart_0.getChildParts().getPart("vir-in-fre"));

        CadPart cadPart_40 = ((CadPart) compositePart_0.getChildParts().getPart("vir-in-rec"));

        CadPart cadPart_41 = ((CadPart) compositePart_0.getChildParts().getPart("vir-ptc-in"));

        CadPart cadPart_42 = ((CadPart) compositePart_0.getChildParts().getPart("vir-ptc-out"));

        CadPart cadPart_43 = ((CadPart) compositePart_0.getChildParts().getPart("vir-rim"));

        CadPart cadPart_44 = ((CadPart) compositePart_0.getChildParts().getPart("vir-rot-zone"));

        CadPart cadPart_45 = ((CadPart) compositePart_0.getChildParts().getPart("vir-scroll-out"));

        CadPart cadPart_46 = ((CadPart) compositePart_0.getChildParts().getPart("vir-shower-lh"));

        CadPart cadPart_47 = ((CadPart) compositePart_0.getChildParts().getPart("vir-shower-rh"));

        CadPart cadPart_48 = ((CadPart) compositePart_0.getChildParts().getPart("vir-vent-ctr"));

        CadPart cadPart_49 = ((CadPart) compositePart_0.getChildParts().getPart("vir-vent-lh"));

        CadPart cadPart_50 = ((CadPart) compositePart_0.getChildParts().getPart("vir-vent-rh"));

        autoMeshOperation_7.getInputGeometryObjects().setObjects(cadPart_29, cadPart_30, cadPart_31, cadPart_32, cadPart_33, cadPart_34, cadPart_35, cadPart_36, cadPart_37, cadPart_38, cadPart_39, cadPart_40, cadPart_41, cadPart_42, cadPart_43, cadPart_44, cadPart_45, cadPart_46, cadPart_47, cadPart_48, cadPart_49, cadPart_50);
    }
}
