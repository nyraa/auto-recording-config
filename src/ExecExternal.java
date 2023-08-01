import java.io.*;

public class ExecExternal {
    public static int exec(String command[]) throws IOException, InterruptedException
    {
        return exec(command, null);
    }
    public static int exec(String command[], String workDir) throws IOException, InterruptedException
    {
        ProcessBuilder builder = new ProcessBuilder(command);
        if(workDir != null)
        {
            builder.directory(new File(workDir));
        }
        Process process = builder.start();
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        String result = "";
        while((line = reader.readLine()) != null)
        {
            result += line + "\n";
        }
        return process.exitValue();
    }
}
