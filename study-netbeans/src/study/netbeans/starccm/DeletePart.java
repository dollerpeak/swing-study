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
import star.common.Simulation;
import star.common.SimulationPartManager;
import star.common.StarMacro;

public class DeletePart extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {
        Simulation sim = getActiveSimulation();
        SimulationPartManager spm = sim.get(SimulationPartManager.class);

        // 최상위 파트들부터 시작
        for (GeometryPart gp : spm.getParts()) {
            removeRecursive(gp, sim);
        }

        sim.println("=== Done ===");
    }

    private void removeRecursive(GeometryPart part, Simulation sim) {
        // CompositePart라면 내부 자식까지 검사
        if (part instanceof CompositePart) {
            CompositePart cp = (CompositePart) part;

            // 삭제 대상 수집
            List<GeometryPart> toRemove = new ArrayList<>();
            for (GeometryPart child : cp.getChildParts().getParts()) {
                String lowerName = child.getPresentationName().toLowerCase();

                if (lowerName.startsWith("vir")
                        || lowerName.startsWith("outlet")
                        || lowerName.startsWith("inlet")
                        || lowerName.startsWith("wall")) {
                    sim.println("Removing (child of " + cp.getPresentationName() + "): " + child.getPresentationName());
                    toRemove.add(child);
                }
            }

            // 실제 삭제 실행
            if (!toRemove.isEmpty()) {
                cp.getChildParts().removeParts(new NeoObjectVector(toRemove.toArray(new GeometryPart[0])));
                sim.println("Removed " + toRemove.size() + " parts from Composite: " + cp.getPresentationName());
            }

            // 재귀적으로 더 깊은 CompositePart 탐색
            for (GeometryPart child : cp.getChildParts().getParts()) {
                removeRecursive(child, sim);
            }
        }
    }
}
