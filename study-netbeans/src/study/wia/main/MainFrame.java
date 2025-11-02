/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package study.wia.main;

import study.wia.common.CommonManager;
import study.wia.common.LoggerManager;
import study.wia.common.ReadExcel;
import hdwia.hvac.enums.SelectKind;
import hdwia.hvac.enums.SelectJList;
import study.wia.sub.mesh.ArtificaialFrame;
import study.wia.sub.mesh.DoorInfoFrame;
import study.wia.sub.mesh.RemeshFrame;
import study.wia.sub.start.StartFrame;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import star.base.neo.NeoObjectVector;
import star.base.neo.StringVector;
import star.base.query.NameOperator;
import star.base.query.NamePredicate;
import star.base.query.Query;
import star.common.CompositePart;
import star.common.GeometryPart;
import star.common.IntegerValue;
import star.common.PartContact;
import star.common.PartContactManager;
import star.common.PartSurface;
import star.common.PartSurfaceManager;
import star.common.ScalarPhysicalQuantity;
import star.common.Simulation;
import star.common.SimulationPartManager;
import star.common.Units;
import star.meshing.AutoMeshOperation;
import star.meshing.BaseSize;
import star.meshing.CadPart;
import star.meshing.MaximumCellSize;
import star.meshing.MeshOperationManager;
import star.meshing.MesherParallelModeOption;
import star.meshing.PartsMinimumSurfaceSize;
import star.meshing.PartsResurfacerSurfaceProximity;
import star.meshing.RelativeOrAbsoluteOption;
import star.meshing.SurfaceCurvature;
import star.prismmesher.NumPrismLayers;
import star.prismmesher.PrismThickness;
import study.wia.function.ListToListMoveDragAndDropHandler;

@SuppressWarnings("this-escape")
public class MainFrame extends javax.swing.JFrame {
    private Simulation simulation;
    private LoggerManager loggerMgr;
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    // sub frame
    private StartFrame startFrame;
    private RemeshFrame remeshFrame;
    private DoorInfoFrame doorInfoFrame;
    private ArtificaialFrame artificaialFrame;
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    // tab mesh
    private DefaultListModel<String> tabMeshDeletePartListModel = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshFluidPartListModel = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshActiveListModel1 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshActiveListModel2 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshActiveListModel3 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshActiveListModel4 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshSolidPartListModel = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshInActiveListModel1 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshInActiveListModel2 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshInActiveListModel3 = new DefaultListModel<>(); 
    private DefaultListModel<String> tabMeshAutoMeshInActiveListModel4 = new DefaultListModel<>();     
    // 엑셀데이터
    Map<String, String> tabMeshExcelAutoMeshInActive1 = new HashMap<>();
    Map<String, String> tabMeshExcelAutoMeshInActive2 = new HashMap<>();
    Map<String, String> tabMeshExcelAutoMeshInActive3 = new HashMap<>();
    Map<String, String> tabMeshExcelAutoMeshInActive4 = new HashMap<>();
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    // star-ccm
    // 동일파트 여부 확인
    Map<String, Integer> samePartMap = new HashMap<>();
    // 선택된 파트의 위치에 따라 composite의 이름을 모두 알아야 함
    // 선택 위치에 다라 composite가 없을 수도 잇음
    List<String> compositeNameList = new ArrayList<>();
    int MAX_COUNT_COMPOSITE = 20;
       
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame(Simulation simulation) {
        this.simulation = simulation;
        
        initLoggerManager();
        initComponents();    
        initialize();
        
        //loggerMgr.info("init MainFrame");
    }

    private MainFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void initLoggerManager() {
        loggerMgr = new LoggerManager(simulation);
    }
    
    private void initialize() {
        initListData();        
        initSubFrame();        
        
        //setTestData();
    }
    
    private void initSubFrame() {
        startFrame = new StartFrame(this);
        startFrame.setLocationRelativeTo(this);
        startFrame.setResizable(false);
        
        remeshFrame = new RemeshFrame(this);
        remeshFrame.setLocationRelativeTo(this);
        remeshFrame.setResizable(false);
        
        doorInfoFrame = new DoorInfoFrame(this);
        doorInfoFrame.setLocationRelativeTo(this);
        doorInfoFrame.setResizable(false);

        artificaialFrame = new ArtificaialFrame(this);
        artificaialFrame.setLocationRelativeTo(this);
        artificaialFrame.setResizable(false);
        
        // 프로세스 실행시 디렉토리 선택부터
        setVisible(false);
        startFrame.setVisible(true);
    }
    
