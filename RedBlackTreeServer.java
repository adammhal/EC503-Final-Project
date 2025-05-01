import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class RedBlackTreeServer {

    private static final RedBlackTree<Integer> tree = new RedBlackTree<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tree", new TreeHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:8080/tree");
    }

    static class TreeHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();

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
            OutputStream os = exchange.getResponseBody();
            os.write(respBytes);
            os.close();
        }
    }
}
