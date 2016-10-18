package per.refresh.util;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ReptileUtil {

    private final static String URLTEXT = "http://www.doumi.com/hz/";

    public static void isFirst(String tagText){

        try {
            Parser parser = null;
            try {
                parser = new Parser(new URL(URLTEXT).openConnection());
            } catch (IOException e) {
                e.printStackTrace();
            }
            NodeList nodeList = getAList(parser, "");
            for(int i = 0; i < nodeList.size(); i++){
                Node node = nodeList.elementAt(i);
                String url = ((LinkTag)node).getLink();
                if(url.indexOf("2085433") != -1){
                    System.out.println(i);
                }
            }

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public static NodeList getAList(Parser parser, String className) throws ParserException {

        return parser.extractAllNodesThatMatch(new LinkRegexFilter("^"+ URLTEXT + "jz_\\d*\\.htm$"));
    }

    public static void main(String[] args){
      /*  isFirst("");*/
    }
}
