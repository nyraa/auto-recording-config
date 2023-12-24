import org.ini4j.Ini;

public class Section
{
    public static String[] KEEP_TYPE = { "Default", "Yes", "No", "Repeat"};
    public static String[] KEEP_VALUE = {"Default", "true", "false", "repeat"};
    public static int KEEP_DEFAULT = 0;
    public static int KEEP_TRUE = 1;
    public static int KEEP_FALSE = 2;
    public static int KEEP_REPEAT = 3;

    public static String[] KEYS = {"type", "room", "start", "end", "keep", "password", "name", "email", "repeat"};

    private String sectionName;
    private Ini.Section section;
    public Section(String sectionName, Ini.Section section)
    {
        this.sectionName = sectionName;
        this.section = section;
    }
    public void setSection(String sectionName, Ini.Section section)
    {
        this.sectionName = sectionName;
        this.section = section;
    }
    public Ini.Section getSection()
    {
        return this.section;
    }
    public String getSectionName()
    {
        return sectionName;
    }
    public String getType()
    {
        return section.getOrDefault("type", "");
    }
    public String getStartTime()
    {
        return section.getOrDefault("start", "");
    }
    public String getEndTime()
    {
        return section.getOrDefault("end", "");
    }
    public String getRoomInfo()
    {
        return section.getOrDefault("room", "");
    }
    public String getPassword()
    {
        return section.getOrDefault("password", "");
    }
    public String getUsername()
    {
        return section.getOrDefault("name", "");
    }
    public String getUseremail()
    {
        return section.getOrDefault("email", "");
    }
    public int getKeep()
    {
        String keep = section.getOrDefault("keep", "");
        if (keep.equals("true"))
            return KEEP_TRUE;
        else if (keep.equals("false"))
            return KEEP_FALSE;
        else if (keep.equals("repeat"))
            return KEEP_REPEAT;
        else
            return KEEP_DEFAULT;
    }
    public String getRepeat()
    {
        return section.getOrDefault("repeat", "");
    }
    public boolean setSectionName(String sectionName)
    {
        if(!this.sectionName.equals(this.sectionName = sectionName))
            return true;
        else
            return false;
    }
    public void setType(String type)
    {
        section.put("type", type);
    }
    public boolean setStartTime(String startTime)
    {
        // check format in YYYY-MM-DD HH:MM:SS
        if (startTime.length() != 19)
            return false;
        if (startTime.charAt(4) != '-' || startTime.charAt(7) != '-' || startTime.charAt(10) != ' ' || startTime.charAt(13) != ':' || startTime.charAt(16) != ':')
            return false;
        for (int i = 0; i < 19; i++)
        {
            if (i == 4 || i == 7 || i == 10 || i == 13 || i == 16)
                continue;
            if (startTime.charAt(i) < '0' || startTime.charAt(i) > '9')
                return false;
        }
        section.put("start", startTime);
        return true;
    }
    public boolean setEndTime(String endTime)
    {
        // check format in YYYY-MM-DD HH:MM:SS
        if (endTime.length() != 19)
            return false;
        if (endTime.charAt(4) != '-' || endTime.charAt(7) != '-' || endTime.charAt(10) != ' ' || endTime.charAt(13) != ':' || endTime.charAt(16) != ':')
            return false;
        for (int i = 0; i < 19; i++)
        {
            if (i == 4 || i == 7 || i == 10 || i == 13 || i == 16)
                continue;
            if (endTime.charAt(i) < '0' || endTime.charAt(i) > '9')
                return false;
        }
        section.put("end", endTime);
        return true;
    }
    public void setRoomInfo(String roomInfo)
    {
        section.put("room", roomInfo);
    }
    public void setPassword(String password)
    {
        if(password.equals(""))
            section.remove("password");
        else
            section.put("password", password);
    }
    public void setUsername(String username)
    {
        if(username.equals(""))
            section.remove("name");
        else
            section.put("name", username);
    }
    public void setUseremail(String useremail)
    {
        if(useremail.equals(""))
            section.remove("email");
        else
            section.put("email", useremail);
    }
    public void setKeep(int keep)
    {
        if(keep == KEEP_DEFAULT)
            section.remove("keep");
        else
            section.put("keep", KEEP_VALUE[keep]);
    }
    public void setRepeat(String repeat)
    {
        if(repeat.equals("P0DT0H0M0S"))
        {
            section.remove("repeat");
        }
        else
        {
            section.put("repeat", repeat);
        }
    }
}
