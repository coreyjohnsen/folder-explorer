//////////////// P07 Folder Explorer //////////////////////////////////////////
//
// Title: FolderExplorer.java
// Course: CS 300 Fall 2021
//
// Author: Corey Johnsen
// Email: cjjohnsen@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html
//                 Used to view methods of the File class
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class tests the FolderExplorer class
 */
public class FolderExplorerTester {

  /**
   * Checks that the getContents method in FolderExplorer works as expected
   * 
   * @param folder is the base directory to reference
   * @returns true if getContents works, false otherwise
   */
  public static boolean testGetContents(File folder) {
    try {
      // Scenario 1
      // list the basic contents of the cs300 folder
      ArrayList<String> listContent = FolderExplorer.getContents(folder);
      // expected output must contain "exams preparation", "grades",
      // "lecture notes", "programs", "reading notes", "syllabus.txt",
      // and "todo.txt" only.
      String[] contents = new String[] {"exams preparation", "grades", "lecture notes", "programs",
          "reading notes", "syllabus.txt", "todo.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (listContent.size() != 7) {
        System.out.println("Problem detected: cs300 folder must contain 7 elements.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }
      // Scenario 2 - list the contents of the grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      listContent = FolderExplorer.getContents(f);
      if (listContent.size() != 0) {
        System.out.println("Problem detected: grades folder must be empty.");
        return false;
      }
      // Scenario 3 - list the contents of the p02 folder
      f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
      listContent = FolderExplorer.getContents(f);
      if (listContent.size() != 1 || !listContent.contains("FishTank.java")) {
        System.out.println(
            "Problem detected: p02 folder must contain only " + "one file named FishTank.java.");
        return false;
      }
      // Scenario 4 - List the contents of a file
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FolderExplorer.getContents(f);
        System.out.println("Problem detected: Your FolderExplorer.getContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // no problem detected
      }
      // Scenario 5 - List the contents of not found directory/file
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        listContent = FolderExplorer.getContents(f);
        System.out.println("Problem detected: Your FolderExplorer.getContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // behavior expected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FolderExplorer.getContents() has thrown"
          + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Checks that the base case of getDeepContents method in FolderExplorer works as expected
   * 
   * @param folder is the base directory to reference
   * @returns true if base case of getDeepContents works, false otherwise
   */
  public static boolean testDeepGetContentsBaseCase(File folder) {

    ArrayList<String> testing = new ArrayList<String>();
    File dir = new File(
        folder.getPath() + File.separator + "exams preparation" + File.separator + "exam1");
    String[] contents = new String[] {"codeSamples.java", "outline.txt"};
    List<String> expectedList = Arrays.asList(contents);

    // test valid base case of folder "reading notes"
    try {
      testing = FolderExplorer.getDeepContents(dir);
      if (testing.size() != 2) {
        System.out.println("Array size should be 2");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!testing.contains(expectedList.get(i))) {
          System.out.println("Array contents invalid");
          return false;
        }
      }

    } catch (NotDirectoryException e) {
      System.out.println("Exception incorrectly thrown");
      return false;
    } catch (Exception e) {
      System.out.println("Exception incorrectly thrown");
      return false;
    }

    // test invalid directory
    dir = new File(folder.getPath() + File.separator + "syllabus.txt");
    try {
      testing = FolderExplorer.getDeepContents(dir);
      return false;
    } catch (NotDirectoryException e) {
      // do nothing
    } catch (Exception e) {
      System.out.println("Exception incorrectly thrown");
      return false;
    }

    return true;
  }

  /**
   * Checks that the recursive case of getDeepContents method in FolderExplorer works as expected
   * 
   * @param folder is the base directory to reference
   * @returns true if recursive case of getDeepContents works, false otherwise
   */
  public static boolean testDeepListRecursiveCase(File folder) {

    ArrayList<String> testing = new ArrayList<String>();
    File dir = new File(folder.getPath() + File.separator + "programs");

    // test valid recursive case
    String[] contents = new String[] {"ClimbingTracker.java", "ClimbingTrackerTester.java",
        "FishTank.java", "ExceptionalClimbing.java", "ExceptionalClimbingTester.java",
        "Program01.pdf", "Program02.pdf", "Program03.pdf"};
    List<String> expectedList = Arrays.asList(contents);
    try {
      testing = FolderExplorer.getDeepContents(dir);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size does not match expected size");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!testing.contains(expectedList.get(i))) {
          System.out.println("Contents do not match expected files");
          return false;
        }
      }
    } catch (NotDirectoryException e) {
      System.out.println("Unexpected exception thrown");
      return false;
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test invalid directory
    dir = new File(folder.getPath() + File.separator + "syllabus.txt");
    try {
      testing = FolderExplorer.getDeepContents(dir);
      return false;
    } catch (NotDirectoryException e) {
      // do nothing
    } catch (Exception e) {
      System.out.println("Exception incorrectly thrown");
      return false;
    }

    return true;
  }

  /**
   * Checks that the lookupByName method in FolderExplorer works as expected
   * 
   * @param folder is the base directory to reference
   * @returns true if lookupByName works, false otherwise
   */
  public static boolean testLookupByFileName(File folder) {
    ArrayList<String> testing = new ArrayList<String>();
    String output = "";
    File dir = new File(folder.getPath());
    String foundPath;

    // test valid search for "Generics.txt"
    String searchFor = "Generics.txt";
    try {
      foundPath = FolderExplorer.lookupByName(dir, searchFor);
      if (!foundPath.equals("cs300" + File.separator + "lecture notes" + File.separator + "unit2"
          + File.separator + "Generics.txt"))
        ;
    } catch (NoSuchElementException e) {
      System.out.println("Unexpected exception thrown");
      return false;
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test valid search for "ClimbingTracker.java"
    searchFor = "ClimbingTracker.java";
    try {
      foundPath = FolderExplorer.lookupByName(dir, searchFor);
      if (!foundPath.equals("cs300" + File.separator + "programs" + File.separator + "p01"
          + File.separator + "ClimbingTracker.java"))
        ;
    } catch (NoSuchElementException e) {
      System.out.println("Unexpected exception thrown");
      return false;
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test invalid search for "invalidlol.txt"
    searchFor = "invalidlol.txt";
    try {
      foundPath = FolderExplorer.lookupByName(dir, searchFor);
      return false;
    } catch (NoSuchElementException e) {
      if (!e.getMessage().equals("No file matching name found"))
        return false;
      // do nothing
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test invalid directory
    searchFor = "ClimbingTracker.java";
    dir = new File(folder.getPath() + File.separator + "syllabus.txt");
    try {
      foundPath = FolderExplorer.lookupByName(dir, searchFor);
      return false;
    } catch (NoSuchElementException e) {
      if (!e.getMessage().equals("Directory not valid"))
        return false;
      // do nothing
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test non-existing directory
    searchFor = "ClimbingTracker.java";
    dir = new File(folder.getPath() + File.separator + "syllabi");
    try {
      foundPath = FolderExplorer.lookupByName(dir, searchFor);
      return false;
    } catch (NoSuchElementException e) {
      if (!e.getMessage().equals("Directory not valid"))
        return false;
      // do nothing
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test null search name
    searchFor = null;
    dir = new File(folder.getPath());
    try {
      foundPath = FolderExplorer.lookupByName(dir, searchFor);
      return false;
    } catch (NoSuchElementException e) {
      if (!e.getMessage().equals("File name is null"))
        return false;
      // do nothing
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    return true;
  }

  /**
   * Checks that the base case of lookupByKey method in FolderExplorer works as expected
   * 
   * @param folder is the base directory to reference
   * @returns true if the base case of lookupByKey works, false otherwise
   */
  public static boolean testLookupByKeyBaseCase(File folder) {

    ArrayList<String> testing = new ArrayList<String>();
    File dir = new File(folder.getPath() + File.separator + "programs" + File.separator + "p01");

    // test valid base case ".java" in p01
    String key = ".java";
    String[] contents = new String[] {"ClimbingTracker.java", "ClimbingTrackerTester.java"};
    List<String> expectedList = Arrays.asList(contents);

    try {
      testing = FolderExplorer.lookupByKey(dir, key);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size should be 2");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!testing.contains(expectedList.get(i))) {
          System.out.println("Found files do not match expected files");
          return false;
        }
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test valid base case "Tester" in p01
    key = "Tester";
    contents = new String[] {"ClimbingTrackerTester.java"};
    expectedList = Arrays.asList(contents);

    try {
      testing = FolderExplorer.lookupByKey(dir, key);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size should be 1");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!testing.contains(expectedList.get(i))) {
          System.out.println("Found files do not match expected files");
          return false;
        }
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test base case where no files are found
    key = "nope";
    contents = new String[] {};
    expectedList = Arrays.asList(contents);

    try {
      testing = FolderExplorer.lookupByKey(dir, key);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size should be 0");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test null key
    key = null;
    contents = new String[] {};
    expectedList = Arrays.asList(contents);

    try {
      testing = FolderExplorer.lookupByKey(dir, key);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size should be 0");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test invalid directory
    key = ".java";
    contents = new String[] {};
    expectedList = Arrays.asList(contents);
    dir = new File(folder.getPath() + File.separator + "programs" + File.separator + "p01"
        + File.separator + "ClimbingTracker.java");
    try {
      testing = FolderExplorer.lookupByKey(dir, key);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size should be 0");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    // test non-existent directory
    key = ".java";
    contents = new String[] {};
    expectedList = Arrays.asList(contents);
    dir = new File(folder.getPath() + File.separator + "programs" + File.separator + "p100");
    try {
      testing = FolderExplorer.lookupByKey(dir, key);
      if (testing.size() != expectedList.size()) {
        System.out.println("Array size should be 0");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception thrown");
      return false;
    }

    return true;
  }

  /**
   * Called when the program runs
   */
  public static void main(String[] args) {
    System.out.println("testGetContents: " + testGetContents(new File("cs300")));
    System.out.println("testDeepBase: " + testDeepGetContentsBaseCase(new File("cs300")));
    System.out.println("testDeep: " + testDeepListRecursiveCase(new File("cs300")));
    System.out.println("testName: " + testLookupByFileName(new File("cs300")));
    System.out.println("testKey: " + testLookupByKeyBaseCase(new File("cs300")));
  }

}
