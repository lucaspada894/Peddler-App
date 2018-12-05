package org.springframework.samples.peddler.images;



import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.peddler.user.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileController {
	

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @SuppressWarnings("resource")
	@RequestMapping(path= "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("fileId") String fileId, @RequestParam("fileType") String fileType, @RequestParam("file") String file) throws FileNotFoundException, IOException {
    	
    	String newFile = file.replaceAll(" ", "+");
        logger.info(fileId);
    	
    	byte[] imageByte=Base64.decodeBase64(newFile);
    	ByteArrayInputStream temp = new ByteArrayInputStream(imageByte);
    	BufferedImage bufferedImage = ImageIO.read(temp);
    	
    	
    	File outputfile = new File("/var/www/html/uploads" + fileId + "." + fileType);
    	ImageIO.write(bufferedImage, fileType , outputfile);
        logger.info(newFile);
        
        
        return newFile;
        
    }


    
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public @ResponseBody ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    
}