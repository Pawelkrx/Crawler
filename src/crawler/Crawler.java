package crawler;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sqlLite.*;

/**
 *
 * @author PawelX
 */
/*
    Celem programu jest pobranie wszystkich linków ze stron:
        jezdziectwo.york.info.pl
        wedkarstwo.york.info.pl
*/
public class Crawler {
    private String host;
    private ArrayList<String> links = new ArrayList<String>();
    private int delay=0;
    private int MAX_DEPTH = 2;
    //private 
   public static void main(String[] args) throws Exception
   {
       String url = "https://www.olx.pl/motoryzacja";
       //url = "https://jezdziectwo.york.info.pl";
       new Crawler(url,1000).init(url);
   }
   
   // constructor with just url
   Crawler(String url)
   {
       this.host = url;
       this.delay = 0;
   }
   
   // with delay
   Crawler(String url,int delay)
   {
       this.host = url;
       this.delay = delay;
   }
   
   private void init(String url){
       
       getPageContent(url,0);
       
   }
   
   public void getPageContent(String url,int depth)
   {
       // connections to database
       Connect db = new Connect();
       System.out.println(db.contains(url));
       //try{Thread.sleep(delay);}catch(InterruptedException e){System.out.println(e);} 
       //if(!links.contains(url))
       //if(!db.contains(url))
       try
        {
            URL obj = new URL(url);        
            HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
            
            String contentType = conn.getContentType();            
            
            //db.add(url);

            //links.add(url);
            // if contet Type from site host is text/html
            // parse this site looking href links
            System.out.println("Content type:"+conn.getContentType());
            System.out.println("url:"+url);
            
            if(conn.getContentType().contains("text/html"))
            {
                
                String html = getStringURLContent(conn);        
                //System.out.println(html);
                Document document = Jsoup.parse(html);
                //System.out.println("Aktualna strona:"+host);
                System.out.println("Aktualny link:"+url);
                Elements linksOnPage = document.select("a[href]");
                int size = linksOnPage.size();
                System.out.println("Ilosc linków:"+size);
                //System.out.println("Aktualna głębokość:"+depth);
                depth++;
                
                for (Element page : linksOnPage) { 
                    {
                    //System.out.println("delay:"+delay);
                    try{Thread.sleep(delay);}catch(InterruptedException e){System.out.println(e);} 
                    
                    //System.out.println(page.attr("href"));
                    getPageContent(page.attr("href"),depth);
                    }
                }
            }
            
            
            //Document document = Jsoup.connect(URL).get();                
            //Pattern isHttpPattern = Pattern.compile("https://"+host+".*"|http://.*");
            //Matcher isHttpMatcher = isHttpPattern.matcher(link);
            //boolean isHttpLink = isHttpMatcher.matches();           
            //Elements linksOnPage = document.select("*[href~=(http*.)|(/.*)]");
            
            
            
            
        } catch (Exception e)
            {
            System.out.println(e);
            }
        
   } // end of getPageContent()
   
   public String getStringURLContent(HttpsURLConnection conn)
   {
        
        String html = "";
        try 
        {BufferedReader in =
            new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();
        //InputStreamReader a = new InputStreamReader(conn.getInputStream();
      
	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
        html = response.toString();
        } catch (Exception e){};
        //response.toString();
        
   return html;
   } // end of getStringURLContent
   public boolean isInsideLink(String link,String host)
    {
        //System.out.println("URL:"+link+" host:"+host);
        //host = "https://konieswiata.pl";
        //link = "203,Smakołyki";
        //link = "http://google.pl";
        //link = "https://konieswiata.pl/ssss";
        // is http Link
        Pattern isHttpPattern = Pattern.compile("https://.*|http://.*|https://www.*|http://.*");
        Matcher isHttpMatcher = isHttpPattern.matcher(link);
        boolean isHttpLink = isHttpMatcher.matches();
                
        Pattern isHttpHostPattern = Pattern.compile(host+".*");
        Matcher isHttpHostMatcher = isHttpHostPattern.matcher(link);
        boolean isHttpHost = isHttpHostMatcher.matches();
       //System.out.println(isHttpLink+" "+isHttpHost);
       
       if(isHttpLink && isHttpHost)
       {
       //System.out.println("Jest linkiem wewnętrznym:"+link);
       return true;
       }
       else
       if(!isHttpLink)
       {
           //System.out.println("Nie jest http");
       return true;
       }else
       {
           //System.out.println("jest linkiem zewnętrznym");
       return false;
       }
       
    
    } // end 
     
   

}

class Person
{
    String Imie;
    String Nazwisko;
    public static int id = 0;
    Person(String Imie,String Nazwisko)
    {
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        id++;
    }
}