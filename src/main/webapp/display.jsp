<!DOCTYPE html>
<%
  // Get the string that we have set via setAttribute("values") in the Java code.
  final String values = (String) request.getAttribute("values");
%>
<html>
  <head>
    <title>Sudoku Solver</title>

    <!-- CSS formatting of some HTML elements. -->
    <style type="text/css">
      #sudoku-table input {
        float: left;
        padding-top: 5px;
        padding-bottom: 5px;
        width:34px; height:24px;
        text-align:center;
        vertical-align:middle;
        font-size: 16pt;
        -webkit-border-radius:0px;
      }
      .mt {
        border-top:thick solid #000000;
      }
      .mr {
        border-right:thick solid #000000;
      }
      .ml {
        border-left:thick solid #000000;
      }
      .mb {
        border-bottom:thick solid #000000;
      }
      select {
        font-size: 16pt;
      }
      textarea {
        font-size: 16pt;
        width: 500px;
      }
      table {
        border-collapse: collapse;
      }
      td {
      }
      th {
      }
      body {
        font-size: 14pt;
      }
    </style>

    <script type="text/javascript">
      function getValues() {
        var result = '';
        for (var i = 1; i <= 9; i++) {    // Row index (1...9).
          for (var j = 1; j <= 9; j++) {  // Column index (1..9).
            var cell = document.getElementById('cell' + String(i) + String(j));
            if (cell.value) {
              result += cell.value;
            } else {
              result += '0';
            }
            if (i != 9 || j != 9) {
              result += ',';
            }
          }
        }
        var cell = document.getElementById('values-to-solve');
        cell.value = result;
      }

      function setValues() {
        var cell = document.getElementById('sudoku-vals');
        var values = cell.value.split(',');
        var i = 1;  // Row index (1..9).
        var j = 1;  // Column index (1..9).
        for (var idx = 0; idx < values.length; ++idx) {
          var cell = document.getElementById('cell' + String(i) + String(j));
          if (values[idx] == '0') {
            cell.value = '';
          } else {
            cell.value = values[idx];
          }
          ++j;
          if (j > 9) {
            j = 1;
            ++i;
          }
        }
      }
    </script>
  </head>

  <body onload="setValues();">
    <div id="sudoku-table">
      <table cellspacing="0" cellpadding="0">
        <tr id="row1">
          <td><input type="text" id="cell11" class="ml mt" value="" /></td>
          <td><input type="text" id="cell12" class="mt"    value="" /></td>
          <td><input type="text" id="cell13" class="mt"    value="" /></td>
          <td><input type="text" id="cell14" class="ml mt" value="" /></td>
          <td><input type="text" id="cell15" class="mt"    value="" /></td>
          <td><input type="text" id="cell16" class="mt"    value="" /></td>
          <td><input type="text" id="cell17" class="ml mt" value="" /></td>
          <td><input type="text" id="cell18" class="mt"    value="" /></td>
          <td><input type="text" id="cell19" class="mt mr" value="" /></td>
        </tr>
        <tr id="row2">
          <td><input type="text" id="cell21" class="ml" value="" /></td>
          <td><input type="text" id="cell22"            value="" /></td>
          <td><input type="text" id="cell23"            value="" /></td>
          <td><input type="text" id="cell24" class="ml" value="" /></td>
          <td><input type="text" id="cell25"            value="" /></td>
          <td><input type="text" id="cell26"            value="" /></td>
          <td><input type="text" id="cell27" class="ml" value="" /></td>
          <td><input type="text" id="cell28"            value="" /></td>
          <td><input type="text" id="cell29" class="mr" value="" /></td>
        </tr>
        <tr id="row3">
          <td><input type="text" id="cell31" class="ml" value="" /></td>
          <td><input type="text" id="cell32"            value="" /></td>
          <td><input type="text" id="cell33"            value="" /></td>
          <td><input type="text" id="cell34" class="ml" value="" /></td>
          <td><input type="text" id="cell35"            value="" /></td>
          <td><input type="text" id="cell36"            value="" /></td>
          <td><input type="text" id="cell37" class="ml" value="" /></td>
          <td><input type="text" id="cell38"            value="" /></td>
          <td><input type="text" id="cell39" class="mr" value="" /></td>
        </tr>
        <tr id="row4">
          <td><input type="text" id="cell41" class="ml mt" value="" /></td>
          <td><input type="text" id="cell42" class="mt"    value="" /></td>
          <td><input type="text" id="cell43" class="mt"    value="" /></td>
          <td><input type="text" id="cell44" class="ml mt" value="" /></td>
          <td><input type="text" id="cell45" class="mt"    value="" /></td>
          <td><input type="text" id="cell46" class="mt"    value="" /></td>
          <td><input type="text" id="cell47" class="ml mt" value="" /></td>
          <td><input type="text" id="cell48" class="mt"    value="" /></td>
          <td><input type="text" id="cell49" class="mt mr" value="" /></td>
        </tr>
        <tr id="row5">
          <td><input type="text" id="cell51" class="ml" value="" /></td>
          <td><input type="text" id="cell52"            value="" /></td>
          <td><input type="text" id="cell53"            value="" /></td>
          <td><input type="text" id="cell54" class="ml" value="" /></td>
          <td><input type="text" id="cell55"            value="" /></td>
          <td><input type="text" id="cell56"            value="" /></td>
          <td><input type="text" id="cell57" class="ml" value="" /></td>
          <td><input type="text" id="cell58"            value="" /></td>
          <td><input type="text" id="cell59" class="mr" value="" /></td>
        </tr>
        <tr id="row6">
          <td><input type="text" id="cell61" class="ml" value="" /></td>
          <td><input type="text" id="cell62"            value="" /></td>
          <td><input type="text" id="cell63"            value="" /></td>
          <td><input type="text" id="cell64" class="ml" value="" /></td>
          <td><input type="text" id="cell65"            value="" /></td>
          <td><input type="text" id="cell66"            value="" /></td>
          <td><input type="text" id="cell67" class="ml" value="" /></td>
          <td><input type="text" id="cell68"            value="" /></td>
          <td><input type="text" id="cell69" class="mr" value="" /></td>
        </tr>
        <tr id="row7">
          <td><input type="text" id="cell71" class="ml mt" value="" /></td>
          <td><input type="text" id="cell72" class="mt"    value="" /></td>
          <td><input type="text" id="cell73" class="mt"    value="" /></td>
          <td><input type="text" id="cell74" class="mt mt" value="" /></td>
          <td><input type="text" id="cell75" class="mt"    value="" /></td>
          <td><input type="text" id="cell76" class="mt"    value="" /></td>
          <td><input type="text" id="cell77" class="mt ml" value="" /></td>
          <td><input type="text" id="cell78" class="mt"    value="" /></td>
          <td><input type="text" id="cell79" class="mt mr" value="" /></td>
        </tr>
        <tr id="row8">
          <td><input type="text" id="cell81" class="ml" value="" /></td>
          <td><input type="text" id="cell82"            value="" /></td>
          <td><input type="text" id="cell83"            value="" /></td>
          <td><input type="text" id="cell84" class="ml" value="" /></td>
          <td><input type="text" id="cell85"            value="" /></td>
          <td><input type="text" id="cell86"            value="" /></td>
          <td><input type="text" id="cell87" class="ml" value="" /></td>
          <td><input type="text" id="cell88"            value="" /></td>
          <td><input type="text" id="cell89" class="mr" value="" /></td>
        </tr>
        <tr id="row9">
          <td><input type="text" id="cell91" class="mb ml" value="" /></td>
          <td><input type="text" id="cell92" class="mb"    value="" /></td>
          <td><input type="text" id="cell93" class="mb"    value="" /></td>
          <td><input type="text" id="cell94" class="mb ml" value="" /></td>
          <td><input type="text" id="cell95" class="mb"    value="" /></td>
          <td><input type="text" id="cell96" class="mb"    value="" /></td>
          <td><input type="text" id="cell97" class="mb ml" value="" /></td>
          <td><input type="text" id="cell98" class="mb"    value="" /></td>
          <td><input type="text" id="cell99" class="mb mr" value="" /></td>
        </tr>
      </table>
    </div>
    <form name="solve_form" action="/sudoku" method="get">
      <!-- The value of this tag is transferred to the textXY fields by setValues(). -->
      <input id="sudoku-vals" type="hidden" name="values" value=<%= values %> />
      <!-- The value of this tag is set by getValues() and used in the Java code. -->
      <input id="values-to-solve" type="hidden" name="solve" value="" />
      <!-- Clicking on this button will trigger a GET request with parameter solve=2,,3,1,9,5,,.. -->
      <input id="solve-button" type="submit" value="Solve!" onclick="javascript:getValues();" />
    </form>
  </body>
</html>
