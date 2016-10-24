package per.refresh.util;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ReptileUtil {

    private final static String URLTEXT = "http://www.doumi.com/hz/";

    public static List<Integer> getRank(List<String> workIds){

        List<Integer> workRands = new ArrayList<Integer>();
        try {
            Parser parser = null;
            parser = new Parser(new URL(URLTEXT).openConnection());
            NodeList nodeList = getAList(parser, "");

            for(String tmp : workIds) {
                for (int i = 0; i < nodeList.size(); i++) {
                    Node node = nodeList.elementAt(i);
                    String url = ((LinkTag) node).getLink();
                    if (url.indexOf(tmp) != -1) {
                        workRands.add(i + 1);
                    }
                }
            }

        } catch (ParserException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return workRands;
    }

    public static NodeList getAList(Parser parser, String className) throws ParserException {

        return parser.extractAllNodesThatMatch(new LinkRegexFilter("^"+ URLTEXT + "jz_\\d*\\.htm$"));
    }

    public static void main(String[] args){
      /*  isFirst("");*/
    }
}
