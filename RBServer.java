// This server listens to localhost:8080/tree and supports the front end to send insert=10, delete=20, search=15 through POST requests.

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class RBServer {

    // make sure only one redblacktree exsis
    private static final RedBlackTree<Integer> tree = new RedBlackTree<>();

    // main function to start server
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tree", new TreeHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:8080/tree");
    }

    // read instructions from front-end
    static class TreeHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers h = exchange.getResponseHeaders();
            h.add("Access-Control-Allow-Origin", "*");
            h.add("Access-Control-Allow-Headers", "Content-Type");
            h.add("Access-Control-Allow-Methods", "POST, OPTIONS");

        
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1); 
                return;
            }

            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                try (exchange) {
                    String resp = tree.toJson();                       // current tree
                    byte[] out = resp.getBytes(StandardCharsets.UTF_8);
                    
                    h.add("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, out.length);
                    exchange.getResponseBody().write(out);                   // send body
                } // current tree
                return;                                            // finished
            }
          

            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }


            String body;
            try (InputStream is = exchange.getRequestBody()) {
                body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }

            String response;
            if (body.startsWith("insert=")) {
                int val = Integer.parseInt(body.substring(7));
                tree.insert(val);
                response = tree.toJson();
            } else if (body.startsWith("delete=")) {
                int val = Integer.parseInt(body.substring(7));
                tree.delete(val);
                response = tree.toJson();
            } else if (body.startsWith("search=")) {
                int val = Integer.parseInt(body.substring(7));
                boolean found = tree.contains(val);
                response = "{\"found\":" + found + "}";
            } else {
                response = "{\"error\":\"Invalid command\"}";
            }

            byte[] respBytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, respBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(respBytes);
            }
        }
    }
}
