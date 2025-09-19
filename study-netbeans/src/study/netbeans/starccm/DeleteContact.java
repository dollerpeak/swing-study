/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.starccm;

import java.util.Collection;
import star.common.GeometryPart;
import star.common.PartContact;
import star.common.PartContactManager;
import star.common.Simulation;
import star.common.StarMacro;

public class DeleteContact extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {
        Simulation sim = getActiveSimulation();
        PartContactManager pcm = sim.get(PartContactManager.class);

        // 모든 PartContact 가져오기
        Collection<PartContact> allContacts = pcm.getObjects();

        sim.println("Total PartContacts: " + allContacts.size());

        for (PartContact pc : allContacts) {
            GeometryPart p1 = pc.getPart1();
            GeometryPart p2 = pc.getPart2();

            // sim.println("-----------------------------------");
            sim.println("Contact Name      : " + pc.getPresentationName());
            /* sim.println("Qualified Name    : " + pc.getQualifiedName());
      sim.println("Between Parts     : "
        + (p1 != null ? p1.getPresentationName() : "null") + " <--> "
        + (p2 != null ? p2.getPresentationName() : "null"));
      sim.println("Primary Part      : " 
        + (pc.getPrimaryPart() != null ? pc.getPrimaryPart().getPresentationName() : "null"));
      sim.println("Tolerance         : " + pc.getTolerance());
      sim.println("Is Empty?         : " + pc.isEmpty());
             */
            sim.get(PartContactManager.class).removeObjects(pc);

        }

        sim.print("All PartsContacts has been deleted");

        sim.println("=== Done ===");
    }
}
