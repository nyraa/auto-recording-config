import javax.swing.DefaultListModel;
import org.ini4j.*;
import java.io.*;

public class DataManagementModel extends DefaultListModel<Section>
{
    private Ini.Section DEFAULT;
    private Ini ini;
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
            this.ini = new Wini(new File(filename));

            for (String sectionName : ini.keySet())
            {
                if(sectionName.equals("DEFAULT"))
                {
                    this.DEFAULT = ini.get(sectionName);
                    continue;
                }
                Section section = new Section(sectionName, ini.get(sectionName));
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
            this.ini.store();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Section spawnSection(String sectionName)
    {
        if(ini.containsKey(sectionName))
            return null;
        Ini.Section iniSection = this.ini.add(sectionName);
        Section section = new Section(sectionName, iniSection);
        addElement(section);
        return section;
    }

    public void renameSection(Section section, String oldName)
    {
        // rename from oldName to section.getSectionName()
        if(ini.containsKey(section.getSectionName()))
            return;
        Ini.Section oldSection = section.getSection();
        Ini.Section newSection = ini.add(section.getSectionName());
        for (String optionKey : oldSection.keySet()) {
            newSection.add(optionKey, oldSection.fetch(optionKey));
        }
        ini.remove(oldName);
        section.setSection(section.getSectionName(), newSection);
    }
    
    @Override
    public boolean removeElement(Object obj)
    {
        if (obj instanceof Section)
        {
            Section section = (Section) obj;
            this.ini.remove(section.getSectionName());
            super.removeElement(obj);
            return true;
        }
        return false;
    }

}