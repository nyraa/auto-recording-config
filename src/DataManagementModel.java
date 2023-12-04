import javax.swing.DefaultListModel;
import org.ini4j.*;

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
            Wini ini = new Wini(new File(filename));

            for (String sectionName : ini.keySet())
            {
                if(sectionName.equals("DEFAULT"))
                {
                    continue;
                }
                Section section = new Section();
                section.setSectionName(sectionName);
                for(String key : Section.KEYS)
                {
                    switch (key) {
                        case "type":
                            section.setType(ini.get(sectionName, key));
                            break;
                        case "room":
                            section.setRoomInfo(ini.get(sectionName, key));
                            break;
                        case "start":
                            section.setStartTime(ini.get(sectionName, key));
                            break;
                        case "end":
                            section.setEndTime(ini.get(sectionName, key));
                            break;
                        case "keep":
                            switch(ini.get(sectionName, key))
                            {
                                case "true":
                                    section.setKeep(Section.KEEP_TRUE);
                                    break;
                                case "false":
                                    section.setKeep(Section.KEEP_FALSE);
                                    break;
                                case "repeat":
                                    section.setKeep(Section.KEEP_REPEAT);
                                    break;
                                default:
                                    section.setKeep(Section.KEEP_DEFAULT);
                                    break;
                            }
                            break;
                        case "password":
                            section.setPassword(ini.get(sectionName, key));
                            break;
                        case "name":
                            section.setUsername(ini.get(sectionName, key));
                            break;
                        case "email":
                            section.setUseremail(ini.get(sectionName, key));
                            break;
                        case "repeat":
                            section.setRepeat(ini.get(sectionName, key));
                            break;
                        default:
                            section.setUnknown(key, ini.get(sectionName, key));
                            break;
                    }
                }
                addElement(section);
            }
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
            Wini ini = new Wini(new File(filename));

            for(int i = 0; i < size(); i++)
            {
                Section section = get(i);
                String sectionName = section.getSectionName();
                ini.put(sectionName, "type", section.getType());
                ini.put(sectionName, "room", section.getRoomInfo());
                ini.put(sectionName, "start", section.getStartTime());
                ini.put(sectionName, "end", section.getEndTime());
                ini.put(sectionName, "repeat", section.getRepeat());
                ini.put(sectionName, "keep", Section.KEEP_TYPE[section.getKeep()]);
                String password = section.getPassword();
                if(!password.equals(""))
                {
                    ini.put(sectionName, "password", section.getPassword());
                }
                String name = section.getUsername();
                if(!name.equals(""))
                {
                    ini.put(sectionName, "name", section.getUsername());
                }
                String email = section.getUseremail();
                if(!email.equals(""))
                {
                    ini.put(sectionName, "email", section.getUseremail());
                }
                for(String key : section.getUnknown().keySet())
                {
                    ini.put(sectionName, key, section.getUnknown().get(key));
                }
            }
            ini.store();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
