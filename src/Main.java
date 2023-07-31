public class Main
{
    public static void main(String[] args)
    {
        DataManagementModel.loadSchedule(args[0]);
        Window window = new Window();
        window.setVisible(true);
    }
}