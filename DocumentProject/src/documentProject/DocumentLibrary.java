package documentProject;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @author 91matfri
 */
public class DocumentLibrary {
    private static InputReader reader = InputReader.getInputReader();
    private static DocumentLibrary library;
    private static List<TxtDocument> documentList = new ArrayList<>();
    private static String title ="";
    private static String textContent = "";
    private static String docPath = DifferentLocalStoragePaths.getDocPath();
    private static int updateIndex;
    private static boolean savedDoc =false;
    public static DocumentLibrary getLibrary(){
        if (library ==null){
            library = new DocumentLibrary(); }
        return library;
    }

//test
    public void saveToTxtFile() throws IOException {
        File txtFile = new File(docPath+"\\DocumentProject\\src\\documentPackage\\"+title+".txt");
        String path = docPath+"\\DocumentProject\\src\\documentPackage";
        if (txtFile.getParent().equals(path)&&txtFile.createNewFile()&&txtFile.canExecute()){
            FileWriter writer = new FileWriter(txtFile);
            writer.write(textContent);
            writer.close();
            System.out.println("\""+title+"\" was created.");
            savedDoc =true; }
        else System.out.println("file already exists or invalid name for document");
    }

    public void updateFile(String title,String textContent) throws IOException {
        File txtFile = new File(docPath+"\\DocumentProject\\src\\documentPackage\\"+title+".txt");
        FileWriter writer = new FileWriter(txtFile);
        writer.append(textContent);
        writer.close();
    }
    public void updateTextContent(String title, String textContent){
        if (!documentExists(title))
        documentList.get(updateIndex).setTextContent(textContent);
    }

    public void readInFilesToList() throws IOException {
        File getAllFiles = new File(docPath+"\\DocumentProject\\src\\documentPackage");
        File[] fileArray = getAllFiles.listFiles();
        for (File txtfile: fileArray) {
            if (txtfile.isFile()&&txtfile.canExecute()&&txtfile.getName().endsWith(".txt")){
                savedDoc = true;
                addToList(new TxtDocument(cutString(txtfile.getName()),Files.readString(Paths.get(txtfile.getPath()), StandardCharsets.ISO_8859_1)));
            }
        }
    }

    public void createNewTxtFile() throws IOException {
        readInTitle();
        readInTextcontent();
        saveToTxtFile();
        addToList(new TxtDocument(title,textContent));
    }
    //reason for this method to return string is if we are going to use a View.
    public String printAllTitles(){
        String viewTitle="";
        if (documentList.size()!=0){
            for (TxtDocument title : documentList) {
            System.out.println(title.getTitle());
            viewTitle = title.getTitle()+ "\n" +viewTitle; }
        }
        else {System.out.println("The library is empty."); }
        return viewTitle;
    }

    public void printTextContent(){
        boolean titleFound = false;
        if (documentList.size()>0){
            for (TxtDocument txtContent : documentList) {
                if (txtContent.getTitle().equals(title)){
                System.out.println(txtContent.getTextContent());
                titleFound =true;
                break; }
            }
            if (!titleFound) {
                System.out.println("There is no \""+title+"\" in the library."); }
        }
        else {System.out.println("The library is empty."); }
    }

    public void deleteTxtFileFromLocalAndList() throws IOException {
            title = "";
            readInTitle();
            if (!documentExists(title)) {
                deleteTxtDocument();
                deleteTxtFile();
                System.out.println("\"" + title + "\" was deleted."); }
        else{
            System.out.println("There is no title \"" + title + "\" and no file was deleted."); }
    }

    public String cutString(String pathName){
        return (String) pathName.subSequence(0,pathName.length()-4);
    }

    public void deleteTxtDocument(){
        documentList.remove(documentList.get(updateIndex));
    }

    public void deleteTxtFile(){
        File deleteFile = new File(docPath+
                "\\DocumentProject\\src\\documentPackage\\"+title+".txt");
        if (deleteFile.isFile()){
        deleteFile.delete();
        }
    }

    public void addToList(TxtDocument document){
        if (documentExists(document.getTitle())&&savedDoc==true){
        documentList.add(document);
        savedDoc =false; }
    }
    public boolean documentExists(String docName){
        for (int i = 0; i <documentList.size() ; i++) {
            if (documentList.get(i).getTitle().equals(docName)) {
                updateIndex =i;
                return false; }
        }
            return true;
    }

    public void chooseTitleToPrint() {
        readInTitle();
        printTextContent();
    }

    public List<TxtDocument> getDocumentList() {
        return documentList;
    }

    public static String getTitle() {
        return title;
    }

    public void readInTextcontent(){
        textContent = "";
        System.out.println("Write into the document: ");
        while (textContent.equals("")) {
            textContent=reader.getString(); }
    }

    public void readInTitle(){
        title = "";
        System.out.println("Please enter the title you want to print:");
        while (title.equals("")) {
            title=reader.getString(); }
    }

    public static void setTitle(String title) {
        DocumentLibrary.title = title;
    }

    public static String getTextContent() {
        return textContent;
    }
    public String getTextContent(String title) {
        for (TxtDocument text:documentList) {
            if (text.getTitle().equals(title)) {
            return text.getTextContent(); }
        }
        return null;
    }

    public String[] createStringArray(String textContent){
        reader.setSc(new Scanner(textContent));
        ArrayList<String> tempList = new ArrayList();
        while (reader.getSc().hasNext()){
            tempList.add(reader.getSc().next());
        }
        reader.setSc(new Scanner(System.in));
        return tempList.toArray(new String[0]);
    }

    /**
     * quick sorting algorithm
     * @param words all sorted together
     */
    public static void quickSort(String[] words) {
        //upper case and lower case sorted together
        Comparator<String> caseInsensitiveStringComparator = (word1, word2) -> word1.compareToIgnoreCase(word2);
        new QuickSort<String>(caseInsensitiveStringComparator).sort(words);
    }

    public static void setTextContent(String textContent) {
        DocumentLibrary.textContent = textContent;
    }


    // methods from wordsreader class for sorting word documents
    public String[] readFile(FileInputStream filename) throws FileNotFoundException {
        FileInputStream stream = new FileInputStream(String.valueOf(filename));
        return readFile(stream); //stream - API flow
    }
    private String[] readFile(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        ArrayList<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            String next = scanner.next();
            words.add(next);
        }
        return words.toArray(new String[0]); //the placeholder to the Array to tell what the type of the Array it is
        //the Object Array we return as a String Array
    }
}
