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
        return "window.clickerMul = function clickMulti(moveList){\n" + "       function sleep(milliseconds) {\n" +
               "       var currentTime = new Date().getTime();\n" +
               "       while (currentTime + milliseconds >= new Date().getTime()) {\n" +
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
        return "window.clickerMul(" + Arrays.toString(list) + ");";
    }

    @SuppressWarnings("SameReturnValue")
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
}
