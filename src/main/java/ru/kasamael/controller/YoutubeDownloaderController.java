package ru.kasamael.controller;

import com.github.kiulian.downloader.YoutubeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kasamael.model.dto.AudioDTO;
import ru.kasamael.model.dto.TrackDownloadRequestDTO;
import ru.kasamael.service.MainService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/downloader")
public class YoutubeDownloaderController {

    private final MainService mainService;

    @PostMapping("/track")
    @ResponseBody
    public void getTrackFromUrl(@RequestBody TrackDownloadRequestDTO trackDownloadRequestDTO) throws IOException, YoutubeException {
        mainService.getTrackFile(trackDownloadRequestDTO);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<AudioDTO> getAllAudio() throws IOException {
        return mainService.getAllAudio();
    }
}
