package vendingmachine;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vendingmachine.controller.VendingMachineController;

public class App {
    public static void main(String[] args){
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("vendingmachine");
        appContext.refresh();
        VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();
    }
}