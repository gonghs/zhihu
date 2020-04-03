package ice.maple.springbootossupload.util;


import com.aliyun.oss.OSSClient;
import ice.maple.springbootossupload.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myconfig.aliyun")
@PropertySource(value = "classpath:/ossconfig.yml",factory = YamlPropertySourceFactory.class)
public class UploadParam {
    private  String accessKey;
    private  String secretKey;
    private  String bucket;
    private  String ossEndPoint;
    private  String callbackUrl;

    private  OSSClient ossClient;

    public OSSClient getOssClient(){
        if (ossClient==null){
            ossClient = new OSSClient(ossEndPoint, accessKey, secretKey);
        }
        return ossClient;
    }

    public  String getOssUrlPrefix(){
        String ossUrlPrefix = "http://"+bucket+"."+ossEndPoint;
        return ossUrlPrefix;
    }

    public  void shutDown(){
        if (ossClient!=null){
            ossClient.shutdown();
            ossClient=null;
        }
    }

    public  String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public  String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public  String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public  String getOssEndPoint() {
        return ossEndPoint;
    }

    public void setOssEndPoint(String ossEndPoint) {
        this.ossEndPoint = ossEndPoint;
    }


    public  void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public  String getCallbackUrl() {
        return callbackUrl;
    }

    public  void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
