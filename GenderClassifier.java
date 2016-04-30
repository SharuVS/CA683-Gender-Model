
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GenderClassifier 
{
    ArrayList Tokenisedwords1 = new ArrayList();
    ArrayList Tokenisedwords2 = new ArrayList();
    ArrayList TokenisedGender1 = new ArrayList();
    ArrayList TokenisedGender2 = new ArrayList();
    ArrayList FemalePattern1 = new ArrayList();
    ArrayList MalePattern1 = new ArrayList();
    ArrayList Pattern = new ArrayList();
    ArrayList list1 = new ArrayList();
    ArrayList list2 = new ArrayList();
    int count;
    double Accuracy;
    int i , j , k;
    public static void main(String []args) throws FileNotFoundException, IOException
    {
        GenderClassifier gc = new GenderClassifier();
        gc.readStore1();
        System.out.println("Done reading Training data. \n");
        gc.readStore2();
        System.out.println("Done REading Testing data. \n");
        gc.categorise2();
        System.out.println("Done pattern analysing. \n");
        gc.model();
        System.out.println("done");
    }
    public void readStore1() throws FileNotFoundException, IOException 
    {
        String str1 = "FirstNameTest.csv";
        String line1;
        BufferedReader br1 = null;
	String cvsSplitBy = ",";
        br1 = new BufferedReader(new FileReader(str1));
        count = 70;
        while ((line1 = br1.readLine()) != null) 
        {
            String[] Names = line1.split(cvsSplitBy);
            String word =Names[1];
            Tokenisedwords1.add(word.toLowerCase());
            String Gender = Names[Names.length-1];
            TokenisedGender1.add(Gender);
        }
        for(i=0;i<Tokenisedwords1.size()-1;i++)
        {
            //System.out.println("Name @ "+i+" : "+gc.Tokenisedwords.get(i)+" and Gender : "+gc.TokenisedGender.get(i)+"\n");
            if(TokenisedGender1.get(i).equals("Female") || TokenisedGender1.get(i).equals("female") )
            {
                String s = Tokenisedwords1.get(i).toString();
                int len = s.length()-1;
                //System.out.println(s+" "+s.length()+"");
                if(len > 4)
                {
                    String pattern = s.substring(len-2);
                    FemalePattern1.add(pattern);
                    //System.out.println("Female = "+pattern);
                }
                else
                {
                    if(len < 3)
                    {
                        FemalePattern1.add(s);
                    }
                    if(len == 4)
                    {
                        FemalePattern1.add(s.substring(1));
                        //System.out.println("Female = "+s.substring(1));
                    }
                }
            }
            else
            {
                String s = Tokenisedwords1.get(i).toString();
                int len = s.length()-1;
                //System.out.println(s+" "+s.length()+"");
                if(len > 4)
                {
                    String pattern = s.substring(len-2);
                    MalePattern1.add(pattern);
                    //System.out.println("Male = "+pattern);
                }
                else
                {
                    if(len < 3)
                    {
                        MalePattern1.add(s);
                    }
                    if(len == 4)
                    {
                        MalePattern1.add(s.substring(1));
                        //System.out.println("Male = "+s.substring(1));
                    }
                }
            }
        }
    }
    public void readStore2() throws FileNotFoundException, IOException
    {
        String str2 = "FirstNameTest(2).csv";
        String line2;
        BufferedReader br2 = null;
        String cvsSplitBy = ",";
        br2 = new BufferedReader(new FileReader(str2));
        while ((line2 = br2.readLine()) != null) 
        {
            String[] Names = line2.split(cvsSplitBy);
            String word =Names[1];
            Tokenisedwords2.add(word.toLowerCase());
            String Gender = Names[Names.length-1];
            TokenisedGender2.add(Gender);
        }
        for(i=0;i<=Tokenisedwords2.size()-1;i++)
        {
            //System.out.println("Name @ "+i+" : "+gc.Tokenisedwords.get(i)+" and Gender : "+gc.TokenisedGender.get(i)+"\n");
            String s = Tokenisedwords2.get(i).toString();
            int len = s.length()-1;
            if(len > 4)
            {
                String pattern = s.substring(len-2);
                Pattern.add(pattern);
            }
            else
            {
                if(len < 3)
                {
                    Pattern.add(s);
                }
                if(len == 4)
                {
                        Pattern.add(s.substring(1));
                        //System.out.println("Female = "+s.substring(1));
                }
            }
            
        }
    }
    public void categorise2() throws IOException
    {
        
        int len1 = FemalePattern1.size()-1;
        int len2 = MalePattern1.size()-1;
        int probability = count;
        for(i=0;i<Pattern.size()-1;i++)
        {
            int count1=0, count2 =0;
            for(j=0;j<FemalePattern1.size()-1;j++)
            {
                String stri = (String)Pattern.get(i);
                String strj = (String)FemalePattern1.get(j);
                if(stri.equalsIgnoreCase(strj))
                {
                    count1++;
                } 
                if(j == FemalePattern1.size()-2)
                {
                    double prob = Math.ceil(((double)count1/(double)len1)*100);
                    String filename= "file1.csv";
                    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                    String tmp = ""+stri+","+prob+",Female \n";
                    fw.write(tmp);//appends the string to the file
                    fw.close();
                }
            }
        }
        Accuracy = probability;
        System.out.println("Added female patterns \n");
        for(i=0;i<Pattern.size()-1;i++)
        {
            int count1 =0, count2=0;
            for(j=0;j<MalePattern1.size()-1;j++)
            {
                String stri = (String)Pattern.get(i);
                String strj = (String)MalePattern1.get(j);
                if(stri.equalsIgnoreCase(strj))
                {
                    count2++;
                } 
                if(j == MalePattern1.size()-2)
                {
                    double prob = Math.ceil(((double)count2/(double)len2)*100);
                    String filename= "file2.csv";
                    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                    String tmp = ""+stri+","+prob+",Male \n";
                    fw.write(tmp);//appends the string to the file
                    fw.close();
                }
            }
        }
        System.out.println("Added male patterns \n");
    }
    public void model() throws FileNotFoundException, IOException
    {
        BufferedReader fileReader1 = new BufferedReader(new FileReader("file1.csv"));
        String line1;   
        while ((line1 = fileReader1.readLine()) != null) 
        {
            String[] tokens = line1.split(",");
            list1.add(tokens[1]);
        }
        BufferedReader fileReader2 = new BufferedReader(new FileReader("file2.csv"));
        String line2; 
        while ((line2 = fileReader2.readLine()) != null) 
        {
            String[] tokens = line2.split(",");
            list2.add(tokens[1]);
            //System.out.println("Added");
            //System.out.println(tokens[1]);
        }
        for(i=0;i<Pattern.size()-1;i++)
        {
            double a1 = Double.parseDouble((String)list1.get(i));
            double a2 = Double.parseDouble((String)list2.get(i));
            if(a1 > a2)
            {
                if(TokenisedGender2.get(i).toString().equals("Female"))
                {
                    Accuracy++;
                }
            }
            else
            {
                if(TokenisedGender2.get(i).toString().equals("Male"))
                {
                    Accuracy++;
                }
            }
        }
        Accuracy = (Accuracy/(Pattern.size()-1))*100;
        System.out.println("Accuracy : "+Accuracy);
    }
}

