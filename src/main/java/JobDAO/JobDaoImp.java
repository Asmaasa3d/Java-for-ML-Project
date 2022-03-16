package JobDAO;

import org.apache.commons.csv.CSVFormat;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import smile.clustering.KMeans;
import smile.plot.swing.ScatterPlot;
import smile.clustering.PartitionClustering;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.measure.NominalScale;

import smile.data.vector.IntVector;
import smile.io.Read;

import javax.imageio.ImageIO;


public class JobDaoImp implements JobDaoI{

    DataFrame df = null;
    List<Job> Jobs = new ArrayList<> ();
    public JobDaoImp(){

        String path="src/main/resources/Wuzzuf_Jobs.csv";
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame tmp = null;
        try {
            tmp = Read.csv (path, format);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        this.df=tmp;
        ListIterator<Tuple> iterator = df.stream ().collect (Collectors.toList ()).listIterator ();
        while (iterator.hasNext () ) {
            Tuple t = iterator.next ();
            Job p = new Job ((String)t.get ("Title"),(String)t.get ("Company"),(String)t.get ("Location"),"Type",
                    (String)t.get ("Level"),(String)t.get ("YearsExp"),(String)t.get ("Country"),(String)t.get ("Skills"));
            this.Jobs.add (p);

        }

    }


@Override
    public  String viewSampleData() {

        ListIterator<Tuple> iterator = df.stream ().limit(30).collect (Collectors.toList ()).listIterator ();
    String html = String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Sample From The Data") +
            "<table style=\"width:100%;text-align: center\">" +
            "<tr><th>Title</th><th>Company</th><th>Location</th><th>Type</th><th>Level</th><th>YearsExp</th><th>Country</th><th>Skills</th></tr>";
        while (iterator.hasNext () ) {
            Tuple t = iterator.next ();
            html += "<tr>\n" +"<td>"+(String)t.get ("Title")+"</td>\n" +"<td>"+(String)t.get ("Company")+"</td>\n" +"<td>"+(String)t.get ("Location")+"</td>\n"
                    +"<td>"+(String)t.get ("Type")+"</td>\n" +"<td>"+(String)t.get ("Level")+"</td>\n" +"<td>"+(String)t.get ("YearsExp")+"</td>\n"+"<td>"+(String)t.get ("Country")+"</td>\n"+"<td>"+(String)t.get ("Skills")
                    +"</td>\n"+"  </tr>";

        }

        return html;

    }

    @Override
    public String getSchema(){
        String html=String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Summary and Schema") ;

        String[] st = df.schema().toString().replace("[","").replace("]","").split(",");
        html+=String.format("<h2 style=\"text-align:center;\"> Total records = %d</h2>", df.size() );
        html+="<table style=\"width:100%;text-align: center\">";
        for (String st1 : st) {
            html += "<tr><td ><br>"+ st1 +"</tr></td>" ;
        }
        return html;
    }



    @Override
    public String cleanData(){
        String html=String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Data Cleaning") ;
        int n = df.nrows();
        html+=String.format("<h2 style=\"text-align:center;\"> Total records before cleaning = %d</h2>",  n);
        DataFrame nonNullData= df.omitNullRows();
        df = df.omitNullRows();
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null  = %d</h2>", df.nrows()- nonNullData.nrows ());
        DataFrame DF_NullTitle = DataFrame.of(df.stream().filter(row -> !row.getString("Title").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Title)  = %d</h2>", (df.size() - DF_NullTitle.size()));
        DataFrame DF_NullCompany = DataFrame.of(df.stream().filter(row -> !row.getString("Company").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Company)  = %d</h2>", (df.size() - DF_NullCompany.size()));
        DataFrame DF_NullLocation = DataFrame.of(df.stream().filter(row -> !row.getString("Location").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Location)  = %d</h2>", (df.size() - DF_NullLocation.size()));
        DataFrame DF_NullType = DataFrame.of(df.stream().filter(row -> !row.getString("Type").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Type)  = %d</h2>", (df.size() - DF_NullType.size()));
        DataFrame DF_NullLevel = DataFrame.of(df.stream().filter(row -> !row.getString("Level").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Level)  = %d</h2>", (df.size() - DF_NullLevel.size()));
        DataFrame DF_NullYearsExp = DataFrame.of(df.stream().filter(row -> !row.getString("YearsExp").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (YearsExp)  = %d</h2>", (df.size() - DF_NullYearsExp.size()));
        DataFrame DF_NullCountry = DataFrame.of(df.stream().filter(row -> !row.getString("Country").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Country)  = %d</h2>", (df.size() - DF_NullCountry.size()));
        DataFrame DF_NullSkills = DataFrame.of(df.stream().filter(row -> !row.getString("Skills").contains("null")));
        html+=String.format("<h4 style=\"text-align:center;\"> Number of records having null in Column (Skills)  = %d</h2>", (df.size() - DF_NullSkills.size()));

        df = DataFrame.of(df.stream().filter(row -> !row.getString("YearsExp").contains("null")));
        df = DataFrame.of(df.stream().filter(row -> !row.getString("Skills").contains("null")));
        List<Tuple> l = df.stream().distinct().collect(Collectors.toList());
        df = DataFrame.of(df.stream().distinct());
        html+=String.format("<h4 style=\"text-align:center;\"> Number of Duplicate records   = %d</h2>",  df.nrows()-l.size());
        int Number_Of_All_Nulls = (DF_NullYearsExp.size() + DF_NullSkills.size());
        html+=String.format("<h2 style=\"text-align:center;\"> Number of records After cleaning = %d</h2>",  (Number_Of_All_Nulls - n ));

        return html;
    }

// 6.Find out what are the most popular job titles.
//7. Show step 6 in bar chart
    public LinkedHashMap<String, Integer> SortByValue (HashMap<String, Integer> ToSortCompany) {
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(ToSortCompany.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> es1, Map.Entry<String, Integer> es2) {
                return es2.getValue().compareTo(es1.getValue());
            }
        });
        LinkedHashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
@Override
    public String CountJobsForEachCompany () {
        List<Job> ALLDATA = this.Jobs;
        HashMap<String, Integer> CountCompany = new HashMap<>();
        for(Job job : ALLDATA){
            if (CountCompany.containsKey(job.getCompany())){
                CountCompany.put(job.getCompany(), CountCompany.get(job.getCompany()) + 1);
            }
            else{
                CountCompany.put(job.getCompany(), 1);
            }
        }
        HashMap<String, Integer> AfterSorting = SortByValue(CountCompany);
        // System.out.println(AfterSorting);
        int limit = 25;
        List<String> companies = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        short n = 0;
        for (String a : AfterSorting.keySet()) {
            companies.add(a);
            counts.add(AfterSorting.get(a));
            n++;
            if (n >= limit){
                break;
            }
        }
        Showpiechart(companies , counts);
        Iterator<String> companiesIterator = companies.iterator();
        Iterator<Integer> countsIterator = counts.iterator();
        String html = String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Count the jobs for each company ") +
                "<h3 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">The most demanding companies for jobs is " + companies.get(0) + "</h3>" +
                "<table style=\"width:100%;text-align: center;border: 1px solid\">" +
                "<tr style=\"border: 1px solid\"><th style=\"border: 1px solid\">Company</th><th style=\"border: 1px solid\">Count</th></tr>";
        while (companiesIterator.hasNext() && countsIterator.hasNext()) {
            html += "<tr>\n" +"<td>"+companiesIterator.next()+"</td>\n" +"<td>"+countsIterator.next()+"</td>\n" + "  </tr>";
        }

        return html;
    }

@Override
    public void Showpiechart (List<String> companies, List<Integer> counts) {

        PieChart chart = new PieChartBuilder().width(1500).height(900).title("Count the jobs for each company (Pie Chart )").build();
        for (int i = 0; i < companies.size(); i++) {
            chart.addSeries(companies.get(i), counts.get(i));
        }
        try {
            BitmapEncoder.saveBitmapWithDPI(chart, "src/main/resources/Step5_PieChart", BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

@Override
    public String skillsCount(){
        Map<Job, List<String>> map = new LinkedHashMap<>();

        map=this.Jobs.stream().collect(Collectors.toMap(Job->Job, Job::getSkills_strings));
        List<String> allSkills = map.values() // Collection<List<Integer>>
                .stream()                      // Stream<List<Integer>>
                .flatMap(List::stream)         // Stream<Integer>
                .collect(Collectors.toList());
//        System.out.print(allSkills);
        Map<String, Long> skillCnt = allSkills.stream()
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting ()));
        LinkedHashMap<String, Long> SkillsSortedMap = new LinkedHashMap<>();
        skillCnt.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> SkillsSortedMap.put( x.getKey(), x.getValue()));
        String html = String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Skills Count") +
                "<table style=\"width:100%;text-align: center;border: 1px solid\">" +
                "<tr style=\"border: 1px solid\"><th style=\"border: 1px solid\">Skill</th><th style=\"border: 1px solid\">Count</th></tr>";
        for (var entry : SkillsSortedMap.entrySet()) {
            html += "<tr>\n" +"<td>"+entry.getKey()+"</td>\n" +"<td>"+entry.getValue()+"</td>\n" +"</tr>";
        }

        LinkedHashMap<String, Long> toDraw = new LinkedHashMap<>();
        SkillsSortedMap.entrySet().stream().limit(30).forEachOrdered(x -> toDraw.put(x.getKey(), x.getValue()));
        ShowBarchart(toDraw,"Step7_BarChart","Job Titles");
        return html;
    }

    @Override
    public String  mostPopularJobTitles(){


        Map<String, Long> result =
                this.Jobs.stream ().collect (
                        Collectors.groupingBy (
                                Job::getTitle, Collectors.counting ()
                        )
                );
        LinkedHashMap<String, Long> reverseSortedMap = new LinkedHashMap<>();
        result.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(30).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        String html = String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Most popular job titles") +
                "<table style=\"width:100%;text-align: center;border: 1px solid\">" +
                "<tr style=\"border: 1px solid\"><th style=\"border: 1px solid\">Title</th><th style=\"border: 1px solid\">Count</th></tr>";
        for (var entry : reverseSortedMap.entrySet()) {
            html += "<tr>\n" +"<td>"+entry.getKey()+"</td>\n" +"<td>"+entry.getValue()+"</td>\n" +"</tr>";
        }
//    Collections.sort(result);
//    System.out.println(result);
        LinkedHashMap<String, Long> toDraw = new LinkedHashMap<>();
        reverseSortedMap.entrySet().stream().limit(6).forEachOrdered(x -> toDraw.put(x.getKey(), x.getValue()));
        ShowBarchart(toDraw,"Step7_BarChart","Job Titles");
        return html;
    }

@Override
    public void ShowBarchart (Map<String, Long> map,String loc,String title) {
//        Map<String, Long> map = new HashMap<String, Long>();
        String[] keys = new String[map.size()];
        Long[] values = new Long[map.size()];
        int index = 0;
        for (var entry : map.entrySet()) {
            keys[index] = entry.getKey();
            values[index] = entry.getValue();
            index++;
//           if (index>=10)break;
        }

        CategoryChart chart = new CategoryChartBuilder().width(1500).height(900).title("Most popular "+title).build();
        // Customize Chart
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
        chart.addSeries(title, Arrays.asList(keys), Arrays.asList(values));

        try {
            BitmapEncoder.saveBitmapWithDPI(chart, "src/main/resources/"+loc, BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//

//Step8 and Step9

@Override
    public String  mostPopularAreas(){
//        System.out.println(this.Jobs);

        Map<String, Long> result =
                this.Jobs.stream ().collect (
                        Collectors.groupingBy (
                                Job::getLocation, Collectors.counting ()
                        )
                );
        LinkedHashMap<String, Long> reverseSortedMap = new LinkedHashMap<>();
        result.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        String html = String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Most popular Areas") +
                "<table style=\"width:100%;text-align: center;border: 1px solid\">" +
                "<tr style=\"border: 1px solid\"><th style=\"border: 1px solid\">Area</th><th style=\"border: 1px solid\">Count</th></tr>";
        for (var entry : reverseSortedMap.entrySet()) {
            html += "<tr>\n" +"<td>"+entry.getKey()+"</td>\n" +"<td>"+entry.getValue()+"</td>\n" +"</tr>";
        }
        LinkedHashMap<String, Long> toDraw = new LinkedHashMap<>();
        reverseSortedMap.entrySet().stream().limit(10).forEachOrdered(x -> toDraw.put(x.getKey(), x.getValue()));
        ShowBarchart(toDraw,"Step9_BarChart","Areas");



        return html;
    }



@Override
    public  int[] factorizeYears( String col_name) {
        String[] values = this.df.stringVector(col_name).distinct().toArray(new String[]{});
//        this.df = this.df.merge(IntVector.of("YearsExpFact", df.stringVector(col_name).factorize(new NominalScale(values)).toIntArray()));


        return this.df.stringVector(col_name).factorize(new NominalScale(values)).toIntArray();
    }



@Override
    public  String factorizeYears( ) {
        this.df = this.df.merge(IntVector.of("YearsExpFact", factorizeYears("YearsExp")));
        String html = String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:SpringGreen;\">%s</h1>", "Factorize Years of Exp ") +
    "<table style=\"width:100%;text-align: center;border: 1px solid\">" +
            "<tr style=\"border: 1px solid\"><th style=\"border: 1px solid\">String</th><th style=\"border: 1px solid\">Factorized</th></tr>";

        ListIterator<Tuple> iterator = this.df.stream ().collect (Collectors.toList ()).listIterator ();

        while (iterator.hasNext()) {
            Tuple t = iterator.next ();
            html += "<tr>\n" +"<td>"+(String) t.get("YearsExp") +"</td>\n" +"<td>"+ t.get("YearsExpFact")+"</td>\n" +"</tr>";

        }

        return html;

    }
@Override
    public DataFrame FactorizeData(DataFrame df) {
        df = df.merge(IntVector.of("YearsExpFact", factorizeYears("YearsExp")));
        df = df.merge(IntVector.of("JobsFact", factorizeYears( "Title")));
        df = df.merge(IntVector.of("CompanyFact", factorizeYears("Company")));
//    System.out.println("HHH 111 :  " + df);
        return df;
    }

 
 @Override
    public void KmeanGraph() throws IOException {
        df = FactorizeData(this.df);
        DataFrame kmean = df.select("JobsFact", "CompanyFact");
//        System.out.println("HHH  :  " + kmean);
        KMeans clusters = PartitionClustering.run(100, () -> KMeans.fit(kmean.toArray(), 4));
        BufferedImage image = ScatterPlot.of(kmean.toArray(), clusters.y, '.').canvas().setAxisLabels("Companies", "Jobs").toBufferedImage(900, 500);

        File output = new File("src/main/resources/Kmeans.png");
        ImageIO.write(image, "png", output);

    }




}
