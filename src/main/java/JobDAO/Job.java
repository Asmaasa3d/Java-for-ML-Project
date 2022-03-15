package JobDAO;

import java.util.List;

public class Job {

    //job attributes
    String Company;
    String Title;
    String Location;
    String Type;
    String Level;
    String Years_EXP;
    String Country;
    String Skills;

    public Job (String Title,String Company,String Location, String Type, String Level,String Years_EXP,String Country,String Skills) {
        this.Title=Title;
        this.Company=Company;
        this.Location=Location;
        this.Type=Type;
        this.Level=Level;
        this.Years_EXP=Years_EXP;
        this.Country=Country;
        this.Skills=Skills;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public void setLocation(String location) {
        Location = location;
    }
    public  List<String> getSkills_strings() { List<String> SkillsString = List.of(Skills.split(",")); return SkillsString; }
    public void setType(String type) {
        Type = type;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public void setYears_EXP(String years_EXP) {
        Years_EXP = years_EXP;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public String getTitle() {
        return Title;
    }

    public String getCompany() {
        return Company;
    }

    public String getLocation() {
        return Location;
    }

    public String getType() {
        return Type;
    }

    public String getLevel() {
        return Level;
    }

    public String getYears_EXP() {
        return Years_EXP;
    }

    public String getCountry() {
        return Country;
    }

    public String getSkills() {
        return Skills;
    }


    @Override
    public String toString() {
        return "Job{" +
                "Title='" + Title + '\'' +
                ", Company='" + Company + '\'' +
                ", Location='" + Location + '\'' +
                ", Type='" + Type + '\'' +
                ", Level='" + Level + '\'' +
                ", Years_EXP='" + Years_EXP + '\'' +
                ", Country='" + Country + '\'' +
                ", Skills='" + Skills + '\'' +
                '}';
    }
    ////rania ashtar shatora///
}