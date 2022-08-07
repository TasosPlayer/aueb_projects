package Parsing;
/**
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
public class QueryDoc {
	 private String queryNumber;
	  private String text;
	  public QueryDoc(String queryNumber, String text) {
	        this.queryNumber = queryNumber;
	        this.text = text;
	    }
	  public String toString() {
	        String ret = "MyQuery{"
	                + "\n\tQueryNumber: " + queryNumber
	                + "\n\tText: " + text;                
	        return ret + "\n}";
	    }
	  public String getQueryNumber() {
	        return queryNumber;
	    }
	    
	    public void setQueryNumber(String queryNumber) {
	        this.queryNumber = queryNumber;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }
}
