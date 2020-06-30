package andrii.romanyshyn.shopsoftserve._4_Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {
    public static final String IMG_DIR = "E:\\Логос\\Project\\IdeaProjects\\SoftServe\\ShopSoftServe\\" +
            "src\\main\\resources\\static\\Tickets\\ShopSavedFiles";

    public String saveFile(String imgB64Format) throws IOException {
        createDir(IMG_DIR);

        String[] data = imgB64Format.split(",");
        String metaInfo = data[0];
        String base64File = data[1];
        String fileName = createFileName(null, getFileExtensionFromMetaInfo(metaInfo));
        Files.write(Paths.get(IMG_DIR, fileName), Base64.getDecoder().decode(base64File.getBytes()));
        return fileName;
    }

    private String createFileName(String fileName, String fileExtension) {
        if (fileName == null) {
            fileName = UUID.randomUUID().toString();
        }
        return String.format("%s.%s", fileName, fileExtension);
    }

    private String getFileExtensionFromMetaInfo(String metaInfo) {
        return metaInfo.split("/")[1].split(";")[0];
    }

    private void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
