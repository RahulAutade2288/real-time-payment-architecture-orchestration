package realtimepaymentarchitectureorchestration.app;

import java.time.*;
import java.util.*;


/**
 * Application entry point for the real-time payment architecture and orchestration demo.
 * This class bootstraps configuration and the bootstrapper, and exposes a tiny
 * interactive CLI loop so developers can trigger flows while reading the code.
 */
public class RealTimePaymentApplication {

    public static void main(String[] args) {
        System.out.println("=== Real-Time Payment Architecture and Orchestration Demo ===");
        ApplicationConfig config = new ApplicationConfig();
        config.loadDefaults();

        Bootstrapper bootstrapper = new Bootstrapper(config);
        bootstrapper.start();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("Enter command [demo, info, quit]: ");
                String command = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                if ("quit".equals(command)) {
                    System.out.println("Shutting down demo.");
                    break;
                } else if ("demo".equals(command)) {
                    bootstrapper.runDemoPayment();
                } else if ("info".equals(command)) {
                    printInfo(config);
                } else {
                    System.out.println("Unknown command: " + command);
                }
            }
        }
    }

    private static void printInfo(ApplicationConfig config) {
        System.out.println("Build timestamp : " + config.getBuildTimestamp());
        System.out.println("Environment     : " + config.getEnvironmentName());
        System.out.println("Demo profile    : " + config.getProfileName());
        System.out.println("Properties:");
        config.getProperties().forEach((k, v) -> System.out.println("  " + k + " = " + v));
    }
}
