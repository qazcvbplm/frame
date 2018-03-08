package sunwou.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import sunwou.exception.MyException;

public class FileUtil {
	
	public static final String IMAGE="image";
	
	public static final String DOCUMENT="document";
	
	public static final String VIDEO="video";
	
	public static final String MUSIC="music";
	
	private static String root="";
	

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
	
	public static String save(String dirName,String type,HttpServletRequest req,MultipartFile file) {
		root=new File(req.getSession().getServletContext().getRealPath("/")).getParent();
		String datetime=TimeUtil.formatDate(new Date(), TimeUtil.TO_S2);
		String newFileName=datetime+"."+type;
		String date=datetime.substring(0,8);
		String dirname=root+dirName+"/"+date;
		File dirs=new File(dirname);
		String filePath=dirname+"/"+newFileName;
		if(!dirs.exists())
		{
			dirs.mkdirs();
		}
		try {
			file.transferTo(new File(filePath));
		} catch (IllegalStateException e ) {
			throw new MyException("文件上传出错请重试");
		} catch (IOException e) {
			throw new MyException("文件上传出错请重试");
		}
		return dirName+"/"+date+"/"+newFileName;
	}
	
	
	public static void compress(String filePath,float compressd){
			try {
				if(checkType(filePath, IMAGE)!=null){
					File file=new File(root+filePath);
					Thumbnails.of(file).scale(compressd).toFile(file);
				}
			} catch (IOException e) {
				throw new MyException("文件压缩失败");
			}
	}
	
	
	public static String fileup(String dirName,HttpServletRequest req,String type,MultipartFile file,Boolean compressFlag,Float compressd) {
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
