package Group1.Java4MLProject;

import JobDAO.JobDaoImp;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;

@RestController

public class Controller    {
    // 1. Read the dataset, convert it to DataFrame or Spark
    //Dataset, and display some from it.

    @GetMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @GetMapping("/view")
    public String viewSampleDataMap(){
        JobDaoImp tmp=new JobDaoImp();
        return tmp.viewSampleData();
    }

//    2. Display structure and summary of the data.
    @GetMapping ("/schema")
    public String summaryAndSchema(){
        JobDaoImp tmp=new JobDaoImp();
        return tmp.getSchema();
    }


//    3. Clean the data (null, duplications)
    @GetMapping ("/clean")
    public String clean(){
        JobDaoImp tmp=new JobDaoImp();
        tmp.mostPopularJobTitles();
        return tmp.cleanData();
    }


//    4. Count the jobs for each company and display that in order
//            (What are the most demanding companies for jobs?)
    @GetMapping ("/CountForEachCompany")
    public String CountForCompant(){
        JobDaoImp tmp=new JobDaoImp();
        return tmp.CountJobsForEachCompany();
    }

//   5. Show step 4 in a pie chart
    @GetMapping ("/PieChart")
    public ResponseEntity<byte[]> getPieChart() {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("src/main/resources/Step5_PieChart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }



//6. Find out what are the most popular job titles.
    @GetMapping ("/popularJobTitle")
    public String popular(){
        JobDaoImp tmp=new JobDaoImp();
        return tmp.mostPopularJobTitles();
    }


//7. Show step 6 in bar chart
    @GetMapping ("/JobBarChart")
    public ResponseEntity<byte[]> getJobBarChart() {
        JobDaoImp tmp=new JobDaoImp();
        String pla= tmp.mostPopularJobTitles();
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("src/main/resources/Step7_BarChart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

//8. Find out the most popular areas?
    @GetMapping ("/popularAreas")
    public String getPopularAreas(){
        JobDaoImp tmp=new JobDaoImp();
        return tmp.mostPopularAreas();
    }


//    9. Show step 8 in bar chart
    @GetMapping ("/AreaBarChart")
    public ResponseEntity<byte[]> getِAreaBarChart() {
        JobDaoImp tmp=new JobDaoImp();
        String pla= tmp.mostPopularAreas();
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("src/main/resources/Step9_BarChart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

//10. Print skills one by one and how many each repeated and
//    order the output to find out the most important skills
//    required?
    @GetMapping ("/skillsCount")
    public String skillCnt(){
        JobDaoImp tmp=new JobDaoImp();
        return tmp.skillsCount();
    }

//11. Factorize the YearsExp feature and convert it to numbers
//    in new col.

    @GetMapping ("/factorize")
    public String Factorize(){
        JobDaoImp tmp=new JobDaoImp();
     return tmp.factorizeYears();
    }




//    12. Apply K-means for job title and companies (Bonus)
    @GetMapping ("/Kmeans")
    public ResponseEntity<byte[]> Kmean() throws IOException {
        JobDaoImp tmp=new JobDaoImp();
        tmp.KmeanGraph();
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("src/main/resources/Kmeans.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }



}
