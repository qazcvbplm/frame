package sunwou.valueobject;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="文件上传对象")
public class FileUpParamObject{

	@ApiModelProperty(value="文件对象")
	@NotEmpty(message="文件为空")
	private MultipartFile file;
	@ApiModelProperty(value="文件类型")
	@NotEmpty(message="文件类型为空")
	private String type;
	@ApiModelProperty(value="是否压缩",dataType="boolean")
	private boolean compress=false;
	@ApiModelProperty(value="压缩系数")
	private float compressd;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

	public float getCompressd() {
		return compressd;
	}

	public void setCompressd(float compressd) {
		this.compressd = compressd;
	}
	
	
}
