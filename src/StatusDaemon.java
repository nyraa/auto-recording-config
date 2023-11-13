import javax.swing.JLabel;

public class StatusDaemon extends Thread {
    private JLabel statusLabel;
    private JLabel runningLabel;
    private String status;
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

    private void checkStatus() {
        
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
