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
