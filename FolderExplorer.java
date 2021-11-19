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
import java.util.NoSuchElementException;

/**
 * This class searches through files and explores folders
 */
public class FolderExplorer {

  /**
   * Finds and returns all files within a given directory
   * 
   * @param currentDirectory is the directory to search
   * @returns an ArrayList containing the names of all found files
   * @throws NotDirectoryException if currentDirectory is not a directory
   */
  public static ArrayList<String> getContents(File currentDirectory) throws NotDirectoryException {

    ArrayList<String> contents = new ArrayList<String>();

    // check if currentDirectory is a directory
    if (!currentDirectory.isDirectory())
      throw new NotDirectoryException("Not a directory");

    // add all files to the ArrayList
    for (int i = 0; i < currentDirectory.list().length; i++)
      contents.add(currentDirectory.list()[i]);

    return contents;

  }

  /**
   * Finds and returns all files within a given directory and its sub-directories
   * 
   * @param currentDirectory is the directory to search
   * @returns an ArrayList containing the names of all found files
   * @throws NotDirectoryException if currentDirectory is not a directory
   */
  public static ArrayList<String> getDeepContents(File currentDirectory)
      throws NotDirectoryException {

    ArrayList<String> deepContents = new ArrayList<String>();

    // checks if currentDirectory is a directory
    if (!currentDirectory.isDirectory())
      throw new NotDirectoryException("Not a directory");

    // adds all files to the ArrayList, and if a sub-directory is found, add all files within that
    // directory to the ArrayList
    for (int i = 0; i < currentDirectory.list().length; i++) {
      if (currentDirectory.listFiles()[i].isFile()) // base case
        deepContents.add(currentDirectory.list()[i]);
      else if (currentDirectory.listFiles()[i].isDirectory()) // recursive case
        deepContents.addAll(getDeepContents(currentDirectory.listFiles()[i]));
    }

    return deepContents;

  }

  /**
   * Finds and returns the path to a file with a given name in a directory and its sub-directories
   * 
   * @param currentDirectory is the directory to search
   * @param fileName         is the name of the file to search for
   * @returns a String containing the path to the found file
   * @throws NoSuchElementException if currentDirectory is not a directory or does not exist, if
   *                                fileName is null, or if files are not found
   */
  public static String lookupByName(File currentDirectory, String fileName) {

    // checks if currentDirectory exists and is a directory, and that the file name is not null
    if (!currentDirectory.isDirectory() || !currentDirectory.exists())
      throw new NoSuchElementException("Directory not valid");
    else if (fileName == null)
      throw new NoSuchElementException("File name is null");

    String foundFile = null;

    // checks if each file in the directory has the desired file name, and searches sub-directories
    // for the desired file name. Stores the path and breaks the loop when a file is found
    for (int i = 0; i < currentDirectory.list().length; i++) {
      if (currentDirectory.listFiles()[i].isFile() && currentDirectory.list()[i].equals(fileName)) {
        // base case
        foundFile = currentDirectory.listFiles()[i].getPath();
        break;
      } else if (currentDirectory.listFiles()[i].isDirectory()) {
        try {
          // recursive case
          if (lookupByName(currentDirectory.listFiles()[i], fileName) != null) {
            foundFile = lookupByName(currentDirectory.listFiles()[i], fileName);
            break;
          }
        } catch (NoSuchElementException e) {
          if (e.getMessage().equals("No file matching name found")) {
            // do nothing
          } else {
            e.printStackTrace();
          }
        }
      }
    }

    // ensures that the loop found a file with the desired name and throws an exception if it didn't
    if (foundFile != null)
      return foundFile;
    throw new NoSuchElementException("No file matching name found");

  }

  /**
   * Finds and returns all files containing a given string of text in a directory and its
   * sub-directories
   * 
   * @param currentDirectory is the directory to search
   * @param key              is the string of text to search for
   * @returns an ArrayList containing the names of all files found with the matching key, or an
   *          empty ArrayList if the key is null, no files are found, or currentDirectory is not a
   *          directory or does not exist
   */
  public static ArrayList<String> lookupByKey(File currentDirectory, String key) {

    ArrayList<String> foundFiles = new ArrayList<String>();

    // checks that currentDirectory exists and is a directory and that key is not null
    if (!currentDirectory.isDirectory() || !currentDirectory.exists() || key == null)
      return foundFiles;

    // searches directory and all sub-directories for files with the matching key and adds them to
    // an ArrayList
    for (int i = 0; i < currentDirectory.list().length; i++) {
      if (currentDirectory.listFiles()[i].isFile() && currentDirectory.list()[i].contains(key))
        foundFiles.add(currentDirectory.list()[i]); // base case
      else if (currentDirectory.listFiles()[i].isDirectory())
        foundFiles.addAll(lookupByKey(currentDirectory.listFiles()[i], key)); // recursive case
    }

    return foundFiles;

  }

  /**
   * Finds and returns all files within a given directory that are within a size range
   * 
   * @param currentDirectory is the directory to search
   * @param sizeMin          is the minimum size in bytes
   * @param sizeMax          is the maximum size in bytes
   * @returns an ArrayList containing the names of all found files matching the size constraint, or
   *          an empty ArrayList if currentDirectory is not a directory or does not exist, or if no
   *          matches are found
   */
  public static ArrayList<String> lookupBySize(File currentDirectory, long sizeMin, long sizeMax) {

    ArrayList<String> foundFiles = new ArrayList<String>();

    // checks that currentDirectory is a directory and exists
    if (!currentDirectory.isDirectory() || !currentDirectory.exists())
      return foundFiles;

    // looks through directory and sub-directories for files matching the given size range and adds
    // them to an ArrayList if they do
    for (int i = 0; i < currentDirectory.list().length; i++) {
      if (currentDirectory.listFiles()[i].isFile()
          && currentDirectory.listFiles()[i].length() >= sizeMin
          && currentDirectory.listFiles()[i].length() <= sizeMax) // base case
        foundFiles.add(currentDirectory.list()[i]);
      else if (currentDirectory.listFiles()[i].isDirectory()) // recursive case
        foundFiles.addAll(lookupBySize(currentDirectory.listFiles()[i], sizeMin, sizeMax));
    }

    return foundFiles;

  }

}
