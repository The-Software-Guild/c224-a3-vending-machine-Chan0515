package vendingmachine.service;

import org.junit.jupiter.api.Test;
import vendingmachine.dao.VendingMachineAuditDaoImpl;
import vendingmachine.dao.VendingMachinePersistenceException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VendingMachineAuditDaoImplStub {
    VendingMachineAuditDaoImpl auditTest = new VendingMachineAuditDaoImpl();
    @Test
    public void auditEntryTest() throws VendingMachinePersistenceException, FileNotFoundException {
        auditTest.writeAuditEntry("TEST");
        String currentLine = null;
        Scanner scanner = new Scanner(
                new BufferedReader(
                        new FileReader("audit.txt")));
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
        }
        String[] test = currentLine.split("::");
        assertTrue(test[1].equals("TEST"), "Test audit entry not found.");
    }
}