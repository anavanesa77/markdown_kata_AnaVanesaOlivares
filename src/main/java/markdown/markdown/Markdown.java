package markdown.markdown;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Markdown {
    public static String analyzeText(String text) {
        if (!text.contains("(")) {
            return text;
        }
        String textWithoutBrackets = text.replace("[", "").replace("]", "");
        return generateAnchors(textWithoutBrackets);
    }

    public static String generateAnchors(String text) {

        String webPage = "";
        String anchor = "";
        int anchorNumber = 0;
        Map<String, String> anchorWebpageMap = new HashMap<>();

        Pattern findContentInsideParentheses = Pattern.compile("\\((.*?)\\)");
        Matcher contentInsideParentheses = findContentInsideParentheses.matcher(text);

        while (contentInsideParentheses.find()) {
            for (Map.Entry<String, String> entry : anchorWebpageMap.entrySet()) {
                webPage = contentInsideParentheses.group(1);
                if (entry.getValue().equals(webPage)) {
                    anchor = entry.getKey();
                    text = contentInsideParentheses.replaceFirst(anchor);
                    return text;
                }
                anchor = "[^anchor" + anchorNumber + "]";
                var anchorIncludedInTheText = contentInsideParentheses.replaceFirst(anchor);
                text = anchorIncludedInTheText + "\n" + anchor + ":" + webPage;
            }

            contentInsideParentheses = findContentInsideParentheses.matcher(text);
            anchorNumber++;
            anchorWebpageMap.put(anchor, webPage);
        }
        return text;
    }

}