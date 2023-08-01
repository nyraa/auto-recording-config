import javax.swing.DefaultListModel;

import java.io.*;

public class DataManagementModel extends DefaultListModel<Section>
{
    private String header = null;
    public DataManagementModel(String filename)
    {
        super();
        loadSchedule(filename);
    }
    public boolean loadSchedule(String filename)
    {
        // read line
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
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
                        
                        addElement(currentSection);
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
                        currentSection.setKeep(propValue.equals("true") ? Section.KEEP_TRUE : Section.KEEP_FALSE);
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
                addElement(currentSection);
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
    public boolean writeSchedule(String filename)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            writer.write(header);
            writer.write("\n");
            for(int i = 0; i < size(); i++)
            {
                Section section = get(i);
                writer.write("[" + section.getSectionName() + "]\n");
                writer.write("type = " + section.getType() + "\n");
                writer.write("room = " + section.getRoomInfo() + "\n");
                writer.write("start = " + section.getStartTime() + "\n");
                writer.write("end = " + section.getEndTime() + "\n");
                int keep = section.getKeep();
                if(keep != Section.KEEP_DEFAULT)
                {
                    writer.write("keep = " + (keep == Section.KEEP_TRUE ? "true" : "false") + "\n");
                }
                String password = section.getPassword();
                if(!password.equals(""))
                {
                    writer.write("password = " + section.getPassword() + "\n");
                }
                String name = section.getUsername();
                if(!name.equals(""))
                {
                    writer.write("name = " + section.getUsername() + "\n");
                }
                String email = section.getUseremail();
                if(!email.equals(""))
                {
                    writer.write("email = " + section.getUseremail() + "\n");
                }
                writer.write("\n");
            }
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
