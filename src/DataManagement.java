import java.util.Vector;
import java.io.*;

public class DataManagement
{
    private static Vector<Section> sections = new Vector<>();
    private static String header = null;
    public static boolean loadSchedule(String filename)
    {
        // read line
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line = null;
            Section currentSection = null;
            String sectionName = null;
            while((line = reader.readLine()) != null)
            {
                if(line.equals(""))
                {
                    continue;
                }
                if(line.startsWith("[") && line.endsWith("]"))
                {
                    if(sectionName != null && !sectionName.equals("DEFAULT"))
                    {
                        sections.add(currentSection);
                    }
                    sectionName = line.substring(1, line.length() - 1);
                    if(line.equals("[DEFAULT]"))
                    {
                        header = line + "\n";
                    }
                    else
                    {
                        currentSection = new Section();
                        currentSection.setSectionName(sectionName);
                    }
                    continue;
                }
                if(sectionName.equals("DEFAULT"))
                {
                    header += line + "\n";
                }
                else
                {
                    String propName = line.substring(0, line.indexOf("=")).trim();
                    String propValue = line.substring(line.indexOf("=") + 1).trim();
                    // props: type, room, start, end, keep, password, name, email
                    if(propName.equals("type"))
                    {
                        currentSection.setType(propValue);
                    }
                    else if(propName.equals("room"))
                    {
                        currentSection.setRoomInfo(propValue);
                    }
                    else if(propName.equals("start"))
                    {
                        currentSection.setStartTime(propValue);
                    }
                    else if(propName.equals("end"))
                    {
                        currentSection.setEndTime(propValue);
                    }
                    else if(propName.equals("keep"))
                    {
                        currentSection.setKeep(propValue.equals("true"));
                    }
                    else if(propName.equals("password"))
                    {
                        currentSection.setPassword(propValue);
                    }
                    else if(propName.equals("name"))
                    {
                        currentSection.setUsername(propValue);
                    }
                    else if(propName.equals("email"))
                    {
                        currentSection.setUseremail(propValue);
                    }
                }
            }
            if(sectionName != null && !sectionName.equals("DEFAULT"))
            {
                sections.add(currentSection);
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static Vector<Section> getSections()
    {
        return sections;
    }
}
