(javac RBServer.java && java RBServer &) \
&& (cd frontend && python3 -m http.server 3000 &) \
&& open http://localhost:3000/index.html