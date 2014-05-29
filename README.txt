This is an educational project to build a web app to solve sudokus using Java and Google App Engine.

Files
-----

Java:
- SudokuServlet.java: Contains the core code to solve a sudoku as well as the 
  HTTP servlet to hook that code up with HTTP request/response.

HTML:
- display.jsp: Template for HTML responses. Can 'see' SudokuServlet variables.
- index.jsp: Forwards to SudokuServlet.
- favicon.ico: The little icon displayed in the browser tab.

Configuration:
- pom.xml: Apache Maven build configuration.
- web.xml: Google App Engine's 'Deployment Descriptor' that maps URLs to Java servlets.
- appengine-web.xml: Additional config for Google App Engine and Java Web Apps.
- logging.properties: Google App Engine logging verbosity.