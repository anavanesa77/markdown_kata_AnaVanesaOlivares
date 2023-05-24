import markdown.markdown.Markdown;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 *
 * To do list:
 * 1. [this book] -> this book
 * 2. new line -> [^anchor1]: https://codigosostenible.com
 * 3. [this book (https://codigosostenible.com and some [other text] (https://codigosostenible.com) and some other text line. -> [this book (https://codigosostenible.com and some [other text] (https://codigosostenible.com) and some other text line.
 * 4. [this book] (https://codigosostenible.com) and some [other text] (https://tdd.com) and some other text line. -> this book [^anchor1] and some other text [^anchor2] and some other text line.
 * 5. [this book] (https://codigosostenible.com) and some [other text] (https://codigosostenible.com) and some other text line. -> this book [^anchor1] and some other text [^anchor1] and some other text line.
 */

class MarkdownShould {

    @Test
    void not_replace_if_there_are_not_links() {
        String text = "[this book] and some other text and " +
                "some other text line.";
        String modifiedText = Markdown.analyzeText(text);
        Assertions.assertEquals("[this book] and some other text " +
                "and some other text line.", modifiedText);
    }

    @Test
    void replace_a_link_by_an_anchor() {
        String text = "[this book] (https://codigosostenible.com) and some other text and " +
                "some other text line.";
        String modifiedText = Markdown.analyzeText(text);
        Assertions.assertEquals("this book [^anchor1] and some other text " +
                "and some other text line.\n[^anchor1]:https://codigosostenible.com", modifiedText);
    }

    @Test
    void replace_some_links_by_some_anchor() {
        String text = "[this book] (https://codigosostenible.com) and some [other text] (https://tdd.com) and some other text line.";
        String modifiedText = Markdown.analyzeText(text);
        Assertions.assertEquals("this book [^anchor1] and some other text [^anchor2] and some other text " +
                "line.\n[^anchor1]:https://codigosostenible.com\n[^anchor2]:https://tdd.com", modifiedText);
    }

    @Test
    void replace_some_links_with_the_same_anchor() {
        String text = "[this book] (https://codigosostenible.com) and some [other text] (https://codigosostenible.com) and some other text line.";
        String modifiedText = Markdown.analyzeText(text);
        Assertions.assertEquals("this book [^anchor1] and some other text [^anchor1] and some other text line.\n[^anchor1]:https://codigosostenible.com", modifiedText);
    }

}