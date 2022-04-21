package principal;

import com.sun.net.httpserver.Headers;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;                                                     //https://berghem.com.br/pt/
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class Crawler {

    public static void main(String[] args) {

        Scanner scanner_url = new Scanner(System.in);
        System.out.println("insira a url alvo, Exemplo:  https://alvo.com.br");
        String url = scanner_url.nextLine();
        crawl(1, url, new ArrayList<String>());
    }

    @SuppressWarnings("empty-statement")
    private static void crawl(int level, String url, ArrayList<String> visited) {
        if (level <= 5) {
            Document doc = request(url, visited);
            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if (visited.contains(next_link) == false) {
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> v) {
        try {

            Connection con = Jsoup.connect(url);
            org.jsoup.nodes.Document doc = con.get();
            con.header(url, url);
            System.out.println(con);
            Connection.Response response = Jsoup.connect(url).execute();
            Document document = Jsoup.connect(url).get();

            if (con.response().statusCode() == 200) {
                System.out.println("LINK: " + url + " /n");
                //System.out.println(doc.title());
                System.out.println("HEADERS ENCONTRADOS EM: " + url + " -- Header: " + response.multiHeaders());
                for (Element sentence : doc.getElementsByTag("form")) {
                    System.out.println("POSSÍVEL FORMULÁRIO ENCONTRADO EM:" + url + sentence.text());
                }

                v.add(url);
                return (Document) doc;
            }
        } catch (Exception e) {
            return null;

        }
        return null;
    }

}



