package App;

// tested for lucene 7.7.2 and jdk13
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.PhraseQuery;

import Parsing.Fileparser;
import Parsing.MyDoc;
import Parsing.QueryDoc;
import  java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import Parsing.QueriesParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;


/**
 * Creates a lucene's inverted index from an xml file.
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
public class Baseline {
    
    /**
     * Configures IndexWriter.
     * Creates a lucene's inverted index.
     *
     */
    public Baseline() throws Exception{
        
    	String indexLocation = ("index"); //define where to store the index        
       	String txtfile =  "LISA/LISA0.501"; // define which file to parse to put on the index
       
        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexLocation + "'...");
            
            Directory dir = FSDirectory.open(Paths.get(indexLocation));
            // define which analyzer to use for the normalization of documents
            Analyzer analyzer = new EnglishAnalyzer();
            // define retrieval model 
            Similarity similarity = new ClassicSimilarity();
            // configure IndexWriter
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setSimilarity(similarity);
            
            // 1. Create a new index in the directory, or
            // append new documents to the index
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

            IndexWriter indexWriter = new IndexWriter(dir, iwc);
            
            // parse text documents using our File parser and index it
            List<MyDoc> docs = Fileparser.parse(txtfile);
            for (MyDoc doc : docs){
                indexDoc(indexWriter, doc);
            }
            indexWriter.close();
             
            // 2. parse the queries
            List<QueryDoc> queries = Parsing.QueriesParser.parse("LISA/LISA.QUE");
            
            // 3. search all the index and match to each query
            
            // define how many hits per query
            int hitsPerPage = 50;
            
            String field = "contents"; //define which field will be searched  
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation))); //IndexReader is an abstract class, providing an interface for accessing an index.
           IndexSearcher indexSearcher = new IndexSearcher(indexReader);
           
           //create the file to put the results in the specified format for trec_eval
           File myfile = new File("C:\\Users\\User\\Desktop\\new new\\ProjectPart1\\results50.txt");
           FileWriter fileWriter = new FileWriter(myfile,true);
           BufferedWriter bw = new BufferedWriter(fileWriter);
     
          // search the index for each of the 35 queries
           for(int i=0;i<35;i++) {
           		search(indexSearcher, field,queries.get(i), hitsPerPage, bw );
           }

            // searcher can only be closed when there
            // is no need to access the documents any more. 
            indexReader.close();
            bw.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
            
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }
    
    private void search(IndexSearcher indexSearcher, String field, QueryDoc queryDoc, int hitsPerPage, BufferedWriter bw) {
		// TODO Auto-generated method stub
    	try{
    			// define which analyzer to use for the normalization of user's query
    			Analyzer analyzer = new EnglishAnalyzer();
            
    			// create a query parser on the field "contents"
    			QueryParser parser = new QueryParser(field, analyzer);
    			
    			System.out.println("Start query: " + queryDoc.getQueryNumber());
    			
    			// parse the query according to QueryParser
    			Query query = parser.parse(queryDoc.getText());

    			System.out.println("Searching for: " + queryDoc.toString());
                
                // search the index using the indexSearcher
                TopDocs results = indexSearcher.search(query, hitsPerPage);
                
                ScoreDoc[] hits = results.scoreDocs;
                long numTotalHits = results.totalHits;
                System.out.println(numTotalHits + " total matching documents");

                //display results for the query i
                
                System.out.println("Found " + hits.length + " hits.");
                
                for(int i=0;i<hits.length;++i) {
                	int docId = hits[i].doc;
                	Document d = indexSearcher.doc(docId);
                	System.out.println((i + 1) + ". " + d.get("document") + " " + d.get("title"));
                	bw.write(queryDoc.getQueryNumber() + " 0 "+ d.get("document") + " 0 "+hits[i].score +" STANDARD"); // write on the file resultsk.txt
                    bw.newLine();
                }    
        } catch(Exception e){
            e.printStackTrace();
        }
	}

	/**
     * Creates a Document by adding Fields in it and 
     * indexes the Document with the IndexWriter
     *
     * @param indexWriter the indexWriter that will index Documents
     * @param mydoc the document to be indexed
     *
     */
    private void indexDoc(IndexWriter indexWriter, MyDoc mydoc){
        try {
            // make a new, empty document
            Document doc = new Document();
            
            // create the fields of the document and add them to the document
            StoredField document = new StoredField("document", mydoc.getDocument());
            doc.add(document);
            StoredField title = new StoredField("title", mydoc.getTitle());
            doc.add(title);
            StoredField text = new StoredField("text", mydoc.getText());
            doc.add(text);
            String fullSearchableText = mydoc.getDocument() + " " + mydoc.getTitle() + " " + mydoc.getText();            
            TextField contents = new TextField("contents", fullSearchableText, Field.Store.NO);
            doc.add(contents);
            
            if (indexWriter.getConfig().getOpenMode() == OpenMode.CREATE_OR_APPEND) {
                // New index, so we just add the document (no old document can be there):
                System.out.println("adding " + mydoc);
                indexWriter.addDocument(doc);
            } 
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
        	new Baseline();
        	new ReadIndex();
        	new RelConverter();
        	
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}