package App;

// tested for lucene 7.7.2 and jdk13
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.classification.utils.DocToDoubleVectorUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
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
import utils.IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import Parsing.QueriesParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Jama.Matrix;
import Jama.SingularValueDecomposition;


/**
 * Creates a lucene's inverted index from an xml file.
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
public class LSI {
    
    /**
     * Configures IndexWriter.
     * Creates a lucene's inverted index.
     *
     */
    public LSI(String[] txtfiles) throws Exception{
        
    	String indexLocation = ("index"); //define where to store the index        
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
            //iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
            
            IndexWriter indexWriter = new IndexWriter(dir, iwc);
            
            FieldType type = new FieldType();
			type.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
			type.setTokenized(true);
			type.setStored(true);
			type.setStoreTermVectors(true);
			
            // parse text documents using our File parser and index it
            List<MyDoc> docs = Fileparser.parse(txtfiles[0]);
            docs.addAll(Fileparser.parse(txtfiles[1])) ;
            docs.addAll(Fileparser.parse(txtfiles[2])) ;
            docs.addAll(Fileparser.parse(txtfiles[3])) ;
            docs.addAll(Fileparser.parse(txtfiles[4])) ;
            docs.addAll(Fileparser.parse(txtfiles[5])) ;
            docs.addAll(Fileparser.parse(txtfiles[6])) ;
            docs.addAll(Fileparser.parse(txtfiles[7])) ;
            docs.addAll(Fileparser.parse(txtfiles[8])) ;
            docs.addAll(Fileparser.parse(txtfiles[9])) ;
            docs.addAll(Fileparser.parse(txtfiles[10])) ;
            docs.addAll(Fileparser.parse(txtfiles[11])) ;
            docs.addAll(Fileparser.parse(txtfiles[12])) ;
            docs.addAll(Fileparser.parse(txtfiles[13])) ;
            
          //2. parse the queries
            List<QueryDoc> queries = Parsing.QueriesParser.parse("LISA/LISA.QUE");
            //add queries to index 
            for (int i=0 ; i<queries.size() ; i++){
                indexQueries(indexWriter, queries.get(i),type);
            } 
            for (MyDoc doc : docs){
                addDocWithTermVector(indexWriter, doc, type);
            } 

          

            indexWriter.close();


            IndexReader reader = DirectoryReader.open(dir);

            //testSparseFreqDoubleArrayConversion(reader);
            createQueriesTermArray(reader);

            reader.close();
          
			
            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
            
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }
    
    
    private static void testSparseFreqDoubleArrayConversion(IndexReader reader) throws Exception {
    	//File svd = new File("C:\\Users\\User\\Desktop\\ProjectPart2\\svd.txt");
		//FileWriter Writter = new FileWriter(svd,true);
		//BufferedWriter bufferwriter = new BufferedWriter(Writter);
		Terms fieldTerms = MultiFields.getTerms(reader, "contents");   //the number of terms in the lexicon after analysis of the Field "contents"
		System.out.println("Terms:" + fieldTerms.size());
		TermsEnum it = fieldTerms.iterator();						//iterates through the terms of the lexicon
		while(it.next() != null) {
			System.out.print(it.term().utf8ToString() + " "); 		//prints the terms 
		}
		System.out.println();
		System.out.println();
		if (fieldTerms != null && fieldTerms.size() != -1) {
			IndexSearcher indexSearcher = new IndexSearcher(reader);
			for (ScoreDoc scoreDoc : indexSearcher.search(new MatchAllDocsQuery(), Integer.MAX_VALUE).scoreDocs) {   //retrieves all documents
				System.out.println("DocID: " +  scoreDoc.doc);
				//bufferwriter.write("DocID:" + scoreDoc.doc);
				//bufferwriter.newLine();
				Terms docTerms = reader.getTermVector(scoreDoc.doc, "contents");
				
				Double[] vector = DocToDoubleVectorUtils.toSparseLocalFreqDoubleArray(docTerms, fieldTerms); //creates document's vector
				NumberFormat nf = new DecimalFormat("0.#");
				for(int i = 0; i<=vector.length-1; i++ ) {
					System.out.print(nf.format(vector[i])+ " ");   //prints document's vector
					//bufferwriter.write(nf.format(vector[i])+ " ");
					
				}
				//bufferwriter.newLine();
				System.out.println();
				System.out.println();
			}
		}  
		//bufferwriter.close();
    }

	/**
     * Creates a Document by adding Fields in it and 
     * indexes the Document with the IndexWriter
     *
     * @param indexWriter the indexWriter that will index Documents
     * @param mydoc the document to be indexed
     *
     */
	private static void addDocWithTermVector(IndexWriter indexWriter, MyDoc mydoc, FieldType type) throws IOException {
		Document doc = new Document();
	    //TextField title = new TextField("title", value, Field.Store.YES);
		
		// create the fields of the document and add them to the document
        String value = mydoc.getTitle() +  " " + mydoc.getText();
        
        Field field = new Field("contents", value, type);		
        
		doc.add(field);  //this field has term Vector enabled.
		indexWriter.addDocument(doc);
	}
	
		private void indexQueries(IndexWriter indexWriter, QueryDoc queryDoc, FieldType type){
            try {
                // make a new, empty document
                Document query = new Document();


             // create the fields of the document and add them to the document
                String value = queryDoc.getText();

                Field field = new Field("fullQuery", value, type);

                query.add(field);  //this field has term Vector enabled.
                indexWriter.addDocument(query);

            } catch(Exception e){
                e.printStackTrace();
            }
        }
 private static void createQueriesTermArray(IndexReader queryReader) throws Exception {
//	 	File svd = new File("C:\\Users\\User\\Desktop\\ProjectPart2\\QueryTerms.txt");
//		FileWriter Writter = new FileWriter(svd,true);
//		BufferedWriter bufferwriter = new BufferedWriter(Writter);	
	 	Terms QueryfieldTerms = MultiFields.getTerms(queryReader, "contents");   //the number of terms in the lexicon after analysis of the Field "contents"
		System.out.println("Terms:" + QueryfieldTerms.size());
		double[][] fullVector= new double[13579][35];
		TermsEnum it = QueryfieldTerms.iterator();						//iterates through the terms of the lexicon
		while(it.next() != null) {
			System.out.print(it.term().utf8ToString() + " "); 		//prints the terms 
		}
		System.out.println();
		System.out.println();
		if (QueryfieldTerms != null && QueryfieldTerms.size() != -1) {
			int k =1;
			for (int i=0 ; i<=34 ; i++) {   //retrieves all 35 queries
				System.out.println("QueryID: " + k );
				k++;
				Terms queryTerms = queryReader.getTermVector(i, "fullQuery");
		
				Double[] queryvector = DocToDoubleVectorUtils.toSparseLocalFreqDoubleArray(queryTerms, QueryfieldTerms); //creates query's vector
				NumberFormat nf = new DecimalFormat("0.#");
				for(int j= 0; j<=queryvector.length-1; j++ ) {
						
					fullVector[j][i]= queryvector[j]; //create termxQuery 2-dimensional array
					System.out.print(nf.format(queryvector[j])+ " ");   //prints query's vector
					//bufferwriter.write(nf.format(queryvector[j])+ " ");
					}
				//bufferwriter.newLine();
				System.out.println();
				System.out.println();
			}
		}
		//bufferwriter.close();
		

    }
	
    public static void main(String[] args) {
        try {
        	String[] txtfiles = new String[14];
        	txtfiles[0] ="LISA/LISA0.001";
        	txtfiles[1] ="LISA/LISA0.501";
        	txtfiles[2] ="LISA/LISA1.001";
        	txtfiles[3] ="LISA/LISA1.501";
        	txtfiles[4] ="LISA/LISA2.001";
        	txtfiles[5] ="LISA/LISA2.501";
        	txtfiles[6] ="LISA/LISA3.001";
        	txtfiles[7] ="LISA/LISA3.501";
        	txtfiles[8] ="LISA/LISA4.001";
        	txtfiles[9] ="LISA/LISA4.501";
        	txtfiles[10] ="LISA/LISA5.001";
        	txtfiles[11] ="LISA/LISA5.501";
        	txtfiles[12] ="LISA/LISA5.627";
        	txtfiles[13] ="LISA/LISA5.850";
        	
        	new LSI(txtfiles);
        	
        	//new ReadIndex();
        	//new RelConverter();
        	
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}