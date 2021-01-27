package ru.kasamael.controller;

import com.github.kiulian.downloader.YoutubeException;
import ru.kasamael.model.dto.AudioDTO;
import ru.kasamael.model.dto.AudioDownloadRequestDTO;
import ru.kasamael.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/downloader")
public class YoutubeDownloaderController {

    private final MainService mainService;

    @PostMapping("/audio")
    @ResponseBody
    public byte[] getAudioFromUrl(@RequestBody AudioDownloadRequestDTO audioDownloadRequestDTO) throws IOException, YoutubeException {
        return mainService.getAudioFile(audioDownloadRequestDTO);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<AudioDTO> getAllAudio() throws IOException {
        return mainService.getAllAudio();
    }
}
