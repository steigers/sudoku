This is an educational project to build a web app to solve sudokus using Java and Google App Engine.

Files
-----

Java:
- src/main/java/com/google/appengine/sudoku/SudokuServlet.java:
  Contains the core code to solve a sudoku as well as the 
  HTTP servlet to connect it with HTTP requests/responses.

HTML:
- src/main/webapp/display.jsp:
  Template for HTML responses. Can 'see' SudokuServlet variables.
- src/main/webapp/index.jsp:
  Forwards to SudokuServlet.
- src/main/webapp/favicon.ico:
  The little icon displayed in the browser tab.

Configuration:
- pom.xml:
  Apache Maven build configuration.
- src/main/webapp/WEB-INF/web.xml:
  Google App Engine's 'Deployment Descriptor' that maps URLs to Java servlets.
- src/main/webapp/WEB-INF/appengine-web.xml:
  Additional config for Google App Engine and Java Web Apps.
- src/main/webapp/WEB-INF/logging.properties:
  Google App Engine logging verbosity.