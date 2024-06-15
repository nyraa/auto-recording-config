import java.io.File;

import javax.swing.JLabel;
import org.ini4j.*;

public class StatusDaemon extends Thread {
    private JLabel statusLabel;
    private JLabel runningLabel;
    private String state;
    private String running;
    private String configFilePath;

    public StatusDaemon(String configPath) {
        configFilePath = configPath;
    }
    public void setStatusLabel(JLabel label) {
        statusLabel = label;
    }

    public void setRunningLabel(JLabel label) {
        runningLabel = label;
    }

    public void setStatusFilePath(String path) {
        configFilePath = path;
    }

    public void checkStatus() {
        // load status from config file
        try {
            Ini ini = new Wini(new File(configFilePath));
            state = ini.get("DEFAULT", "state");
            running = ini.get("DEFAULT", "running");
            statusLabel.setText(state);
            runningLabel.setText(running);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            checkStatus();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
