import java.util.HashMap;

public class Section
{
    public static String[] KEEP_TYPE = { "Default", "Yes", "No", "Repeat"};
    public static int KEEP_DEFAULT = 0;
    public static int KEEP_TRUE = 1;
    public static int KEEP_FALSE = 2;
    public static int KEEP_REPEAT = 3;

    public static String[] KEYS = {"type", "room", "start", "end", "keep", "password", "name", "email", "repeat"};

    private String sectionName;
    private String type;
    private String startTime;
    private String endTime;
    private String roomInfo;
    private String password;
    private String username;
    private String useremail;
    private String repeat;
    private int keep;
    private HashMap<String, String> unknown;

    public Section()
    {
        this.sectionName = "";
        this.type = "";
        this.startTime = "";
        this.endTime = "";
        this.roomInfo = "";
        this.password = "";
        this.username = "";
        this.useremail = "";
        this.repeat = "";
        this.keep = KEEP_DEFAULT;
        this.unknown = new HashMap<String, String>();
    }
    public void setUnknown(String key, String value)
    {
        unknown.put(key, value);
    }
    public String getSectionName()
    {
        return sectionName;
    }
    public String getType()
    {
        return type;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public String getEndTime()
    {
        return endTime;
    }
    public String getRoomInfo()
    {
        return roomInfo;
    }
    public String getPassword()
    {
        return password;
    }
    public String getUsername()
    {
        return username;
    }
    public String getUseremail()
    {
        return useremail;
    }
    public int getKeep()
    {
        return keep;
    }
    public String getRepeat()
    {
        return repeat;
    }
    public HashMap<String, String> getUnknown()
    {
        return unknown;
    }
    public void setSectionName(String sectionName)
    {
        this.sectionName = sectionName;
    }
    public void setType(String type)
    {
        this.type = type;
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
        this.startTime = startTime;
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
        this.endTime = endTime;
        return true;
    }
    public void setRoomInfo(String roomInfo)
    {
        this.roomInfo = roomInfo;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setUseremail(String useremail)
    {
        this.useremail = useremail;
    }
    public void setKeep(int keep)
    {
        this.keep = keep;
    }
    public void setRepeat(String repeat)
    {
        this.repeat = repeat;
    }
    public void setUnknown(HashMap<String, String> unknown)
    {
        this.unknown = unknown;
    }
}
