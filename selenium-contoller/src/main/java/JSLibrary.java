import java.util.Arrays;

public class JSLibrary {
    public static String createObserver(){
        return "window.updatedList = [];\n" + 
               "var target = document.getElementById('A43');\n" +
               "const config = {\n" + 
               "    attributes: true,\n" + 
               "    childList: true,\n" +
               "    subtree: true};\n" +
               "const callback = function(mutationsList) {\n" +
               "    window.updatedList.push(mutationsList);\n" + 
               "};\n" +
               "const observer = new MutationObserver(callback);\n" +
               "observer.observe(target, config);";
    }
    
    public static String getMultiClicker(){
        return "window.clickerMul = function clickMulti(moveList){\n" + "       function sleep(miliseconds) {\n" +
               "       var currentTime = new Date().getTime();\n" +
               "       while (currentTime + miliseconds >= new Date().getTime()) {\n" +
               "       }\n" + "    }\n" + "\tfor(var i = 0; i<moveList.length; i+=2){\n" +
               "\t\tvar row = moveList[i];\n" + "\t\tvar col = moveList[i+1];\n" +
               "\t\tvar ele = document.getElementById(\"cell_\" + row + \"_\" +col);\n" +
               "\t\tvar myEvent1 = new MouseEvent('mousedown');\n" +
               "\t\tvar myEvent2 = new MouseEvent('mouseup');\n" +
               "\t\tele.dispatchEvent(myEvent1);\n" + "\t\tele.dispatchEvent(myEvent2);\n" +
               "        sleep(0);\n" + "        console.log(\"Iteration ended: \" + i);\n" +
               "\t}\n" + "}";
    }
    
    public static String getMultiClick(int[] list){

        String s = "window.clickerMul(" + Arrays.toString(list) + ");";
        System.out.println("js string:" +s);
        return s;
    }
    
    public static String getElementsWithCoordinates(){
        return "var result = [];\n" +
               "var elements = document.getElementsByClassName(\"cell\");\n" +
               "for (var i = 0; i<elements.length; i++){\n" +
               "    result.push([elements[i].dataset.x, elements[i].dataset.y, elements[i]]);\n" +
               "}\n" + "return result;";
    }
    
    public static String clearUpdates(){
        return "window.updatedList = [];";
    }
    public static String getUpdatedAttributes(){
        return "var result = [];\n" +
               "for (var i = 0; i < window.updatedList.length; i++){\n" +
               "     for(var j = 0; j<window.updatedList[i].length; j++){" +
               "        result.push(window.updatedList[i][j].target.id); " +
               "        result.push(window.updatedList[i][j].target.classList.toString());" +
               "}} return result";
    }
    public static String getAllAtrributes(){
        return "var result = [];\n" +
               "var elements = document.getElementsByClassName(\"cell\");\n" +
               "for (var i = 0; i < elements.length; i++){\n" +
               "     result.push(elements[i].id); " +
               "     result.push(elements[i].classList);" +
               "} return result";
    }
}
