package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.utils.FastdfsUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fast")
public class FastdfsController {
    @PostMapping("/upload")
    public JsonResult upload(@RequestPart(value = "file",required = true) MultipartFile file){
        String upload=null;
        try {
            String fileanme = file.getOriginalFilename();
            System.out.println(fileanme);
            String substring = fileanme.substring(fileanme.lastIndexOf(".")+1);
            upload = FastdfsUtil.upload(file.getBytes(), substring);
            String url ="http://123.207.27.208/"+upload;
            return JsonResult.me().setData(url).setMessage("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return JsonResult.me().setMessage("上传失败").setSuccess(false);
        }
    }
    @DeleteMapping
    public JsonResult delete(@RequestParam(required = true,value = "path") String  path){
        //截取第一个斜杠
        String pathTmp = path.substring(1); // goup1/xxxxx/yyyy
        String groupName =  pathTmp.substring(0, pathTmp.indexOf("/")); //goup1
        String remotePath = pathTmp.substring(pathTmp.indexOf("/")+1);// /xxxxx/yyyy
        System.out.println(groupName);
        System.out.println(remotePath);
        boolean delete = FastdfsUtil.delete(groupName, remotePath);
        if(delete){
            return JsonResult.me().setMessage("上传成功");
        }else {
            return JsonResult.me().setMessage("删除失败").setSuccess(false);
        }
    }
}