    private void initListData() {
        // list, datamodel
        tabMeshDeletePartList.setModel(tabMeshDeletePartListModel);
        tabMeshFluidPartList.setModel(tabMeshFluidPartListModel);
        tabMeshSolidPartList.setModel(tabMeshSolidPartListModel);
        tabMeshAutoMeshActiveList1.setModel(tabMeshAutoMeshActiveListModel1);
        tabMeshAutoMeshActiveList2.setModel(tabMeshAutoMeshActiveListModel2);
        tabMeshAutoMeshActiveList3.setModel(tabMeshAutoMeshActiveListModel3);
        tabMeshAutoMeshActiveList4.setModel(tabMeshAutoMeshActiveListModel4);        
        tabMeshAutoMeshInActiveList1.setModel(tabMeshAutoMeshInActiveListModel1);
        tabMeshAutoMeshInActiveList2.setModel(tabMeshAutoMeshInActiveListModel2);
        tabMeshAutoMeshInActiveList3.setModel(tabMeshAutoMeshInActiveListModel3);
        tabMeshAutoMeshInActiveList4.setModel(tabMeshAutoMeshInActiveListModel4);
        
        // list, handler
        tabMeshDeletePartList.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "deletePartList"));
        tabMeshFluidPartList.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "fluidPartList"));
        tabMeshSolidPartList.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "solidPartList"));
        tabMeshAutoMeshActiveList1.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "autoMeshActiveList1"));
        tabMeshAutoMeshActiveList2.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "autoMeshActiveList2"));
        tabMeshAutoMeshActiveList3.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "autoMeshActiveList3"));
        tabMeshAutoMeshActiveList4.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "autoMeshActiveList4"));

        // list, 관계
        ListToListMoveDragAndDropHandler.allowPair("deletePartList", "fluidPartList", true);
        ListToListMoveDragAndDropHandler.allowPair("fluidPartList", "solidPartList", true);
        ListToListMoveDragAndDropHandler.allowPair("solidPartList", "autoMeshActiveList1", true);
        ListToListMoveDragAndDropHandler.allowPair("solidPartList", "autoMeshActiveList2", true);
        ListToListMoveDragAndDropHandler.allowPair("solidPartList", "autoMeshActiveList3", true);
        ListToListMoveDragAndDropHandler.allowPair("solidPartList", "autoMeshActiveList4", true);
    }

    /****************************************************************************
    * star-ccm
    ****************************************************************************/
    private void SC_loadPart() {
        loggerMgr.info("SC_loadPart");
        SC_findPart(SelectKind.SELECT_ALL, null);
        
        // 동일 파트 이름 확인
        String name = "";
        for (Entry<String, Integer> entry : samePartMap.entrySet()) {
            if (entry.getValue() > 1) {
                name += entry.getKey() + ", ";
            }
        }
        if (name.length() > 0) {
            CommonManager.showMessageDialog(this, "알림", "동일 Part 이름이 존재합니다.\n" + name, JOptionPane.WARNING_MESSAGE);
        } else {
            loggerMgr.info("동일 Part 이름 없음, name = " + name);
        }
    }
   
    private String SC_findPart(SelectKind findParts, String name) {
        //loggerMgr.info("... SC_findPart, " + findParts.name());

        // composite 이름 저장 초기화
        compositeNameList.clear();

        SimulationPartManager simulationPartManager = simulation.get(SimulationPartManager.class);
        for (GeometryPart geometryPart : simulationPartManager.getParts()) {
            //loggerMgr.info("<============== start");
            // 재귀호출
            String reval = SC_recursiveFindPart(geometryPart, findParts, name);
            //loggerMgr.info("compositeNameList.size() = " + compositeNameList.size());
            //loggerMgr.info("toString() = " + compositeNameList.toString());
            if (reval != null) {
                return reval;
            } else {
                // composite 이름 저장 초기화
                compositeNameList.clear();
            }
            //loggerMgr.info("<============== end");
        }

        return null;
    }
    
    private String SC_recursiveFindPart(GeometryPart geometryPart, SelectKind findParts, String name) {
        String findName;

        if (geometryPart instanceof CadPart) {
            //loggerMgr.info("CadPart, geometryPart.getPresentationName() = " + geometryPart.getPresentationName());            
            findName = SC_detailFindPart((CadPart) geometryPart, findParts, name);
            if (findName != null) {
                return findName;
            }
        } else if (geometryPart instanceof CompositePart) {
            CompositePart compositePart = (CompositePart) geometryPart;
            //loggerMgr.info("CompositePart, compositePart.getPresentationName() = " + compositePart.getPresentationName());
            compositeNameList.add(compositePart.getPresentationName());

            for (GeometryPart child : compositePart.getChildParts().getParts()) {
                if (child instanceof CadPart) {
                    //loggerMgr.info("- CadPart, child.getPresentationName() = " + child.getPresentationName());
                    findName = SC_detailFindPart((CadPart) child, findParts, name);
                    if (findName != null) {
                        return findName;
                    }
                } else if (child instanceof CompositePart) {
                    findName = SC_recursiveFindPart(child, findParts, name);
                    if (findName != null) {
                        return findName;
                    }
                }
            }
        } else {
            //loggerMgr.info("CompositePart, CadPart 이외...");
        }

        return null;
    }
    
    private String SC_detailFindPart(CadPart cadPart, SelectKind findParts, String name) {
        if (findParts == SelectKind.SELECT_ALL) {
            // 대소문자 구분없이 소문자로 변환
            String lowerName = cadPart.getPresentationName().toLowerCase();
            if (lowerName.startsWith("vir")
                    || lowerName.startsWith("outlet")
                    || lowerName.startsWith("inlet")
                    || lowerName.startsWith("wall")) {
                tabMeshDeletePartListModel.addElement(cadPart.getPresentationName());
            } else {
                tabMeshFluidPartListModel.addElement(cadPart.getPresentationName());
            }
            
            // 동일 파트 이름 검사용
            if(true == samePartMap.containsKey(cadPart.getPresentationName())){
                samePartMap.put(cadPart.getPresentationName(), samePartMap.get(cadPart.getPresentationName()) + 1);
            }else {
                samePartMap.put(cadPart.getPresentationName(), 1);
            }            
        } else if (findParts == SelectKind.SELECT_ONE) {
            if (name.equals(cadPart.getPresentationName())) {
                return cadPart.getPresentationName();
            }
        }

        return null;
    }
    
    private boolean SC_renamePart(String currentName, String newName) {
        boolean reval = false;

        CompositePart[] compositePart = new CompositePart[MAX_COUNT_COMPOSITE];
        String partName = SC_findPart(SelectKind.SELECT_ONE, currentName);
        //loggerMgr.info("partName = " + partName);

        if (partName != null) {
            //loggerMgr.info("compositeNameList.size() = " + compositeNameList.size());
            if (compositeNameList.size() <= 0) {
                CadPart cadPart = ((CadPart) simulation.get(SimulationPartManager.class).getPart(partName));
                cadPart.setPresentationName(newName);

                reval = true;
            } else {
                for (int i = 0; i < compositeNameList.size(); i++) {
                    //loggerMgr.info("compositeName = " + compositeNameList.get(i));
                    if (i == 0) {
                        compositePart[i] = ((CompositePart) simulation.get(SimulationPartManager.class).getPart(compositeNameList.get(i)));
                    } else {
                        compositePart[i] = ((CompositePart) compositePart[i - 1].getChildParts().getPart(compositeNameList.get(i)));
                    }

                    // 마지막
                    if (i + 1 >= compositeNameList.size()) {
                        CadPart cadPart = ((CadPart) compositePart[i].getChildParts().getPart(partName));
                        cadPart.setPresentationName(newName);

                        reval = true;
                    }
                }
            }
        }        

        return reval;
    } 
        
    @SuppressWarnings("unchecked")
    private boolean SC_deletePart(String name) {
        boolean reval = false;

        CompositePart[] compositePart = new CompositePart[MAX_COUNT_COMPOSITE];
        String partName = SC_findPart(SelectKind.SELECT_ONE, name);
        //loggerMgr.info("partName = " + partName);

        if (partName != null) {
            //loggerMgr.info("compositeNameList.size() = " + compositeNameList.size());
            if (compositeNameList.size() <= 0) {
                CadPart cadPart = ((CadPart) simulation.get(SimulationPartManager.class).getPart(partName));
                simulation.get(SimulationPartManager.class).removeParts(new NeoObjectVector(new Object[]{cadPart}));
                //loggerMgr.info("delete cadpart");

                reval = true;
            } else {
                for (int i = 0; i < compositeNameList.size(); i++) {
                    //loggerMgr.info("compositeName = " + compositeNameList.get(i));                    
                    if (i == 0) {
                        compositePart[i] = ((CompositePart) simulation.get(SimulationPartManager.class).getPart(compositeNameList.get(i)));
                        //loggerMgr.info("delete compositePart, 최초, compositeNameList.get(i) = " + compositeNameList.get(i));
                    } else {
                        compositePart[i] = ((CompositePart) compositePart[i - 1].getChildParts().getPart(compositeNameList.get(i)));
                        //loggerMgr.info("delete compositePart, 중간, compositeNameList.get(i) = " + compositeNameList.get(i));
                    }
                }

                //loggerMgr.info("delete compositePart, 마지막, partName = " + partName);
                CadPart cadPart = ((CadPart) compositePart[compositeNameList.size() - 1].getChildParts().getPart(partName));
                compositePart[compositeNameList.size() - 1].getChildParts().removeParts(new NeoObjectVector(new Object[]{cadPart}));

                reval = true;
            }
        }

        return reval;
    }
    
    private void SC_deleteAllContacts() {
        loggerMgr.info("SC_deleteAllContacts");
        
        PartContactManager partContactManager = simulation.get(PartContactManager.class);
        Collection<PartContact> allContacts = partContactManager.getObjects();
        
        for (PartContact partContact : allContacts) {
            //loggerMgr.info("partContact.getPresentationName() = " + partContact.getPresentationName());
            simulation.get(PartContactManager.class).removeObjects(partContact);
        }
    }
    
    private void SC_surfaceNameCombine() {
        loggerMgr.info("SC_surfaceName");
        SimulationPartManager simulationPartManager = simulation.get(SimulationPartManager.class);
        
        for (GeometryPart geometryPart : simulationPartManager.getParts()) {
            //loggerMgr.info("SC_surfaceName, loop");
            SC_recursiveSurfaceNameCombine(geometryPart);
        }
    }
    
    private void SC_recursiveSurfaceNameCombine(GeometryPart geometryPart) {
        if (geometryPart instanceof CadPart) {
            SC_detailSurfaceNameCombine((CadPart) geometryPart);
        } else if (geometryPart instanceof CompositePart) {
            CompositePart compositePart = (CompositePart) geometryPart;
            for (GeometryPart child : compositePart.getChildParts().getParts()) {
                SC_recursiveSurfaceNameCombine(child);
            }
        } else {
            //loggerMgr.info("CompositePart, CadPart 이외...");
        }
    }
    
    @SuppressWarnings("unchecked")
    private void SC_detailSurfaceNameCombine(CadPart cadPart) {
        PartSurfaceManager partSurfaceManager = cadPart.getPartSurfaceManager();
        Collection<PartSurface> allSurfs = partSurfaceManager.getPartSurfaces();

        List<PartSurface> solidList = new ArrayList<>();
        List<PartSurface> coloredList = new ArrayList<>();

        // surface 목록 가져오기
        for (PartSurface ps : allSurfs) {
            // 대소문자 구분없이 소문자로 변환
            String lower = ps.getPresentationName().toLowerCase();
            if (lower.startsWith("solid.")) {
                solidList.add(ps);
            } else if (lower.startsWith("colored")) {
                coloredList.add(ps);
            }
        }

        // 처리할 대상 없음
        if (solidList.isEmpty() && coloredList.isEmpty()) {
            //loggerMgr.info("병합대상 없음, 대상문자열 = solid. , colored");
            return; 
        }

        // solid. 병합
        if (solidList.size() > 1) {
            //cadPart.combinePartSurfaces(new NeoObjectVector(solidList.toArray(new PartSurface[0])));
            cadPart.combinePartSurfaces(new NeoObjectVector(solidList.toArray(PartSurface[]::new)));
            //loggerMgr.info("solid. 병합, cadPart.getPresentationName() = " + cadPart.getPresentationName());
        }
        
        // colored 병합
        if (coloredList.size() > 1) {
            //cadPart.combinePartSurfaces(new NeoObjectVector(coloredList.toArray(new PartSurface[0])));
            cadPart.combinePartSurfaces(new NeoObjectVector(coloredList.toArray(PartSurface[]::new)));
            //loggerMgr.info("colored 병합, cadPart.getPresentationName() = " + cadPart.getPresentationName());
        }

        // surface 목록 가져오기
        List<PartSurface> candidates = new ArrayList<>();
        for (PartSurface ps : partSurfaceManager.getPartSurfaces()) {
            // 대소문자 구분없이 소문자로 변환
            String lower = ps.getPresentationName().toLowerCase();
            if (lower.startsWith("solid.") || lower.startsWith("colored")) {
                candidates.add(ps);
            }
        }

        if (!candidates.isEmpty()) {
            // solid + colored 를 최종 병합
            if (candidates.size() > 1) {
                //cadPart.combinePartSurfaces(new NeoObjectVector(candidates.toArray(new PartSurface[0])));
                cadPart.combinePartSurfaces(new NeoObjectVector(candidates.toArray(PartSurface[]::new)));
                //loggerMgr.info("solid. || colored 병합, cadPart.getPresentationName() = " + cadPart.getPresentationName());
            }

            // surface 목록 가져오기
            for (PartSurface ps : partSurfaceManager.getPartSurfaces()) {
                // 대소문자 구분없이 소문자로 변환
                String lower = ps.getPresentationName().toLowerCase();
                if (lower.startsWith("solid.") || lower.startsWith("colored")) {
                    String newName = "wall-" + cadPart.getPresentationName();
                    ps.setPresentationName(newName);
                    
                    //loggerMgr.info("solid. || colored, wall- rename = " + cadPart.getPresentationName());
                    // 하나만 이름 변경하면 됨
                    break; 
                }
            }
        } else {
            //loggerMgr.info("solid. || colored 병합없음");
        }
    }
    
    @SuppressWarnings("unchecked")
    private void SC_makeAutoMesh(Map<String, String> autoMeshActive) {
        // part 정보가 있을때만
        if (autoMeshActive.get("Parts").length() > 0) {
//        AutoMeshOperation autoMeshOperation_0 = simulation.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.trimmer.TrimmerAutoMesher", "star.resurfacer.ResurfacerAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[]{}));
//        autoMeshOperation_0.setPresentationName("Automated Mesh1");
//        autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
//        autoMeshOperation_0.getInputGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "core"), Query.STANDARD_MODIFIERS));
//
//        AutoMeshOperation autoMeshOperation_1 = simulation.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.dualmesher.DualAutoMesher", "star.resurfacer.PrismAutoMesher", "star.prismmesher.ResurfacerAutoMesher"}), new NeoObjectVector(new Object[]{}));
//        autoMeshOperation_1.setPresentationName("Automated Mesh2");
//        autoMeshOperation_1.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
//        autoMeshOperation_1.getInputGeometryObjects().setQuery(null);
//
//        AutoMeshOperation autoMeshOperation_2 = simulation.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[]{"star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher", "star.resurfacer.ResurfacerAutoMesher"}), new NeoObjectVector(new Object[]{}));
//        autoMeshOperation_2.setPresentationName("Automated Mesh3");
//        autoMeshOperation_2.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
//        autoMeshOperation_2.getInputGeometryObjects().setQuery(null);
            ////////////////////////////////////////////////////////////////////
        
        AutoMeshOperation autoMeshOperation = simulation.get(MeshOperationManager.class).createAutoMeshOperation(
                    new StringVector(new String[]{
                "star.dualmesher.DualAutoMesher",
                "star.prismmesher.PrismAutoMesher",
                "star.resurfacer.ResurfacerAutoMesher"}), new NeoObjectVector(new Object[]{}));
            autoMeshOperation.setPresentationName("Automated Mesh1");
            autoMeshOperation.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
            autoMeshOperation.getInputGeometryObjects().setQuery(null);

            // 파트가져오기
            String[] partNameArray = autoMeshActive.get("Parts").split(",");
            for (int i = 0; i < partNameArray.length; i++) {
                partNameArray[i] = partNameArray[i].trim();
            }

            CadPart[] cadPartArray = new CadPart[partNameArray.length];
            for (int j = 0; j < partNameArray.length; j++) {
                //loggerMgr.info("trim, partNameArray = " + partNameArray[j]);

                CompositePart[] compositePart = new CompositePart[MAX_COUNT_COMPOSITE];
                CadPart cadPart = null;
                String partName = SC_findPart(SelectKind.SELECT_ONE, partNameArray[j]);
                //loggerMgr.info("partName = " + partName);

                if (partName != null) {
                    //loggerMgr.info("compositeNameList.size() = " + compositeNameList.size());
                    if (compositeNameList.size() <= 0) {
                        cadPart = ((CadPart) simulation.get(SimulationPartManager.class).getPart(partName));
                    } else {
                        for (int i = 0; i < compositeNameList.size(); i++) {
                            //loggerMgr.info("compositeName = " + compositeNameList.get(i));
                            if (i == 0) {
                                compositePart[i] = ((CompositePart) simulation.get(SimulationPartManager.class).getPart(compositeNameList.get(i)));
                            } else {
                                compositePart[i] = ((CompositePart) compositePart[i - 1].getChildParts().getPart(compositeNameList.get(i)));
                            }

                            // 마지막
                            if (i + 1 >= compositeNameList.size()) {
                                cadPart = ((CadPart) compositePart[i].getChildParts().getPart(partName));
                            }
                        }
                    }

                    cadPartArray[j] = cadPart;
                    //loggerMgr.info("cadPart = " + cadPart.getPresentationName());
                }
            }
            autoMeshOperation.getInputGeometryObjects().setObjects(cadPartArray);

            // unit
            Units baseSizeUnits = simulation.getUnitsManager().getObject(autoMeshActive.get("Base Size-unit"));
            Units units = simulation.getUnitsManager().getObject("");

            autoMeshOperation.getDefaultValues().get(BaseSize.class).setValueAndUnits(Float.parseFloat(autoMeshActive.get("Base Size")), baseSizeUnits);

            PartsMinimumSurfaceSize partsMinimumSurfaceSize = autoMeshOperation.getDefaultValues().get(PartsMinimumSurfaceSize.class);
            partsMinimumSurfaceSize.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
            partsMinimumSurfaceSize.getAbsoluteSizeValue().setValueAndUnits(Double.parseDouble(autoMeshActive.get("Absolute")), baseSizeUnits);

            SurfaceCurvature surfaceCurvature = autoMeshOperation.getDefaultValues().get(SurfaceCurvature.class);
            surfaceCurvature.setNumPointsAroundCircle(Double.parseDouble(autoMeshActive.get("Surface Curvature")));

            PartsResurfacerSurfaceProximity partsResurfacerSurfaceProximity = autoMeshOperation.getDefaultValues().get(PartsResurfacerSurfaceProximity.class);
            partsResurfacerSurfaceProximity.setNumPointsInGap(Double.parseDouble(autoMeshActive.get("Points in gap")));

            NumPrismLayers numPrismLayers = autoMeshOperation.getDefaultValues().get(NumPrismLayers.class);
            IntegerValue integerValue = numPrismLayers.getNumLayersValue();
            integerValue.getQuantity().setValue(Double.parseDouble(autoMeshActive.get("Number of Prism Layers")));

            PrismThickness prismThickness = autoMeshOperation.getDefaultValues().get(PrismThickness.class);
            prismThickness.getRelativeSizeScalar().setValueAndUnits(Double.parseDouble(autoMeshActive.get("Prism Layer Total Thickness")), units);

            MaximumCellSize maximumCellSize = autoMeshOperation.getDefaultValues().get(MaximumCellSize.class);
            maximumCellSize.getRelativeSizeScalar().setValueAndUnits(Double.parseDouble(autoMeshActive.get("Maximum Tet Size")), units);
        }
    }

    
    /****************************************************************************
    * funtion
    ****************************************************************************/
    public LoggerManager getLoggerManager(){
        return loggerMgr;
    }
    
    public Simulation getSimulation(){
        return simulation;
    }
    
    public StartFrame getStartFrame(){
        return startFrame;
    }
    
