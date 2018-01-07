package sunwou.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class FileUtil {
	
	public static final String IMAGE="image";
	
	public static final String DOCUMENT="document";
	
	public static final String VIDEO="video";
	
	public static final String MUSIC="music";
	

	public static String checkType(String fileName,String type){
		String result=null;
        int profixIndex=fileName.lastIndexOf(".")+1;
        String profix=fileName.substring(profixIndex);
        switch (type) {
		case IMAGE:
			if(profix.equals("jpg")||profix.equals("png")||profix.equals("jpeg")||profix.equals("gif"))
				result=profix;
			break;
		case DOCUMENT:
			if(profix.equals("doc")||profix.equals("xln")||profix.equals("pdf"))
				result=profix;
			break;
		case VIDEO:
			if(profix.equals("mp4")||profix.equals("flv"))
				result=profix;
			break;
		case MUSIC:
			if(profix.equals("mp3")||profix.equals("avi"))
				result=profix;
			break;
		}
		return result;
	}
	
	public static String save(String dirName,String type,HttpServletRequest req,MultipartFile file) throws IllegalStateException, IOException{
		String root=new File(req.getSession().getServletContext().getRealPath("/")).getParent();
		String newFileName=TimeUtil.sdfWithoutInterval.format(new Date())+"."+type;
		String date=TimeUtil.sdfLastHaveDay.format(new Date());
		String dirname=root+dirName+"/"+date;
		File dirs=new File(dirname);
		String filePath=dirname+"/"+newFileName;
		if(!dirs.exists())
		{
			dirs.mkdirs();
		}
		file.transferTo(new File(filePath));
		return dirName+"/"+date+"/"+newFileName;
	}
	
	
	public static void compress(String filePath,float compressd) throws IOException{
		if(checkType(filePath, IMAGE)!=null)
		Thumbnails.of(filePath).scale(compressd).toFile(filePath);
	}
	
	
	public static String fileup(String dirName,HttpServletRequest req,String type,MultipartFile file,Boolean compressFlag,Float compressd) throws IllegalStateException, IOException{
		String profix=null;
		if((profix=checkType(file.getOriginalFilename(),type))!=null)
		{
			String result=save(dirName, profix, req, file);
			if(compressFlag!=null&&compressFlag)
				compress(result,compressd);
			return  result;
		}
		return null;
	}
	
}
