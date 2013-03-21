import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class NumberOfCode{
	/**
	 * 计算整个目录包含的源代码文件的代码总行数，注释除外
	 * @param dirPath
	 * @return
	 */
	public static long numberOfCodeInDirectoryNoComments(String dirPath){
		long number=0;
		File dir=new File(dirPath);
		if(!dir.exists()){
			System.out.println("directory is not exist!");
			return 0;
		}
		Queue<File> queue=new LinkedBlockingQueue<File>();
		boolean commentFlag=false;
		queue.add(dir.getAbsoluteFile());
		while(!queue.isEmpty()){
			File currentFile=queue.poll();
			System.out.println(currentFile.toString());
			if(currentFile.isDirectory()){
				String[] lists=currentFile.list();
				for (String string : lists) {
					queue.add(new File(currentFile.getAbsolutePath()+File.separator+string));
				}
			}else{
				BufferedReader br = null;
				try {
					br=new BufferedReader(new InputStreamReader(new FileInputStream(currentFile)));
					String aLine=br.readLine();
					while(aLine!=null){
						aLine=aLine.trim();
						if(aLine.length()!=0){
							if(!commentFlag){
								if(aLine.startsWith("/*")){
									commentFlag=true;
									if(aLine.endsWith("*/"))
										commentFlag=false;
								}else{/**
								#bug# this program cann't ignore this style comment
								*/
									if(!aLine.startsWith("//"))
										number++;
								}
							}else{
								if(aLine.startsWith("/*")){
									commentFlag=true;
									if(aLine.endsWith("*/"))
										commentFlag=false;
								}else if(aLine.endsWith("*/")){
									commentFlag=false;
								}
							}
						}
						aLine=br.readLine();
					}
				} catch (FileNotFoundException e) {
					System.out.println("File is not exist!");
				}catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return number;
	}
	
	/**
	 * 计算单个源代码文件的代码行数,注释除外
	 * @param file
	 * @return
	 */
	public static long numberOfCodeInFileNoComments(String file){
		long number=0;
		BufferedReader br = null;
		boolean commentFlag=false;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String aLine=br.readLine();
			while(aLine!=null){
				aLine=aLine.trim();
				if(aLine.length()!=0){
					if(!commentFlag){
						if(aLine.startsWith("/*")){
							commentFlag=true;
							if(aLine.endsWith("*/"))
								commentFlag=false;
						}else{/**
						#bug# this program cann't ignore this style comment
						*/
							if(!aLine.startsWith("//"))
								number++;
						}
					}else{
						if(aLine.startsWith("/*")){
							commentFlag=true;
							if(aLine.endsWith("*/"))
								commentFlag=false;
						}else if(aLine.endsWith("*/")){
							commentFlag=false;
						}
					}
				}
				aLine=br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File is not exist!");
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return number;
	}
	
	/**
	 * 计算整个目录包含的源代码文件的代码总行数，包含注释
	 * @param dirPath
	 * @return
	 */
	public static long numberOfCodeInDirectory(String dirPath){
		long number=0;
		File dir=new File(dirPath);
		if(!dir.exists()){
			System.out.println("directory is not exist!");
			return 0;
		}
		Queue<File> queue=new LinkedBlockingQueue<File>();
		queue.add(dir.getAbsoluteFile());
		while(!queue.isEmpty()){
			File currentFile=queue.poll();
			System.out.println(currentFile.toString());
			if(currentFile.isDirectory()){
				String[] lists=currentFile.list();
				for (String string : lists) {
					queue.add(new File(currentFile.getAbsolutePath()+File.separator+string));
				}
			}else{
				BufferedReader br = null;
				try {
					br=new BufferedReader(new InputStreamReader(new FileInputStream(currentFile)));
					String aLine=br.readLine();
					while(aLine!=null){
						if(aLine!=""){
							number++;
						}
						aLine=br.readLine();
					}
				} catch (FileNotFoundException e) {
					System.out.println("File is not exist!");
				}catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return number;
	}

	/**
	 * 计算单个源代码文件的代码行数，包含注释
	 * @param file
	 * @return
	 */
	public static long numberOfCodeInFile(String file){
		long number=0;
		BufferedReader br = null;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String aLine=br.readLine();
			while(aLine!=null){
				if(aLine!=""){
					number++;
				}
				aLine=br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File is not exist!");
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return number;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		long number=0l;
		if(args.length==1){
			String path=args[0];
			File file=new File(path).getAbsoluteFile();
			if(file.isDirectory()){
				number=numberOfCodeInDirectoryNoComments(path);	
			}else{
				number=numberOfCodeInFileNoComments(path);	
			}
		}else if(args.length==2){
			String flag=args[0];
			String path=args[1];
			if(!flag.equals("-c")){
				System.out.println("This is small tool used for counting number of source codes");
				System.out.println("Usage:java NumberOfCode [ dir | file ]");
				System.out.println("options include:");
				System.out.println("    -c    include comments");
				return;
			}
			File file=new File(path).getAbsoluteFile();
			if(file.isDirectory()){
				number=numberOfCodeInDirectory(path);	
			}else{
				number=numberOfCodeInFile(path);	
			}
		}else{
			System.out.println("This is small tool used for counting number of source codes");
			System.out.println("Usage:java NumberOfCode [-options]  dir | file");
			System.out.println("options include:");
			System.out.println("    -c    include comments");
			return;
		}
		System.out.println("number of source codes: "+number+" lines");
	}
}