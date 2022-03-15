package JobDAO;

import smile.data.DataFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JobDaoI {
     public  String getJobs();

     public String getSchema();
     public String cleanData();
     public List<Job> GetAllData();
     public  String viewSampleData();
     public String CountJobsForEachCompany ();
     public void Showpiechart (List<String> companies, List<Integer> counts);
     public String skillsCount();
     public String  mostPopularJobTitles();
     public void ShowBarchart (Map<String, Long> map, String loc, String title);
     public String  mostPopularAreas();
     public  int[] factorizeYears( String col_name);
     public  String factorizeYears( );
     public DataFrame FactorizeData(DataFrame df);
     public void KmeanGraph() throws IOException;











}