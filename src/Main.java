import javax.swing.JOptionPane;

public class Main
{
    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            JOptionPane.showMessageDialog(null, "Base dir missing", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        Window window = new Window(args[0]);
        window.setVisible(true);
    }
}