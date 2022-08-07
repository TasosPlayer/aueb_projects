package Parsing;

/**
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
public class MyDoc {

    private String document;
    private String title;
    private String text;

    public MyDoc(String document, String title, String text) {
        this.document = document;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        String ret = "MyDoc{"
                + "\n\tDocument: " + document
                + "\n\tTitle: " + title
                + "\n\tText: " + text;                
        return ret + "\n}";
    }

    //---- Getters & Setters definition ----
    public String getDocument() {
        return document;
    }
    
    public void setDocument(String document) {
        this.document = document;
    }


    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
