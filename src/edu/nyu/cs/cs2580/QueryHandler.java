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

		if ((uriPath != null)){
			if (uriPath.equals("/search")){
                if(uriQuery != null){
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
            else if (uriPath.equals("/result")){
                if(uriQuery != null){
				    Map<String,String> query_map = getQueryMap(uriQuery);
				    Set<String> keys = query_map.keySet();
                    if(keys.contains("did")){
                        int did = Integer.parseInt(query_map.get("did"));
                        queryResponse = resultHtmlResponse(did);
                        format = "text/html";
                    }
                }
            }
            else if (uriPath.equals("/click")){
                if(uriQuery != null){
				    Map<String,String> query_map = getQueryMap(uriQuery);
				    Set<String> keys = query_map.keySet();
                    if(keys.contains("sid") && keys.contains("did") && keys.contains("query")){
                        int sid = Integer.parseInt(query_map.get("sid"));
                        int did = Integer.parseInt(query_map.get("did"));
                        query = query_map.get("query");
                        Logger.addLog(sid, query, did, "click");
                        queryResponse = clickHtmlResponse(did);
                        format = "text/html";
                    }
                }
            }
            else if (uriPath.equals("/log")){
                queryResponse = logTextResponse();
                format = "text/plain";
            }
		}

        
		// Construct a simple response.
        sendResponse(exchange, new Response(format, queryResponse));
	}

    private void sendResponse(HttpExchange exchange, Response rp) throws IOException{
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", rp.format);
		exchange.sendResponseHeaders(200, 0);  // arbitrary number of bytes
		OutputStream responseBody = exchange.getResponseBody();
		responseBody.write(rp.response.getBytes());
		responseBody.close();
    }

    private String logTextResponse(){
        StringBuilder sb = new StringBuilder();
        Vector<Logger> logs = Logger.getAllLog();
        System.out.println("logs size: " + logs.size());
        for(int i = 0; i < logs.size(); i++){
            Logger lg = logs.get(i);
            sb.append(lg._sid);
            sb.append("\t");
            sb.append(lg._query);
            sb.append("\t");
            sb.append(lg._did);
            sb.append("\t");
            sb.append(lg._action);
            sb.append("\t");
            sb.append(lg._time);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String resultHtmlResponse(int did){
        Document doc = _indexer.getDoc(did);
        StringBuilder sb = new StringBuilder();
        if(doc != null){
            sb.append("<!DOCTYPE html><html><head></head><body>");
            sb.append("<h1>");
            sb.append(doc.get_title_string());
            sb.append("</h1><br>");
            sb.append(doc.get_body_string());
            sb.append("</body></html>");
        }
        return sb.toString();
    }
    private String clickHtmlResponse(int did){
        Document doc = _indexer.getDoc(did);
        StringBuilder sb = new StringBuilder();
		if(doc != null){
            sb.append("<!DOCTYPE html><html><head>");
            sb.append("<meta http-equiv=\"refresh\" content=\"0;URL='");
            sb.append("/result?did=");
            sb.append(doc._docid);
            sb.append("'\"/>");
            sb.append("</head><body></body></html>");
        }
        return sb.toString();
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
        sb.append("<!DOCTYPE html><html><head></head><body>");
        sb.append(queryResponse);
        int sid = Logger.getSid();
		Iterator<ScoredDocument> itr = scoredDocuments.iterator();
		while(itr.hasNext()){
			ScoredDocument sd = itr.next();
            sb.append("<a href=\"");

            sb.append("/click?did=");
            sb.append(sd._did);

            sb.append("&sid=");
            sb.append(sid);

            sb.append("&query=");
            sb.append(query);

            sb.append("\">");
            sb.append(sd._title);
            sb.append("</a><br>\n");
        }
        sb.append("</body></html>");
        return sb.toString();
    }
}

class Response{
    public String format;
    public String response;
    public Response(String format, String response){
        this.format = format;
        this.response = response;
    }
}
