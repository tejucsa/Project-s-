/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Process;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 *
 * @author user
 */
public class ServerUpdate {
public JFileChooser filechooser;
public int chooser;
public String path,data;
public StringTokenizer str;
public FileInputStream fis;
public String UpdateData() throws IOException {
	String data="";
    filechooser=new JFileChooser();
    chooser=filechooser.showOpenDialog(filechooser);
    if(chooser==JFileChooser.APPROVE_OPTION){
        path=filechooser.getSelectedFile().getAbsolutePath();
        String fname=filechooser.getSelectedFile().getName();
        str= new StringTokenizer(fname,".");
        str.nextToken();
        String fr=str.nextToken();
        if(fr.equals("txt") ||fr.equals("java") ||fr.equals("html")||fr.equals("jsp")){
            try{
            	FileInputStream fis1 = new FileInputStream("system.properties");
                Properties p = new Properties();
                p.load(fis1);
                String pathtostore=p.getProperty("Path");
                //System.out.println(pathtostore);
                FileInputStream fis=new FileInputStream(path);
                byte by[]=new byte[fis.available()];
                fis.read(by);
                data=new String(by);
                FileOutputStream fos=new FileOutputStream(pathtostore+"/ServerData/"+fname);
                fos.write(by);
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
   }
    return data;
}

}
