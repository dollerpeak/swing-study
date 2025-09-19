/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.starccm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import star.base.neo.NeoObjectVector;
import star.common.CompositePart;
import star.common.GeometryPart;
import star.common.PartContact;
import star.common.PartContactManager;
import star.common.PartSurface;
import star.common.PartSurfaceManager;
import star.common.Simulation;
import star.common.SimulationPartManager;
import star.common.StarMacro;
import star.meshing.CadPart;


public class RenameSurface extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {
        Simulation sim = getActiveSimulation();
        SimulationPartManager spm = sim.get(SimulationPartManager.class);

        // 최상위 파트부터 재귀적으로 탐색 시작
        for (GeometryPart gp : spm.getParts()) {
            processPartRecursive(gp, sim);
        }

        sim.println("=== Done merging and renaming surfaces ===");
    }

    /**
     * 재귀적으로 CompositePart 내부 탐색
     */
    private void processPartRecursive(GeometryPart part, Simulation sim) {
        if (part instanceof CadPart) {
            handleCadPart((CadPart) part, sim);
        } else if (part instanceof CompositePart) {
            CompositePart cp = (CompositePart) part;
            for (GeometryPart child : cp.getChildParts().getParts()) {
                processPartRecursive(child, sim);
            }
        }
    }

    /**
     * CadPart 내부 처리
     */
    private void handleCadPart(CadPart cadPart, Simulation sim) {
        PartSurfaceManager psm = cadPart.getPartSurfaceManager();
        Collection<PartSurface> allSurfs = psm.getPartSurfaces();

        List<PartSurface> solidList = new ArrayList<>();
        List<PartSurface> coloredList = new ArrayList<>();

        // 분류: solid. / colored.
        for (PartSurface ps : allSurfs) {
            String lower = ps.getPresentationName().toLowerCase();
            if (lower.startsWith("solid.")) {
                solidList.add(ps);
            } else if (lower.startsWith("colored")) {
                coloredList.add(ps);
            }
        }

        if (solidList.isEmpty() && coloredList.isEmpty()) {
            return; // 처리할 대상 없음
        }

        sim.println("Processing part: " + cadPart.getPresentationName());

        // 그룹별 병합 (있을 때만)
        if (solidList.size() > 1) {
            cadPart.combinePartSurfaces(new NeoObjectVector(solidList.toArray(new PartSurface[0])));
            sim.println(" - Merged " + solidList.size() + " solid surfaces");
        }
        if (coloredList.size() > 1) {
            cadPart.combinePartSurfaces(new NeoObjectVector(coloredList.toArray(new PartSurface[0])));
            sim.println(" - Merged " + coloredList.size() + " colored surfaces");
        }

        // 다시 surface 목록 가져오기
        List<PartSurface> candidates = new ArrayList<>();
        for (PartSurface ps : psm.getPartSurfaces()) {
            String lower = ps.getPresentationName().toLowerCase();
            if (lower.startsWith("solid.") || lower.startsWith("colored")) {
                candidates.add(ps);
            }
        }

        if (!candidates.isEmpty()) {
            // solid + colored 를 최종 병합
            if (candidates.size() > 1) {
                cadPart.combinePartSurfaces(new NeoObjectVector(candidates.toArray(new PartSurface[0])));
                sim.println(" - Final merge of solid+colored surfaces");
            }

            // 병합 후 다시 surface 가져와 이름 변경
            for (PartSurface ps : psm.getPartSurfaces()) {
                String lower = ps.getPresentationName().toLowerCase();
                if (lower.startsWith("solid.") || lower.startsWith("colored")) {
                    String newName = "wall-" + cadPart.getPresentationName();
                    ps.setPresentationName(newName);
                    sim.println(" -> Renamed to: " + newName);
                    break; // 하나만 이름 변경하면 됨
                }
            }
        }
    }
}
