package avada.media.myhouse24_admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TestController {

  //  @GetMapping("about-us")
    public String showTest(){
        return "about-us";
    }

    @RestController
    public class FileUploadController {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @PostMapping("about-us")
   // public String postTest(@RequestParam("doc") MultipartFile doc){
        public String upload(MultipartFile uploadFile, HttpServletRequest req){
            String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
            String format = sdf.format(new Date());
            File folder = new File(realPath + format);
            if (!folder.isDirectory()){ // Если текущий каталог не существует
                folder.mkdirs(); // Создать новый каталог
            }
            String oldName = uploadFile.getOriginalFilename(); // Старое имя
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length()); //Новое имя
            try {
                uploadFile.transferTo(new File(folder,newName)); // сохранить документ
                String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/" + format + newName;
                return "redirect:/about-us";
                //  return filePath; // Возвращаемся к пути доступа сгенерированного загруженного файла
            } catch (Exception e){
                e.printStackTrace();
            }
            return "Загрузка не удалась!";
        }
    }
}
