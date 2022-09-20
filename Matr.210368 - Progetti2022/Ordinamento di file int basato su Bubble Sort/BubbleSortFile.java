package BubbleSortFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class BubbleSortFile {
	
		public static void main( String[] args ) throws IOException {
			Scanner sc=new Scanner( System.in );
			System.out.print("Nome file = ");
			String nomeFile=sc.nextLine();
			
			File f=new File(nomeFile);
			String name=f.getName();
			String path=f.getAbsolutePath();
			int i=path.indexOf( name );
			String dirPath=path.substring(0,i);

			DataInputStream dis=null; 
			DataOutputStream tmp=null; 
			for(;;) {
				dis=new DataInputStream( new BufferedInputStream( new FileInputStream(nomeFile) ) );
				tmp=new DataOutputStream( new BufferedOutputStream( new FileOutputStream( dirPath+"\\tmp") ) );
				System.out.print("int x = ");
				int size = (int) f.length();
				if(size<= 1) break; //caso in cui il file risulta ordinato
				int x=sc.nextInt(), y=0;
				//if( x==0 ) break;
				int pos = 1;
				boolean scambi=false;
				for(;;) {
					while(pos<size) {
						try {
							y=dis.readInt();
							if( x<y ) {tmp.writeInt(x);
							x = y;
							break; } //caso in cui i due elementi sono ordinati
							else { tmp.writeInt(y); scambi=true; }
						}//caso in cui devo effettuare lo scambio
						catch( EOFException e ) {//in caso di inaspettato riscontro di fine file
							break;
						}
					}//while
				tmp.writeInt(x);
				if( scambi ) { //se ci sono stati scambi dopo la prima passata di Bubble Sort
					tmp.writeInt(y);
					for(;;) {
						try {
							y=dis.readInt(); tmp.writeInt(y);
						}catch( EOFException e ) { break; }
					}//for loop finale
				} //if finale
				//altrimenti il file è ordinato e posso chiuderlo
				dis.close(); tmp.close();
				//rovesciamento di ruoli tra i due file: origine e temporaneo
				f.delete();
				File ft=new File(dirPath+"\\tmp");
				ft.renameTo(f);
			}//for
			}//mostra file
			System.out.println("Contenuto file ordinato:");
			dis=new DataInputStream( new BufferedInputStream( new FileInputStream(nomeFile) ) );
			for(;;) {
				try {
					int x = dis.readInt();
					System.out.println(x);
				}catch(EOFException e) {break;}
			}
			dis.close();
			sc.close();
		}//main
	}//BubbleSortFile


