package TestPackage;

import documentProject.DifferentLocalStoragePaths;
import documentProject.DocumentLibrary;
import documentProject.TxtDocument;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DocumentLibraryTest {
    private DocumentLibrary testLibrary = DocumentLibrary.getLibrary();

    @Test
    @Order(1)
    void readAllFilesTest() throws IOException {
        int i=0;
        File allTestFIles = new File(DifferentLocalStoragePaths.docPath+"\\DocumentProject\\src\\documentPackage");
        File[] fileArr = allTestFIles.listFiles();
        testLibrary.readInFilesToList();
        assertTrue(fileArr.length == testLibrary.getDocumentList().size());
        for (File file : fileArr) {
            assertTrue(file.getName().equals(testLibrary.getDocumentList().get(i).getTitle()));
            i++;
        }
    }

    @Test
    void getLibraryTest() {
        DocumentLibrary library = null;
        DocumentLibrary.getLibrary();
        assertFalse(library != null);
    }

    @Test
    void createTxtDocTest() throws IOException {
        DocumentLibrary.getLibrary().createNewTxtFile();
        DocumentLibrary.getLibrary().getDocumentList().get(DocumentLibrary.getLibrary().getDocumentList().size()-1).getTitle().equals("tjäna");
    }
    @Test
    void saveToTxtFileTest() throws IOException {
            testLibrary.saveToTxtFile();
            File testfile = new File("");
            assertFalse(testfile.exists());
        testfile= new File(DifferentLocalStoragePaths.docPath+"\\" +
                "DocumentProject\\src\\documentPackage\\"+testLibrary.getTitle()+".txt");
        assertFalse(testfile.createNewFile());
        assertTrue(testfile.exists());
        assertEquals(testLibrary.getTitle()+".txt",testfile.getName());
    }


    @Test
    void deleteFileTest() throws IOException {
        File testFile = new File(DifferentLocalStoragePaths.docPath+"\\DocumentProject\\src\\documentPackage\\testFile.txt");
        testFile.createNewFile();
        testLibrary.deleteTxtFile(testFile.getName());
        assertFalse(testFile.exists());

    }
    @Test
    void deleteDocumentList(){
        TxtDocument testTxt = new TxtDocument("deleteTest","deleteContent");
        testLibrary.addToList(testTxt);
        assertTrue(testLibrary.getDocumentList().contains(testTxt));
        testLibrary.deleteTxtDocument(testTxt);
        assertFalse(testLibrary.getDocumentList().contains(testTxt));
    }
    @Test
   public void cutString(){
        String txt = "test.txt";
        testLibrary.cutString(txt);
        assertEquals("test",txt);
    }
}






