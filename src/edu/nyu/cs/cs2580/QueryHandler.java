package edu.nyu.cs.cs2580;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;

class QueryHandler implements HttpHandler {
	private static String plainResponse =
			"Request received, but I am not smart enough to echo yet!\n";

	private Index _indexer;
	private Ranker _ranker;

	public QueryHandler(Index indexer){
		_indexer = indexer;
	}

	public static Map<String, String> getQueryMap(String query){  
		String[] params = query.split("&");  
		Map<String, String> map = new HashMap<String, String>();  
		for (String param : params){  
			String name = param.split("=")[0];  
			String value = param.split("=")[1];  
			map.put(name, value);  
		}
		return map;  
	} 

	public void handle(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();
		if (!requestMethod.equalsIgnoreCase("GET")){  // GET requests only.
			return;
		}

		// Print the user request header.
		Headers requestHeaders = exchange.getRequestHeaders();
		System.out.print("Incoming request: ");
		for (String key : requestHeaders.keySet()){
			System.out.print(key + ":" + requestHeaders.get(key) + "; ");
		}
		System.out.println();
		String queryResponse = "";  
		String uriQuery = exchange.getRequestURI().getQuery();
		String uriPath = exchange.getRequestURI().getPath();
		System.out.println("uriQuery: " + uriQuery);
		System.out.println("uriPath: " + uriPath);
		String query = "";
        String format = "text/plain";

		if ((uriPath != null) && (uriQuery != null)){
			if (uriPath.equals("/search")){
				Map<String,String> query_map = getQueryMap(uriQuery);
				Set<String> keys = query_map.keySet();
				if (keys.contains("query")){
					query = query_map.get("query");
					if (keys.contains("ranker")){
						// This will get the string t
						String ranker_type = query_map.get("ranker");
						// @CS2580: Invoke different ranking functions inside your
						// implementation of the Ranker class.
						if (ranker_type.equals("cosine")){
							_ranker = Ranker.Factory.getRankerByParameter(RankerType.COSINE, _indexer);
						} else if (ranker_type.equals("QL")){
							_ranker = Ranker.Factory.getRankerByParameter(RankerType.QL, _indexer);
						} else if (ranker_type.equals("phrase")){
							_ranker = Ranker.Factory.getRankerByParameter(RankerType.PHRASE, _indexer);
						} else if (ranker_type.equals("numviews")){
							_ranker = Ranker.Factory.getRankerByParameter(RankerType.NUMVIEWS, _indexer);
						} else if (ranker_type.equals("linear")){
							_ranker = Ranker.Factory.getRankerByParameter(RankerType.LINEAR, _indexer);
						} else {
						    _ranker = Ranker.Factory.getRankerByParameter(RankerType.NONE, _indexer);
						}
					}
					else {
						_ranker = Ranker.Factory.getRankerByParameter(RankerType.NONE, _indexer);
					}
		            // Getting the results of Ranking
		            Vector<ScoredDocument> scoredDocuments = _ranker.runQuery(query);

                    if(keys.contains("format") && query_map.get("format").equals("html")){
		                queryResponse = searchHtmlResponse(scoredDocuments, queryResponse,query);
                        format = "text/html";
                    }
                    else{
		                // check if return type is necessary. 
		                queryResponse = searchTextResponse(scoredDocuments, queryResponse,query);
                    }
				}
                else{
                    // no query 
                }
			}
		}

        
		// Construct a simple response.
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", format);
		exchange.sendResponseHeaders(200, 0);  // arbitrary number of bytes
		OutputStream responseBody = exchange.getResponseBody();
		responseBody.write(queryResponse.getBytes());
		responseBody.close();
	}

	private String searchTextResponse(Vector<ScoredDocument> scoredDocuments, String queryResponse, String query){
		Iterator<ScoredDocument> itr = scoredDocuments.iterator();
		while(itr.hasNext()){
			ScoredDocument sd = itr.next();
			queryResponse = queryResponse + query + "\t" + sd.asString();
			if (queryResponse.length() > 0){
				queryResponse = queryResponse + "\n";
			}
		}
		return queryResponse;
	}
	private String searchHtmlResponse(Vector<ScoredDocument> scoredDocuments, String queryResponse, String query){
        StringBuilder sb = new StringBuilder();
        sb.append(queryResponse);
		Iterator<ScoredDocument> itr = scoredDocuments.iterator();
		while(itr.hasNext()){
			ScoredDocument sd = itr.next();
            sb.append("<a href=\"");
            sb.append("");
            sb.append("\">");
            sb.append(sd._title);
            sb.append("</a><br>\n");
        }
        return sb.toString();
    }
}