//    public void setVisibleFrame() {
//        if (startFrame.isActive() == true) {
//            startFrame.setVisible(true);
//        } else if (remeshFrame.isActive() == true) {
//            remeshFrame.setVisible(true);
//        } else if (doorInfoFrame.isActive() == true) {
//            doorInfoFrame.setVisible(true);
//        } else if (artificaialFrame.isActive() == true) {
//            artificaialFrame.setVisible(true);
//        } else {
//            setVisible(true);
//        }
//    }

    public void uiInitialize(String excelPath) {
        // part정보 로드
        SC_loadPart();
        
        // 엑셀파일 정보 로드
        loadExcel(excelPath);
        
        // automesh list 초기셋팅
        initAutoMeshList();
    }
    
    private void initAutoMeshList() {
        // 엑셀파일 기반으로 automesh에 포함된 part는 fluid 파트에서 빼서 옮겨두기

        setAutoMeshActiveList(tabMeshFluidPartListModel, tabMeshAutoMeshInActiveListModel1, tabMeshAutoMeshActiveListModel1);
        setAutoMeshActiveList(tabMeshFluidPartListModel, tabMeshAutoMeshInActiveListModel2, tabMeshAutoMeshActiveListModel2);
        setAutoMeshActiveList(tabMeshFluidPartListModel, tabMeshAutoMeshInActiveListModel3, tabMeshAutoMeshActiveListModel3);
        setAutoMeshActiveList(tabMeshFluidPartListModel, tabMeshAutoMeshInActiveListModel4, tabMeshAutoMeshActiveListModel4);        

        // 데이터가 없는 리스트는 비활성화
        if (tabMeshAutoMeshActiveListModel1.size() <= 0) {
            tabMeshAutoMeshActiveList1.setEnabled(false);
            //autoMeshActiveList1.setDragEnabled(false);
            tabMeshAutoMeshActiveList1.setBackground(new Color(235, 235, 235));
            //
            tabMeshAutoMeshInActiveList1.setBackground(new Color(235, 235, 235));
            tabMeshAutoMeshInActiveTextArea1.setBackground(new Color(235, 235, 235));
        }
        if (tabMeshAutoMeshActiveListModel2.size() <= 0) {
            tabMeshAutoMeshActiveList2.setEnabled(false);
            //autoMeshActiveList2.setDragEnabled(false);
            tabMeshAutoMeshActiveList2.setBackground(new Color(235, 235, 235));
            //
            tabMeshAutoMeshInActiveList2.setBackground(new Color(235, 235, 235));
            tabMeshAutoMeshInActiveTextArea2.setBackground(new Color(235, 235, 235));
        }
        if (tabMeshAutoMeshActiveListModel3.size() <= 0) {
            tabMeshAutoMeshActiveList3.setEnabled(false);
            //autoMeshActiveList3.setDragEnabled(false);
            tabMeshAutoMeshActiveList3.setBackground(new Color(235, 235, 235));
            //
            tabMeshAutoMeshInActiveList3.setBackground(new Color(235, 235, 235));
            tabMeshAutoMeshInActiveTextArea3.setBackground(new Color(235, 235, 235));
        }
        if (tabMeshAutoMeshActiveListModel4.size() <= 0) {
            tabMeshAutoMeshActiveList4.setEnabled(false);
            //autoMeshActiveList4.setDragEnabled(false);
            tabMeshAutoMeshActiveList4.setBackground(new Color(235, 235, 235));
            //
            tabMeshAutoMeshInActiveList4.setBackground(new Color(235, 235, 235));
            tabMeshAutoMeshInActiveTextArea4.setBackground(new Color(235, 235, 235));
            
        }
    }

    private void setAutoMeshActiveList(DefaultListModel<String> baseModel, DefaultListModel<String> inActiveModel, DefaultListModel<String> activeModel) {
        int i, j;

        for (i = 0; i < inActiveModel.size(); i++) {
            for (j = baseModel.size() - 1; j >= 0; j--) {
                if (true == inActiveModel.get(i).equals(baseModel.get(j))) {
                    baseModel.remove(j);
                    activeModel.addElement(inActiveModel.get(i));
                    break;
                }
            }
        }
    }
    
    private void makeAutoMesh() {
        //Map<String, String> test = makeDetailAutoMesh(new HashMap<>(excelAutoMeshInActive1), autoMeshActiveListModel1);        
        //loggerMgr.info("원본 = " + excelAutoMeshInActive1.get("Parts"));
        //loggerMgr.info("사본 = " + test.get("Parts"));
        
        SC_makeAutoMesh(makeDetailAutoMesh(new HashMap<>(tabMeshExcelAutoMeshInActive1), tabMeshAutoMeshActiveListModel1));
        SC_makeAutoMesh(makeDetailAutoMesh(new HashMap<>(tabMeshExcelAutoMeshInActive2), tabMeshAutoMeshActiveListModel2));
        SC_makeAutoMesh(makeDetailAutoMesh(new HashMap<>(tabMeshExcelAutoMeshInActive3), tabMeshAutoMeshActiveListModel3));
        SC_makeAutoMesh(makeDetailAutoMesh(new HashMap<>(tabMeshExcelAutoMeshInActive4), tabMeshAutoMeshActiveListModel4));
    }
    
    private Map<String, String> makeDetailAutoMesh(Map<String, String> excelAutoMeshInActive, DefaultListModel<String> autoMeshActiveListModel) {
        // exel part 데이터 형식
        //cond-core, evap-core, filter-core, ptc-core
        String partName = "";
        Map<String, String> makeAutoMeshMap = excelAutoMeshInActive;

        for (int i = 0; i < autoMeshActiveListModel.getSize(); i++) {
            // 마지막 데이터 처리
            if (i + 1 >= autoMeshActiveListModel.getSize()) {
                partName += autoMeshActiveListModel.getElementAt(i);
            } else {
                partName += autoMeshActiveListModel.getElementAt(i) + ",";
            }
        }
        // 초기화
        makeAutoMeshMap.put("Parts", "");
        // 새로적용
        makeAutoMeshMap.put("Parts", partName);

        return makeAutoMeshMap;
    }
    
    private void loadExcel(String excelPath) {
        try {
            // 읽을 엑셀 파일 경로
            //Path excelFile = Path.of("C:\\Users\\cadit\\OneDrive\\바탕 화면\\kht\\doc\\automesh관련_20250919\\porous_media_macro_R2_automation.xlsm"); 
            Path excelFile = Path.of(excelPath);
            ReadExcel reader = ReadExcel.open(excelFile);

            // 모든 시트명 출력
            List<String> allTab = reader.getSheetNames();
            loggerMgr.info("allTab = " + allTab.toString());

            String selectTab = "AutomatedMesh";

            // automesh
            if (allTab.contains(selectTab) == true) {
                // - parts
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B4"), reader.get(selectTab, "B5"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G4"), reader.get(selectTab, "G5"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L4"), reader.get(selectTab, "L5"));

                // - meshers
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B7"), reader.get(selectTab, "B8") + "," + reader.get(selectTab, "B9") + "," + reader.get(selectTab, "B10"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G7"), reader.get(selectTab, "G8") + "," + reader.get(selectTab, "G9") + "," + reader.get(selectTab, "G10"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L7"), reader.get(selectTab, "L8") + "," + reader.get(selectTab, "L9") + "," + reader.get(selectTab, "L10"));

                // - Prism Layer Mesher	
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B15"), reader.get(selectTab, "C15"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G15"), reader.get(selectTab, "H15"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L15"), reader.get(selectTab, "M15"));

                // - Stretching Function
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B16"), reader.get(selectTab, "C16"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G16"), reader.get(selectTab, "H16"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L16"), reader.get(selectTab, "M16"));

                // - Distribution Mode
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B17"), reader.get(selectTab, "C17"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G17"), reader.get(selectTab, "H17"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L17"), reader.get(selectTab, "M17"));

                // - Gap Fill Percentage
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B18"), reader.get(selectTab, "C18"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G18"), reader.get(selectTab, "H18"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L18"), reader.get(selectTab, "M18"));

                // - Minimum Thickness Percentage
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B19"), reader.get(selectTab, "C19"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G19"), reader.get(selectTab, "H19"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L19"), reader.get(selectTab, "M19"));

                // - Layer Reduction Percentage
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B20"), reader.get(selectTab, "C20"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G20"), reader.get(selectTab, "H20"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L20"), reader.get(selectTab, "M20"));

                // - Boundary March Angle
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B21"), reader.get(selectTab, "C21"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G21"), reader.get(selectTab, "H21"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L21"), reader.get(selectTab, "M21"));

                // - Concave Angle Limit
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B22"), reader.get(selectTab, "C22"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G22"), reader.get(selectTab, "H22"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L22"), reader.get(selectTab, "M22"));

                // - Convex Angle Limit
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B23"), reader.get(selectTab, "C23"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G23"), reader.get(selectTab, "H23"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L23"), reader.get(selectTab, "M23"));

                // - Near Core Layer Aspect Ratio
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B24"), reader.get(selectTab, "C24"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G24"), reader.get(selectTab, "H24"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L24"), reader.get(selectTab, "M24"));

                // - Default Controls
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B26"), reader.get(selectTab, "C26"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G26"), reader.get(selectTab, "H26"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L26"), reader.get(selectTab, "M26"));

                // - Base Size
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B28"), reader.get(selectTab, "C28"));
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B28") + "-unit", reader.get(selectTab, "D28"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G28"), reader.get(selectTab, "H28"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G28") + "-unit", reader.get(selectTab, "I28"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L28"), reader.get(selectTab, "M28"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L28") + "-unit", reader.get(selectTab, "N28"));

                // - Target Surface Size
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B29"), reader.get(selectTab, "C29"));
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "D29"), reader.get(selectTab, "E29"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G29"), reader.get(selectTab, "H29"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "I29"), reader.get(selectTab, "J29"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L29"), reader.get(selectTab, "M29"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "N29"), reader.get(selectTab, "O29"));

                // - Minimum Surface Size
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B31"), reader.get(selectTab, "C31"));
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "D31"), reader.get(selectTab, "E31"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G31"), reader.get(selectTab, "H31"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "I31"), reader.get(selectTab, "J31"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L31"), reader.get(selectTab, "M31"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "N31"), reader.get(selectTab, "O31"));

                // - Surface Curvature
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B32"), reader.get(selectTab, "C32"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G32"), reader.get(selectTab, "H32"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L32"), reader.get(selectTab, "M32"));

                // - Surface Proximity
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B33"), reader.get(selectTab, "C33"));
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "D33"), reader.get(selectTab, "E33"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G33"), reader.get(selectTab, "H33"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "I33"), reader.get(selectTab, "J33"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L33"), reader.get(selectTab, "M33"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "N33"), reader.get(selectTab, "O33"));

                // - Number of Prism Layers
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B34"), reader.get(selectTab, "C34"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G34"), reader.get(selectTab, "H34"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L34"), reader.get(selectTab, "M34"));

                // - Prism Layer Stretching
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B35"), reader.get(selectTab, "C35"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G35"), reader.get(selectTab, "H35"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L35"), reader.get(selectTab, "M35"));

                // - Prism Layer Total Thickness
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B36"), reader.get(selectTab, "C36"));
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "D36"), reader.get(selectTab, "E36"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G36"), reader.get(selectTab, "H36"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "I36"), reader.get(selectTab, "J36"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L36"), reader.get(selectTab, "M36"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "N36"), reader.get(selectTab, "O36"));

                // - Volume Growth Rate
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B37"), reader.get(selectTab, "C37"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G37"), reader.get(selectTab, "H37"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L37"), reader.get(selectTab, "M37"));

                // - Maximum Tet Size
                tabMeshExcelAutoMeshInActive1.put(reader.get(selectTab, "B38"), reader.get(selectTab, "C38"));
                tabMeshExcelAutoMeshInActive2.put(reader.get(selectTab, "G38"), reader.get(selectTab, "H38"));
                tabMeshExcelAutoMeshInActive3.put(reader.get(selectTab, "L38"), reader.get(selectTab, "M38"));

                // ui에 적용
                setAutoMeshInActive(tabMeshExcelAutoMeshInActive1, tabMeshAutoMeshInActiveListModel1, tabMeshAutoMeshInActiveTextArea1);
                setAutoMeshInActive(tabMeshExcelAutoMeshInActive2, tabMeshAutoMeshInActiveListModel2, tabMeshAutoMeshInActiveTextArea2);
                setAutoMeshInActive(tabMeshExcelAutoMeshInActive3, tabMeshAutoMeshInActiveListModel3, tabMeshAutoMeshInActiveTextArea3);
                setAutoMeshInActive(tabMeshExcelAutoMeshInActive4, tabMeshAutoMeshInActiveListModel4, tabMeshAutoMeshInActiveTextArea4);

//                // 테스트
//                // 데이터 확인 테스트
//                //loggerMgr.info("excelAutoMeshInActive1 = " + excelAutoMeshInActive1.toString());
//                //loggerMgr.info("excelAutoMeshInActive2 = " + excelAutoMeshInActive2.toString());
//                //loggerMgr.info("excelAutoMeshInActive3 = " + excelAutoMeshInActive3.toString());
//                
//                // 데이터 적용 테스트
//                //SC_makeAutoMesh(excelAutoMeshInActive1);
            } else {
                CommonManager.showMessageDialog(this, "알림", "파일에 " + selectTab + " 탭이 존재하지 않습니다.", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            loggerMgr.error("e = " + e.toString());
            CommonManager.showMessageDialog(this, "알림", "파일을 읽는 중 문제가 발생했습니다.", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void setAutoMeshInActive(Map<String, String> excelAutoMeshInActive, DefaultListModel<String> autoMeshInActiveModel, JTextArea autoMeshInActiveTextArea) {
        String[] partNameArray;
        String textArea = "";
        
        if(excelAutoMeshInActive.size() > 0){
            // - Parts
            partNameArray = excelAutoMeshInActive.get("Parts").split(",");
            for (int i = 0; i < partNameArray.length; i++) {
                partNameArray[i] = partNameArray[i].trim();
                autoMeshInActiveModel.addElement(partNameArray[i]);
            }
            
            textArea = "";
            textArea += "Meshers : " + excelAutoMeshInActive.get("Meshers") + "\n";
            textArea += "Prism Layer Mesher : " + excelAutoMeshInActive.get("Prism Layer Mesher") + "\n";
            textArea += "Stretching Function : " + excelAutoMeshInActive.get("Stretching Function") + "\n";
            textArea += "Distribution Mode : " + excelAutoMeshInActive.get("Distribution Mode") + "\n";
            textArea += "Gap Fill Percentage : " + excelAutoMeshInActive.get("Gap Fill Percentage") + "\n";
            textArea += "Minimum Thickness Percentage : " + excelAutoMeshInActive.get("Minimum Thickness Percentage") + "\n";
            textArea += "Layer Reduction Percentage : " + excelAutoMeshInActive.get("Layer Reduction Percentage") + "\n";
            textArea += "Boundary March Angle : " + excelAutoMeshInActive.get("Boundary March Angle") + "\n";
            textArea += "Concave Angle Limit : " + excelAutoMeshInActive.get("Concave Angle Limit") + "\n";
            textArea += "Convex Angle Limit : " + excelAutoMeshInActive.get("Convex Angle Limit") + "\n";
            textArea += "Near Core Layer Aspect Ratio : " + excelAutoMeshInActive.get("Near Core Layer Aspect Ratio") + "\n";
            textArea += "Default Controls : " + excelAutoMeshInActive.get("Default Controls") + "\n";
            textArea += "Base Size : " + excelAutoMeshInActive.get("Base Size") + " " + excelAutoMeshInActive.get("Base Size-unit") + "\n";
            textArea += "Target Surface Size : " + excelAutoMeshInActive.get("Target Surface Size") + "\n";
            textArea += "Relative to base : " + excelAutoMeshInActive.get("Relative to base") + "\n";
            textArea += "Minimum Surface Size : " + excelAutoMeshInActive.get("Minimum Surface Size") + "\n";
            textArea += "Absolute : " + excelAutoMeshInActive.get("Absolute") + "\n";
            textArea += "Surface Curvature : " + excelAutoMeshInActive.get("Surface Curvature") + "\n";
            textArea += "Surface Proximity : " + excelAutoMeshInActive.get("Surface Proximity") + "\n";
            textArea += "Points in gap : " + excelAutoMeshInActive.get("Points in gap") + "\n";
            textArea += "Number of Prism Layers : " + excelAutoMeshInActive.get("Number of Prism Layers") + "\n";
            textArea += "Prism Layer Stretching : " + excelAutoMeshInActive.get("Prism Layer Stretching") + "\n";
            textArea += "Prism Layer Total Thickness : " + excelAutoMeshInActive.get("Prism Layer Total Thickness") + "\n";
            textArea += "Volume Growth Rate : " + excelAutoMeshInActive.get("Volume Growth Rate") + "\n";
            textArea += "Maximum Tet Size : " + excelAutoMeshInActive.get("Maximum Tet Size") + "\n";
            //
            autoMeshInActiveTextArea.setText(textArea);
        }
    }
    
    private void deleteListData(JList<String> list, DefaultListModel<String> model, SelectJList selctJList, SelectKind selectKind) {
        int[] index;
        int result;
        boolean allDelete = true;

        if (selectKind == SelectKind.SELECT_ONE) {
            // 리스트에서 마우스 선택 (한개일수도 ctl+a로 전체일수도)
            index = list.getSelectedIndices();
            Arrays.sort(index);

            if (index.length > 0) {
                result = CommonManager.showConfirmDialog(this, "삭제", "선택한 항목을 삭제하시겠습니까?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    for (int i = index.length - 1; i >= 0; i -= 1) {
                        //loggerMgr.info("delete name = " + model.get(index[i]));                    
                        if (true == SC_deletePart(model.get(index[i]))) {
                            model.remove(index[i]);
                        } else {
                            allDelete = false;
                            CommonManager.showMessageDialog(this, "알림", "다시 시도해 주세요.", JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    if (allDelete == true) {
                        if (selctJList == SelectJList.MAIN_DELETE) {
                            //loggerMgr.info("delete list, delete");
                            SC_deleteAllContacts();
                            SC_surfaceNameCombine();
                        }
                    }
                } else {
                    // 취소
                }
            }
        } else if (selectKind == SelectKind.SELECT_ALL) {
            // delete 버튼, 전체삭제
            for (int i = model.getSize() - 1; i >= 0; i -= 1) {
                if (true == SC_deletePart(model.get(i))) {
                    model.remove(i);
                } else {
                    allDelete = false;
                }
            }

            if (allDelete == false) {
                CommonManager.showMessageDialog(this, "알림", "다시 시도해 주세요.", JOptionPane.WARNING_MESSAGE);
            } else {
                SC_deleteAllContacts();
                SC_surfaceNameCombine();
            }
        }
    }

    private void renameListData(JList<String> list, DefaultListModel<String> model, SelectJList seelctJList) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            String currentValue = model.getElementAt(selectedIndex);

            // 입력 다이얼로그 표시
            String newValue = CommonManager.showInputDialog(this, "수정할 내용을 입력하세요.", currentValue);

            // 취소했거나 빈 문자열이면 변경하지 않음
            if (newValue != null) {
                newValue = newValue.trim(); // 공백 제거
                if (newValue.length() > 0) {
                    if(true == SC_renamePart(currentValue, newValue)){
                        model.setElementAt(newValue, selectedIndex);
                    } else {
                        CommonManager.showMessageDialog(this, "알림", "다시 시도해 주세요.", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    CommonManager.showMessageDialog(this, "알림", "빈 값으로 변경할 수 없습니다.", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // 취소
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel21 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        mesh = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabMeshDeletePartList = new javax.swing.JList<>();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabMeshFluidPartList = new javax.swing.JList<>();
        tabMeshDeleteAllButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabMeshAutoMeshActiveList1 = new javax.swing.JList<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabMeshAutoMeshActiveList2 = new javax.swing.JList<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabMeshAutoMeshActiveList3 = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabMeshAutoMeshActiveList4 = new javax.swing.JList<>();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabMeshSolidPartList = new javax.swing.JList<>();
        tabMeshAutoMeshConfirmButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        tabMeshRadioButton2 = new javax.swing.JRadioButton();
        tabMeshRadioButton3 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveList1 = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveTextArea1 = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveList2 = new javax.swing.JList<>();
        jScrollPane11 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveTextArea2 = new javax.swing.JTextArea();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveList3 = new javax.swing.JList<>();
        jScrollPane13 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveTextArea3 = new javax.swing.JTextArea();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveList4 = new javax.swing.JList<>();
        jScrollPane15 = new javax.swing.JScrollPane();
        tabMeshAutoMeshInActiveTextArea4 = new javax.swing.JTextArea();
        jPanel20 = new javax.swing.JPanel();
        tabMeshArtificialDuctSetupButton = new javax.swing.JButton();
        tabMeshDoorInfoButton = new javax.swing.JButton();
        tabMeshRemeshButton = new javax.swing.JButton();
        tabMeshSkipButton = new javax.swing.JButton();
        setup = new javax.swing.JPanel();
        post = new javax.swing.JPanel();
        solving = new javax.swing.JPanel();
        report = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("현대위아 HVAC 해석");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Delete Part List"));

        tabMeshDeletePartList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshDeletePartList.setDragEnabled(true);
        tabMeshDeletePartList.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshDeletePartList.setPrototypeCellValue("width");
        tabMeshDeletePartList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshDeletePartListMouseClicked(evt);
            }
        });
        tabMeshDeletePartList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshDeletePartListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabMeshDeletePartList);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Fluid Part List"));

        tabMeshFluidPartList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshFluidPartList.setDragEnabled(true);
        tabMeshFluidPartList.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshFluidPartList.setPrototypeCellValue("width");
        tabMeshFluidPartList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshFluidPartListMouseClicked(evt);
            }
        });
        tabMeshFluidPartList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshFluidPartListKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tabMeshFluidPartList);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMeshDeleteAllButton1.setText("전체삭제");
        tabMeshDeleteAllButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabMeshDeleteAllButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabMeshDeleteAllButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabMeshDeleteAllButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh Part List"));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 1"));

        tabMeshAutoMeshActiveList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshActiveList1.setDragEnabled(true);
        tabMeshAutoMeshActiveList1.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshAutoMeshActiveList1.setPrototypeCellValue("width");
        tabMeshAutoMeshActiveList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshAutoMeshActiveList1MouseClicked(evt);
            }
        });
        tabMeshAutoMeshActiveList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshAutoMeshActiveList1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabMeshAutoMeshActiveList1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 2"));

        tabMeshAutoMeshActiveList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshActiveList2.setDragEnabled(true);
        tabMeshAutoMeshActiveList2.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshAutoMeshActiveList2.setPrototypeCellValue("width");
        tabMeshAutoMeshActiveList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshAutoMeshActiveList2MouseClicked(evt);
            }
        });
        tabMeshAutoMeshActiveList2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshAutoMeshActiveList2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tabMeshAutoMeshActiveList2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 3"));

        tabMeshAutoMeshActiveList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshActiveList3.setDragEnabled(true);
        tabMeshAutoMeshActiveList3.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshAutoMeshActiveList3.setPrototypeCellValue("width");
        tabMeshAutoMeshActiveList3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshAutoMeshActiveList3MouseClicked(evt);
            }
        });
        tabMeshAutoMeshActiveList3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshAutoMeshActiveList3KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tabMeshAutoMeshActiveList3);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 4"));

        tabMeshAutoMeshActiveList4.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshActiveList4.setDragEnabled(true);
        tabMeshAutoMeshActiveList4.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshAutoMeshActiveList4.setPrototypeCellValue("width");
        tabMeshAutoMeshActiveList4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshAutoMeshActiveList4MouseClicked(evt);
            }
        });
        tabMeshAutoMeshActiveList4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshAutoMeshActiveList4KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tabMeshAutoMeshActiveList4);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Solid Part List"));

        tabMeshSolidPartList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshSolidPartList.setDragEnabled(true);
        tabMeshSolidPartList.setDropMode(javax.swing.DropMode.INSERT);
        tabMeshSolidPartList.setPrototypeCellValue("width");
        tabMeshSolidPartList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMeshSolidPartListMouseClicked(evt);
            }
        });
        tabMeshSolidPartList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabMeshSolidPartListKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tabMeshSolidPartList);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );

        tabMeshAutoMeshConfirmButton.setText("Part 확인 완료");
        tabMeshAutoMeshConfirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabMeshAutoMeshConfirmButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabMeshAutoMeshConfirmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabMeshAutoMeshConfirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Automatic Surface Repair", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        buttonGroup1.add(tabMeshRadioButton2);
        tabMeshRadioButton2.setSelected(true);
        tabMeshRadioButton2.setText("ON");

        buttonGroup1.add(tabMeshRadioButton3);
        tabMeshRadioButton3.setText("OFF");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabMeshRadioButton2)
                .addGap(52, 52, 52)
                .addComponent(tabMeshRadioButton3)
                .addGap(258, 258, 258))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabMeshRadioButton2)
                    .addComponent(tabMeshRadioButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh Preview"));

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 1"));

        tabMeshAutoMeshInActiveList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshInActiveList1.setEnabled(false);
        tabMeshAutoMeshInActiveList1.setPrototypeCellValue("width");
        jScrollPane8.setViewportView(tabMeshAutoMeshInActiveList1);

        tabMeshAutoMeshInActiveTextArea1.setColumns(20);
        tabMeshAutoMeshInActiveTextArea1.setRows(5);
        tabMeshAutoMeshInActiveTextArea1.setEnabled(false);
        jScrollPane9.setViewportView(tabMeshAutoMeshInActiveTextArea1);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9)
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 2"));

        tabMeshAutoMeshInActiveList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshInActiveList2.setEnabled(false);
        tabMeshAutoMeshInActiveList2.setPrototypeCellValue("width");
        jScrollPane10.setViewportView(tabMeshAutoMeshInActiveList2);

        tabMeshAutoMeshInActiveTextArea2.setColumns(20);
        tabMeshAutoMeshInActiveTextArea2.setRows(5);
        tabMeshAutoMeshInActiveTextArea2.setEnabled(false);
        jScrollPane11.setViewportView(tabMeshAutoMeshInActiveTextArea2);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11)
                .addContainerGap())
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 3"));

        tabMeshAutoMeshInActiveList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshInActiveList3.setEnabled(false);
        tabMeshAutoMeshInActiveList3.setPrototypeCellValue("width");
        jScrollPane12.setViewportView(tabMeshAutoMeshInActiveList3);

        tabMeshAutoMeshInActiveTextArea3.setColumns(20);
        tabMeshAutoMeshInActiveTextArea3.setRows(5);
        tabMeshAutoMeshInActiveTextArea3.setEnabled(false);
        jScrollPane13.setViewportView(tabMeshAutoMeshInActiveTextArea3);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13)
                .addContainerGap())
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Automated Mesh 4"));

        tabMeshAutoMeshInActiveList4.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        tabMeshAutoMeshInActiveList4.setEnabled(false);
        tabMeshAutoMeshInActiveList4.setPrototypeCellValue("width");
        jScrollPane14.setViewportView(tabMeshAutoMeshInActiveList4);

        tabMeshAutoMeshInActiveTextArea4.setColumns(20);
        tabMeshAutoMeshInActiveTextArea4.setRows(5);
        tabMeshAutoMeshInActiveTextArea4.setEnabled(false);
        jScrollPane15.setViewportView(tabMeshAutoMeshInActiveTextArea4);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMeshArtificialDuctSetupButton.setText("Artificial Duct Setup");
        tabMeshArtificialDuctSetupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabMeshArtificialDuctSetupButtonActionPerformed(evt);
            }
        });

        tabMeshDoorInfoButton.setText("Door 정보 확인");
        tabMeshDoorInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabMeshDoorInfoButtonActionPerformed(evt);
            }
        });

        tabMeshRemeshButton.setText("Remesh (Surface Repair)");
        tabMeshRemeshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabMeshRemeshButtonActionPerformed(evt);
            }
        });

        tabMeshSkipButton.setText("SKIP");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabMeshArtificialDuctSetupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tabMeshDoorInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabMeshSkipButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabMeshRemeshButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabMeshRemeshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabMeshDoorInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabMeshArtificialDuctSetupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabMeshSkipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout meshLayout = new javax.swing.GroupLayout(mesh);
        mesh.setLayout(meshLayout);
        meshLayout.setHorizontalGroup(
            meshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(meshLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(meshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(meshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        meshLayout.setVerticalGroup(
            meshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(meshLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(meshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(meshLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(meshLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mesh", mesh);

        javax.swing.GroupLayout setupLayout = new javax.swing.GroupLayout(setup);
        setup.setLayout(setupLayout);
        setupLayout.setHorizontalGroup(
            setupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
        );
        setupLayout.setVerticalGroup(
            setupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Setup", setup);

        javax.swing.GroupLayout postLayout = new javax.swing.GroupLayout(post);
        post.setLayout(postLayout);
        postLayout.setHorizontalGroup(
            postLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
        );
        postLayout.setVerticalGroup(
            postLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Post", post);

        javax.swing.GroupLayout solvingLayout = new javax.swing.GroupLayout(solving);
        solving.setLayout(solvingLayout);
        solvingLayout.setHorizontalGroup(
            solvingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
        );
        solvingLayout.setVerticalGroup(
            solvingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Solving", solving);

        javax.swing.GroupLayout reportLayout = new javax.swing.GroupLayout(report);
        report.setLayout(reportLayout);
        reportLayout.setHorizontalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
        );
        reportLayout.setVerticalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Report", report);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    

    private void tabMeshFluidPartListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshFluidPartListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshFluidPartList, tabMeshFluidPartListModel, SelectJList.MAIN_FLUID, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshFluidPartListKeyPressed

    private void tabMeshDeletePartListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshDeletePartListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshDeletePartList, tabMeshDeletePartListModel, SelectJList.MAIN_DELETE, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshDeletePartListKeyPressed

    private void tabMeshAutoMeshActiveList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshAutoMeshActiveList1, tabMeshAutoMeshActiveListModel1, SelectJList.MAIN_AUTOMATED_ACTIVE_1, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList1KeyPressed

    private void tabMeshAutoMeshActiveList2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshAutoMeshActiveList2, tabMeshAutoMeshActiveListModel2, SelectJList.MAIN_AUTOMATED_ACTIVE_2, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList2KeyPressed

    private void tabMeshAutoMeshActiveList3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshAutoMeshActiveList3, tabMeshAutoMeshActiveListModel3, SelectJList.MAIN_AUTOMATED_ACTIVE_3, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList3KeyPressed

    private void tabMeshAutoMeshActiveList4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshAutoMeshActiveList4, tabMeshAutoMeshActiveListModel4, SelectJList.MAIN_AUTOMATED_ACTIVE_4, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList4KeyPressed

    private void tabMeshSolidPartListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabMeshSolidPartListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(tabMeshSolidPartList, tabMeshSolidPartListModel, SelectJList.MAIN_SOLID, SelectKind.SELECT_ONE);
        }
    }//GEN-LAST:event_tabMeshSolidPartListKeyPressed

    private void tabMeshDeletePartListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshDeletePartListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshDeletePartList, tabMeshDeletePartListModel, SelectJList.MAIN_DELETE);
        }
    }//GEN-LAST:event_tabMeshDeletePartListMouseClicked

    private void tabMeshFluidPartListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshFluidPartListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshFluidPartList, tabMeshFluidPartListModel, SelectJList.MAIN_FLUID);
        }
    }//GEN-LAST:event_tabMeshFluidPartListMouseClicked

    private void tabMeshAutoMeshActiveList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList1MouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshAutoMeshActiveList1, tabMeshAutoMeshActiveListModel1, SelectJList.MAIN_AUTOMATED_ACTIVE_1);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList1MouseClicked

    private void tabMeshAutoMeshActiveList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList2MouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshAutoMeshActiveList2, tabMeshAutoMeshActiveListModel2, SelectJList.MAIN_AUTOMATED_ACTIVE_2);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList2MouseClicked

    private void tabMeshAutoMeshActiveList3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList3MouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshAutoMeshActiveList3, tabMeshAutoMeshActiveListModel3, SelectJList.MAIN_AUTOMATED_ACTIVE_3);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList3MouseClicked

    private void tabMeshAutoMeshActiveList4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshActiveList4MouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshAutoMeshActiveList4, tabMeshAutoMeshActiveListModel4, SelectJList.MAIN_AUTOMATED_ACTIVE_4);
        }
    }//GEN-LAST:event_tabMeshAutoMeshActiveList4MouseClicked

    private void tabMeshSolidPartListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMeshSolidPartListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(tabMeshSolidPartList, tabMeshSolidPartListModel, SelectJList.MAIN_SOLID);
        }
    }//GEN-LAST:event_tabMeshSolidPartListMouseClicked

    private void tabMeshRemeshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabMeshRemeshButtonActionPerformed
        this.setVisible(false);
        remeshFrame.setVisible(true);
    }//GEN-LAST:event_tabMeshRemeshButtonActionPerformed

    private void tabMeshDoorInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabMeshDoorInfoButtonActionPerformed
        this.setVisible(false);
        doorInfoFrame.setVisible(true);
    }//GEN-LAST:event_tabMeshDoorInfoButtonActionPerformed

    private void tabMeshArtificialDuctSetupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabMeshArtificialDuctSetupButtonActionPerformed
        this.setVisible(false);
        artificaialFrame.setVisible(true);
    }//GEN-LAST:event_tabMeshArtificialDuctSetupButtonActionPerformed

    private void tabMeshAutoMeshConfirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabMeshAutoMeshConfirmButtonActionPerformed
        makeAutoMesh();
    }//GEN-LAST:event_tabMeshAutoMeshConfirmButtonActionPerformed

    private void tabMeshDeleteAllButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabMeshDeleteAllButton1ActionPerformed
        deleteListData(tabMeshDeletePartList, tabMeshDeletePartListModel, SelectJList.MAIN_DELETE, SelectKind.SELECT_ALL);
    }//GEN-LAST:event_tabMeshDeleteAllButton1ActionPerformed

    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mesh;
    private javax.swing.JPanel post;
    private javax.swing.JPanel report;
    private javax.swing.JPanel setup;
    private javax.swing.JPanel solving;
    private javax.swing.JButton tabMeshArtificialDuctSetupButton;
    private javax.swing.JList<String> tabMeshAutoMeshActiveList1;
    private javax.swing.JList<String> tabMeshAutoMeshActiveList2;
    private javax.swing.JList<String> tabMeshAutoMeshActiveList3;
    private javax.swing.JList<String> tabMeshAutoMeshActiveList4;
    private javax.swing.JButton tabMeshAutoMeshConfirmButton;
    private javax.swing.JList<String> tabMeshAutoMeshInActiveList1;
    private javax.swing.JList<String> tabMeshAutoMeshInActiveList2;
    private javax.swing.JList<String> tabMeshAutoMeshInActiveList3;
    private javax.swing.JList<String> tabMeshAutoMeshInActiveList4;
    private javax.swing.JTextArea tabMeshAutoMeshInActiveTextArea1;
    private javax.swing.JTextArea tabMeshAutoMeshInActiveTextArea2;
    private javax.swing.JTextArea tabMeshAutoMeshInActiveTextArea3;
    private javax.swing.JTextArea tabMeshAutoMeshInActiveTextArea4;
    private javax.swing.JButton tabMeshDeleteAllButton1;
    private javax.swing.JList<String> tabMeshDeletePartList;
    private javax.swing.JButton tabMeshDoorInfoButton;
    private javax.swing.JList<String> tabMeshFluidPartList;
    private javax.swing.JRadioButton tabMeshRadioButton2;
    private javax.swing.JRadioButton tabMeshRadioButton3;
    private javax.swing.JButton tabMeshRemeshButton;
    private javax.swing.JButton tabMeshSkipButton;
    private javax.swing.JList<String> tabMeshSolidPartList;
    // End of variables declaration//GEN-END:variables
}
