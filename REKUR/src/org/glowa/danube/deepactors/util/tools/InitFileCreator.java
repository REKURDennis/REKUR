package org.glowa.danube.deepactors.util.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class InitFileCreator{

  public static void main(String[] args) throws IOException{
      File orgFile = new File(args[0]);
      // read last line, tokenize, create identical new lines while inc first token
      File newFile = new File(args[0]+"new");
      BufferedReader reader = new BufferedReader(new FileReader(orgFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
      
      String read = "";
      String lastLine = "";
      while(lastLine != null){
          lastLine = reader.readLine();
          if(lastLine != null){
              writer.write(lastLine);
              writer.newLine();
              read = lastLine; 
          }
      }
      System.out.println("Last line: " + read);
      StringTokenizer tokenizer = new StringTokenizer(read,";");
      int lastLineId = Integer.parseInt(tokenizer.nextToken());
      StringBuffer tailOrgLineBuf = new StringBuffer();
      while(tokenizer.hasMoreTokens()) tailOrgLineBuf.append(tokenizer.nextToken()+";");
      String tailOrgLine = tailOrgLineBuf.toString();
      for(int i=0; i<240000; i++){
          lastLineId++;
          String newRow = lastLineId + ";" + tailOrgLine;
          writer.write(newRow);
          writer.newLine();
      }
      reader.close();
      writer.flush();
      writer.close();
      System.exit(0);
  }
}